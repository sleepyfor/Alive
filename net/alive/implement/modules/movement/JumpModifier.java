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
    public Value<Double> jumpHeight = new Value<>("Jump Height", 0.6, 0.1, 3.0, 0.1);
    public Value<Double> moveSpeed = new Value<>("Speed", 1.8, 0.1, 3.0, 0.1);
    public static Value<Boolean> glide = new Value<>("Glide", true);
    public Value<Double> glideSpeed = new Value<>("Glide Speed", 0.05, 0.01, 0.1, 0.01);

    @Subscribe
    public Listener<PlayerUpdateEvent> update = new Listener<>(event -> {
        var df = new DecimalFormat("##.##");
        setSuffix(df.format(jumpHeight.getValueObject()) + " | " + df.format(moveSpeed.getValueObject()));
        if (mc.thePlayer.isMoving() && mc.thePlayer.onGround)
            PlayerUtils.getJump(mc.thePlayer.motionY = jumpHeight.getValueObject());
        if (mc.thePlayer.isMoving() && !mc.thePlayer.onGround)
            PlayerUtils.setSpeed(PlayerUtils.getBaseMoveSpeed() * moveSpeed.getValueObject());
        if (glide.getValueObject() && (!mc.thePlayer.onGround && mc.thePlayer.fallDistance >= 0.2))
            mc.thePlayer.motionY += glideSpeed.getValueObject();
    });
}
