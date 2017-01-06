package com.snv.guard;

import com.snv.guard.hmac.HmacRequester;
import com.snv.user.UserService;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Auth token configurer
 * Created by Michael DESIGAUD on 14/02/2016.
 */
public class XAuthTokenConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final AuthenticationService authenticationService;
    private final HmacRequester hmacRequester;
    private final UserService userService;

    public XAuthTokenConfigurer(AuthenticationService authenticationService, HmacRequester hmacRequester, UserService userService) {
        this.authenticationService = authenticationService;
        this.hmacRequester = hmacRequester;
        this.userService = userService;
    }

    @Override
    public void configure(HttpSecurity builder) throws Exception {
        XAuthTokenFilter xAuthTokenFilter = new XAuthTokenFilter(authenticationService, hmacRequester, userService);

        //Trigger this filter before SpringSecurity authentication validator
        builder.addFilterBefore(xAuthTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
