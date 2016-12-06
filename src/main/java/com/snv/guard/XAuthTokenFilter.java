package com.snv.guard;

import com.snv.guard.hmac.HmacException;
import com.snv.guard.hmac.HmacRequester;
import com.snv.guard.hmac.HmacSigner;
import com.snv.user.User;
import com.snv.user.UserService;
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
import java.text.ParseException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Auth token filter
 * Created by Michael DESIGAUD on 14/02/2016.
 */
public class XAuthTokenFilter extends GenericFilterBean {

    private static final Log LOG = LogFactory.getLog(XAuthTokenFilter.class);
    private final AuthenticationService authenticationService;
    private final HmacRequester hmacRequester;

    @Autowired
    private UserService userService;

    public XAuthTokenFilter(AuthenticationService authenticationService, HmacRequester hmacRequester) {
       this.hmacRequester = hmacRequester;
       this.authenticationService = authenticationService;
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

        if (!hmacRequester.canVerify(request)){
            filterChain.doFilter(request, response);
        } else {

            try {

                Cookie jwtCookie = findJwtCookie(request);
                Assert.notNull(jwtCookie,"No jwt cookie found");

                String jwt = jwtCookie.getValue();
                String login = HmacSigner.getJwtClaim(jwt, AuthenticationService.JWT_CLAIM_LOGIN);
                Assert.notNull(login,"No login found in JWT");

                //Get user from cache
                User user = userService.byLogin(login);
                Assert.notNull(user,"No user found with login: "+login);

                Assert.isTrue(HmacSigner.verifyJWT(jwt,user.getPrivateSecret()),"The Json Web Token is invalid");

                Assert.isTrue(!HmacSigner.isJwtExpired(jwt),"The Json Web Token is expired");

                String csrfHeader = request.getHeader(AuthenticationService.CSRF_CLAIM_HEADER);
                Assert.notNull(csrfHeader,"No csrf header found");

                String jwtCsrf = HmacSigner.getJwtClaim(jwt, AuthenticationService.CSRF_CLAIM_HEADER);
                Assert.notNull(jwtCsrf,"No csrf claim found in jwt");

                //Check csrf token (prevent csrf attack)
                Assert.isTrue(jwtCsrf.equals(csrfHeader));

                this.authenticationService.tokenAuthentication(user);
                filterChain.doFilter(request,response);
            } catch (HmacException | ParseException e) {
                LOG.info("Forbidden", e);
                response.setStatus(403);
            }
        }

    }
}
