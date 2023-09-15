package net.alive.api.gui.click.component.value;

import net.alive.api.gui.click.component.Component;
import net.alive.api.value.Value;
import net.alive.api.value.types.BooleanValue;
import net.alive.utils.gui.RenderingUtils;

import java.awt.*;

public class BooleanComponent extends Component {

    public Value value;

    public BooleanComponent(Value value){
        this.value = value;
    }

    @Override
    public void drawComponent() {
        setX(88);
        setY(34);
        setWidth(10);
        setHeight(5);
        RenderingUtils.drawRectangle(x, y, x + width, y + height, new Color(25, 25, 25, 255).getRGB());
    }
}
