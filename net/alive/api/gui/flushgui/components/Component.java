package net.alive.api.gui.flushgui.components;

import lombok.Getter;
import lombok.Setter;
import net.alive.api.gui.click.ClickGUI;

@Getter @Setter
public abstract class Component {

    public float x, y, width, height;
    public ClickGUI parent;

    public abstract void drawComponent();
}
