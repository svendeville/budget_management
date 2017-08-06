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

import com.snv.berkeleydb.CatalogDataBase;
import com.snv.berkeleydb.DataBaseFactory;
import com.snv.berkeleydb.DataBaseNamesEnum;
import com.snv.common.CrudService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by sylvain on 09/01/17.
 */
@Service
public class AccountCrudService extends DataBaseFactory<Account> implements CrudService<Account> {

    private static final DataBaseNamesEnum ACCOUNTCRUDSERVICENAME = DataBaseNamesEnum.ACCOUNTS;
    private static boolean isAccountCrudServiceImplemented = Boolean.FALSE;

    public AccountCrudService() {
        if (!Boolean.FALSE.equals(isAccountCrudServiceImplemented)) {
            throw new IllegalStateException("L'instance AccountCrudService ne peut être implémenté plus d'une fois.");
        }
        this.open();
        isAccountCrudServiceImplemented = Boolean.TRUE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isOpen() {
        return super.isOpen();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void open() {
        super.open(ACCOUNTCRUDSERVICENAME);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() {
        super.close();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Account create(Account entity) {
        final Long primaryKey = CatalogDataBase.getInstance().getNextSequence(ACCOUNTCRUDSERVICENAME.name());
        entity.setId(primaryKey);
        if (super.insertEntity(primaryKey, entity)) {
            return entity;
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Account get(long entityKey) {
        return super.get(entityKey);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Account> getAll() {
        return super.getAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Account update(Account entity) {
        if (super.updateEntity(entity.getId(), entity)) {
            return entity;
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean delete(long entityKey) {
        return super.deleteEntity(entityKey);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean delete(Account entity) {
        return this.delete(entity.getId());
    }
}
