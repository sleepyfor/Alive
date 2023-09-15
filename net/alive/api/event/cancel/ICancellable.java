package net.alive.api.event.cancel;

public interface ICancellable {
    boolean isCancelled();
    void setCancelled(boolean cancelled);
}
