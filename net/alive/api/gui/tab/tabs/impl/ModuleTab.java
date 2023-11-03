package net.alive.api.gui.tab.tabs.impl;

import lombok.Setter;
import net.alive.Client;
import net.alive.api.gui.tab.TabGui;
import net.alive.api.gui.tab.tabs.Tab;
import net.alive.api.module.Category;
import net.alive.api.module.Module;
import net.alive.api.module.ModuleInfo;
import net.alive.implement.modules.render.ClickGui;
import net.alive.implement.modules.render.Hud;
import net.alive.utils.gui.CustomFontRenderer;
import net.alive.utils.gui.RenderingUtils;
import net.minecraft.client.gui.Gui;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class ModuleTab extends Tab {

    public CustomFontRenderer font = Client.INSTANCE.getFontManager().createFont(17);
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
        if(parent.extended && !Hud.blur.getValueObject()) {
            RenderingUtils.scissorBox((int) x, (int) y, (int) ((int) x + parent.off), (int) ((int) y + height));
            Gui.drawRect(x, y, x + parent.off, y + height, new Color(10, 10, 10, 200).getRGB());
            GL11.glDisable(GL11.GL_SCISSOR_TEST);
        }
        font.drawStringWithShadow(text, (float) (x + 3 + offset), (float) (y + 6), color);
        super.drawTab();
    }
}
