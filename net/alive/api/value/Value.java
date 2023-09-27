package net.alive.api.value;

import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class Value<T> {

    private String valueName;
    private T valueObject;
    private double min;
    private double max;
    private boolean isCombo;
    private double inc;
    private ArrayList<String> combo;

    public Value(String valueName, T valueObject) {
        this.valueName = valueName;
        this.valueObject = valueObject;
        this.min = 0;
        this.max = 0;
        isCombo = false;
    }

    public Value(String valueName, T valueObject, double min, double max, double inc) {
        this.valueName = valueName;
        this.valueObject = valueObject;
        this.min = min;
        this.max = max;
        this.inc = inc;
        isCombo = false;
    }

    public Value(String valueName, T valueObject, int min, int max, int inc) {
        this.valueName = valueName;
        this.valueObject = valueObject;
        this.min = min;
        this.max = max;
        this.inc = inc;
        isCombo = false;
    }

    public Value(String valueName, String selectedName, ArrayList<String> combo) {
        this.valueName = valueName;
        this.valueObject = (T) selectedName;
        this.combo = combo;
        isCombo = true;
    }

    public Value(String valueName, String selectedName, String... modes) {
        this.valueName = valueName;
        this.valueObject = (T) selectedName;
        this.combo = Lists.newArrayList(modes);
        isCombo = true;
    }
}
