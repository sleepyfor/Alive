package net.alive.implement.modules.movement;

import net.alive.api.event.annotation.Subscribe;
import net.alive.api.event.listener.impl.Listener;
import net.alive.api.module.Category;
import net.alive.api.module.Module;
import net.alive.api.module.ModuleInfo;
import net.alive.api.value.Value;
import net.alive.implement.events.player.PlayerUpdateEvent;
import net.alive.implement.events.render.Render2DEvent;
import net.alive.utils.player.PlayerUtils;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;

@ModuleInfo(name = "Flight", keyBind = Keyboard.KEY_F, category = Category.MOVEMENT)
public class Flight extends Module {

    public Value<ArrayList<String>> mode = new Value<>("Mode", "Creative", "Creative", "Vanilla");
    public Value<Double> flightSpeed = new Value<>("Speed", 0.8, 0.1, 2., 0.05);
    public Value<Boolean> ground = new Value<>("Ground", false);

    @Subscribe
    public Listener<PlayerUpdateEvent> update = new Listener<>(event -> {
        setSuffix(getMode("Mode"));
        switch (getMode("Mode")) {
            case "Creative":
                mc.thePlayer.capabilities.isFlying = true;
                mc.thePlayer.capabilities.setFlySpeed(flightSpeed.getValueObject().floatValue() / 10);
                break;
            case "Vanilla":
                setSuffix(ground.getValueObject() ? getMode("Mode") + "Ground" : getMode("Mode"));
                mc.thePlayer.onGround = ground.getValueObject();
                mc.thePlayer.motionY = (mc.thePlayer.movementInput.jump ? flightSpeed.getValueObject() : mc.thePlayer.movementInput.sneak ? -flightSpeed.getValueObject() : 0);
                PlayerUtils.setSpeed(mc.thePlayer.moveForward != 0 || mc.thePlayer.moveStrafing != 0 ? flightSpeed.getValueObject() : 0);
                break;
        }
    });

    @Override
    public void onDisable() {
        if (mc.theWorld == null) return;
        mc.thePlayer.capabilities.isFlying = false;
        PlayerUtils.setSpeed(0.2);
        super.onDisable();
    }
}
