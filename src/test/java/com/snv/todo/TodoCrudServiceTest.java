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

import java.util.List;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 *
 * @author Sylvain
 */
@RunWith(MockitoJUnitRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TodoCrudServiceTest {
   
   private static TodoCrudService service;
   private static Todo expected;
   
   @BeforeClass
   public static void setUp() {
        service  = new TodoCrudService();
        expected = new Todo();
        expected.setText("my test todo");
        expected.setFinished(true);
        expected.setColor("blue");
   }
   
   @Test
   public void A_should_open_data_base_on_get_implementation() {
      Assert.assertTrue("Data Base is not open", service.isOpen());
      service.close();
      Assert.assertFalse("Data Base is open", service.isOpen());
   }
   
   @Test
   public void B_should_create_todo_and_return_same_with_id() {
      this.openService();
      
      Todo actual = service.create(expected);
      expected.setId(actual.getId());
      this.closeService();
      
        assertNotNull("todo created can not be null !", actual);
        assertEquals("todos not same !", actual, expected);
        assertEquals("todo's text is not same", actual.getText(), expected.getText());
        assertEquals("todo's color is not same", actual.getColor(), expected.getColor());
        assertEquals("todo's finished is not same", actual.isFinished(), expected.isFinished());
   }
   
   @Test
   public void C_should_find_todo_by_id_end_return_it() {
      this.openService();
      
      Todo actual = service.get(expected.getId());
      this.closeService();
      
        assertNotNull("todo created can not be null !", actual);
        assertEquals("todos not same !", actual, expected);
        assertEquals("todo's text is not same", actual.getText(), expected.getText());
        assertEquals("todo's color is not same", actual.getColor(), expected.getColor());
        assertEquals("todo's finished is not same", actual.isFinished(), expected.isFinished());
   }
   
   @Test
   public void D_should_find_none_empty_list_when_getAll() {
      this.openService();
      
      List<Todo> actual = service.getAll();
      this.closeService();
      
      Assert.assertNotNull("List of todo can't be null", actual);
      Assert.assertFalse("List of todo can't be empty", actual.isEmpty());
      actual.stream().forEach(todo -> {
        assertNotNull("todo created can not be null !", todo);
        assertNotNull("todo's text is not same", todo.getText());
        assertNotNull("todo's color is not same", todo.getColor());
        assertNotNull("todo's finished is not same", todo.isFinished());
      });
   }
   
   @Test
   public void E_should_return_updated_todo() {
      expected.setText("UpdatedText");
      this.openService();
      
      Todo actual = service.update(expected);
      this.closeService();
      
        assertNotNull("todo created can not be null !", actual);
        assertEquals("todos not same !", actual, expected);
        assertEquals("todo's text is not same", actual.getText(), expected.getText());
        assertEquals("todo's color is not same", actual.getColor(), expected.getColor());
        assertEquals("todo's finished is not same", actual.isFinished(), expected.isFinished());
   }
   
   @Test
   public void F_should_return_true_when_delete_known_todo() {
      this.openService();
      
      boolean isDeleted = service.delete(expected);
      this.closeService();
      
      Assert.assertTrue("Todo is not deleted", isDeleted);
   }
    
    @Test(expected = IllegalStateException.class)
    public void should_throw_IllegalStateException_when_try_second_implentation () {
        new TodoCrudService();
    }
   
   private void openService() {
      if (!service.isOpen()) {
         service.open();
      }
   }
   
   private void closeService() {
      if (service.isOpen()) {
         service.close();
      }
   }
}
