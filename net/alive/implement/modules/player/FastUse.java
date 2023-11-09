package net.alive.implement.modules.player;

import net.alive.api.event.annotation.Subscribe;
import net.alive.api.event.listener.impl.Listener;
import net.alive.api.module.Category;
import net.alive.api.module.Module;
import net.alive.api.module.ModuleInfo;
import net.alive.api.value.Value;
import net.alive.implement.events.player.PlayerUpdateEvent;
import net.minecraft.network.play.client.C03PacketPlayer;

@ModuleInfo(name = "Fast Use", keyBind = 0, category = Category.PLAYER)
public class FastUse extends Module {
    public Value<Double> packets = new Value<>("Packets", 4.0, 1, 25, 1);

    @Subscribe
    public Listener<PlayerUpdateEvent> update = new Listener<>(event -> {
        setSuffix(String.valueOf(packets.getValueObject().intValue()));
        if (mc.thePlayer.isEating())
            for (int i = 0; i < packets.getValueObject(); i++)
                mc.thePlayer.sendQueue.addToSendQueueEvent(new C03PacketPlayer());
    });
}
