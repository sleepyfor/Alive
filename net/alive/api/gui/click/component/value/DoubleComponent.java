package net.alive.api.gui.click.component.value;

import lombok.Getter;
import net.alive.api.gui.click.component.Component;
import net.alive.api.value.Value;

@Getter
public class DoubleComponent extends Component {

    public Value<Double> value;
    public boolean sliding;

    public DoubleComponent(Value value){
        this.value = value;
    }
}
