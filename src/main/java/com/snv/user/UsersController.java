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

import com.snv.exceptions.InvalidCredentialException;
import com.snv.guard.AuthenticationService;
import com.snv.guard.hmac.HmacException;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest Crud Iterface that manages Users request. Its main function is to catch request, convert data
 * into functional one and delegate to service the tasks to perform operations upon UserController.
 * Finally, send the response back to the consumer
 * If exceptions occurs, treatment of the corresponding responses are made
 */
@RestController
@RequestMapping(value={"/api/users"})
@CrossOrigin(origins = "*")
public class UsersController implements Users {

    private static final Log LOG = LogFactory.getLog(UsersController.class);

    @Autowired
    private UserService userService;
    
    @Autowired
    private AuthenticationService authenticationService;
    
    /**
     * {@inheritDoc}
     */
    @Override
    @RequestMapping(method = RequestMethod.POST, path = "/create"
            , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public User post(@Valid @RequestBody final User user) {
        return this.userService.create(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @RequestMapping(value = "/{userId}",
            method = RequestMethod.GET
            , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public User get(@PathVariable("userId") Long userId) {
        return this.userService.get(userId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @RequestMapping(method = RequestMethod.GET
            , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<User> getAll() {
        return this.userService.getAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @RequestMapping(method = RequestMethod.PUT
            , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public User put(@RequestBody User user) {
        return this.userService.put(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @RequestMapping(method = RequestMethod.DELETE
            , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Boolean delete(@RequestBody User user) {
        return this.userService.delete(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @RequestMapping(method = RequestMethod.POST, path = "/login"
            , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public User login(@RequestBody final Credential credential, final HttpServletResponse response) {
        try {
            return this.authenticationService.authenticate(credential, response);
        } catch (HmacException e) {
            LOG.error("Authentication failure", e);
        }
        throw new InvalidCredentialException("Authentication failure");
    }

    @Override
    public Boolean logout(String token) {
        this.authenticationService.logout();
        return Boolean.TRUE;
    }
    
}
