package com.snv.guard;

import com.snv.guard.hmac.HmacRequester;
import com.snv.guard.hmac.HmacSecurityConfigurer;
import com.snv.user.User;
import com.snv.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.InMemoryUserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


/**
 * Security configuration
 * Created by Michael DESIGAUD on 14/02/2016.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private HmacRequester hmacRequester;
    
    @Autowired
    private UserService userService;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/**/*.{js}")
                .antMatchers("/node_modules/**")
                .antMatchers("/assets/**")
                .antMatchers("*.{ico}")
                .antMatchers("/**/api-docs*")
                .antMatchers("/**/*.{html}");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/api/users/login").anonymous()
                    .antMatchers("/").anonymous()
                .antMatchers("/api/users/create").anonymous()
                    .antMatchers("/favicon.ico").anonymous()
                    .antMatchers("/api/**").authenticated()
                .and()
                .csrf()
                    .disable()
                    .headers()
                    .frameOptions().disable()
                .and()
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .logout()
                    .permitAll()
                .and()
                    .apply(authTokenConfigurer())
                .and()
                    .apply(hmacSecurityConfigurer());
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        InMemoryUserDetailsManagerConfigurer configurer = auth
                .inMemoryAuthentication()
                    .passwordEncoder(passwordEncoder());

        userService.getAll().forEach((user) -> {
            configurer.withUser(user.getLogin())
                    .password(passwordEncoder().encode(user.getPassword()))
                    .roles(user.getProfile().name());
        });
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    private HmacSecurityConfigurer hmacSecurityConfigurer(){
        return new HmacSecurityConfigurer(hmacRequester);
    }

    private XAuthTokenConfigurer authTokenConfigurer(){
        return new XAuthTokenConfigurer(authenticationService, hmacRequester);
    }
}
