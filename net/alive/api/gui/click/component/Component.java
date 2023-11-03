package net.alive.api.gui.click.component;

import lombok.Getter;
import lombok.Setter;
import net.alive.api.gui.click.ClickGUI;

@Getter
@Setter
public class Component {
    public float x, y, width, height;
    public ClickGUI parent;

    public void drawComponent(){
    }
}
