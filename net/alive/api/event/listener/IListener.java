package net.alive.api.event.listener;

import java.io.Serializable;

/**
 * @author SoonTM
 * @since 7/19/2019
 */
@FunctionalInterface
public interface IListener<T> extends Serializable {
    void invoke(T event);
}
