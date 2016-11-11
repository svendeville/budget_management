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

import java.util.List;
import org.junit.Assert;
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
public class UserCrudServiceTest {
   
   private static UserCrudService service;
   private static User expected;
   
   @BeforeClass
   public static void setUp() {
      service  = new UserCrudService();
      expected = new User();
      expected.setEmail("test@test.fr");
      expected.setFirstName("test");
      expected.setLastName("test");
      expected.setLogin("login");
      expected.setPassword("pass");
   }
   
   @Test
   public void A_should_open_data_base_on_get_implementation() {
      Assert.assertTrue("Data Base is not open", service.isOpen());
      service.close();
      Assert.assertFalse("Data Base is open", service.isOpen());
   }
   
   @Test
   public void B_should_create_user_and_return_same_with_id() {
      this.openService();
      
      User actual = service.create(expected);
      expected.setId(actual.getId());
      this.closeService();
      
      Assert.assertNotNull(actual);
      Assert.assertNotNull(actual.getId());
      Assert.assertEquals(expected.getEmail(), actual.getEmail());
      Assert.assertEquals(expected.getFirstName(), actual.getFirstName());
      Assert.assertEquals(expected.getLastName(), actual.getLastName());
      Assert.assertEquals(expected.getLogin(), actual.getLogin());
      Assert.assertEquals(expected.getPassword(), actual.getPassword());
   }
   
   @Test
   public void C_should_find_user_by_id_end_return_it() {
      this.openService();
      
      User actual = service.get(expected.getId());
      this.closeService();
      
      Assert.assertNotNull(actual);
      Assert.assertNotNull(actual.getId());
      Assert.assertEquals(expected.getEmail(), actual.getEmail());
      Assert.assertEquals(expected.getFirstName(), actual.getFirstName());
      Assert.assertEquals(expected.getLastName(), actual.getLastName());
      Assert.assertEquals(expected.getLogin(), actual.getLogin());
      Assert.assertEquals(expected.getPassword(), actual.getPassword());
   }
   
   @Test
   public void D_should_find_none_empty_list_when_getAll() {
      this.openService();
      
      List<User> actual = service.getAll();
      this.closeService();
      
      Assert.assertNotNull("List of user can't be null", actual);
      Assert.assertFalse("List of user can't be empty", actual.isEmpty());
      actual.stream().forEach(user -> {
         Assert.assertNotNull("user can't be null", user);
         Assert.assertNotNull(user.getId());
         Assert.assertNotNull(user.getEmail());
         Assert.assertNotNull(user.getFirstName());
         Assert.assertNotNull(user.getLastName());
         Assert.assertNotNull(user.getLogin());
         Assert.assertNotNull(user.getPassword());
      });
   }
   
   @Test
   public void E_should_return_updated_user() {
      expected.setEmail("UpdatedEmail@Test");
      this.openService();
      
      boolean isDeleted = service.delete(expected);
      this.closeService();
      
      Assert.assertTrue("User is not deleted", isDeleted);
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
