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
package com.snv.calendar;

import java.util.List;

/**
 * Service interface to manage Calendar events.
 */
public interface CalendarService {

    /**
     * Create user POST endpoint
     *
     * @param calendar the calendar to create
     * @return the same calendar with an identifier
     */
    Calendar create(Calendar calendar);

    /**
     * Get all users
     *
     * @return the list of user containing all dataBase users
     */
    List<Calendar> getAll();

    /**
     * Udapte user PUT endpoint
     *
     * @param calendar the calendar to update
     * @return the same calendar.
     */
    Calendar put(Calendar calendar);

    /**
     * Delete calendar POST endpoint
     *
     * @param calendarId the calendar technical identifier to delete
     * @return boolean true if calendar is successfully deleted, false otherwise
     */
    Boolean delete(Long calendarId);
}
