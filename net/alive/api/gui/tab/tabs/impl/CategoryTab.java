package net.alive.api.gui.tab.tabs.impl;

import net.alive.Client;
import net.alive.api.gui.tab.TabGui;
import net.alive.api.gui.tab.tabs.Tab;
import net.alive.api.module.Category;
import net.alive.implement.modules.render.Hud;
import net.alive.utils.gui.CustomFontRenderer;
import net.alive.utils.gui.RenderingUtils;
import net.minecraft.client.gui.Gui;

import java.awt.*;

public class CategoryTab extends Tab {

    public CustomFontRenderer font = Client.INSTANCE.getFontManager().createFont(17);
    public Category category;

    public CategoryTab(String text, double width, double height, TabGui parent, Category category) {
        super(text, width, height, parent);
        this.category = category;
    }

    @Override
    public void drawTab() {
        if (!Hud.blur.getValueObject())
            Gui.drawRect(parent.getX(), parent.getY(), parent.getX() + width, parent.getY() + height, new Color(10, 10, 10, 200).getRGB());
        font.drawStringWithShadow(text, (float) (parent.getX() + 2 + offset), (float) (parent.getY() + 6), color);
        super.drawTab();
    }
}
