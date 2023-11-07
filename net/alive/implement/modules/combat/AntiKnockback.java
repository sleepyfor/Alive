package net.alive.implement.modules.combat;

import net.alive.api.event.annotation.Subscribe;
import net.alive.api.event.listener.impl.Listener;
import net.alive.api.module.Category;
import net.alive.api.module.Module;
import net.alive.api.module.ModuleInfo;
import net.alive.implement.events.packet.PacketEvent;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S27PacketExplosion;

@ModuleInfo(name = "Anti Knockback", keyBind = 0, category = Category.COMBAT)
public class AntiKnockback extends Module {

    @Subscribe
    public Listener<PacketEvent> packet = new Listener<>(event -> {
        if(event.getPacket() instanceof S27PacketExplosion || event.getPacket() instanceof S12PacketEntityVelocity)
            event.setCancelled(true);
    });

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }
}
