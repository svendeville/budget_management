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
package com.snv.user;

import com.snv.common.CrudService;
import com.snv.exceptions.InvalidCredentialException;
import com.snv.guard.Profile;
import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import static org.mockito.Mockito.verify;

/**
 * Class To test the User service
 */
@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
    
    @InjectMocks
    private final UserService userService = new UserServiceImpl();
    
    @Mock
    private CrudService<User> userCrudService;
    
    private User user;
    private Credential credential;
    
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
    public void should_return_user_with_information() {
        Mockito.when(this.userCrudService.create(Matchers.any(User.class))).thenReturn(this.user);
        User actual = this.userService.create(this.user);
        verify(this.userCrudService, Mockito.times(1)).create(Matchers.any(User.class));
        this.match(actual);
    }
    
    @Test
    public void should_return_user_when_call_get_by_id() {
        Mockito.when(this.userCrudService.get(Matchers.anyLong())).thenReturn(this.user);
        User actual = this.userService.get(Long.valueOf("1"));
        verify(this.userCrudService, Mockito.times(1)).get(Matchers.anyLong());
        this.match(actual);
    }
    
    @Test
    public void should_return_user_list_when_call_getAll() {
        Mockito.when(this.userCrudService.getAll()).thenReturn(Arrays.asList(this.user));
        List<User> actual = this.userService.getAll();
        verify(this.userCrudService, Mockito.times(1)).getAll();
        assertFalse(actual.isEmpty());
        assertTrue(actual.size() == 1);
        this.match(actual.get(0));
    }
    
    @Test
    public void should_return_udpated_user_when_call_put() {
        Mockito.when(this.userCrudService.update(Matchers.any(User.class))).thenReturn(this.user);
        User actual = this.userService.put(this.user);
        verify(this.userCrudService, Mockito.times(1)).update(Matchers.any(User.class));
        this.match(actual);
    }
    
    @Test
    public void should_return_true_when_delete_user () {
        Mockito.when(this.userCrudService.delete(Matchers.any(User.class))).thenReturn(Boolean.TRUE);
        boolean actual = this.userService.delete(this.user);
        verify(this.userCrudService, Mockito.times(1)).delete(Matchers.any(User.class));
        assertTrue(actual);
    }
    
    @Test(expected = InvalidCredentialException.class)
    public void should_throw_InvalidCredentialException_when_login_with_wrong_login() {
        Mockito.when(this.userCrudService.getAll()).thenReturn(null);
        this.userService.byLogin(this.credential);
        verify(this.userCrudService, Mockito.times(1)).getAll();
    }
    
    @Test
    public void should_user_when_call_login_with_rigth_credentials () {
        Mockito.when(this.userCrudService.getAll()).thenReturn(Arrays.asList(this.user));
        User actual = this.userService.byLogin(this.credential);
        verify(this.userCrudService, Mockito.times(1)).getAll();
        this.match(actual);
        assertFalse(actual.getAuthorities().isEmpty());
    }
    
    private void match(User actual) {
        assertNotNull("user created can not be null !", actual);
        assertEquals("users not same !", actual, this.user);
        assertEquals("user's first name is not same", actual.getFirstName(), this.user.getFirstName());
        assertEquals("user's last name is not same", actual.getLastName(), this.user.getLastName());
        assertEquals("user's email name is not same", actual.getEmail(), this.user.getEmail());
        assertEquals("user's login name is not same", actual.getLogin(), this.user.getLogin());
        assertEquals("user's password name is not same", actual.getPassword(), this.user.getPassword());
    }
}
