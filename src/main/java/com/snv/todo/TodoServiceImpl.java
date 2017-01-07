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

import com.snv.common.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 *
 * @author sylvain
 */
@Component
public class TodoServiceImpl implements TodoService {
    
    @Autowired
    private CrudService<Todo> crudService;

    /**
     * {@inheritDoc}
     */
    @Override
    public Todo create(Todo todo) {
        return crudService.create(todo);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Todo> getAll() {
        return crudService.getAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Todo put(Todo todo) {
        return crudService.update(todo);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean delete(Long todoId) {
        return crudService.delete(todoId);
    }
    
}
