package net.alive.implement.modules.player;

import lombok.var;
import net.alive.api.event.annotation.Subscribe;
import net.alive.api.event.listener.impl.Listener;
import net.alive.api.module.Category;
import net.alive.api.module.Module;
import net.alive.api.module.ModuleInfo;
import net.alive.api.value.Value;
import net.alive.implement.events.player.PlayerUpdateEvent;
import net.minecraft.network.play.client.C03PacketPlayer;

import java.text.DecimalFormat;

@ModuleInfo(name = "Fast Place", keyBind = 0, category = Category.PLAYER)
public class FastPlace extends Module {
    public Value<Double> placeDelay = new Value<>("Place Delay", 1.0, 0, 4, 1);

    @Subscribe
    public Listener<PlayerUpdateEvent> update = new Listener<>(event -> {
        setSuffix(String.valueOf(placeDelay.getValueObject().intValue()));
        mc.rightClickDelayTimer = placeDelay.getValueObject().intValue();
    });
}
