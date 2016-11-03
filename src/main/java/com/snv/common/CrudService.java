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
package com.snv.common;

import com.sleepycat.je.DatabaseException;
import com.snv.exceptions.BudgetDataBaseException;
import com.snv.exceptions.KeyAlreadyExistException;
import com.snv.exceptions.NoDataFoundException;
import java.util.List;

/**
 * Interface to manage crud's service
 */
public interface CrudService<T> {
    
    /**
     * insert en entity of T object to data base
     * @param entity the entity to insert
     * @return the new created entity or null if an error occured
     * @throws com.snv.exceptions.KeyAlreadyExistException
     */
    T create(final T entity) throws BudgetDataBaseException, KeyAlreadyExistException;
    
    /**
     * get the entity of T object by primaray key
     * @param entityKey the primary key of the T object search
     * @return T object found or null if no entity found
     */
    T get(final long entityKey) throws NoDataFoundException;
    
    /**
     * get all entity of T object
     * @return an list of T object
     */
    List<T> getAll() throws DatabaseException, IllegalArgumentException;
    
    /**
     * update an entity parameter passed
     * @param entity the entity to udate
     * @return the entity updated or null if an error occured
     */
    T update(final T entity);
    
    /**
     * delete a data base entry by the entity primary key
     * @param entityKey the primary key of the entity to delete
     * @return boolean true if the entity can delete, false otherwise
     */
    boolean delete(final long entityKey);
    
    /**
     * delete an entity parameter passed
     * @param entity the entity to delete
     * @return boolean true if the entity can delete, false otherwise
     */
    boolean delete(final T entity);

    void close() throws BudgetDataBaseException;

    boolean isOpen();

    void open() throws BudgetDataBaseException;

}
