package com.snv.guard.hmac;

import com.snv.guard.AuthenticationService;
import com.snv.guard.WrappedRequest;
import org.apache.commons.io.Charsets;
import org.springframework.util.Assert;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Hmac verification filter
 * Created by Michael DESIGAUD on 16/02/2016.
 */
public class HmacSecurityFilter extends GenericFilterBean {

    private static final Log LOG = LogFactory.getLog(HmacSecurityFilter.class);

    public static final Integer JWT_TTL = 20;

    private final HmacRequester hmacRequester;

    public HmacSecurityFilter(HmacRequester hmacRequester) {
        this.hmacRequester = hmacRequester;
    }

    /**
     * Find a cookie which contain a JWT
     * @param request current http request
     * @return Cookie found, null otherwise
     */
    private Cookie findJwtCookie(HttpServletRequest request) {
        if(request.getCookies() == null || request.getCookies().length == 0) {
            return null;
        }
        for(Cookie cookie : request.getCookies()) {
            if(cookie.getName().contains(AuthenticationService.JWT_APP_COOKIE)) {
                return cookie;
            }
        }
        return null;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        WrappedRequest wrappedRequest = new WrappedRequest(request);

        try {
            Assert.notNull(hmacRequester, "hmacRequester must not be null");

            if (!hmacRequester.canVerify(request)) {
                LOG.info("HmacSecurityFilter :: Authorized");
                filterChain.doFilter(wrappedRequest, response);
            } else {
                //Get Authentication header
                Cookie jwtCookie = findJwtCookie(request);
                Assert.notNull(jwtCookie,"No jwt cookie found");

                String jwt = jwtCookie.getValue();

                if (jwt == null || jwt.isEmpty()) {
                    throw new HmacException("The JWT is missing from the '" + HmacUtils.AUTHENTICATION + "' header");
                }

                String digestClient = request.getHeader(HmacUtils.X_DIGEST);

                if (digestClient == null || digestClient.isEmpty()) {
                    throw new HmacException("The digest is missing from the '" + HmacUtils.X_DIGEST + "' header");
                }

                //Get X-Once header
                String xOnceHeader = request.getHeader(HmacUtils.X_ONCE);

                if (xOnceHeader == null || xOnceHeader.isEmpty()) {
                    throw new HmacException("The date is missing from the '" + HmacUtils.X_ONCE + "' header");
                }

                String url = request.getRequestURL().toString();
                if (request.getQueryString() != null) {
                    url += "?" + URLDecoder.decode(request.getQueryString(), Charsets.UTF_8.displayName());
                }

                String encoding = HmacSigner.getJwtClaim(jwt, HmacSigner.ENCODING_CLAIM_PROPERTY);
                String iss = HmacSigner.getJwtIss(jwt);

                //Get public secret key
                String secret = hmacRequester.getPublicSecret(iss);
                Assert.notNull(secret, "Secret key cannot be null");

                String message;
                if ("POST".equals(request.getMethod()) || "PUT".equals(request.getMethod()) || "PATCH".equals(request.getMethod())) {
                    message = request.getMethod().concat(wrappedRequest.getBody()).concat(url).concat(xOnceHeader);
                } else {
                    message = request.getMethod().concat(url).concat(xOnceHeader);
                }

                //Digest are calculated using a public shared secret
                String digestServer = HmacSigner.encodeMac(secret, message, encoding);
                LOG.info("HMAC JWT: " + jwt);
                LOG.info("HMAC url digest: " + url);
                LOG.info("HMAC Message server: " + message);
                LOG.info("HMAC Secret server: " + secret);
                LOG.info("HMAC Digest server: " + digestServer);
                LOG.info("HMAC Digest client: " + digestClient);

                if (digestClient.equals(digestServer)) {
                    LOG.info("Request is valid, digest are matching");

                    Map<String,String> customClaims = new HashMap<>();
                    customClaims.put(HmacSigner.ENCODING_CLAIM_PROPERTY, HmacUtils.HMAC_SHA_256);
                    HmacToken hmacToken = HmacSigner.getSignedToken(secret,String.valueOf(iss),JWT_TTL,customClaims);
                    response.setHeader(HmacUtils.X_TOKEN_ACCESS, hmacToken.getJwt());

                    filterChain.doFilter(wrappedRequest, response);
                } else {
                    LOG.info("Server message: " + message);
                    throw new HmacException("Digest are not matching! Client: " + digestClient + " / Server: " + digestServer);
                }
            }

        } catch(HmacException | IllegalArgumentException e) {
            LOG.info("Error while generating hmac token", e);
            response.setStatus(403);
            response.getWriter().write(e.getMessage());
        }
    }
}