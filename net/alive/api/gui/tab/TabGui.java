package net.alive.api.gui.tab;

import com.sun.org.apache.xpath.internal.operations.Mod;
import lombok.Getter;
import net.alive.Client;
import net.alive.api.gui.tab.tabs.Tab;
import net.alive.api.gui.tab.tabs.impl.CategoryTab;
import net.alive.api.gui.tab.tabs.impl.ModuleTab;
import net.alive.api.module.Category;
import net.alive.api.module.Module;
import net.alive.implement.modules.render.Hud;
import net.alive.utils.gui.RenderingUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Getter
public class TabGui {
    public List<CategoryTab> categories;
    public double x, y, y2, modY, originalY, anchorX, anchorY, off;
    public List<ModuleTab> modules;
    public Category selected;
    public int index, index2;
    public Module selection;
    public boolean extended;


    public void draw(double x, double y) {
        this.anchorX = x;
        this.x = x;
        this.originalY = y;
        this.anchorY = originalY + 10;
        int i = 0;
        int i2 = 0;
        if (extended)
            off = RenderingUtils.progressiveAnimation(off, 70, 0.8);
        if (Hud.blur.getValueObject()) {
            RenderingUtils.drawRectangle((float) getX(), (float) (anchorY + 10), (float) (getX() + 60), (float) (getY() + 20), new Color(10, 10, 10, 30).getRGB());
            RenderingUtils.drawBlurredRect(RenderingUtils.BlurType.NORMAL, getX(), anchorY + 10, getX() + 60, getY() + 20, new Color(10, 10, 10, 200).getRGB());
            if (extended) {
                RenderingUtils.scissorBox(getX() + 62, (anchorY + 10) + getModuleOffset(), getX() + 62 + off,
                        (anchorY + (modules.size() * 20) + 9) + getModuleOffset());
                RenderingUtils.drawRectangle((float) (getX() + 62), (float) ((anchorY + 11) + getModuleOffset()), (float) (getX() + 62 + off),
                        (float) ((anchorY + (modules.size() * 18) + 11) + getModuleOffset()), new Color(10, 10, 10, 30).getRGB());
                RenderingUtils.drawBlurredRect(RenderingUtils.BlurType.NORMAL, getX() + 62, (anchorY + 11) + getModuleOffset(), getX() + 62 + off,
                        (anchorY + (modules.size() * 18) + 11) + getModuleOffset(), new Color(10, 10, 10, 200).getRGB());
                GL11.glDisable(GL11.GL_SCISSOR_TEST);
            }
        }
        for (CategoryTab categoryTab : categories) {
            i += categoryTab.height;
            this.y = y + i;
            categoryTab.drawTab();
            if (selected == categoryTab.category) {
                if (Hud.blur.getValueObject())
                    categoryTab.color = new Color(Hud.red.getValueObject().intValue(), Hud.green.getValueObject().intValue(), Hud.blue.getValueObject().intValue(), 155).getRGB();
                else
                    categoryTab.color = new Color(Hud.red.getValueObject().intValue(), Hud.green.getValueObject().intValue(), Hud.blue.getValueObject().intValue(), 255).getRGB();
                categoryTab.offset = RenderingUtils.progressiveAnimation(categoryTab.offset, 2, 0.2);
            } else {
                if (Hud.blur.getValueObject())
                    categoryTab.color = new Color(255, 255, 255, 155).getRGB();
                else
                    categoryTab.color = -1;
                categoryTab.offset = RenderingUtils.progressiveAnimation(categoryTab.offset, 0, 0.2);
            }
            for (ModuleTab moduleTab : modules) {
                if (selection == moduleTab.module)
                    moduleTab.offset = RenderingUtils.progressiveAnimation(moduleTab.offset, 3, 0.2);
                else
                    moduleTab.offset = RenderingUtils.progressiveAnimation(moduleTab.offset, 0, 0.2);
                if (moduleTab.module.isEnabled())
                    moduleTab.color = new Color(Hud.red.getValueObject().intValue(), Hud.green.getValueObject().intValue(), Hud.blue.getValueObject().intValue(),
                            Hud.blur.getValueObject() ? 150 : 255).getRGB();
                else
                    moduleTab.color = new Color(255, 255, 255, Hud.blur.getValueObject() ? 150 : 255).getRGB();
            }
        }
        for (ModuleTab moduleTab : modules) {
            i2 += moduleTab.height;
            modY = i2;
            moduleTab.drawTab();
        }
        if (index > 4) index = 0;
    }

    public void initMate() {
        categories = new ArrayList<>();
        modules = new ArrayList<>();
        for (Category category : Category.values())
            categories.add(new CategoryTab(category.realName, 60, 18, this, category));
    }

    public void doKeys(int key) {
        if (key == Keyboard.KEY_DOWN) {
            if (!extended) {
                off = 0;
                index++;
                if (index > 4) index = 0;
            } else {
                index2++;
                if (index2 > modules.size() - 1)
                    index2 = 0;
                selection = modules.get(index2).module;
            }
        }
        if (key == Keyboard.KEY_UP) {
            if (!extended) {
                off = 0;
                index--;
                if (index < 0) index = 4;
            } else {
                index2--;
                if (index2 < 0)
                    index2 = modules.size() - 1;
                selection = modules.get(index2).module;
            }
        }
        if (key == Keyboard.KEY_RIGHT) {
            if (!extended) {
                index2 = 0;
                off = 0;
                for (Module module : Client.INSTANCE.getModuleManager().getModulesByCategory(selected)) {
                    y2 += 20;
                    modules.add(new ModuleTab(module.getName(), 64, y2 + 58, 60, 18, this, module));
                    extended = true;
                    selection = modules.get(0).module;
                }
            } else {
                selection.toggle();
            }
        }
        if (key == Keyboard.KEY_LEFT) {
            if (extended) {
                y2 = 0;
                modules.clear();
                extended = false;
            }
        }
        switch (index) {
            case 0:
                selected = Category.COMBAT;
                break;
            case 1:
                selected = Category.MOVEMENT;
                break;
            case 2:
                selected = Category.PLAYER;
                break;
            case 3:
                selected = Category.WORLD;
                break;
            case 4:
                selected = Category.RENDER;
                break;
        }
    }

    public int getModuleOffset() {
        int offset = 0;
        switch (selected) {
            case MOVEMENT:
                offset = 18;
                break;
            case PLAYER:
                offset = 36;
                break;
            case RENDER:
                offset = 72;
                break;
            case WORLD:
                offset = 54;
                break;
        }
        return offset;
    }
}
