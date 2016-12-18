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
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest Crud implementaion that manages Todos request. Its main function is to catch request, convert data
 * into functional one and delegate to service the tasks to perform operations upon TodoController.
 * Finally, send the response back to the consumer
 * If exceptions occurs, treatment of the corresponding responses are made
 */
@RestController
@RequestMapping(value = {"api/todos"})
@CrossOrigin(origins = "*")
public class TodoController implements Todos {
    
    @Autowired
    private TodoService todoService;

    /**
     * {@inheritDoc}
     */
    @Override
    @RequestMapping(method = RequestMethod.POST
            , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Todo post(@Valid @RequestBody final Todo todo) {
        return todoService.create(todo);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @RequestMapping(method = RequestMethod.GET
            , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Todo> getAll() {
        return todoService.getAll();
    }

    /**
     * {@inheritDoc}
     */
    @RequestMapping(method = RequestMethod.PUT
            , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @Override
    public Todo put(@Valid @RequestBody final Todo todo) {
        return todoService.put(todo);
    }

    /**
     * {@inheritDoc}
     */
    @RequestMapping(method = RequestMethod.DELETE
            , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @Override
    public Boolean delete(@Valid @RequestBody final Todo todo) {
        return todoService.delete(todo);
    }
    
}
