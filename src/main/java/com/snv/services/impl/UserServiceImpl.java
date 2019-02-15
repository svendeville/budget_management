/*
 * @Copyright 2016 Sylvain Vendeville.
 * This file is part of Budget Management.
 * Budget Management is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Budget Management is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Budget Management. If not, see <http://www.gnu.org/licenses/>.
 */
package com.snv.services.impl;

import com.snv.dao.CrudService;
import com.snv.entity.Credential;
import com.snv.entity.User;
import com.snv.exceptions.InvalidCredentialException;
import com.snv.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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
       User user = this.byLogin(credential.getLogin());
       if (user != null && credential.getPassword().equals(user.getPassword())) {
           return user;
       }
       throw new InvalidCredentialException("Invalid credential, please login with right login and password !");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User byLogin(String login) {
        List<User> users = this.getAll();
        if (users != null && !users.isEmpty()) {
            User user = users
                    .stream()
                    .filter(u -> 
                            u.getLogin().equals(login))
                    .findFirst()
                    .orElse(null);
            if (user != null) {
                this.populateAuthorities(user);
                return user;
            }
        }
        throw new InvalidCredentialException("User can not be found from login passed !");
    }
    
    private void populateAuthorities(final User user) {
        if(user.getAuthorities() == null) {
            user.setAuthorities(new ArrayList<>());
        }
        /*
        switch (user.getProfile().name()) {
            case "ADMIN" :
                Arrays.asList(Profile.values()).stream().forEach(profile -> user.getAuthorities().add((GrantedAuthority) () -> profile.name()));
                break;
            default:
                user.getAuthorities().add((GrantedAuthority) () -> user.getProfile().name());
                break;
        }
        */
    }
    
}
