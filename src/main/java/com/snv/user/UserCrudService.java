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
package com.snv.user;

import com.snv.berkeleyfb.CatalogDataBase;
import com.snv.berkeleyfb.DataBaseFactory;
import com.snv.berkeleyfb.DataBaseNamesEnum;
import com.snv.common.CrudService;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author Sylvain
 */
@Service
public final class UserCrudService extends DataBaseFactory<User> implements CrudService<User> {
    
    private static boolean isUserCrudServiceImplemented = Boolean.FALSE;
    private static final DataBaseNamesEnum USERCRUDSERVICENAME = DataBaseNamesEnum.USERS;

    public UserCrudService() {
        if (!Boolean.FALSE.equals(isUserCrudServiceImplemented)) {
            throw new IllegalStateException("L'instance UserCrudService ne peut être implémenté plus d'une fois.");
        }
        this.open();
        isUserCrudServiceImplemented = Boolean.TRUE;
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
        super.open(USERCRUDSERVICENAME);
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
    public User create(User entity) {
        final Long primaryKey = CatalogDataBase.getInstance().getNextSequence(USERCRUDSERVICENAME.getValue());
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
    public User get(long entityKey) {
        return super.get(entityKey);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public List<User> getAll() {
        return super.getAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User update(User entity) {
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
    public boolean delete(User entity) {
        return super.deleteEntity(entity.getId());
    }
    
}
