package net.alive.implement.events.render;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.alive.api.event.IEvent;
import net.minecraft.client.gui.ScaledResolution;

@AllArgsConstructor @Getter @Setter
public class Render2DEvent implements IEvent {
    private ScaledResolution scaledResolution;
    private float partialTicks;
}
