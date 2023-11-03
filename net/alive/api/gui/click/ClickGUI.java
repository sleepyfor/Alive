package net.alive.api.gui.click;

import lombok.var;
import net.alive.Client;
import net.alive.api.gui.click.component.Component;
import net.alive.api.gui.click.component.button.CategoryButton;
import net.alive.api.gui.click.component.button.ModuleButton;
import net.alive.api.gui.click.component.value.BooleanComponent;
import net.alive.api.gui.click.component.value.DoubleComponent;
import net.alive.api.gui.click.component.value.IntegerComponent;
import net.alive.api.gui.click.component.value.StringComponent;
import net.alive.api.module.Category;
import net.alive.api.module.Module;
import net.alive.api.value.Value;
import net.alive.implement.modules.render.ClickGui;
import net.alive.implement.modules.render.Hud;
import net.alive.utils.gui.CustomFontRenderer;
import net.alive.utils.gui.RenderingUtils;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ClickGUI extends GuiScreen {

    public float x = 100, y = 50, width = 309, height = 350, anchorX, anchorY, scale;
    CustomFontRenderer font15 = Client.INSTANCE.getFontManager().createFont(15);
    CustomFontRenderer font17 = Client.INSTANCE.getFontManager().createFont(17);
    public List<CategoryButton> categories = new ArrayList<>();
    public List<Component> components = new ArrayList<>();
    public List<ModuleButton> modules = new ArrayList<>();
    public Category current = Category.RENDER;
    public boolean settingsScreen, dragging;
    public Module settingsModule;

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
       draw(mouseX, mouseY, 200, 50);
    }

    public void draw(int mouseX, int mouseY, float x, float y){
        if(dragging){
            anchorX = mouseX- (width / 2);
            anchorY = mouseY - (height / 2);
        }
        this.x = anchorX;
        this.y = anchorY;
        scale = (float) RenderingUtils.progressiveAnimation(scale, 1, 0.6);
        RenderingUtils.scale(anchorX + (this.width / 2), (float) anchorY + (this.height / 2), scale);
        drawBackground();
        drawCategories();
        drawModules(mouseX, mouseY);
        if (settingsScreen)
            drawValues(mouseX);
        GlStateManager.popMatrix();
    }

    public void initClickGUI() {
        x = anchorX;
        y = anchorY;
        scale = 0;
        current = Category.COMBAT;
        categories.clear();
        modules.clear();
        components.clear();
        for (Category category : Category.values())
            categories.add(new CategoryButton(category));
        for (Module module : Client.INSTANCE.getModuleManager().getModulesByCategory(current))
            modules.add(new ModuleButton(module));
    }

    @Override
    public void initGui() {
        x = anchorX;
        y = anchorY;
        scale = 0;
    }

    public void drawBackground() {
        var blur = ClickGui.blur.getValueObject();
        var color = new Color(5, 5, 5, blur ? 90 : 255).getRGB();
        if(blur)
            RenderingUtils.drawBlurredRect(RenderingUtils.BlurType.NORMAL, x - 1, y - 1, x + width + 9, y + height, -1);
        Gui.drawRect(x - 1, y - 1, x + width + 9, y + height, new Color(20, 20, 20, blur ? 190 : 250).getRGB());
        if(blur)
            RenderingUtils.drawBlurredRect(RenderingUtils.BlurType.NORMAL, x - 1, y + 59, x + width + 9, y + 60, -1);
        Gui.drawRect(x - 1, y + 59, x + width + 9, y + 60, color);
        if(blur)
            RenderingUtils.drawBlurredRect(RenderingUtils.BlurType.NORMAL, x - 1, y + 59, x + width + 9, y + 60, -1);
        Gui.drawRect(x + 110, y + 59, x + 111, y + height, color);
        if (settingsScreen)
           font17.drawStringWithShadow("Settings for: \247F" + settingsModule.getName(), x + 112, y + height - 10,
                    new Color(Hud.red.getValueObject().intValue(), Hud.green.getValueObject().intValue(), Hud.blue.getValueObject().intValue(), blur ? 150 : 255).getRGB());
    }

    public void drawCategories() {
        var blur = ClickGui.blur.getValueObject();
        int i = 0;
        for (CategoryButton categoryButton : categories) {
            categoryButton.setX(x + 10 + i);
            categoryButton.setY(y + 8);
            categoryButton.setWidth(40);
            categoryButton.setHeight(40);
            if(blur)
                GlStateManager.color(1, 1, 1, 0.6f);
            RenderingUtils.drawImg(new ResourceLocation("/icons/" + categoryButton.category.realName + ".png"),
                    categoryButton.x, categoryButton.y, 40, 40);
            if(blur)
                RenderingUtils.resetColor();
            i += 64;
        }
    }

    public void drawModules(int mouseX, int mouseY) {
        var blur = ClickGui.blur.getValueObject();
        int i = 0;
        for (ModuleButton moduleButton : modules) {
            moduleButton.setX(x - 1);
            moduleButton.setY(y + 61 + i);
            moduleButton.setWidth(110);
            moduleButton.setHeight(25);
            boolean hovered = isHovered(mouseX, mouseY, moduleButton.x, moduleButton.y, moduleButton.width, moduleButton.height);
            int activeColor = hovered ? 25 : 20;
            int enabledColor = moduleButton.module.isEnabled() ? new Color(Hud.red.getValueObject().intValue(), Hud.green.getValueObject().intValue(),
                    Hud.blue.getValueObject().intValue(), blur ? 150 : 255).getRGB() : new Color(255, 255, 255, blur ? 150 : 255).getRGB();
            i += 26;
//            if(blur)
//                RenderingUtils.drawBlurredRect(RenderingUtils.BlurType.NORMAL, moduleButton.x, moduleButton.y, moduleButton.x + moduleButton.width, moduleButton.y + moduleButton.height,
//                       -1);
            RenderingUtils.drawRectangle(moduleButton.x, moduleButton.y, moduleButton.x + moduleButton.width, moduleButton.y + moduleButton.height,
                    new Color(activeColor, activeColor, activeColor, blur ? 50 : 255).getRGB());
            font17.drawCenteredStringWithShadow(moduleButton.module.getName() +
                    " [" + Keyboard.getKeyName(moduleButton.module.getKeybind()) + "]", moduleButton.x + moduleButton.width / 2, moduleButton.y + 10, enabledColor);
        }
    }

    public void drawValues(int mouseX) {
        if (!settingsScreen) return;
        var blur = ClickGui.blur.getValueObject();
        int i = 0;
        for (Component component : components) {
            if (component instanceof StringComponent) {
                component.setX(x + 111);
                component.setY(y + 62 + i);
                component.setWidth(110);
                component.setHeight(13);
                RenderingUtils.drawRectangle(component.x + 3, component.y, component.x + 110, component.y + component.height,
                        new Color(20, 20, 20, blur ? 60 : 255).getRGB());
                font15.drawStringWithShadow(((StringComponent) component).value.getValueName() + ":",
                        component.x + 5, component.y + 4, new Color(Hud.red.getValueObject().intValue(), Hud.green.getValueObject().intValue(),
                                Hud.blue.getValueObject().intValue(), blur ? 150 : 255).getRGB());
                font15.drawStringWithShadow(((StringComponent) component).value.getValueObject(),
                        component.x + component.width - font15.getWidth(((StringComponent) component).value.getValueObject()) - 2, component.y + 4,
                        new Color(255, 255, 255, blur ? 150 : 255).getRGB());
            }
            if (component instanceof BooleanComponent) {
                component.setX(x + 111);
                component.setY(y + 62 + i);
                component.setWidth(110);
                component.setHeight(13);
                var name = ((BooleanComponent) component).value.getValueObject() ? "Yes" : "No";
                RenderingUtils.drawRectangle(component.x + 3, component.y, component.x + 110, component.y + component.height,
                        new Color(20, 20, 20, blur ? 60 : 255).getRGB());
                font15.drawStringWithShadow(((BooleanComponent) component).value.getValueName() + ":",
                        component.x + 5, component.y + 4, new Color(Hud.red.getValueObject().intValue(), Hud.green.getValueObject().intValue(),
                                Hud.blue.getValueObject().intValue(), blur ? 150 : 255).getRGB());
                font15.drawStringWithShadow(name, component.x + component.width - font15.getWidth(name) - 2, component.y + 4, new Color(255, 255, 255, blur ? 150 : 255).getRGB());
            }
            if (component instanceof DoubleComponent) {
                component.setX(x + 111);
                component.setY(y + 62 + i);
                component.setWidth(110);
                component.setHeight(20);
                var val = ((DoubleComponent) component).value;
                double max = val.getMax();
                double min = val.getMin();
                double inc = val.getInc();
                var decimal = new DecimalFormat("#.####");
                var valRounded = decimal.format(val.getValueObject());
                var comX = component.x + 8;
                var comWid = component.width - 11;
                if (((DoubleComponent) component).isSliding())
                    ((DoubleComponent) component).value.setValueObject(round(((mouseX + 2) - (comX)) * ((max - min) / (comWid)) + min, inc));
                if (val.getValueObject() > max)
                    val.setValueObject(max);
                if (val.getValueObject() < min)
                    val.setValueObject(min);
                RenderingUtils.drawRectangle(component.x + 3, component.y, component.x + 110, component.y + component.height,
                        new Color(20, 20, 20, blur ? 60 : 255).getRGB());
                font15.drawStringWithShadow(((DoubleComponent) component).value.getValueName() + ":",
                        component.x + 5, component.y + 4, new Color(Hud.red.getValueObject().intValue(), Hud.green.getValueObject().intValue(),
                                Hud.blue.getValueObject().intValue(), blur ? 150 : 255).getRGB());
                font15.drawStringWithShadow(valRounded, component.x + component.width - font15.getWidth(valRounded) - 2, component.y + 4,
                        new Color(255, 255, 255, blur ? 150 : 255).getRGB());
                RenderingUtils.drawRectangle(component.x + 6, component.y + 16, component.x + component.width - 3, component.y + 17,
                        new Color(255, 255, 255, blur ? 150 : 255).getRGB());
                RenderingUtils.drawRectangle((float) (comX + (val.getValueObject() - min) /
                        (max - min) * comWid) - 2, component.y + 14, (float) (comX + ((val.getValueObject() - min) /
                        (max - min)) * comWid), component.y + 19, new Color(255, 255, 255, blur ? 150 : 255).getRGB());
            }
            i += (component instanceof IntegerComponent || component instanceof DoubleComponent) ? 20 : 13;
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        switch (mouseButton) {
            case 0:
                if(isHovered(mouseX, mouseY, x, y, width, height))
                    dragging = true;
                if(isHovered(mouseX, mouseY, x, y, width, 60))
                    dragging = false;
                for (CategoryButton categoryButton : categories) {
                    if (isHovered(mouseX, mouseY, categoryButton.x, categoryButton.y, categoryButton.width, categoryButton.height) &&
                            !Client.INSTANCE.getModuleManager().getModulesByCategory(categoryButton.category).isEmpty())
                        modules.clear();
                    if(isHovered(mouseX, mouseY, categoryButton.x, categoryButton.y, categoryButton.width, categoryButton.height))
                        dragging = false;
                }
                for (CategoryButton categoryButton : categories) {
                    boolean hovered = isHovered(mouseX, mouseY, categoryButton.x, categoryButton.y, categoryButton.width, categoryButton.height);
                    if (hovered) {
                        current = categoryButton.category;
                        settingsScreen = false;
                        dragging = false;
                    }
                    for (Module module : Client.INSTANCE.getModuleManager().getModulesByCategory(current))
                        if (hovered) {
                            modules.add(new ModuleButton(module));
                            dragging = false;
                        }
                }
                for (ModuleButton moduleButton : modules) {
                    boolean hovered = isHovered(mouseX, mouseY, moduleButton.x, moduleButton.y, moduleButton.width, moduleButton.height);
                    if (hovered) {
                        moduleButton.module.toggle();
                        dragging = false;
                    }
                }
                if (settingsScreen) {
                    for (Component component : components) {
                        if (component instanceof StringComponent)
                            if (isHovered(mouseX, mouseY, component.x, component.y, component.width, component.height)) {
                                ((StringComponent) component).setValue();
                                dragging = false;
                            }
                        if (component instanceof BooleanComponent)
                            if (isHovered(mouseX, mouseY, component.x, component.y, component.width, component.height)) {
                                ((BooleanComponent) component).value.setValueObject(!((BooleanComponent) component).value.getValueObject());
                                dragging = false;
                            }
                        if (component instanceof DoubleComponent)
                            if (isHovered(mouseX, mouseY, component.getX() + 6, component.getY() + 14, component.getWidth() - 3, 5)) {
                                ((DoubleComponent) component).sliding = true;
                                dragging = false;
                            }
                        if (component instanceof IntegerComponent)
                            if (isHovered(mouseX, mouseY, component.getX() + 6, component.getY() + 14, component.getWidth() - 3, 5)) {
                                ((IntegerComponent) component).sliding = true;
                                dragging = false;
                            }
                    }
                }
                break;
            case 1:
                for (ModuleButton moduleButton : modules) {
                    boolean hovered = isHovered(mouseX, mouseY, moduleButton.x, moduleButton.y, moduleButton.width, moduleButton.height);
                    if (hovered) {
                        components.clear();
                        settingsScreen = true;
                        for (Value value : moduleButton.module.getValues()) {
                            if (value.getValueObject() instanceof Boolean)
                                components.add(new BooleanComponent(value));
                            if (value.getValueObject() instanceof String)
                                components.add(new StringComponent(value));
                            if (value.getValueObject() instanceof Double)
                                components.add(new DoubleComponent(value));
                            if (value.getValueObject() instanceof Integer)
                                components.add(new IntegerComponent(value));
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
        for (Component component : components) {
            if (component instanceof DoubleComponent)
                ((DoubleComponent) component).sliding = false;
            if (component instanceof IntegerComponent)
                ((IntegerComponent) component).sliding = false;
        }
        if(dragging) {
            dragging = false;
            anchorX = mouseX- (width / 2);
            anchorY = mouseY - (height / 2);
        }
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
}
