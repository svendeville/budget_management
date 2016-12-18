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
package com.snv.todo;

import java.util.List;

/**
 * Crud Interface that manages Todos.
 */
public interface TodoService {

    /**
     * Create user POST endpoint
     * @param todo the todo to create
     * @return the same todo with an identifier
     */
    public Todo create(Todo todo);

    /**
     * Get all users
     * @return the list of user containing all dataBase users
     */
    public List<Todo> getAll();

    /**
     * Udapte user PUT endpoint
     * @param todo the todo to update
     * @return the same todo.
     */
    public Todo put(Todo todo);

    /**
     * Delete todo POST endpoint
     * @param todo the todo to delete
     * @return boolean true if todo is successfuly deleted, false otherwise
     */
    public Boolean delete(Todo todo);
    
}
