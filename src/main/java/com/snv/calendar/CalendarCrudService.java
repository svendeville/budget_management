package com.snv.calendar;

import com.snv.berkeleydb.CatalogDataBase;
import com.snv.berkeleydb.DataBaseFactory;
import com.snv.berkeleydb.DataBaseNamesEnum;
import com.snv.common.CrudService;

import java.util.List;

/**
 * Created by sylvain on 09/01/17.
 */
public class CalendarCrudService extends DataBaseFactory<Calendar> implements CrudService<Calendar> {

    private static final DataBaseNamesEnum CALENDARCRUDSERVICENAME = DataBaseNamesEnum.CALENDARS;
    private static boolean isCalendarCrudServiceImplemented = Boolean.FALSE;

    public CalendarCrudService() {
        if (!Boolean.FALSE.equals(isCalendarCrudServiceImplemented)) {
            throw new IllegalStateException("L'instance calendarCrudService ne peut être implémenté plus d'une fois.");
        }
        this.open();
        isCalendarCrudServiceImplemented = Boolean.TRUE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isOpen() {
        return super.isOpen();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void open() {
        super.open(CALENDARCRUDSERVICENAME);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() {
        super.close();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Calendar create(Calendar entity) {
        final Long primaryKey = CatalogDataBase.getInstance().getNextSequence(CALENDARCRUDSERVICENAME.name());
        entity.setId(primaryKey);
        if (super.insertEntity(primaryKey, entity)) {
            return entity;
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Calendar get(long entityKey) {
        return super.get(entityKey);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Calendar> getAll() {
        return super.getAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Calendar update(Calendar entity) {
        if (super.updateEntity(entity.getId(), entity)) {
            return entity;
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean delete(long entityKey) {
        return super.deleteEntity(entityKey);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean delete(Calendar entity) {
        return this.delete(entity.getId());
    }
}
