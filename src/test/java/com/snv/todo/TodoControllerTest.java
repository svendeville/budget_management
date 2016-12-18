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
package com.snv.todo;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import java.util.List;
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
public class TodoControllerTest {
    
    private static final String CONTROLLER_URL = "http://localhost:8080/api/todos";
    private final ObjectMapper mapper = new ObjectMapper();
    private MockMvc mockMvc;
    @InjectMocks
    private TodoController todoController;
    @Mock
    private TodoService todoService;
    
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    private Todo todo;
    
    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.todoController)
                .build();
        this.todo = new Todo();
        this.todo.setText("my test todo");
        this.todo.setFinished(true);
        this.todo.setColor("blue");
    }
    
    @Test
    public void should_return_BadRequest_when_call_without_todo() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(CONTROLLER_URL)
        .content(this.mapper.writeValueAsString(new Todo()))
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }
    
    @Test
    public void should_return_BadRequest_when_call_with_empty_Todo_values() throws Exception {
        Todo emptyTodo = new Todo();
        
        emptyTodo.setText("");
        
        mockMvc.perform(MockMvcRequestBuilders.post(CONTROLLER_URL)
        .content(this.mapper.writeValueAsString(emptyTodo))
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }
    
    @Test
    public void should_return_todo_with_information_on_create() throws Exception {
        when(this.todoService.create(Matchers.any(Todo.class))).thenReturn(this.todo);
        
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(CONTROLLER_URL)
        .content(this.mapper.writeValueAsString(todo))
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andReturn();
        
        verify(this.todoService, times(1)).create(Matchers.any(Todo.class));
        
        assertFalse(result.getResponse().getContentAsString().isEmpty());
        Todo actual = objectMapper.readValue(result.getResponse().getContentAsString(), Todo.class);
        assertNotNull("todo created can not be null !", actual);
        assertEquals("todos not same !", actual, this.todo);
        assertEquals("todo's text is not same", actual.getText(), this.todo.getText());
        assertEquals("todo's color is not same", actual.getColor(), this.todo.getColor());
        assertEquals("todo's finished is not same", actual.isFinished(), this.todo.isFinished());
    }
    
    @Test
    public void should_return_todo_with_information_on_update() throws Exception {
        when(this.todoService.put(Matchers.any(Todo.class))).thenReturn(this.todo);
        
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put(CONTROLLER_URL)
        .content(this.mapper.writeValueAsString(todo))
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andReturn();
        
        verify(this.todoService, times(1)).put(Matchers.any(Todo.class));
        
        assertFalse(result.getResponse().getContentAsString().isEmpty());
        Todo actual = objectMapper.readValue(result.getResponse().getContentAsString(), Todo.class);
        assertNotNull("todo created can not be null !", actual);
        assertEquals("todos not same !", actual, this.todo);
        assertEquals("todo's text is not same", actual.getText(), this.todo.getText());
        assertEquals("todo's color is not same", actual.getColor(), this.todo.getColor());
        assertEquals("todo's finished is not same", actual.isFinished(), this.todo.isFinished());
    }
    
    @Test
    public void should_return_list_of_todo_on_getall() throws Exception {
        List<Todo> todos = Arrays.asList(this.todo);
        when(this.todoService.getAll()).thenReturn(todos);
        
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(CONTROLLER_URL)
        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andReturn();
        
        verify(this.todoService, times(1)).getAll();
        
        
        assertFalse(result.getResponse().getContentAsString().isEmpty());
        List<Todo> actual = objectMapper.readValue(result.getResponse().getContentAsString(), List.class);
        
        assertNotNull("List of todos can not be null !", actual);
        assertFalse("List of todos can not be empty", actual.isEmpty());
    }
    
    @Test
    public void should_return_boolean_true_on_delete_success() throws Exception {
        when(this.todoService.delete(Matchers.any(Todo.class))).thenReturn(Boolean.TRUE);
        
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete(CONTROLLER_URL)
        .content(this.mapper.writeValueAsString(todo))
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andReturn();
        
        verify(this.todoService, times(1)).delete(Matchers.any(Todo.class));
        
        
        Boolean actual = objectMapper.readValue(result.getResponse().getContentAsString(), Boolean.class);
        assertNotNull("result on delete can not be null !", actual);
        assertTrue("result on delete is not true !", actual);
    }
}
