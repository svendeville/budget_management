/*
 * @Copyright 2016 Sylvain Vendeville.
 * This file is part of MesComptes.
 * MesComptes is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * MesComptes is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with MesComptes. If not, see <http://www.gnu.org/licenses/>.
 */
package com.snv.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.snv.user.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
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
    
    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.usersController)
                .build();
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
}
