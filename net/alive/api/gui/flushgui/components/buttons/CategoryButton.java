package net.alive.api.gui.flushgui.components.buttons;

import net.alive.Client;
import net.alive.api.gui.flushgui.FlushGUI;
import net.alive.api.gui.flushgui.FlushUtils;
import net.alive.api.gui.flushgui.components.Component;
import net.alive.api.module.Category;
import net.alive.implement.modules.render.Hud;
import net.alive.utils.gui.RenderingUtils;
import net.minecraft.client.gui.Gui;

import java.awt.*;

public class CategoryButton extends Component {

    public FlushUtils animation;
    public Category category;
    public boolean hovered;
    public FlushGUI parent;

    public CategoryButton(Category category, FlushGUI parent) {
        animation = new FlushUtils();
        this.category = category;
        this.parent = parent;
    }

    @Override
    public void drawComponent() {
        int color = new Color(Hud.red.getValueObject().intValue(), Hud.green.getValueObject().intValue(), Hud.blue.getValueObject().intValue(), 185).getRGB();
        animation.drawAnimatedButton(category.realName, x, y, width, height, new Color(255,255,255,185).getRGB(), color, parent.current == category, hovered);
    }
}
