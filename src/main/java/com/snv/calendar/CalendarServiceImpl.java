package com.snv.calendar;

import com.snv.common.CrudService;
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
