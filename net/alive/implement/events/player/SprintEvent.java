package net.alive.implement.events.player;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.alive.api.event.IEvent;

@AllArgsConstructor
@Getter
@Setter
public class SprintEvent implements IEvent {
    private boolean sprint;
}
