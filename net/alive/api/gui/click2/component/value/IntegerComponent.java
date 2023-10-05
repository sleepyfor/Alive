package net.alive.api.gui.click2.component.value;

import lombok.Getter;
import net.alive.api.gui.click2.component.Component;
import net.alive.api.value.Value;

@Getter
public class IntegerComponent extends Component {

    public Value<Integer> value;
    public boolean sliding;

    public IntegerComponent(Value value){
        this.value = value;
    }
}
