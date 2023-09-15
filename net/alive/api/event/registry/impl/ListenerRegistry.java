package net.alive.api.event.registry.impl;

import net.alive.api.event.annotation.Subscribe;
import net.alive.api.event.listener.impl.Listener;
import net.alive.api.event.registry.IRegistry;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * @author SoonTM
 * @since 8/3/2019
 */
public final class ListenerRegistry<T> implements IRegistry<T> {

    private final ConcurrentMap<Class<?>, SortedSet<Listener<?>>> listenerMap = new ConcurrentHashMap<>();

    @Override
    public void register(Object parent) {
        Class parentClass = parent.getClass();
        for (Field field : parentClass.getDeclaredFields()) {
            if (isValidField(field)) {
                Subscribe info = field.getAnnotation(Subscribe.class);
                Listener<?> listener = Objects.requireNonNull(create(parent, field, Listener.class));
                listener.setPriority(info.priority());
                listenerMap.computeIfAbsent(listener.getTarget() , l -> new ConcurrentSkipListSet<>()).add(listener);
            }
        }
    }

    @Override
    public void unregister(Object parent) {
        Class parentClass = parent.getClass();
        for (Field field : parentClass.getDeclaredFields()) {
            if (isValidField(field)) {
                Subscribe info = field.getAnnotation(Subscribe.class);
                Listener<?> listener = Objects.requireNonNull(create(parent, field, Listener.class));
                listener.setPriority(info.priority());
                if (listenerMap.containsKey(listener.getTarget())) {
                    for (int i = 0; i < listenerMap.get(listener.getTarget()).size(); i++) {
                        listenerMap.get(listener.getTarget()).remove(listener);
                    }
                }
            }
        }
    }

    private boolean isValidField(Field field) {
        return Listener.class.isAssignableFrom(field.getType()) && field.isAnnotationPresent(Subscribe.class);
    }

    private Listener create(Object parent, Field field, final Class<Listener> fieldClass) {
        if (!Objects.requireNonNull(field, "Field cannot be null").isAccessible())
            field.setAccessible(true);
        try {
            return Objects.requireNonNull(fieldClass, "Field type class cannot be null").cast(field.get(parent));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Optional<Iterator<Listener<T>>> findSubscribers(T event) {
        final SortedSet<Listener<T>> listeners = (SortedSet<Listener<T>>) (SortedSet) listenerMap.get(Objects.requireNonNull(event, "Event to be called cannot be null").getClass());
        return Optional.ofNullable(listeners).filter(set -> !set.isEmpty()).map(Set::iterator);
    }
}

