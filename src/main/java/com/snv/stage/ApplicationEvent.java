package com.snv.stage;

import com.snv.BudgetManagementApplication;
import javafx.beans.NamedArg;
import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;

public final class ApplicationEvent extends Event {

    private static final long serialVersionUID = 20121107984L;

    public static final EventType<ApplicationEvent> ANY =
            new EventType<>(Event.ANY, "APPLICATION");

    public static final EventType<ApplicationEvent> APPLICATION_EXIT =
            new EventType<>(ApplicationEvent.ANY, "APPLICATION_EXIT");

    public ApplicationEvent(final @NamedArg("source") Application source,
                            final @NamedArg("target") EventTarget target,
                            final @NamedArg("eventType") EventType<? extends Event> eventType) {
        super(source, target, eventType);
    }

    public static ApplicationEvent exitApplicationEvent(EventTarget target) {
        return new ApplicationEvent(BudgetManagementApplication.getApplication(), target, APPLICATION_EXIT);
    }
}
