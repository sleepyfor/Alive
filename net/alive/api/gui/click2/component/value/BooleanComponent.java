package net.alive.api.gui.click2.component.value;

import lombok.Getter;
import net.alive.api.gui.click2.component.Component;
import net.alive.api.value.Value;
import net.alive.utils.gui.RenderingUtils;

import java.awt.*;

@Getter
public class BooleanComponent extends Component {

    public Value<Boolean> value;

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
