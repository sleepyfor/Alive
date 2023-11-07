package net.alive.implement.modules.player;

import net.alive.api.event.annotation.Subscribe;
import net.alive.api.event.listener.impl.Listener;
import net.alive.api.module.Category;
import net.alive.api.module.Module;
import net.alive.api.module.ModuleInfo;
import net.alive.api.value.Value;
import net.alive.implement.events.player.PlayerUpdateEvent;
import net.alive.implement.modules.movement.JumpModifier;
import net.alive.utils.player.PlayerUtils;
import net.minecraft.network.play.client.C03PacketPlayer;

import java.util.List;

@ModuleInfo(name = "No Fall", keyBind = 0, category = Category.PLAYER)
public class NoFall extends Module {
    public Value<String> mode = new Value<>("Mode", "Packet", "Packet", "GroundSpoof");
    public Value<Boolean> blockCheck = new Value<>("Block Check", true);
    public Value<Boolean> ticks = new Value<>("Tick", true);
    public Value<Double> ticksExisted = new Value<>("Ticks Existed", 3., 1, 6, 1);

    @Subscribe
    public Listener<PlayerUpdateEvent> update = new Listener<>(event -> {
        switch (getMode("Mode")) {
            case "Packet":
                if (mc.thePlayer.fallDistance > 3)
                    mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(!ticks.getValueObject() || mc.thePlayer.ticksExisted % ticksExisted.getValueObject() == 0));
                if (blockCheck.getValueObject() && PlayerUtils.blockUnder(mc.thePlayer) && !JumpModifier.glide.getValueObject())
                    mc.thePlayer.fallDistance = 0;
                break;
            case "GroundSpoof":
                if (mc.thePlayer.fallDistance > 3)
                    event.setGround(!ticks.getValueObject() || mc.thePlayer.ticksExisted % ticksExisted.getValueObject() == 0);
                if (blockCheck.getValueObject() && PlayerUtils.blockUnder(mc.thePlayer) && !JumpModifier.glide.getValueObject())
                    mc.thePlayer.fallDistance = 0;
                break;
        }
    });
}
