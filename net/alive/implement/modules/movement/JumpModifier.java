package net.alive.implement.modules.movement;

import lombok.var;
import net.alive.api.event.annotation.Subscribe;
import net.alive.api.event.listener.impl.Listener;
import net.alive.api.module.Category;
import net.alive.api.module.Module;
import net.alive.api.module.ModuleInfo;
import net.alive.api.value.Value;
import net.alive.implement.events.player.PlayerUpdateEvent;
import net.alive.utils.player.PlayerUtils;

import java.text.DecimalFormat;

@ModuleInfo(name = "Jump Modifier", keyBind = 0, category = Category.MOVEMENT)
public class JumpModifier extends Module {
    public static Value<Boolean> disable = new Value<>("Auto Disable", true);
    public static Value<Boolean> glide = new Value<>("Glide", true);
    public Value<Double> glideSpeed = new Value<>("Glide Speed", 0.02, 0.01, 0.02, 0.01);
    public Value<Double> jumpHeight = new Value<>("Jump Height", 0.42, 0.1, 3.5, 0.01);
    public Value<Double> moveSpeed = new Value<>("Speed", 1.8, 0.1, 4.0, 0.1);
    public boolean grounded;

    @Subscribe
    public Listener<PlayerUpdateEvent> update = new Listener<>(event -> {
        var df = new DecimalFormat("##.##");
        setSuffix(df.format(jumpHeight.getValueObject()) + " | " + df.format(moveSpeed.getValueObject()));
        if (mc.thePlayer.isMoving() && mc.thePlayer.onGround && !grounded)
            PlayerUtils.getJump(mc.thePlayer.motionY = jumpHeight.getValueObject());
        if (mc.thePlayer.isMoving() && !mc.thePlayer.onGround)
            PlayerUtils.setSpeed(PlayerUtils.getBaseMoveSpeed() * moveSpeed.getValueObject());
        if (glide.getValueObject() && (!mc.thePlayer.onGround && mc.thePlayer.fallDistance >= 0.2))
            mc.thePlayer.motionY += glideSpeed.getValueObject();
        if (mc.thePlayer.fallDistance >= 0.00001 && disable.getValueObject())
            grounded = true;
        if (mc.thePlayer.onGround && grounded && disable.getValueObject())
            toggle();
    });

    @Override
    public void onEnable() {
        grounded = false;
        super.onEnable();
    }

    @Override
    public void onDisable() {
        grounded = false;
        super.onDisable();
    }
}
