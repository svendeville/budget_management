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

import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service Interface to manage the user bank account.
 */

@Service
public interface AccountService {
    /**
     * Create user bank account endPoint
     *
     * @param account the user bank account to create
     * @return the same user bank account with an identifier
     */
    Account create(final Account account);

    /**
     * Get user bank account by identifier
     *
     * @param accountId the user bank account identifier
     * @return the founded user bank account, null otherwise
     */
    Account get(final Long accountId);

    /**
     * Get all user bank account
     *
     * @return the list of user bank account, empty List otherwise
     */
    List<Account> getAll();

    /**
     * Update user bank account
     *
     * @param account the user bank account to create
     * @return the founded user bank account, null otherwise
     */
    Account put(final Account account);

    /**
     * Delete user bank account
     *
     * @param accountId the user bank account identifier
     * @return boolean true if bank account is successfully deleted, false otherwise
     */
    boolean delete(final Long accountId);
}
