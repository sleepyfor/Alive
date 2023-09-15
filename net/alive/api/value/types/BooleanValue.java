package net.alive.api.value.types;

import lombok.Getter;
import lombok.Setter;
import net.alive.api.value.Value;

public class BooleanValue extends Value {

    @Getter
    @Setter
    public boolean value;

    public BooleanValue(String name, boolean value) {
        this.name = name;
        this.value = value;
    }
}
