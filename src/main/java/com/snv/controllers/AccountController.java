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

package com.snv.controllers;

import com.snv.entity.Account;
import com.snv.services.AccountService;
import de.felixroske.jfxsupport.FXMLController;
import javafx.event.Event;
import javafx.fxml.FXML;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Rest Crud implementation that manages calendars request. Its main function is to catch request, convert data
 * into functional one and delegate to service the tasks to perform operations upon calendarController.
 * Finally, send the response back to the consumer
 * If exceptions occurs, treatment of the corresponding responses are made
 */
@FXMLController
@Slf4j
public class AccountController {

    @Autowired
    private AccountService accountService;

    @FXML
    private Account account = new Account();

    @FXML
    private Collection<Account> accounts = new ArrayList<>();

    @FXML
    private Long accountId;

    public void post(final Event event) {
        this.account = this.accountService.create(account);
    }

    public void get(final Event event) {
        this.account = this.accountService.get(accountId);
    }

    public void getAll(final Event event) {
        this.accounts = this.accountService.getAll();
    }

    public void put(final Event event) {
        this.account = this.accountService.put(account);
    }

    public void delete(final Event event) {
        this.accountService.delete(accountId);
    }
}
