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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Rest Crud Iterface that manages Users request. Its main function is to catch request, convert data
 * into functional one and delegate to service the tasks to perform operations upon UserController.
 * Finally, send the response back to the consumer
 * If exceptions occurs, treatment of the corresponding responses are made
 */
@Api(tags={"users"}, value="users", description = "Crud Api to manage the User", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RequestMapping(value="/users", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public interface Users {
    
    @ApiOperation(value="Create new user", 
            notes = "Post a new user to persit it to the data")
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Created", response = User.class),
        @ApiResponse(code = 400, message = "Bad Request"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Not Found"),
        @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @RequestMapping(method = RequestMethod.POST
            , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    User post(@ApiParam(required = true) @RequestBody final User user);
}