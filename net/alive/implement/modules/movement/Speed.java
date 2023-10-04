package net.alive.implement.modules.movement;

import net.alive.api.event.annotation.Subscribe;
import net.alive.api.event.listener.impl.Listener;
import net.alive.api.module.Category;
import net.alive.api.module.Module;
import net.alive.api.module.ModuleInfo;
import net.alive.api.value.Value;
import net.alive.implement.events.player.PlayerUpdateEvent;
import net.alive.utils.player.PlayerUtils;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;

@ModuleInfo(name = "Speed", keyBind = Keyboard.KEY_V, category = Category.MOVEMENT)
public class Speed extends Module {

    public Value<ArrayList<String>> mode = new Value<>("Mode", "Vanilla", "Vanilla", "NoCheatPlus");
    public Value<Double> speed = new Value<>("Speed", 0.3, 0.1, 1, 0.01);
    public Value<Double> height = new Value<>("HopHeight", 0.4, 0.05, 0.6, 0.01);
    public Value<Double> fallSpeed = new Value<>("Fall Speed", 0.005, 0, 0.3, 0.005);
    public Value<Boolean> hop = new Value<>("Hop", false);
    public Value<Boolean> fastFall = new Value<>("Fast Fall", false);
    public double moveSpeed;
    public boolean slow;

    @Subscribe
    public Listener<PlayerUpdateEvent> update = new Listener<>(event -> {
        setSuffix(getMode("Mode"));
        switch (getMode("Mode")) {
            case "Vanilla":
                setSuffix(height.getValueObject() >= 0.45 ? "Highhop": (height.getValueObject() <= 0.1 ? "Yport" : (height.getValueObject() <= 0.3 ? "Lowhop" : (hop.getValueObject() ? "Bhop" : getMode("Mode")))));
                if(mc.thePlayer.moveForward != 0 || mc.thePlayer.moveStrafing != 0) {
                    if (mc.thePlayer.onGround && hop.getValueObject())
                        mc.thePlayer.motionY = height.getValueObject();
                    if(fastFall.getValueObject() && !mc.thePlayer.onGround)
                        mc.thePlayer.motionY -= fallSpeed.getValueObject();
                    PlayerUtils.setSpeed(speed.getValueObject());
                }
                break;
            case "NoCheatPlus":
                if(mc.thePlayer.moveForward != 0 || mc.thePlayer.moveStrafing != 0) {
                    if(mc.thePlayer.onGround){
                        mc.thePlayer.motionY = PlayerUtils.getJump(0.42F);
                        moveSpeed = PlayerUtils.getBaseMoveSpeed() * 1.47;
                        slow = true;
                    }else {
                        if(slow) {
                            moveSpeed -= 0.36 * (moveSpeed - PlayerUtils.getBaseMoveSpeed());
                            mc.thePlayer.motionY -= 0.0015;
                            slow =  false;
                        }
                        else{
                            moveSpeed -= moveSpeed / 159;

                                mc.thePlayer.motionY -= 0.0015;
                        }
                    }
                    PlayerUtils.setSpeed(moveSpeed);
                }
                //setSuffix(ground.getValueObject() ? getMode("Mode") + "Ground" : getMode("Mode"));
//                mc.thePlayer.onGround = ground.getValueObject();
//                mc.thePlayer.motionY = (mc.thePlayer.movementInput.jump ? flightSpeed.getValueObject() : mc.thePlayer.movementInput.sneak ? - flightSpeed.getValueObject() : 0);
//                    PlayerUtils.setSpeed(mc.thePlayer.moveForward != 0 || mc.thePlayer.moveStrafing != 0 ? flightSpeed.getValueObject() : 0);
                break;
        }
    });

    @Override
    public void onDisable() {
        if (mc.theWorld == null) return;
        PlayerUtils.setSpeed(0.2);
        super.onDisable();
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }
}
