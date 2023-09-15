package net.alive.implement.modules.movement;

import net.alive.api.event.annotation.Subscribe;
import net.alive.api.event.listener.impl.Listener;
import net.alive.api.module.Category;
import net.alive.api.module.Module;
import net.alive.api.module.ModuleInfo;
import net.alive.implement.events.player.SprintEvent;

@ModuleInfo(name = "Sprint", displayName = "Sprint", keyBind = 0, category = Category.MOVEMENT)
public class Sprint extends Module {

    @Subscribe
    public Listener<SprintEvent> doSprinting = new Listener<>(event -> {
        if (mc.thePlayer.moveStrafing == 0 && mc.thePlayer.moveForward == 0) return;
        event.setSprint(true);
        mc.thePlayer.setSprinting(true);
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
