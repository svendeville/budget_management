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

import com.snv.common.CrudService;
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
 * Class To test the Todo service
 */
@RunWith(MockitoJUnitRunner.class)
public class TodoServiceTest {
    
    @InjectMocks
    private final TodoService todoService = new TodoServiceImpl();
    
    @Mock
    private CrudService<Todo> todoCrudService;
    
    private Todo todo;
    
    @Before
    public void setUp() {
        this.todo = new Todo();
        this.todo.setText("my test todo");
        this.todo.setFinished(true);
        this.todo.setColor("blue");
    }
    
    @Test
    public void should_return_todo_with_information() {
        Mockito.when(this.todoCrudService.create(Matchers.any(Todo.class))).thenReturn(this.todo);
        Todo actual = this.todoService.create(this.todo);
        verify(this.todoCrudService, Mockito.times(1)).create(Matchers.any(Todo.class));
        this.match(actual);
    }
    
    @Test
    public void should_return_todo_list_when_call_getAll() {
        Mockito.when(this.todoCrudService.getAll()).thenReturn(Arrays.asList(this.todo));
        List<Todo> actual = this.todoService.getAll();
        verify(this.todoCrudService, Mockito.times(1)).getAll();
        assertFalse(actual.isEmpty());
        assertTrue(actual.size() == 1);
        this.match(actual.get(0));
    }
    
    @Test
    public void should_return_udpated_todo_when_call_put() {
        Mockito.when(this.todoCrudService.update(Matchers.any(Todo.class))).thenReturn(this.todo);
        Todo actual = this.todoService.put(this.todo);
        verify(this.todoCrudService, Mockito.times(1)).update(Matchers.any(Todo.class));
        this.match(actual);
    }
    
    @Test
    public void should_return_true_when_delete_todo () {
        Mockito.when(this.todoCrudService.delete(Matchers.any(Todo.class))).thenReturn(Boolean.TRUE);
        boolean actual = this.todoService.delete(this.todo);
        verify(this.todoCrudService, Mockito.times(1)).delete(Matchers.any(Todo.class));
        assertTrue(actual);
    }
    
    private void match(Todo actual) {
        assertNotNull("todo created can not be null !", actual);
        assertEquals("todos not same !", actual, this.todo);
        assertEquals("todo's text is not same", actual.getText(), this.todo.getText());
        assertEquals("todo's color is not same", actual.getColor(), this.todo.getColor());
        assertEquals("todo's finished is not same", actual.isFinished(), this.todo.isFinished());
    }
}
