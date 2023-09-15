package net.alive.implement.events.player;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.alive.api.event.IEvent;
import net.alive.api.event.state.EventState;
import net.alive.api.event.state.IState;

@AllArgsConstructor @Getter @Setter
public class PlayerUpdateEvent implements IEvent, IState {
    private float yaw, pitch, prevYaw, PrevPitch;
    private double x, y, z, prevX, prevY, prevZ;
    private EventState state;
    private boolean ground;
}