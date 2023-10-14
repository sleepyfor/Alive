package net.alive.api.gui.tab.tabs.impl;

import lombok.Setter;
import net.alive.Client;
import net.alive.api.gui.tab.TabGui;
import net.alive.api.gui.tab.tabs.Tab;
import net.alive.api.module.Category;
import net.alive.api.module.Module;
import net.alive.api.module.ModuleInfo;
import net.alive.implement.modules.render.ClickGui;
import net.alive.utils.gui.RenderingUtils;
import net.minecraft.client.gui.Gui;

import java.awt.*;

public class ModuleTab extends Tab {

    public double off, modAnimation;
    @Setter
    public double x, y, initY;
    public Module module;

    public ModuleTab(String text, double x, double y, double width, double height, TabGui parent, Module module) {
        super(text, width, height, parent);
        this.x = x;
        this.y = parent.originalY;
        this.initY = y;
        this.module = module;
        off = 0;
        modAnimation = x;
    }

    @Override
    public void drawTab() {
        this.y = initY + parent.anchorY - 68;
        off = RenderingUtils.progressiveAnimation(off, 2, 0.2);
        if(!parent.extended)
            Gui.drawRect(x, y, x + off, y + height, new Color(10, 10, 10, ClickGui.blur.getValueObject() ? 90 : 200).getRGB());
        Client.INSTANCE.getFontManager().getArial17().drawStringWithShadow(text, (float) (x + 3 + offset), (float) (y + 6), color);
        super.drawTab();
    }
}
