package com.snv.calendar;

import java.util.List;

/**
 * Created by sylvain on 09/01/17.
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
