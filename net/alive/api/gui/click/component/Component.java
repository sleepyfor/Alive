package net.alive.api.gui.click.component;

import lombok.Getter;
import lombok.Setter;
import net.alive.Client;
import net.alive.api.gui.click.ClickGUI;
import net.alive.utils.gui.CustomFontRenderer;

@Getter
@Setter
public class Component {
    public CustomFontRenderer font = Client.INSTANCE.getFontManager().getArial17();
    public float x, y, width, height;
    public ClickGUI parent;

    public void drawComponent(){
    }
}
