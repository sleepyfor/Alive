package net.alive.api.event.dispatch;

import net.alive.api.event.listener.impl.Listener;

import java.util.Iterator;

/**
 * @author SoonTM
 * @since 8/4/2019
 */
@FunctionalInterface
public interface IDispatcher {
    <T> void dispatch(T event, Iterator<Listener<T>> listeners);
}
