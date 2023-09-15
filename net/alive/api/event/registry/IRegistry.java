package net.alive.api.event.registry;

import net.alive.api.event.listener.impl.Listener;

import java.util.Iterator;
import java.util.Optional;

/**
 * @author SoonTM
 * @since 8/3/2019
 */
public interface IRegistry<T> {

    void register(Object parent);
    void unregister(Object parent);

    Optional<Iterator<Listener<T>>> findSubscribers(T event);
}
