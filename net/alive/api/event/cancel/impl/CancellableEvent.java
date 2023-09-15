package net.alive.api.event.cancel.impl;

import net.alive.api.event.IEvent;
import net.alive.api.event.cancel.ICancellable;

public class CancellableEvent implements IEvent, ICancellable {
    private boolean cancelled;

    @Override
    public void setCancelled(boolean state) {
        cancelled = state;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }
}
