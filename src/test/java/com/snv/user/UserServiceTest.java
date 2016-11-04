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
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Class To test the User service
 */
@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
    
    @InjectMocks
    private UserService userService = new UserServiceImpl();
    
    @Mock
    private CrudService<User> userCrudService;
    
    private User user;
    
    @Before
    public void setUp() {
        this.user = new User();
        this.user.setFirstName("toto");
        this.user.setLastName("tata");
        this.user.setEmail("toto.tata@lol.com");
        this.user.setLogin("test");
        this.user.setPassword("pass");
    }
    
    @Test
    public void should_return_user_with_information() {
        Mockito.when(this.userCrudService.create(Matchers.any(User.class))).thenReturn(this.user);
        User user = this.userService.create(this.user);
        assertNotNull("user created can not be null !", user);
        assertEquals("users not same !", user, this.user);
        assertEquals("user's first name is not same", user.getFirstName(), this.user.getFirstName());
        assertEquals("user's last name is not same", user.getLastName(), this.user.getLastName());
        assertEquals("user's email name is not same", user.getEmail(), this.user.getEmail());
        assertEquals("user's login name is not same", user.getLogin(), this.user.getLogin());
        assertEquals("user's password name is not same", user.getPassword(), this.user.getPassword());
    }
}
