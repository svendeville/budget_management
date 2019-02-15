package com.snv.stage.dispatcher;

import com.snv.stage.Application;
import com.sun.javafx.event.BasicEventDispatcher;
import com.sun.javafx.event.CompositeEventDispatcher;
import com.sun.javafx.event.EventHandlerManager;
import com.sun.javafx.event.EventRedirector;

public class ApplicationEventDispatcher extends CompositeEventDispatcher {

    private final EventRedirector eventRedirector;

    private final EventHandlerManager eventHandlerManager;

    public ApplicationEventDispatcher(Application application) {
        this(new EventRedirector(application), new EventHandlerManager(application));
    }

    public ApplicationEventDispatcher(EventRedirector eventRedirector, EventHandlerManager eventHandlerManager) {
        this.eventRedirector = eventRedirector;
        this.eventHandlerManager = eventHandlerManager;
    }

    public final EventRedirector getEventRedirector() {
        return eventRedirector;
    }

    public final EventHandlerManager getEventHandlerManager() {
        return eventHandlerManager;
    }

    @Override
    public BasicEventDispatcher getFirstDispatcher() {
        return eventRedirector;
    }

    @Override
    public BasicEventDispatcher getLastDispatcher() {
        return eventHandlerManager;
    }
}
