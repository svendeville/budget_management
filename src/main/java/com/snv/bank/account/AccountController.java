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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Rest Crud implementation that manages calendars request. Its main function is to catch request, convert data
 * into functional one and delegate to service the tasks to perform operations upon calendarController.
 * Finally, send the response back to the consumer
 * If exceptions occurs, treatment of the corresponding responses are made
 */
@RestController
@RequestMapping(value = {"/api/bank/accounts"})
@CrossOrigin(origins = "*")
public class AccountController implements Accounts {

    @Autowired
    private AccountService accountService;

    /**
     * {@inheritDoc}
     */
    @RequestMapping(method = RequestMethod.POST, path = "/create"
            , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @Override
    public Account post(@RequestBody(required = true) final Account account) {
        return accountService.create(account);
    }

    /**
     * {@inheritDoc}
     */
    @RequestMapping(method = RequestMethod.GET, path = "/{accountId}"
            , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @Override
    public Account get(@PathVariable(value = "accountId", required = true) final Long accountId) {
        return accountService.get(accountId);
    }

    /**
     * {@inheritDoc}
     */
    @RequestMapping(method = RequestMethod.GET
            , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @Override
    public List<Account> getAll() {
        return accountService.getAll();
    }

    /**
     * {@inheritDoc}
     */
    @RequestMapping(method = RequestMethod.PUT
            , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @Override
    public Account put(@RequestBody(required = true) final Account account) {
        return accountService.put(account);
    }

    /**
     * {@inheritDoc}
     */
    @RequestMapping(method = RequestMethod.DELETE, path = "/{accountId}"
            , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @Override
    public boolean delete(@PathVariable(value = "accountId", required = true) final Long accountId) {
        return accountService.delete(accountId);
    }
}
