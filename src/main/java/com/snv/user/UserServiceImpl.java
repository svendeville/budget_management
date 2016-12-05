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
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl implements UserService {
    
    @Autowired
    private CrudService<User> userCrudService;

    /**
     * {@inheritDoc}
     */
    @Override
    public User create(User user) {
        return this.userCrudService.create(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User get(Long userId) {
        return this.userCrudService.get(userId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<User> getAll() {
        return this.userCrudService.getAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User put(User user) {
        return this.userCrudService.update(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean delete(User user) {
        return this.userCrudService.delete(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User byLogin(Credential credential) {
       List<User> users = this.getAll();
       User user = users
               .stream()
               .filter(u -> 
                       u.getLogin().equals(credential.getLogin()) && 
                       u.getPassword().equals(credential.getPassword()))
               .findFirst()
               .orElse(null);
       if (user != null) {
           return user;
       }
       throw new InvalidCredentialException("Invalid credential, please login with right login and password !");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UUID generateToken() {
        return UUID.randomUUID();
    }
    
}
