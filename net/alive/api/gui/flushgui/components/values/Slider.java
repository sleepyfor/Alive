package net.alive.api.gui.flushgui.components.values;

import lombok.var;
import net.alive.api.gui.flushgui.FlushGUI;
import net.alive.api.gui.flushgui.FlushUtils;
import net.alive.api.gui.flushgui.components.Component;
import net.alive.api.value.Value;
import net.alive.implement.modules.render.Hud;

import java.awt.*;
import java.text.DecimalFormat;

public class Slider extends Component {
    public FlushUtils animation;
    public Value<Double> value;
    public boolean hovered;
    public FlushGUI parent;
    public boolean sliding;

    public Slider(Value value, FlushGUI parent){
        animation = new FlushUtils();
        this.value = value;
        this.parent = parent;
        sliding = false;
    }

    @Override
    public void drawComponent() {
        var color = new Color(Hud.red.getValueObject().intValue(), Hud.green.getValueObject().intValue(), Hud.blue.getValueObject().intValue(), 185);
        var decimal = new DecimalFormat("#.####");
        var valRounded = decimal.format(value.getValueObject());
        animation.drawAnimatedSlider(value.getValueName(), valRounded, x, y, width,
                height, new Color(255,255,255,185), color, hovered, value, this);
    }
}
