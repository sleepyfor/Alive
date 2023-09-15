package net.alive.implement.events.render;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.alive.api.event.IEvent;
import net.minecraft.entity.Entity;

@AllArgsConstructor @Getter @Setter
public class Render3DEvent implements IEvent {
    private float partialTicks;
    private Entity entity;
}
