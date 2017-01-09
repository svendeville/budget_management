package com.snv.calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Rest Crud implementation that manages calendars request. Its main function is to catch request, convert data
 * into functional one and delegate to service the tasks to perform operations upon calendarController.
 * Finally, send the response back to the consumer
 * If exceptions occurs, treatment of the corresponding responses are made
 */
@RestController
@RequestMapping(value = {"api/calendars"})
@CrossOrigin(origins = "*")
public class CalendarController implements Calendars {

    @Autowired
    private CalendarService calendarService;

    /**
     * {@inheritDoc}
     */
    @Override
    @RequestMapping(method = RequestMethod.POST
            , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Calendar post(@Valid @RequestBody final Calendar calendar) {
        return calendarService.create(calendar);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @RequestMapping(method = RequestMethod.GET
            , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Calendar> getAll() {
        return calendarService.getAll();
    }

    /**
     * {@inheritDoc}
     */
    @RequestMapping(method = RequestMethod.PUT
            , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @Override
    public Calendar put(@Valid @RequestBody final Calendar calendar) {
        return calendarService.put(calendar);
    }

    /**
     * {@inheritDoc}
     */
    @RequestMapping(path = "/{calendarId}", method = RequestMethod.DELETE
            , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @Override
    public Boolean delete(@PathVariable("calendarId") final Long calendarId) {
        return calendarService.delete(calendarId);
    }

}
