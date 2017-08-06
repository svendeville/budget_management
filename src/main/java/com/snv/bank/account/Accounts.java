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

package com.snv.bank.account;


import io.swagger.annotations.*;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Rest Crud Interface that manages Bank account request. Its main function is to catch request, convert data
 * into functional one and delegate to service the tasks to perform operations upon BankAccountController.
 * Finally, send the response back to the consumer
 * If exceptions occurs, treatment of the corresponding responses are made
 */
@Api(tags = {"bank-accounts"}, value = "bank account Crud Api to manage the user bank account", produces = MediaType.APPLICATION_JSON_VALUE)
@RequestMapping(value = "/api/bank/accounts", produces = MediaType.APPLICATION_JSON_VALUE)
public interface Accounts {

    /**
     * Create user bank account endPoint
     *
     * @param account the user bank account to create
     * @return the same user bank account with an identifier
     */
    @ApiOperation(value = "Create new user bank account",
            notes = "Post a new user bank account to persist it to the data base")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created", response = Account.class),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @RequestMapping(method = RequestMethod.POST, path = "/create"
            , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Account post(@ApiParam(required = true) @RequestBody(required = true) final Account account);

    /**
     * Get user bank account by identifier endPoint
     *
     * @param accountId the user bank account identifier
     * @return the founded user bank account, null otherwise
     */
    @ApiOperation(value = "Get user bank account by identifier",
            notes = "Get a user bank account in the data base by is identifier")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = Account.class),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @RequestMapping(method = RequestMethod.GET, path = "/{accountId}"
            , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Account get(@ApiParam(required = true) @PathVariable(value = "accountId", required = true) final Long accountId);

    /**
     * Get all user bank account endPoint
     *
     * @return the list of user bank account, empty List otherwise
     */
    @ApiOperation(value = "Get all user bank account",
            notes = "Get all user bank account in the data base")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = Account.class),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @RequestMapping(method = RequestMethod.GET
            , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    List<Account> getAll();

    /**
     * Update user bank account PUT endPoint
     *
     * @param account the user bank account to create
     * @return the founded user bank account, null otherwise
     */
    @ApiOperation(value = "Update user bank account",
            notes = "Update a user bank account in the data base")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = Account.class),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @RequestMapping(method = RequestMethod.PUT
            , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Account put(@ApiParam(required = true) @RequestBody(required = true) final Account account);

    /**
     * Delete user bank account endPoint
     *
     * @param accountId the user bank account identifier
     * @return the founded user bank account, null otherwise
     */
    @ApiOperation(value = "Delete user bank account",
            notes = "Delete a user bank account in the data base")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = Account.class),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @RequestMapping(method = RequestMethod.DELETE, path = "/{accountId}"
            , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    boolean delete(@ApiParam(required = true) @PathVariable(value = "accountId", required = true) final Long accountId);
}
