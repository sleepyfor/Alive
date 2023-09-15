package net.alive.implement.events.player;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.alive.api.event.IEvent;

@AllArgsConstructor @Getter @Setter
public class MovementEvent implements IEvent {
    private double x, y, z;
}
