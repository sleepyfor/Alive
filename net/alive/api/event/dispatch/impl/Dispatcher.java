package net.alive.api.event.dispatch.impl;

import net.alive.api.event.dispatch.IDispatcher;
import net.alive.api.event.listener.impl.Listener;

import java.util.Iterator;
import java.util.concurrent.Executor;

/**
 * @author SoonTM
 * @since 8/4/2019
 */
public final class Dispatcher implements IDispatcher {

    private final Executor executor = Runnable::run;

    @Override
    public <T> void dispatch(T event, Iterator<Listener<T>> listeners) {
        while (listeners.hasNext()) {
            Listener<T> listener = listeners.next();
            executor.execute(() -> {
                try {
                    listener.invoke(event);
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
