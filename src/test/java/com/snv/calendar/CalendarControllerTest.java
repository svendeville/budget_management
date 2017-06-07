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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.snv.todo.Todo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CalendarControllerTest {

    private static final String CONTROLLER_URL = "http://localhost:8080/api/calendars";
    private final ObjectMapper mapper = new ObjectMapper();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private MockMvc mockMvc;
    @InjectMocks
    private CalendarController calendarController;
    @Mock
    private CalendarService calendarService;
    private Calendar calendar;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.calendarController)
                .build();
        this.calendar = new Calendar();
        this.calendar.setId(1l);
        this.calendar.setTitle("my test calendar");
        this.calendar.setStart("2017/01/01");
        this.calendar.setEnd("2017/01/01");
        this.calendar.setColor("blue");
    }

    @Test
    public void should_return_BadRequest_when_call_without_calendar() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(CONTROLLER_URL)
                .content(this.mapper.writeValueAsString(new Calendar()))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    public void should_return_BadRequest_when_call_with_empty_Todo_values() throws Exception {
        Calendar emptyCalendar = new Calendar();

        emptyCalendar.setTitle("");
        emptyCalendar.setStart("");
        emptyCalendar.setEnd("");

        mockMvc.perform(MockMvcRequestBuilders.post(CONTROLLER_URL)
                .content(this.mapper.writeValueAsString(emptyCalendar))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    public void should_return_todo_with_information_on_create() throws Exception {
        when(this.calendarService.create(Matchers.any(Calendar.class))).thenReturn(this.calendar);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(CONTROLLER_URL)
                .content(this.mapper.writeValueAsString(calendar))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andReturn();

        verify(this.calendarService, times(1)).create(Matchers.any(Calendar.class));

        assertFalse(result.getResponse().getContentAsString().isEmpty());
        Calendar actual = objectMapper.readValue(result.getResponse().getContentAsString(), Calendar.class);
        assertNotNull("calendar created can not be null !", actual);
        assertEquals("calendar not same !", actual, this.calendar);
        assertEquals("calendar's text is not same", actual.getTitle(), this.calendar.getTitle());
        assertEquals("calendar's color is not same", actual.getColor(), this.calendar.getColor());
        assertEquals("calendar's Start Date is not same", actual.getStart(), this.calendar.getStart());
    }

    @Test
    public void should_return_todo_with_information_on_update() throws Exception {
        when(this.calendarService.put(Matchers.any(Calendar.class))).thenReturn(this.calendar);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put(CONTROLLER_URL)
                .content(this.mapper.writeValueAsString(calendar))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andReturn();

        verify(this.calendarService, times(1)).put(Matchers.any(Calendar.class));

        assertFalse(result.getResponse().getContentAsString().isEmpty());
        Calendar actual = objectMapper.readValue(result.getResponse().getContentAsString(), Calendar.class);
        assertNotNull("calendar created can not be null !", actual);
        assertEquals("calendar not same !", actual, this.calendar);
        assertEquals("calendar's text is not same", actual.getTitle(), this.calendar.getTitle());
        assertEquals("calendar's color is not same", actual.getColor(), this.calendar.getColor());
        assertEquals("calendar's Start Date is not same", actual.getStart(), this.calendar.getStart());
    }

    @Test
    public void should_return_list_of_todo_on_getall() throws Exception {
        List<Calendar> calendars = Arrays.asList(this.calendar);
        when(this.calendarService.getAll()).thenReturn(calendars);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(CONTROLLER_URL)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andReturn();

        verify(this.calendarService, times(1)).getAll();


        assertFalse(result.getResponse().getContentAsString().isEmpty());
        List<Todo> actual = objectMapper.readValue(result.getResponse().getContentAsString(), List.class);

        assertNotNull("List of calendars can not be null !", actual);
        assertFalse("List of calendars can not be empty", actual.isEmpty());
    }

    @Test
    public void should_return_boolean_true_on_delete_success() throws Exception {
        when(this.calendarService.delete(Matchers.anyLong())).thenReturn(Boolean.TRUE);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete(CONTROLLER_URL + "/" + calendar.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andReturn();

        verify(this.calendarService, times(1)).delete(Matchers.anyLong());


        Boolean actual = objectMapper.readValue(result.getResponse().getContentAsString(), Boolean.class);
        assertNotNull("result on delete can not be null !", actual);
        assertTrue("result on delete is not true !", actual);
    }
}
