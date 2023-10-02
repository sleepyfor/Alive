package net.alive.api.gui.tab.tabs.impl;

import lombok.Setter;
import net.alive.Client;
import net.alive.api.gui.tab.TabGui;
import net.alive.api.gui.tab.tabs.Tab;
import net.alive.api.module.Category;
import net.alive.api.module.Module;
import net.alive.api.module.ModuleInfo;
import net.minecraft.client.gui.Gui;

import java.awt.*;

public class ModuleTab extends Tab {

    @Setter
    public double x, y;
    public Module module;

    public ModuleTab(String text, double x, double  y, double width, double height, TabGui parent, Module module) {
        super(text, width, height, parent);
        this.x = x;
        this.y = y;
        this.module = module;
    }

    @Override
    public void drawTab() {
        Gui.drawRect(x, y, x + width, y + height, new Color(10, 10, 10, 200).getRGB());
        Client.INSTANCE.getFontManager().getArial17().drawStringWithShadow(text, (float) (x + 2 + offset), (float) (y + 6), color);
        super.drawTab();
    }
}
