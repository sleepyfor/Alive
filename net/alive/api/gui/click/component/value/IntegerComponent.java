package net.alive.api.gui.click.component.value;

import lombok.Getter;
import net.alive.api.gui.click.component.Component;
import net.alive.api.value.Value;
import net.alive.utils.gui.RenderingUtils;

import java.awt.*;

@Getter
public class IntegerComponent extends Component {

    public Value<Integer> value;

    public IntegerComponent(Value value){
        this.value = value;
    }
}
