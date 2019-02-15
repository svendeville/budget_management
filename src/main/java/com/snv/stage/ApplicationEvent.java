package com.snv.stage;

import javafx.beans.NamedArg;
import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;

public final class ApplicationEvent extends Event {

    /**
     * Common supertype for all window event types.
     */
    public static final EventType<ApplicationEvent> ANY =
            new EventType<>(Event.ANY, "APPLICATION");
    /**
     * This event occurs on window just after it is shown.
     */
    public static final EventType<ApplicationEvent> APPLICATION_EXIT =
            new EventType<>(ApplicationEvent.ANY, "APPLICATION_EXIT");
    private static final long serialVersionUID = 20121107984L;

    public ApplicationEvent(final @NamedArg("source") Application source,
                            final @NamedArg("target") EventTarget target,
                            final @NamedArg("eventType") EventType<? extends Event> eventType) {
        super(source, target, eventType);
    }
}
