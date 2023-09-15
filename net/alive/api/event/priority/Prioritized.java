package net.alive.api.event.priority;

/**
 * @author SoonTM
 * @since 8/4/2019
 */
public interface Prioritized {
    Priority getPriority();
    void setPriority(Priority priority);
}
