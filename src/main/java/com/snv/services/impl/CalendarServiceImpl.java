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
package com.snv.services.impl;

import com.snv.dao.CrudService;
import com.snv.entity.Calendar;
import com.snv.services.CalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by sylvain on 09/01/17.
 */
@Service
public class CalendarServiceImpl implements CalendarService {

    @Autowired
    private CrudService<Calendar> crudService;

    /**
     * {@inheritDoc}
     */
    @Override
    public Calendar create(Calendar calendar) {
        return crudService.create(calendar);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Calendar> getAll() {
        return crudService.getAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Calendar put(Calendar calendar) {
        return crudService.update(calendar);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean delete(Long calendarId) {
        return crudService.delete(calendarId);
    }

}
