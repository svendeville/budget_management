package com.snv.guard;

import com.snv.guard.hmac.*;
import com.snv.user.Credential;
import com.snv.user.User;
import com.snv.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Authentication service
 * Created by Michael DESIGAUD on 15/02/2016.
 */
@Service
public class AuthenticationService {

    public static final String JWT_APP_COOKIE = "hmac-app-jwt";
    public static final String CSRF_CLAIM_HEADER = "X-HMAC-CSRF";
    public static final String JWT_CLAIM_LOGIN = "login";

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    /**
     * Authenticate a user in Spring Security
     * The following headers are set in the response:
     * - X-TokenAccess: JWT
     * - X-Secret: Generated secret in base64 using SHA-256 algorithm
     * - WWW-Authenticate: Used algorithm to encode secret
     * The authenticated user in set ine the Spring Security context
     * The generated secret is stored in a static list for every user
     * @see MockUsers
     * @param credential credentials
     * @param response http response
     * @return UserDTO instance
     * @throws HmacException
     */
    public User authenticate(Credential credential, HttpServletResponse response) throws HmacException {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(credential.getLogin(),credential.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        //Retrieve security user after authentication
        User user = userService.byLogin(credential);

        //Get Hmac signed token
        String csrfId = UUID.randomUUID().toString();
        Map<String,String> customClaims = new HashMap<>();
        customClaims.put(HmacSigner.ENCODING_CLAIM_PROPERTY, HmacUtils.HMAC_SHA_256);
        customClaims.put(JWT_CLAIM_LOGIN, credential.getLogin());
        customClaims.put(CSRF_CLAIM_HEADER, csrfId);

        //Generate a random secret
        String privateSecret = HmacSigner.generateSecret();
        String publicSecret = HmacSigner.generateSecret();

        // Jwt is generated using the private key
        HmacToken hmacToken = HmacSigner.getSignedToken(privateSecret,String.valueOf(user.getId()), HmacSecurityFilter.JWT_TTL,customClaims);

        // Set public secret and encoding in headers
        response.setHeader(HmacUtils.X_SECRET, publicSecret);
        response.setHeader(HttpHeaders.WWW_AUTHENTICATE, HmacUtils.HMAC_SHA_256);
        response.setHeader(CSRF_CLAIM_HEADER, csrfId);
        response.setHeader(JWT_APP_COOKIE, hmacToken.getJwt());

        //Set JWT as a cookie

        user.setPrivateSecret(privateSecret);
        user.setPublicSecret(publicSecret);
        user.setCsrfId(csrfId);
        user.setJwt(hmacToken.getJwt());

        userService.put(user);

        return user;
    }

    /**
     * Logout a user
     * - Clear the Spring Security context
     * - Remove the stored UserDTO secret
     */
    public void logout(){
        if(SecurityContextHolder.getContext().getAuthentication() != null
                && SecurityContextHolder.getContext().getAuthentication().isAuthenticated())
        {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            user.setPrivateSecret(null);
            user.setPublicSecret(null);
            userService.put(user);
            SecurityContextHolder.getContext().setAuthentication(null);
        }
    }

    /**
     * Authentication for every request
     * - Triggered by every http request except the authentication
     * @see fr.redfroggy.hmac.configuration.security.XAuthTokenFilter
     * Set the authenticated user in the Spring Security context
     * @param user user
     */
    public void tokenAuthentication(User user){
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }
}
