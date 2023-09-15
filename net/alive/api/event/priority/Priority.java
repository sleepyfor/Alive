package net.alive.api.event.priority;

/**
 * @author SoonTM
 * @since 7/19/2019
 */
public enum Priority {
    LOW(3),
    NORMAL(2),
    HIGH(1);

    private final int value;

    Priority(int priority) {
        this.value = priority;
    }

    public final int getValue() {
        return value;
    }
}
