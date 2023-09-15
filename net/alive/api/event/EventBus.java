package net.alive.api.event;

import net.alive.api.event.dispatch.IDispatcher;
import net.alive.api.event.dispatch.impl.Dispatcher;
import net.alive.api.event.registry.IRegistry;
import net.alive.api.event.registry.impl.ListenerRegistry;
/**
 * A tiny, but effective Java eventbus.
 * @author SoonTM
 * @version 1.0
 * @since 7/19/2019
 */
@SuppressWarnings("unchecked")
public final class EventBus<E> {

    private final IRegistry<E> listenerRegistry;
    private final IDispatcher dispatcher;

    public EventBus() {
        listenerRegistry = new ListenerRegistry();
        dispatcher = new Dispatcher();
    }

    public void register(Object parent) {
        listenerRegistry.register(parent);
    }

    public void unregister(Object parent) {
        listenerRegistry.unregister(parent);
    }

    public void call(E event) {
        listenerRegistry.findSubscribers(event).ifPresent(it -> dispatcher.dispatch(event, it));
    }

}
