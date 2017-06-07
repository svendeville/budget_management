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
package com.snv.calendar;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Sylvain
 */
@RunWith(MockitoJUnitRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CalendarCrudServiceTest {

    private static CalendarCrudService service;
    private static Calendar expected;

    @BeforeClass
    public static void setUp() {
        service = new CalendarCrudService();
        expected = new Calendar();
        expected.setTitle("my test calendar");
        expected.setStart("2017/01/01");
        expected.setEnd("2017/01/01");
        expected.setColor("blue");
    }

    @Test
    public void A_should_open_data_base_on_get_implementation() {
        Assert.assertTrue("Data Base is not open", service.isOpen());
        service.close();
        Assert.assertFalse("Data Base is open", service.isOpen());
    }

    @Test
    public void B_should_create_calendar_and_return_same_with_id() {
        this.openService();

        Calendar actual = service.create(expected);
        expected.setId(actual.getId());
        this.closeService();

        assertNotNull("Calendar created can not be null !", actual);
        assertEquals("Calendar not same !", actual, expected);
        assertEquals("Calendar's text is not same", actual.getTitle(), expected.getTitle());
        assertEquals("Calendar's color is not same", actual.getColor(), expected.getColor());
        assertEquals("Calendar's date is not same", actual.getStart(), expected.getStart());
    }

    @Test
    public void C_should_find_calendar_by_id_end_return_it() {
        this.openService();

        Calendar actual = service.get(expected.getId());
        this.closeService();

        assertNotNull("Calendar created can not be null !", actual);
        assertEquals("Calendar not same !", actual, expected);
        assertEquals("Calendar's text is not same", actual.getTitle(), expected.getTitle());
        assertEquals("Calendar's color is not same", actual.getColor(), expected.getColor());
        assertEquals("Calendar's date is not same", actual.getStart(), expected.getStart());
    }

    @Test
    public void D_should_find_none_empty_list_when_getAll() {
        this.openService();

        List<Calendar> actual = service.getAll();
        this.closeService();

        Assert.assertNotNull("List of calendar can't be null", actual);
        Assert.assertFalse("List of calendar can't be empty", actual.isEmpty());
        actual.stream().forEach(calendar -> {
            assertNotNull("calendar created can not be null !", calendar);
            assertNotNull("calendar's text is not same", calendar.getTitle());
            assertNotNull("calendar's color is not same", calendar.getColor());
            assertNotNull("calendar's date is not same", calendar.getStart());
        });
    }

    @Test
    public void E_should_return_updated_calendar() {
        expected.setTitle("UpdatedText");
        this.openService();

        Calendar actual = service.update(expected);
        this.closeService();

        assertNotNull("Calendar created can not be null !", actual);
        assertEquals("Calendar not same !", actual, expected);
        assertEquals("Calendar's text is not same", actual.getTitle(), expected.getTitle());
        assertEquals("Calendar's color is not same", actual.getColor(), expected.getColor());
        assertEquals("Calendar's date is not same", actual.getStart(), expected.getStart());
    }

    @Test
    public void F_should_return_true_when_delete_known_calendar() {
        this.openService();

        boolean isDeleted = service.delete(expected);
        this.closeService();

        Assert.assertTrue("Calendar is not deleted", isDeleted);
    }

    @Test(expected = IllegalStateException.class)
    public void should_throw_IllegalStateException_when_try_second_implentation() {
        new CalendarCrudService();
    }

    private void openService() {
        if (!service.isOpen()) {
            service.open();
        }
    }

    private void closeService() {
        if (service.isOpen()) {
            service.close();
        }
    }
}
