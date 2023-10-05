package net.alive.api.gui.click2.component.button;

import net.alive.api.gui.click2.component.Component;
import net.alive.api.module.Category;
import net.alive.utils.gui.RenderingUtils;
import net.minecraft.client.gui.Gui;

import java.awt.*;

public class CategoryButton extends Component {

    public Category category;

    public CategoryButton(Category category){
        this.category = category;
    }

    @Override
    public void drawComponent() {
        RenderingUtils.drawRectangle(this.x, this.y, this.x + this.width, this.y + this.height, new Color(10, 10, 10, 255).getRGB());
        Gui.drawRect(this.x + width, this.y, this.x + this.width + 1, this.y + this.height, new Color(200, 10, 200, 255).getRGB());
        font.drawCenteredStringWithShadow(category.realName, this.x + font.getWidth(category.realName) / 2, this.y + 10, -1);
    }
}
