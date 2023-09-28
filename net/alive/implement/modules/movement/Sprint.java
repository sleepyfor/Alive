package net.alive.implement.modules.movement;

import net.alive.api.event.annotation.Subscribe;
import net.alive.api.event.listener.impl.Listener;
import net.alive.api.module.Category;
import net.alive.api.module.Module;
import net.alive.api.module.ModuleInfo;
import net.alive.api.value.Value;
import net.alive.implement.events.player.SprintEvent;

import java.util.ArrayList;

@ModuleInfo(name = "Sprint", displayName = "Sprint", keyBind = 0, category = Category.MOVEMENT)
public class Sprint extends Module {

    public Value<ArrayList<String>> mode = new Value<>("Mode", "Multi", "Simple", "Multi");

    @Subscribe
    public Listener<SprintEvent> doSprinting = new Listener<>(event -> {
        setSuffix(getMode("Mode"));
        switch ((String) getValue("Mode").getValueObject()) {
            case "Multi":
                if (mc.thePlayer.moveStrafing == 0 && mc.thePlayer.moveForward == 0) return;
                event.setSprint(true);
                mc.thePlayer.setSprinting(true);
                break;
            case "Simple":
                if (mc.thePlayer.moveForward <= 0) return;
                event.setSprint(true);
                mc.thePlayer.setSprinting(true);
                break;
        }
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
