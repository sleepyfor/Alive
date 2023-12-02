package net.alive.api.gui.flushgui;

import com.sun.org.apache.xpath.internal.operations.Mod;
import lombok.var;
import net.alive.Client;
import net.alive.api.gui.click.component.value.DoubleComponent;
import net.alive.api.gui.flushgui.components.Component;
import net.alive.api.gui.flushgui.components.buttons.CategoryButton;
import net.alive.api.gui.flushgui.components.buttons.ModuleButton;
import net.alive.api.gui.flushgui.components.values.CheckBox;
import net.alive.api.gui.flushgui.components.values.ComboBox;
import net.alive.api.gui.flushgui.components.values.Slider;
import net.alive.api.module.Category;
import net.alive.api.module.Module;
import net.alive.api.notification.Notification;
import net.alive.api.notification.NotificationType;
import net.alive.api.value.Value;
import net.alive.implement.modules.render.Hud;
import net.alive.utils.gui.RenderingUtils;
import net.alive.utils.math.MathUtils;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ChatComponentText;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// CleanClick is a dead project, now we got FlushGui

public class FlushGUI extends GuiScreen {
    public float x = 100, y = 50, width = 266, height = 350, anchorX, anchorY, scale;
    public List<CategoryButton> categories = new ArrayList<>();
    public List<Component> components = new ArrayList<>();
    public List<ModuleButton> modules = new ArrayList<>();
    public Category current = Category.COMBAT;
    public boolean settingsScreen, dragging;
    public Module settingsModule;

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawFlushGui(mouseX, mouseY, x, y);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    private void drawFlushGui(int mouseX, int mouseY, double posX, double posY) {
        scale = (float) RenderingUtils.progressiveAnimation(scale, 1, 0.6);
        RenderingUtils.scale(anchorX + (this.width / 2), anchorY + (this.height / 2), scale);
        if (dragging) {
            anchorX = mouseX - (width / 2);
            anchorY = mouseY - (height / 2);
        }
        this.x = anchorX;
        this.y = anchorY;
        drawGuiBackground();
        drawCategories(mouseX, mouseY);
        drawModules(mouseX, mouseY);
        drawValueScreen(mouseX, mouseY);
        GlStateManager.popMatrix();
    }

    private void drawGuiBackground() {
        var sr = new ScaledResolution(mc);
        RenderingUtils.drawBlurredRect(RenderingUtils.BlurType.NORMAL, x, y, x + width, y + height, new Color(15, 15, 15, 30).getRGB());
        RenderingUtils.drawRectangle(x, y, x + width, y + height, new Color(15, 15, 15, 120).getRGB());
        if (settingsScreen)
            Client.INSTANCE.getArial17().drawStringWithShadow("Settings for: \247F" + settingsModule.getName(), x + 2, y + height - 11,
                    new Color(Hud.red.getValueObject().intValue(), Hud.green.getValueObject().intValue(), Hud.blue.getValueObject().intValue(), 155).getRGB());
    }

    public void drawCategories(int mouseX, int mouseY) {
        int offset = 0;
        for (CategoryButton categoryButton : categories) {
            categoryButton.hovered = isHovered(mouseX, mouseY, categoryButton.x, categoryButton.y, categoryButton.width, categoryButton.height);
            categoryButton.setX(x + 4 + offset);
            categoryButton.setY(y + 4);
            categoryButton.setWidth(50);
            categoryButton.setHeight(20);
            offset += categoryButton.width + 2;
            categoryButton.drawComponent();
        }
    }

    public void drawModules(int mouseX, int mouseY) {
        int offset = 0;
        for (ModuleButton moduleButton : modules) {
            moduleButton.hovered = isHovered(mouseX, mouseY, moduleButton.x, moduleButton.y, moduleButton.width, moduleButton.height);
            moduleButton.setX(x + 4);
            moduleButton.setY(y + 26 + offset);
            moduleButton.setWidth(102);
            moduleButton.setHeight(18);
            offset += moduleButton.height + 2;
            moduleButton.drawComponent();
        }
    }

    public void drawValueScreen(int mouseX, int mouseY) {
        int offset = 0;
        for (Component component : components) {
            if (component instanceof ComboBox) {
                ((ComboBox) component).hovered = isHovered(mouseX, mouseY, component.x, component.y, component.width, component.height);
                component.setX(x + 108);
                component.setY(y + 26 + offset);
                component.setWidth(154);
                component.setHeight(16);
                component.drawComponent();
            }
            if (component instanceof CheckBox) {
                ((CheckBox) component).hovered = isHovered(mouseX, mouseY, component.x, component.y, component.width, component.height);
                component.setX(x + 108);
                component.setY(y + 26 + offset);
                component.setWidth(154);
                component.setHeight(16);
                component.drawComponent();
            }
            if (component instanceof Slider) {
                ((Slider) component).hovered = isHovered(mouseX, mouseY, component.x, component.y, component.width, component.height);
                component.setX(x + 108);
                component.setY(y + 26 + offset);
                component.setWidth(154);
                component.setHeight(22);
                double max = ((Slider) component).value.getMax();
                double min = ((Slider) component).value.getMin();
                var comX = component.x + 8;
                var comWid = component.width - 11;
                var inc = ((Slider) component).value.getInc();
                if (((Slider) component).sliding)
                    ((Slider) component).value.setValueObject(MathUtils.round(((mouseX + 2) - (comX)) * ((max - min) / (comWid)) + min, inc));
                if (((Slider) component).value.getValueObject() > ((Slider) component).value.getMax())
                    ((Slider) component).value.setValueObject(max);
                if (((Slider) component).value.getValueObject() < min)
                    ((Slider) component).value.setValueObject(min);
                component.drawComponent();
            }
            offset += component.height;
        }
    }

    public void initializeGui() {
        x = anchorX;
        y = anchorY;
        scale = 0;
        current = Category.COMBAT;
        categories.clear();
        modules.clear();
        components.clear();
        for (Category category : Category.values())
            categories.add(new CategoryButton(category, this));
        for (Module module : Client.INSTANCE.getModuleManager().getModulesByCategory(current))
            modules.add(new ModuleButton(module, this));
    }

    public boolean isHovered(int mouseX, int mouseY, float x, float y, float width, float height) {
        return mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + height;
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        if (dragging) {
            dragging = false;
            anchorX = mouseX - (width / 2);
            anchorY = mouseY - (height / 2);
        }
        for (Component component : components)
            if (component instanceof Slider)
                ((Slider) component).sliding = false;
        super.mouseReleased(mouseX, mouseY, state);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        for (ModuleButton moduleButton : modules)
            if (moduleButton.binding) {
                if (keyCode == Keyboard.KEY_ESCAPE || keyCode == Keyboard.KEY_DELETE)
                    keyCode = Keyboard.KEY_NONE;
                moduleButton.module.setKeybind(keyCode);
                if (moduleButton.module.getKeybind() == Keyboard.KEY_NONE)
                    Client.INSTANCE.getNotificationManager().addNotification(new Notification(NotificationType.SUCCESS, "Unbound Module!", moduleButton.module.getName()
                            + " has been unbound!", 4000));
                else
                    Client.INSTANCE.getNotificationManager().addNotification(new Notification(NotificationType.SUCCESS, "Bound Module!", moduleButton.module.getName()
                            + " has been bound to " + Keyboard.getKeyName(moduleButton.module.getKeybind()) + "!", 4000));
                moduleButton.binding = false;
            } else
                super.keyTyped(typedChar, keyCode);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        switch (mouseButton) {
            case 0:
                if (isHovered(mouseX, mouseY, x, y, width, height))
                    dragging = true;
                for (CategoryButton categoryButton : categories)
                    if (categoryButton.hovered) {
                        dragging = false;
                        break;
                    }
                for (ModuleButton moduleButton : modules)
                    if (moduleButton.hovered) {
                        moduleButton.module.toggle();
                        dragging = false;
                        break;
                    }
                for (Component component : components) {
                    if (settingsScreen) {
                        if (component instanceof ComboBox && ((ComboBox) component).hovered) {
                            ((ComboBox) component).setValue();
                            dragging = false;
                        }
                        if (component instanceof CheckBox && ((CheckBox) component).hovered) {
                            ((CheckBox) component).value.setValueObject(!((CheckBox) component).value.getValueObject());
                            dragging = false;
                        }
                        if (component instanceof Slider && ((Slider) component).hovered) {
                            ((Slider) component).sliding = true;
                            dragging = false;
                        }
                    }
                }
                for (CategoryButton categoryButton : categories)
                    if (isHovered(mouseX, mouseY, categoryButton.x, categoryButton.y, categoryButton.width, categoryButton.height) &&
                            !Client.INSTANCE.getModuleManager().getModulesByCategory(categoryButton.category).isEmpty()) {
                        modules.clear();
                        components.clear();
                    }
                for (CategoryButton categoryButton : categories)
                    if (categoryButton.hovered) {
                        current = categoryButton.category;
                        for (Module module : Client.INSTANCE.getModuleManager().getModulesByCategory(categoryButton.category))
                            modules.add(new ModuleButton(module, this));
                    }
                break;
            case 1:
                for (ModuleButton moduleButton : modules)
                    if (moduleButton.hovered && !moduleButton.module.getValues().isEmpty()) {
                        components.clear();
                        settingsScreen = true;
                        for (Value value : moduleButton.module.getValues()) {
                            if (value.getValueObject() instanceof String)
                                components.add(new ComboBox(value, this));
                            if (value.getValueObject() instanceof Boolean)
                                components.add(new CheckBox(value, this));
                            if (value.getValueObject() instanceof Double)
                                components.add(new Slider(value, this));
                        }
                        settingsModule = moduleButton.module;
                    }
                break;
            case 2:
                for (ModuleButton moduleButton : modules)
                    if (moduleButton.hovered)
                        moduleButton.binding = true;
                break;
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void initGui() {
        Client.INSTANCE.getNotificationManager().addNotification(new Notification(NotificationType.SUCCESS, "Opened ClickGUI!",
                "ClickGUI has been opened successfully!", 4000));
        width = 266;
        height = 216;
        scale = 0;
        super.initGui();
    }
}
