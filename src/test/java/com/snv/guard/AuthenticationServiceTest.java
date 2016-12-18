/*
 * @Copyright 2016 Sylvain Vendeville.
 * This file is part of Budget Managment.
 * Budget Managment is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Budget Managment is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with MesComptes. If not, see <http://www.gnu.org/licenses/>.
 */
package com.snv.guard;

import static com.snv.guard.AuthenticationService.CSRF_CLAIM_HEADER;
import com.snv.guard.hmac.HmacException;
import com.snv.guard.hmac.HmacUtils;
import com.snv.user.Credential;
import com.snv.user.User;
import com.snv.user.UserService;
import io.undertow.server.HttpServerExchange;
import io.undertow.servlet.spec.HttpServletResponseImpl;
import io.undertow.servlet.spec.ServletContextImpl;
import javax.servlet.http.HttpServletResponse;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@RunWith(MockitoJUnitRunner.class)
public class AuthenticationServiceTest {
    
    @InjectMocks
    private final AuthenticationService authenticationService = new AuthenticationService();
    
    @Mock
    private AuthenticationManager authenticationManager;
    
    @Mock
    private UserService userService;
    
    private User user;
    private Credential credential;
    private Authentication authentication;
    
    @Before
    public void setUp() {
        this.user = new User();
        this.user.setFirstName("toto");
        this.user.setLastName("tata");
        this.user.setEmail("toto.tata@lol.com");
        this.user.setProfile(Profile.ADMIN);
        this.user.setLogin("test");
        this.user.setPassword("pass");
        this.credential = new Credential();
        this.credential.setLogin("test");
        this.credential.setPassword("pass");
    }
    
    @Test
    public void should_return_user_with_security_information_when_authenticate() throws HmacException {
        when(this.authenticationManager.authenticate(Matchers.any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(this.authentication);
        when(this.userService.byLogin(Matchers.any(Credential.class))).thenReturn(this.user);
        HttpServletResponse response = new MockHttpServletResponse();
        User actual = this.authenticationService.authenticate(this.credential, response);
        
        assertEquals(this.user, actual);
        assertTrue(response.getHeader(HmacUtils.X_SECRET) != null);
        assertEquals(actual.getPublicSecret(), response.getHeader(HmacUtils.X_SECRET));
        assertTrue(response.getHeader(HttpHeaders.WWW_AUTHENTICATE) != null);
        assertEquals(response.getHeader(HttpHeaders.WWW_AUTHENTICATE), HmacUtils.HMAC_SHA_256);
        assertTrue(response.getHeader(CSRF_CLAIM_HEADER) != null);
    }
    
    @Test
    public void should_clear_spring_security_context_when_logout() {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(credential.getLogin(),credential.getPassword());
        this.authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(this.authentication);

        this.authenticationService.logout();
        
        assertTrue(SecurityContextHolder.getContext().getAuthentication() == null);
    }
}
