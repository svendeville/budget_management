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
package com.snv.calendar;

import io.swagger.annotations.*;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Rest Crud Interface that manages Calendars request. Its main function is to catch request, convert data
 * into functional one and delegate to service the tasks to perform operations upon calendarController.
 * Finally, send the response back to the consumer
 * If exceptions occurs, treatment of the corresponding responses are made
 */
@Api(tags = {"calendars"}, value = "calendars Crud Api to manage the calendar list", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RequestMapping(value = "/api/calendars", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public interface Calendars {

    /**
     * Create user POST endpoint
     *
     * @param calendar the calendar to create
     * @return the same calendar with an identifier
     */
    @ApiOperation(value = "Create new calendar",
            notes = "Post a new calendar to persit it to the data")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created", response = Calendar.class),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @RequestMapping(method = RequestMethod.POST, path = "/create"
            , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Calendar post(@ApiParam(required = true) @RequestBody final Calendar calendar);

    /**
     * Get all users
     *
     * @return the list of user containing all dataBase users
     */
    @ApiOperation(value = "Get all calendars",
            notes = "Return all calendars found in DataBase")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = Calendar.class),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @RequestMapping(method = RequestMethod.GET
            , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    List<Calendar> getAll();

    /**
     * Udapte user PUT endpoint
     *
     * @param calendar the calendar to update
     * @return the same calendar.
     */
    @ApiOperation(value = "Update calendar pareyer passed",
            notes = "Update the calendar to the data")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = Calendar.class),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @RequestMapping(method = RequestMethod.PUT
            , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Calendar put(@ApiParam(required = true) @RequestBody final Calendar calendar);

    /**
     * Delete calendar POST endpoint
     *
     * @param calendarId the calendar technical identifier to delete
     * @return boolean true if calendar is successfully deleted, false otherwise
     */
    @ApiOperation(value = "Delete calendar endPoint",
            notes = "Delete the parameter passed calendar")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @RequestMapping(path = "/{calendarId}", method = RequestMethod.DELETE
            , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Boolean delete(@ApiParam(required = true) @PathVariable("calendarId") final Long calendarId);
}
