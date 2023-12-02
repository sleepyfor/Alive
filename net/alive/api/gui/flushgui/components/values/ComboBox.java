package net.alive.api.gui.flushgui.components.values;

import lombok.var;
import net.alive.api.gui.flushgui.FlushGUI;
import net.alive.api.gui.flushgui.FlushUtils;
import net.alive.api.gui.flushgui.components.Component;
import net.alive.api.value.Value;
import net.alive.implement.modules.render.Hud;

import java.awt.*;

public class ComboBox extends Component {
    public FlushUtils animation;
    public Value<String> value;
    public boolean hovered;
    public FlushGUI parent;
    public int index;

    public ComboBox(Value value, FlushGUI parent){
        animation = new FlushUtils();
        this.value = value;
        this.parent = parent;
        index = 0;
    }

    @Override
    public void drawComponent() {
        var color = new Color(Hud.red.getValueObject().intValue(), Hud.green.getValueObject().intValue(), Hud.blue.getValueObject().intValue(), 185);
        animation.drawAnimatedCombobox(value.getValueName(), value.getValueObject(), x, y, width,
                height, new Color(255,255,255,185), color, hovered);
    }

    public void setValue() {
        index++;
        if (index >= value.getCombo().size())
            index = 0;
        value.setValueObject(value.getCombo().get(index));
    }
}
