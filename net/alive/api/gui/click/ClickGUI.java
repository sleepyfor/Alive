package net.alive.api.gui.click;

import lombok.var;
import net.alive.Client;
import net.alive.api.gui.click.component.button.CategoryButton;
import net.alive.api.gui.click.component.button.ModuleButton;
import net.alive.api.gui.click.component.value.BooleanComponent;
import net.alive.api.gui.click.component.value.IntegerComponent;
import net.alive.api.gui.click.component.value.StringComponent;
import net.alive.api.module.Category;
import net.alive.api.module.Module;
import net.alive.api.value.Value;
import net.alive.utils.gui.RenderingUtils;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClickGUI extends GuiScreen {

    public float x = 100, y = 50, width = 300, height = 400;
    public List<CategoryButton> categories = new ArrayList<>();
    public List<BooleanComponent> booleanButtons = new ArrayList<>();
    public List<StringComponent> stringButtons = new ArrayList<>();
    public List<IntegerComponent> integerSliders = new ArrayList<>();
    public List<ModuleButton> modules = new ArrayList<>();
    public Category current = Category.RENDER;
    public boolean settingsScreen;
    public Module settingsModule;

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawBackground();
        drawCategories(mouseX, mouseY);
        drawModules(mouseX, mouseY);
        if (settingsScreen)
            drawValues(mouseX, mouseY);
    }

    @Override
    public void initGui() {
        current = Category.RENDER;
        booleanButtons.clear();
        categories.clear();
        modules.clear();
        for (Category category : Category.values())
            categories.add(new CategoryButton(category));
        for (Module module : Client.INSTANCE.getModuleManager().getModulesByCategory(current))
            modules.add(new ModuleButton(module));
    }

    public void drawBackground() {
        var color = new Color(255, 161, 205, 255).getRGB();
        Gui.drawRect(x - 1, y - 1, x + width + 105, y + height + 1, new Color(20, 20, 20, 250).getRGB());
        Gui.drawRect(x + width + 106, y - 1, x + width + 105, y + height + 1, color);
        Gui.drawRect(x + width + 106, y + height, x, y + height + 1, color);
        Gui.drawRect(x - 1, y - 1, x, y + height + 1, color);
        Gui.drawRect(x - 1, y - 1, x + width + 106, y, color);
        Gui.drawRect(x - 1, y + 25, x + width + 106, y + 26, color);
        Gui.drawRect(x + 80, y + 25, x + 81, y + height + 1, color);
    }

    public void drawCategories(int mouseX, int mouseY) {
        int i = 0;
        for (CategoryButton categoryButton : categories) {
            boolean hovered = isHovered(mouseX, mouseY, categoryButton.x, categoryButton.y, categoryButton.width, categoryButton.height);
            boolean active = categoryButton.category == current;
            int color = active ? 25 : (hovered ? 25 : 50);
            categoryButton.setX(x + i);
            categoryButton.setY(y);
            categoryButton.setWidth(80);
            categoryButton.setHeight(25);
            RenderingUtils.drawRectangle(categoryButton.x, categoryButton.y, categoryButton.x + categoryButton.width, categoryButton.y +
                    categoryButton.height, new Color(color, color, color, 255).getRGB());
            if (categoryButton.category != Category.RENDER)
                RenderingUtils.drawRectangle(categoryButton.x + categoryButton.width, categoryButton.y, categoryButton.x + categoryButton.width + 2,
                        categoryButton.y + categoryButton.height, new Color(255, 161, 205, 255).getRGB());
            categoryButton.font.drawCenteredString(categoryButton.category.realName, this.x + i + (categoryButton.width / 2), this.y + 10, -1);
            i += 81;
        }
    }

    public void drawModules(int mouseX, int mouseY) {
        int i = 0;
        for (ModuleButton moduleButton : modules) {
            moduleButton.setX(x);
            moduleButton.setY(y + 27 + i);
            moduleButton.setWidth(80);
            moduleButton.setHeight(25);
            boolean hovered = isHovered(mouseX, mouseY, moduleButton.x, moduleButton.y, moduleButton.width, moduleButton.height);
            int activeColor = moduleButton.module.isEnabled() ? 25 : (hovered ? 25 : 50);
            int enabledColor = moduleButton.module.isEnabled() ? new Color(255, 161, 205, 255).getRGB() : -1;
            i += 26;
            RenderingUtils.drawRectangle(moduleButton.x, moduleButton.y, moduleButton.x + moduleButton.width, moduleButton.y + moduleButton.height,
                    new Color(activeColor, activeColor, activeColor, 255).getRGB());
            RenderingUtils.drawRectangle(moduleButton.x, moduleButton.y + moduleButton.height, moduleButton.x + moduleButton.width,
                    moduleButton.y + moduleButton.height + 1, new Color(255, 161, 205, 255).getRGB());
            Client.INSTANCE.getFontManager().getArial17().drawCenteredStringWithShadow(moduleButton.module.getName(), moduleButton.x +
                    moduleButton.width / 2, moduleButton.y + 10, enabledColor);
        }
    }

    public void drawValues(int mouseX, int mouseY) {
        if (settingsScreen) {
            int i = 0;
            for (BooleanComponent booleanComponent : booleanButtons) {
                i += 25;
                booleanComponent.setX(x + 100);
                booleanComponent.setY(y + 15 + i);
                booleanComponent.setWidth(15);
                booleanComponent.setHeight(7.5f);
                boolean offset = booleanComponent.value.getValueObject();
                RenderingUtils.drawRectangle(booleanComponent.x, booleanComponent.y + 5, booleanComponent.x + booleanComponent.width,
                        booleanComponent.y + booleanComponent.height + 5, new Color(100, 100, 100, 255).getRGB());
                RenderingUtils.drawRectangle((!offset ? 0 : 7.5f) + booleanComponent.x, booleanComponent.y + 5,
                        (!offset ? 0 : 7.5f) + booleanComponent.x + (booleanComponent.width / 2),
                        booleanComponent.y + booleanComponent.height + 5, booleanComponent.value.getValueObject() ?
                                new Color(0, 255, 0, 255).getRGB() : new Color(255, 0, 0, 255).getRGB());
                Client.INSTANCE.getFontManager().getArial17().drawCenteredStringWithShadow(booleanComponent.value.getValueName(),
                        booleanComponent.x + booleanComponent.width / 2, booleanComponent.y - 5, -1);
            }
            int i2 = 0;
            for (StringComponent stringComponent : stringButtons) {
                i2 += 25;
                stringComponent.setX(x + 150);
                stringComponent.setY(y + 15 + i2);
                stringComponent.setWidth(50);
                stringComponent.setHeight(11);
                RenderingUtils.drawRectangle(stringComponent.x, stringComponent.y + 5, stringComponent.x + stringComponent.width,
                        stringComponent.y + stringComponent.height + 5, new Color(100, 100, 100, 255).getRGB());
                Client.INSTANCE.getFontManager().getArial17().drawCenteredStringWithShadow(stringComponent.value.getValueName(),
                        stringComponent.x + stringComponent.width / 2, stringComponent.y - 5, -1);
                Client.INSTANCE.getFontManager().getArial17().drawCenteredStringWithShadow((String) stringComponent.value.getValueObject(),
                        stringComponent.x + stringComponent.width / 2, stringComponent.y + 7, -1);
            }
            int i3 = 0;
            for (IntegerComponent integerComponent : integerSliders) {
                i3 += 25;
                integerComponent.setX(x + 235);
                integerComponent.setY(y + 15 + i3);
                integerComponent.setWidth(100);
                integerComponent.setHeight(11);
                RenderingUtils.drawRectangle(integerComponent.x, integerComponent.y + 5, integerComponent.x + integerComponent.width,
                        integerComponent.y + integerComponent.height + 5, new Color(100, 100, 100, 255).getRGB());
                RenderingUtils.drawRectangle(integerComponent.x, integerComponent.y + 5, (float) (integerComponent.x +
                                ((integerComponent.value.getValueObject() - integerComponent.value.getMin()) /
                                        (integerComponent.value.getMax() - integerComponent.value.getMin())) * integerComponent.width),
                        integerComponent.y + integerComponent.height + 5, -1);
                RenderingUtils.drawRectangle((float) (integerComponent.x + ((integerComponent.value.getValueObject() - integerComponent.value.getMin()) /
                                (integerComponent.value.getMax() - integerComponent.value.getMin())) * integerComponent.width) - 2,
                        integerComponent.y + 5, (float) (integerComponent.x +
                                ((integerComponent.value.getValueObject() - integerComponent.value.getMin()) /
                                        (integerComponent.value.getMax() - integerComponent.value.getMin())) * integerComponent.width),
                        integerComponent.y + integerComponent.height + 5,
                        new Color((Integer) settingsModule.getValue("Red").getValueObject(), (Integer) settingsModule.getValue("Green").getValueObject(),
                                (Integer) settingsModule.getValue("Blue").getValueObject(), 255).getRGB());
                Client.INSTANCE.getFontManager().getArial17().drawCenteredStringWithShadow(integerComponent.value.getValueName() + " -> " + integerComponent.value.getValueObject(),
                        integerComponent.x + integerComponent.width / 2, integerComponent.y - 5, -1);
            }
            Client.INSTANCE.getFontManager().getArial17().drawCenteredStringWithShadow("Settings for: \247F" + settingsModule.getName(),
                    x + 114, y + height - 10, new Color(255, 161, 205, 255).getRGB());
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        switch (mouseButton) {
            case 0:
                if (isHovered(mouseX, mouseY, x, y, x + 308, y / 2))
                    modules.clear();
                for (CategoryButton categoryButton : categories) {
                    boolean hovered = isHovered(mouseX, mouseY, categoryButton.x, categoryButton.y, categoryButton.width, categoryButton.height);
                    if (hovered)
                        current = categoryButton.category;
                    for (Module module : Client.INSTANCE.getModuleManager().getModulesByCategory(current))
                        if (hovered)
                            modules.add(new ModuleButton(module));
                }
                for (ModuleButton moduleButton : modules) {
                    boolean hovered = isHovered(mouseX, mouseY, moduleButton.x, moduleButton.y, moduleButton.width, moduleButton.height);
                    if (hovered)
                        moduleButton.module.toggle();
                }
                if (settingsScreen) {
                    for (BooleanComponent booleanComponent : booleanButtons)
                        if (isHovered(mouseX, mouseY, booleanComponent.x, booleanComponent.y + 5,
                                booleanComponent.width, booleanComponent.height))
                            booleanComponent.value.setValueObject(!booleanComponent.value.getValueObject());
                    for (StringComponent stringComponent : stringButtons) {
                        if (isHovered(mouseX, mouseY, stringComponent.x, stringComponent.y + 5,
                                stringComponent.width, stringComponent.height)) {
                            stringComponent.setValue();
                            }

                        }
                    }
                break;
            case 1:
                settingsScreen = true;
                for (ModuleButton moduleButton : modules) {
                    boolean hovered = isHovered(mouseX, mouseY, moduleButton.x, moduleButton.y, moduleButton.width, moduleButton.height);
                    if (hovered) {
                        booleanButtons.clear();
                        stringButtons.clear();
                        integerSliders.clear();
                        settingsScreen = true;
                        // booleanValues.addAll(moduleButton.module.getValues());
                        for (Value value : moduleButton.module.getValues()) {
                            if (value.getValueObject() instanceof Boolean)
                                booleanButtons.add(new BooleanComponent(value));
                            if (value.getValueObject() instanceof String)
                                stringButtons.add(new StringComponent(value));
                            if(value.getValueObject() instanceof Integer)
                                integerSliders.add(new IntegerComponent(value));
                        }
                        settingsModule = moduleButton.module;
                    }
                }
                break;
        }
    }

    public boolean isHovered(int mouseX, int mouseY, float x, float y, float width, float height) {
        return mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + height;
    }
}
