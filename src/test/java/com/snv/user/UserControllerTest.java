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

import com.fasterxml.jackson.databind.ObjectMapper;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {
    
    private static final String CONTROLLER_URL = "http://localhost:8080/users";
    private final ObjectMapper mapper = new ObjectMapper();
    private MockMvc mockMvc;
    @InjectMocks
    private UsersController usersController;
    @Mock
    private UserService userService;
    
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    private User user;
    
    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.usersController)
                .build();
        this.user = new User();
        this.user.setFirstName("toto");
        this.user.setLastName("tata");
        this.user.setEmail("toto.tata@lol.com");
        this.user.setLogin("test");
        this.user.setPassword("pass");
    }
    
    @Test
    public void should_return_BadRequest_when_call_without_user() throws Exception {
        User user = new User();
        mockMvc.perform(MockMvcRequestBuilders.post(CONTROLLER_URL)
        .content(this.mapper.writeValueAsString(user))
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }
    
    @Test
    public void should_return_BadRequest_when_call_with_empty_user_values() throws Exception {
        User user = new User();
        
        user.setFirstName("");
        user.setLastName("");
        user.setEmail("");
        user.setLogin("");
        user.setPassword("");
        
        mockMvc.perform(MockMvcRequestBuilders.post(CONTROLLER_URL)
        .content(this.mapper.writeValueAsString(user))
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }
    
    @Test
    public void should_return_user_with_information() throws Exception {
        when(this.userService.create(Matchers.any(User.class))).thenReturn(this.user);
        
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(CONTROLLER_URL)
        .content(this.mapper.writeValueAsString(user))
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andReturn();
        
        assertFalse(result.getResponse().getContentAsString().isEmpty());
        User actual = objectMapper.readValue(result.getResponse().getContentAsString(), User.class);
        assertNotNull("user created can not be null !", actual);
        assertEquals("users not same !", actual, this.user);
        assertEquals("user's first name is not same", actual.getFirstName(), this.user.getFirstName());
        assertEquals("user's last name is not same", actual.getLastName(), this.user.getLastName());
        assertEquals("user's email name is not same", actual.getEmail(), this.user.getEmail());
        assertEquals("user's login name is not same", actual.getLogin(), this.user.getLogin());
        assertEquals("user's password name is not same", actual.getPassword(), this.user.getPassword());
    }
}
