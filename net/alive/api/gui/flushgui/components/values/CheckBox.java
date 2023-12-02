package net.alive.api.gui.flushgui.components.values;

import lombok.var;
import net.alive.api.gui.flushgui.FlushGUI;
import net.alive.api.gui.flushgui.FlushUtils;
import net.alive.api.gui.flushgui.components.Component;
import net.alive.api.module.Module;
import net.alive.api.value.Value;
import net.alive.implement.modules.render.Hud;
import org.lwjgl.input.Keyboard;

import java.awt.*;

public class CheckBox extends Component {
    public FlushUtils animation;
    public Value<Boolean> value;
    public boolean hovered;
    public FlushGUI parent;

    public CheckBox(Value value, FlushGUI parent){
        animation = new FlushUtils();
        this.value = value;
        this.parent = parent;
    }

    @Override
    public void drawComponent() {
        var color = new Color(Hud.red.getValueObject().intValue(), Hud.green.getValueObject().intValue(), Hud.blue.getValueObject().intValue(), 185);
        animation.drawAnimatedCheckbox(value.getValueName(), value.getValueObject() ? "Yes" : "No", x, y, width,
                height, new Color(255,255,255,185), color, value.getValueObject(), hovered);
    }
}
