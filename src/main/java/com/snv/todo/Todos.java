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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Rest Crud Iterface that manages Todos request. Its main function is to catch request, convert data
 * into functional one and delegate to service the tasks to perform operations upon TodoController.
 * Finally, send the response back to the consumer
 * If exceptions occurs, treatment of the corresponding responses are made
 */
@Api(tags={"todos"}, value="Todos Crud Api to manage the Todo list", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RequestMapping(value="/api/todos", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public interface Todos {
    
    /**
     * Create user POST endpoint
     * @param todo the todo to create
     * @return the same todo with an identifier
     */
    @ApiOperation(value="Create new todo", 
            notes = "Post a new todo to persit it to the data")
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Created", response = Todo.class),
        @ApiResponse(code = 400, message = "Bad Request"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Not Found"),
        @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @RequestMapping(method = RequestMethod.POST, path = "/create"
            , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Todo post(@ApiParam(required = true) @RequestBody final Todo todo);
    
    /**
     * Get all users
     * @return the list of user containing all dataBase users
     */
    @ApiOperation(value="Get all todos", 
            notes = "Return all todos found in DataBase")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Success", response = Todo.class),
        @ApiResponse(code = 400, message = "Bad Request"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Not Found"),
        @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @RequestMapping(method = RequestMethod.GET
            , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    List<Todo> getAll();
    
    /**
     * Udapte user PUT endpoint
     * @param todo the todo to update
     * @return the same todo.
     */
    @ApiOperation(value="Update todo pareyer passed", 
            notes = "Update the todo to the data")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Success", response = Todo.class),
        @ApiResponse(code = 400, message = "Bad Request"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Not Found"),
        @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @RequestMapping(method = RequestMethod.PUT
            , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Todo put(@ApiParam(required = true) @RequestBody final Todo todo);
    
    /**
     * Delete todo POST endpoint
     * @param todo the todo to delete
     * @return boolean true if todo is successfuly deleted, false otherwise
     */
    @ApiOperation(value="Delete todo endPoint", 
            notes = "Delete the parameter passed todo")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Success"),
        @ApiResponse(code = 400, message = "Bad Request"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Not Found"),
        @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @RequestMapping(method = RequestMethod.DELETE
            , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Boolean delete(@ApiParam(required = true) @RequestBody final Todo todo);
}
