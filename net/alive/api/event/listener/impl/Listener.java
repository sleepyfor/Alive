package net.alive.api.event.listener.impl;

import net.alive.api.event.listener.IListener;
import net.alive.api.event.priority.Prioritized;
import net.alive.api.event.priority.Priority;
import net.alive.api.event.utility.ReflectionUtil;

/**
 * @author SoonTM
 * @since 7/19/2019
 */
public final class Listener<T> implements IListener<T>, Prioritized, Comparable<Listener<?>> {

    private final IListener<T> listener;
    private final Class<T> target;
    private Priority priority;

    public Listener(IListener<T> listener) {
        this.listener = listener;
        this.target = ReflectionUtil.resolveListenerTarget(listener);
    }

    @Override
    public void invoke(T event) {
        listener.invoke(event);
    }

    public IListener<T> getListener() {
        return listener;
    }

    public Class<T> getTarget() {
        return target;
    }

    @Override
    public Priority getPriority() {
        return priority;
    }

    @Override
    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    @Override
    public int compareTo(Listener<?> listener) {
        final int discriminant = Integer.compare(priority.getValue(), listener.getPriority().getValue());
        return discriminant == 0 ? System.identityHashCode(listener) - System.identityHashCode(this) : discriminant;
    }
}
