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

import com.snv.user.api.Users;
import com.snv.user.model.User;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest Crud Iterface that manages Users request. Its main function is to catch request, convert data
 * into functional one and delegate to service the tasks to perform operations upon UserController.
 * Finally, send the response back to the consumer
 * If exceptions occurs, treatment of the corresponding responses are made
 */
@RestController
@RequestMapping(value={"/users"})
@CrossOrigin(origins = "*")
public class UsersController implements Users {

    /**
     * {@inheritDoc}
     */
    @Override
    public User post(User user) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
