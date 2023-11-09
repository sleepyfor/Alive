package net.alive.implement.events.player;

import net.alive.api.event.IEvent;
import net.alive.api.event.cancel.ICancellable;

public class SafewalkEvent implements IEvent, ICancellable {
    private boolean cancelled;

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {

    }
}
