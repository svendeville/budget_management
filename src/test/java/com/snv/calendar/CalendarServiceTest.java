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

import com.snv.common.CrudService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;

/**
 * Class To test the Todo service
 */
@RunWith(MockitoJUnitRunner.class)
public class CalendarServiceTest {

    @InjectMocks
    private final CalendarService calendarService = new CalendarServiceImpl();

    @Mock
    private CrudService<Calendar> crudService;

    private Calendar calendar;

    @Before
    public void setUp() {
        this.calendar = new Calendar();
        this.calendar.setId(1l);
        this.calendar.setTitle("my test calendar");
        this.calendar.setStart("2017/01/01");
        this.calendar.setEnd("2017/01/01");
        this.calendar.setColor("blue");
    }

    @Test
    public void should_return_todo_with_information() {
        Mockito.when(this.crudService.create(Matchers.any(Calendar.class))).thenReturn(this.calendar);
        Calendar actual = this.calendarService.create(this.calendar);
        verify(this.crudService, Mockito.times(1)).create(Matchers.any(Calendar.class));
        this.match(actual);
    }

    @Test
    public void should_return_todo_list_when_call_getAll() {
        Mockito.when(this.crudService.getAll()).thenReturn(Arrays.asList(this.calendar));
        List<Calendar> actual = this.calendarService.getAll();
        verify(this.crudService, Mockito.times(1)).getAll();
        assertFalse(actual.isEmpty());
        assertTrue(actual.size() == 1);
        this.match(actual.get(0));
    }

    @Test
    public void should_return_udpated_todo_when_call_put() {
        Mockito.when(this.crudService.update(Matchers.any(Calendar.class))).thenReturn(this.calendar);
        Calendar actual = this.calendarService.put(this.calendar);
        verify(this.crudService, Mockito.times(1)).update(Matchers.any(Calendar.class));
        this.match(actual);
    }

    @Test
    public void should_return_true_when_delete_todo() {
        Mockito.when(this.crudService.delete(Matchers.anyInt())).thenReturn(Boolean.TRUE);
        boolean actual = this.calendarService.delete(this.calendar.getId());
        verify(this.crudService, Mockito.times(1)).delete(Matchers.anyInt());
        assertTrue(actual);
    }

    private void match(Calendar actual) {
        assertNotNull("calendar created can not be null !", actual);
        assertEquals("calendar not same !", actual, this.calendar);
        assertEquals("calendar's text is not same", actual.getTitle(), this.calendar.getTitle());
        assertEquals("calendar's finished is not same", actual.getStart(), this.calendar.getStart());
        assertEquals("calendar's finished is not same", actual.getEnd(), this.calendar.getEnd());
        assertEquals("calendar's color is not same", actual.getColor(), this.calendar.getColor());
    }
}
