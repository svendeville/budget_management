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
import com.snv.guard.AuthenticationService;
import com.snv.guard.Profile;
import java.util.Arrays;
import java.util.List;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {
    
    private static final String CONTROLLER_URL = "http://localhost:8080/api/users";
    private final ObjectMapper mapper = new ObjectMapper();
    private MockMvc mockMvc;
    @InjectMocks
    private UsersController usersController;
    @Mock
    private UserService userService;
    @Mock
    private AuthenticationService authenticationService;
    
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    private User user;
    private Credential credential;
    
    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.usersController)
                .build();
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
    public void should_return_BadRequest_when_call_without_user() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(CONTROLLER_URL)
        .content(this.mapper.writeValueAsString(new User()))
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }
    
    @Test
    public void should_return_BadRequest_when_call_with_empty_user_values() throws Exception {
        User emptyUser = new User();
        
        emptyUser.setFirstName("");
        emptyUser.setLastName("");
        emptyUser.setEmail("");
        emptyUser.setLogin("");
        emptyUser.setPassword("");
        
        mockMvc.perform(MockMvcRequestBuilders.post(CONTROLLER_URL)
        .content(this.mapper.writeValueAsString(emptyUser))
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }
    
    @Test
    public void should_return_user_with_information_on_create() throws Exception {
        when(this.userService.create(Matchers.any(User.class))).thenReturn(this.user);
        
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(CONTROLLER_URL + "/create")
        .content(this.mapper.writeValueAsString(user))
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andReturn();
        
        verify(this.userService, times(1)).create(Matchers.any(User.class));
        
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
    
    @Test
    public void should_return_user_with_information_on_update() throws Exception {
        when(this.userService.put(Matchers.any(User.class))).thenReturn(this.user);
        
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put(CONTROLLER_URL)
        .content(this.mapper.writeValueAsString(user))
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andReturn();
        
        verify(this.userService, times(1)).put(Matchers.any(User.class));
        
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
    
    @Test
    public void should_return_list_of_user_on_getall() throws Exception {
        List<User> users = Arrays.asList(this.user);
        when(this.userService.getAll()).thenReturn(users);
        
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(CONTROLLER_URL)
        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andReturn();
        
        verify(this.userService, times(1)).getAll();
        
        
        assertFalse(result.getResponse().getContentAsString().isEmpty());
        List<User> actual = objectMapper.readValue(result.getResponse().getContentAsString(), List.class);
        
        assertNotNull("List of users can not be null !", actual);
        assertFalse("List of users can not be empty", actual.isEmpty());
    }
    
    @Test
    public void should_return_user_on_get() throws Exception {
        when(this.userService.get(Matchers.anyLong())).thenReturn(this.user);
        
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(CONTROLLER_URL + "/1")
        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andReturn();
        
        verify(this.userService, times(1)).get(Matchers.anyLong());
        
        
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
    
    @Test
    public void should_return_boolean_true_on_delete_success() throws Exception {
        when(this.userService.delete(Matchers.any(User.class))).thenReturn(Boolean.TRUE);
        
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete(CONTROLLER_URL)
        .content(this.mapper.writeValueAsString(user))
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andReturn();
        
        verify(this.userService, times(1)).delete(Matchers.any(User.class));
        
        
        Boolean actual = objectMapper.readValue(result.getResponse().getContentAsString(), Boolean.class);
        assertNotNull("result on delete can not be null !", actual);
        assertTrue("result on delete is not true !", actual);
    }
    
    @Test
    public void should_return_user_on_login () throws Exception {
        when(this.authenticationService.authenticate(Matchers.any(Credential.class), Matchers.any(HttpServletResponse.class))).thenReturn(this.user);
        
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(CONTROLLER_URL + "/login")
        .content(this.mapper.writeValueAsString(user))
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andReturn();
        
        verify(this.authenticationService, times(1)).authenticate(Matchers.any(Credential.class), Matchers.any(HttpServletResponse.class));
        
        
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
