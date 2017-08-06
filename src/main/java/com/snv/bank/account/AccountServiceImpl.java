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

import com.snv.common.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service implementation to manage the user bank account.
 */
@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private CrudService<Account> crudService;
    /**
     * {@inheritDoc}
     */
    @Override
    public Account create(Account account) {
        return crudService.create(account);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Account get(Long accountId) {
        return crudService.get(accountId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Account> getAll() {
        return crudService.getAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Account put(Account account) {
        return crudService.update(account);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean delete(Long accountId) {
        return crudService.delete(accountId);
    }
}
