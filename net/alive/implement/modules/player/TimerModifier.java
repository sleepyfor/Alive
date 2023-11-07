package net.alive.implement.modules.player;

import lombok.var;
import net.alive.api.event.annotation.Subscribe;
import net.alive.api.event.listener.impl.Listener;
import net.alive.api.module.Category;
import net.alive.api.module.Module;
import net.alive.api.module.ModuleInfo;
import net.alive.api.value.Value;
import net.alive.implement.events.player.PlayerUpdateEvent;

import java.text.DecimalFormat;

@ModuleInfo(name = "Timer Modifier", keyBind = 0, category = Category.PLAYER)
public class TimerModifier extends Module {
    public Value<Boolean> ticks = new Value<>("Ticks", false);
    public Value<Double> ticksExisted = new Value<>("Ticks Existed", 3., 2, 20, 1);
    public Value<Double> timerSpeed = new Value<>("Timer Speed", 2., .1, 3, .05);

    @Subscribe
    public Listener<PlayerUpdateEvent> update = new Listener<>(event -> {
        var df = new DecimalFormat("##.##");
        setSuffix(df.format(timerSpeed.getValueObject()));
       mc.timer.timerSpeed = ticks.getValueObject() ? (mc.thePlayer.ticksExisted % ticksExisted.getValueObject() == 0 ?
               timerSpeed.getValueObject().floatValue() : 1.0f) : timerSpeed.getValueObject().floatValue();
    });

    @Override
    public void onDisable() {
        super.onDisable();
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }
}
