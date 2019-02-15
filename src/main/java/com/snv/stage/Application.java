package com.snv.stage;

import com.snv.stage.dispatcher.ApplicationEventDispatcher;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.*;

public class Application implements EventTarget {

    private ApplicationEventDispatcher internalEventDispatcher;

    /**
     * Specifies the event dispatcher for this node. The default event
     * dispatcher sends the received events to the registered event handlers and
     * filters. When replacing the value with a new {@code EventDispatcher},
     * the new dispatcher should forward events to the replaced dispatcher
     * to maintain the node's default event handling behavior.
     */
    private ObjectProperty<EventDispatcher> eventDispatcher;

    public final EventDispatcher getEventDispatcher() {
        return eventDispatcherProperty().get();
    }

    public final void setEventDispatcher(EventDispatcher value) {
        eventDispatcherProperty().set(value);
    }

    public final ObjectProperty<EventDispatcher> eventDispatcherProperty() {
        initializeInternalEventDispatcher();
        return eventDispatcher;
    }

    @Override
    public EventDispatchChain buildEventDispatchChain(EventDispatchChain tail) {
        if (eventDispatcher != null) {
            final EventDispatcher eventDispatcherValue = eventDispatcher.get();
            if (eventDispatcherValue != null) {
                tail = tail.prepend(eventDispatcherValue);
            }
        }

        return tail;
    }

    /**
     * Registers an event handler to this node. The handler is called when the
     * node receives an {@code Event} of the specified type during the bubbling
     * phase of event delivery.
     *
     * @param <T>          the specific event class of the handler
     * @param eventType    the type of the events to receive by the handler
     * @param eventHandler the handler to register
     * @throws NullPointerException if the event type or handler is null
     */
    public final <T extends Event> void addEventHandler(
            final EventType<T> eventType,
            final EventHandler<? super T> eventHandler) {
        getInternalEventDispatcher().getEventHandlerManager()
                .addEventHandler(eventType, eventHandler);
    }

    private ApplicationEventDispatcher getInternalEventDispatcher() {
        initializeInternalEventDispatcher();
        return internalEventDispatcher;
    }

    private void initializeInternalEventDispatcher() {
        if (internalEventDispatcher == null) {
            internalEventDispatcher = createInternalEventDispatcher();
            eventDispatcher = new SimpleObjectProperty<>(
                    this,
                    "eventDispatcher",
                    internalEventDispatcher);
        }
    }

    private ApplicationEventDispatcher createInternalEventDispatcher() {
        return new ApplicationEventDispatcher(this);
    }
}
