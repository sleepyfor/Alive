package net.alive.api.gui.click;

import lombok.var;
import net.alive.Client;
import net.alive.api.gui.click.component.button.CategoryButton;
import net.alive.api.gui.click.component.button.ModuleButton;
import net.alive.api.gui.click.component.value.BooleanComponent;
import net.alive.api.gui.click.component.value.DoubleComponent;
import net.alive.api.gui.click.component.value.IntegerComponent;
import net.alive.api.gui.click.component.value.StringComponent;
import net.alive.api.module.Category;
import net.alive.api.module.Module;
import net.alive.api.value.Value;
import net.alive.implement.modules.movement.Flight;
import net.alive.implement.modules.render.Hud;
import net.alive.utils.gui.RenderingUtils;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ChatComponentText;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ClickGUI extends GuiScreen {

    public float x = 100, y = 50, width = 300, height = 400;
    public List<CategoryButton> categories = new ArrayList<>();
    public List<BooleanComponent> booleanButtons = new ArrayList<>();
    public List<StringComponent> stringButtons = new ArrayList<>();
    public List<DoubleComponent> doubleSliders = new ArrayList<>();
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

    public void initClickGUI(){
        current = Category.COMBAT;
        booleanButtons.clear();
        categories.clear();
        modules.clear();
        for (Category category : Category.values())
            categories.add(new CategoryButton(category));
        for (Module module : Client.INSTANCE.getModuleManager().getModulesByCategory(current))
            modules.add(new ModuleButton(module));
    }

    @Override
    public void initGui() {

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
        if (settingsScreen)
            Client.INSTANCE.getFontManager().getArial17().drawStringWithShadow("Settings for: \247F" + settingsModule.getName(),
                    x + 82, y + height - 10, new Color(255, 161, 205, 255).getRGB());
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
            Client.INSTANCE.getFontManager().getArial17().drawCenteredStringWithShadow(moduleButton.module.getName() +
                            " [" + Keyboard.getKeyName(moduleButton.module.getKeybind()) + "]", moduleButton.x + moduleButton.width / 2, moduleButton.y + 10, enabledColor);
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
            drawDoubleSliders(mouseX, mouseY, i3);
            drawIntegerSliders(mouseX, mouseY, i3);
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
                    if (hovered) {
                        current = categoryButton.category;
                        settingsScreen = false;
                    }
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
                    for (StringComponent stringComponent : stringButtons)
                        if (isHovered(mouseX, mouseY, stringComponent.x, stringComponent.y + 5,
                                stringComponent.width, stringComponent.height))
                            stringComponent.setValue();
                    for (DoubleComponent doubleComponent : doubleSliders)
                        if (isHovered(mouseX, mouseY, doubleComponent.getX(), doubleComponent.getY() + 5, doubleComponent.getWidth(), doubleComponent.getHeight())) {
                            doubleComponent.sliding = true;
                        }
                    for (IntegerComponent integerComponent : integerSliders)
                        if (isHovered(mouseX, mouseY, integerComponent.getX(), integerComponent.getY() + 5, integerComponent.getWidth(), integerComponent.getHeight())) {
                            integerComponent.sliding = true;
                        }
                }
                break;
            case 1:
                for (ModuleButton moduleButton : modules) {
                    boolean hovered = isHovered(mouseX, mouseY, moduleButton.x, moduleButton.y, moduleButton.width, moduleButton.height);
                    if (hovered) {
                        booleanButtons.clear();
                        stringButtons.clear();
                        doubleSliders.clear();
                        integerSliders.clear();
                        settingsScreen = true;
                        for (Value value : moduleButton.module.getValues()) {
                            if (value.getValueObject() instanceof Boolean)
                                booleanButtons.add(new BooleanComponent(value));
                            if (value.getValueObject() instanceof String)
                                stringButtons.add(new StringComponent(value));
                            if (value.getValueObject() instanceof Double)
                                doubleSliders.add(new DoubleComponent(value));
                            if (value.getValueObject() instanceof Integer)
                                integerSliders.add(new IntegerComponent(value));
                        }
                        settingsModule = moduleButton.module;
                    }
                }
                break;
            case 2:
                for (ModuleButton moduleButton : modules) {
                    boolean hovered = isHovered(mouseX, mouseY, moduleButton.x, moduleButton.y, moduleButton.width, moduleButton.height);
                    if (hovered)
                        moduleButton.binding = true;
                }
                break;
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        for (ModuleButton moduleButton : modules)
            if (moduleButton.binding) {
                if (keyCode == Keyboard.KEY_ESCAPE || keyCode == Keyboard.KEY_DELETE)
                    keyCode = Keyboard.KEY_NONE;
                moduleButton.module.setKeybind(keyCode);
                if (moduleButton.module.getKeybind() == Keyboard.KEY_NONE)
                    mc.thePlayer.addChatMessage(new ChatComponentText("\2477<\247fA\247flive\2477>\247r \247c\247b" +
                            "Unbound " + moduleButton.module.getName()));
                else
                    mc.thePlayer.addChatMessage(new ChatComponentText("\2477<\247fA\247flive\2477>\247r \247c\247b" +
                            moduleButton.module.getName() + " has been bound to " + Keyboard.getKeyName(moduleButton.module.getKeybind())));
                moduleButton.binding = false;
            } else {
                super.keyTyped(typedChar, keyCode);
            }
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        for (DoubleComponent doubleComponent : doubleSliders)
            doubleComponent.sliding = false;
        for (IntegerComponent integerComponent : integerSliders)
            integerComponent.sliding = false;
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    private double round(double num, double increment) {
        double v = (double) Math.round(num / increment) * increment;
        BigDecimal bd = new BigDecimal(v);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.floatValue();
    }

    public boolean isHovered(int mouseX, int mouseY, float x, float y, float width, float height) {
        return mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + height;
    }

    public void drawDoubleSliders(int mouseX, int mouseY, int i) {
        for (DoubleComponent doubleComponent : doubleSliders) {
            i += 25;
            var val = doubleComponent.getValue();
            double max = val.getMax();
            double min = val.getMin();
            double inc = val.getInc();
            double perc = (double) (width - 3) / (max - min);
            double valRounded = round(val.getValueObject(), val.getInc());
            double barWidth = (perc * valRounded - perc * min + 2);
            var decimal = new DecimalFormat("#.####");
            if (doubleComponent.isSliding())
                doubleComponent.value.setValueObject(round(((mouseX + 2) - doubleComponent.getX()) * (max - min) / doubleComponent.getWidth() + min, inc));
            if (val.getValueObject() > max)
                val.setValueObject(max);
            if (val.getValueObject() < min)
                val.setValueObject(min);
            doubleComponent.setX(x + 235);
            doubleComponent.setY(y + 15 + i);
            doubleComponent.setWidth(140);
            doubleComponent.setHeight(11);
            RenderingUtils.drawRectangle(doubleComponent.x, doubleComponent.y + 5, doubleComponent.x + doubleComponent.width,
                    doubleComponent.y + doubleComponent.height + 5, new Color(100, 100, 100, 255).getRGB());
            RenderingUtils.drawRectangle(doubleComponent.x, doubleComponent.y + 5, (float) (doubleComponent.x +
                            ((doubleComponent.value.getValueObject() - doubleComponent.value.getMin()) /
                                    (doubleComponent.value.getMax() - doubleComponent.value.getMin())) * doubleComponent.width),
                    doubleComponent.y + doubleComponent.height + 5, -1);
            RenderingUtils.drawRectangle((float) (doubleComponent.x + ((doubleComponent.value.getValueObject() - doubleComponent.value.getMin()) /
                            (doubleComponent.value.getMax() - doubleComponent.value.getMin())) * doubleComponent.width) - 2,
                    doubleComponent.y + 5, (float) (doubleComponent.x +
                            ((doubleComponent.value.getValueObject() - doubleComponent.value.getMin()) /
                                    (doubleComponent.value.getMax() - doubleComponent.value.getMin())) * doubleComponent.width),
                    doubleComponent.y + doubleComponent.height + 5, new Color(Hud.red.getValueObject().intValue(), Hud.green.getValueObject().intValue(),
                            Hud.blue.getValueObject().intValue(), 255).getRGB());
            Client.INSTANCE.getFontManager().getArial17().drawCenteredStringWithShadow(doubleComponent.value.getValueName() + " -> " +
                            decimal.format(doubleComponent.value.getValueObject()),
                    doubleComponent.x + doubleComponent.width / 2, doubleComponent.y - 5, -1);
        }
    }

    public void drawIntegerSliders(int mouseX, int mouseY, int i) {
        for (IntegerComponent integerComponent : integerSliders) {
            i += 25;
            var val = integerComponent.getValue();
            int max = (int) val.getMax();
            int min = (int) val.getMin();
            int inc = (int) val.getInc();
            //double perc = (double) (width - 3) / (max - min);
            double valRounded = round(val.getValueObject(), val.getInc());
            //double barWidth = (perc * valRounded - perc * min + 2);
            if (integerComponent.isSliding())
                integerComponent.value.setValueObject((int) round(((mouseX + 2) - integerComponent.getX()) * (max - min) / integerComponent.getWidth() + min, inc));
            if (val.getValueObject() > max)
                val.setValueObject(max);
            if (val.getValueObject() < min)
                val.setValueObject(min);
            integerComponent.setX(x + 235);
            integerComponent.setY(y + 15 + i);
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
                    new Color(Hud.red.getValueObject().intValue(), Hud.green.getValueObject().intValue(), Hud.blue.getValueObject().intValue(), 255).getRGB());
            Client.INSTANCE.getFontManager().getArial17().drawCenteredStringWithShadow(integerComponent.value.getValueName() + " -> " + integerComponent.value.getValueObject(),
                    integerComponent.x + integerComponent.width / 2, integerComponent.y - 5, -1);
        }
    }
}
