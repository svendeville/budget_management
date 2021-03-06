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
package com.snv.services;

import com.snv.entity.Todo;

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
    Todo create(Todo todo);

    /**
     * Get all users
     * @return the list of user containing all dataBase users
     */
    List<Todo> getAll();

    /**
     * Udapte user PUT endpoint
     * @param todo the todo to update
     * @return the same todo.
     */
    Todo put(Todo todo);

    /**
     * Delete todo POST endpoint
     * @param todoId the todo technical identifier to delete
     * @return boolean true if todo is successfully deleted, false otherwise
     */
    Boolean delete(Long todoId);
    
}
