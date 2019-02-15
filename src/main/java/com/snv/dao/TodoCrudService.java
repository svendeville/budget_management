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
package com.snv.dao;

import com.snv.berkeleydb.CatalogDataBase;
import com.snv.berkeleydb.DataBaseFactory;
import com.snv.berkeleydb.DataBaseNamesEnum;
import com.snv.entity.Todo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * @author sylvain
 */
@Service
@Slf4j
public class TodoCrudService extends DataBaseFactory<Todo> implements CrudService<Todo> {
    
    private static boolean isTodoCrudServiceImplemented = Boolean.FALSE;
    private static final DataBaseNamesEnum TODOCRUDSERVICENAME = DataBaseNamesEnum.TODOS;

    public TodoCrudService() {
        if (!Boolean.FALSE.equals(isTodoCrudServiceImplemented)) {
            throw new IllegalStateException("L'instance TodoCrudService ne peut être implémenté plus d'une fois.");
        }
        this.open();
        isTodoCrudServiceImplemented = Boolean.TRUE;
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
        super.open(TODOCRUDSERVICENAME);
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
    public Todo create(Todo entity) {
        final Long primaryKey = CatalogDataBase.getInstance().getNextSequence(TODOCRUDSERVICENAME.name());
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
    public Todo get(long entityKey) {
        return super.get(entityKey);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public List<Todo> getAll() {
        return super.getAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Todo update(Todo entity) {
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
    public boolean delete(Todo entity) {
        return this.delete(entity.getId());
    }
    
}
