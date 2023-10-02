package net.alive.api.gui.tab.tabs;

import lombok.Getter;
import lombok.Setter;
import net.alive.Client;
import net.alive.api.gui.tab.TabGui;
import net.alive.api.gui.tab.tabs.impl.CategoryTab;
import net.minecraft.client.gui.Gui;

import java.awt.*;

@Getter@Setter
public class Tab {
    public double width, height, offset;
    public TabGui parent;
    public String text;
    public int color;

    public Tab(String text, double width, double height, TabGui parent) {
        this.text = text;
        this.width = width;
        this.height = height;
        this.parent = parent;
    }

    public void drawTab() {
//        Gui.drawRect(parent.getX(), parent.getY(), parent.getX() + width, parent.getY() + height, new Color(10, 10, 10, 200).getRGB());
//        Client.INSTANCE.getFontManager().getArial17().drawStringWithShadow(text, (float) (parent.getX() + 2 + offset), (float) (parent.getY() + 6), color);
    }
}
