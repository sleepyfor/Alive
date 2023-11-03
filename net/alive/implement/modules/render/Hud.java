package net.alive.implement.modules.render;

import lombok.var;
import net.alive.Client;
import net.alive.api.event.annotation.Subscribe;
import net.alive.api.event.listener.impl.Listener;
import net.alive.api.module.Category;
import net.alive.api.module.Module;
import net.alive.api.module.ModuleInfo;
import net.alive.api.notification.Notification;
import net.alive.api.value.Value;
import net.alive.implement.events.keys.KeyboardEvent;
import net.alive.implement.events.render.Render2DEvent;
import net.alive.utils.gui.CustomFontRenderer;
import net.alive.utils.gui.RenderingUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;

import java.awt.*;
import java.util.*;
import java.util.List;

@ModuleInfo(name = "Hud", displayName = "Hud", keyBind = 0, category = Category.RENDER)
public class Hud extends Module {
    public Value<String> mode = new Value<>("Mode", "Alive", "Alive", "Logo");
    public Value<Boolean> arraylist = new Value<>("Arraylist", true);
    public Value<Boolean> watermark = new Value<>("Watermark", true);
    public static Value<Boolean> suffix = new Value<>("Suffix", true);
    public Value<Boolean> tabgui = new Value<>("TabGUI", true);
    public Value<Boolean> notifications = new Value<>("Notifications", true);
    public static Value<Boolean> blur = new Value<>("Blur", true);
    public static Value<Double> red = new Value<>("Red", 168., 0, 255, 1);
    public static Value<Double> green = new Value<>("Green", 204., 0, 255, 1);
    public static Value<Double> blue = new Value<>("Blue", 255., 0, 255, 1);
    CustomFontRenderer arial19 = Client.INSTANCE.getFontManager().createFont(19);
    CustomFontRenderer arial17 = Client.INSTANCE.getFontManager().createFont(17);
    CustomFontRenderer arial11 = Client.INSTANCE.getFontManager().createFont(11);
    public int offset;

    @Subscribe
    public Listener<Render2DEvent> renderHud = new Listener<>(event -> {
        setSuffix((String) getValue("Mode").getValueObject());
        if (watermark.getValueObject()) drawWatermark(blur.getValueObject(), event);
        if (tabgui.getValueObject()) drawTabgui();
        if (notifications.getValueObject()) Client.INSTANCE.getNotificationManager().drawNotifications();
        if (arraylist.getValueObject()) drawArraylist(blur.getValueObject(), event);
    });

    @Subscribe
    public Listener<KeyboardEvent> doKeyboard = new Listener<>(event -> {
        if (tabgui.getValueObject())
            Client.INSTANCE.getTabGui().doKeys(event.getKey());
    });

    private void drawWatermark(boolean blur, Render2DEvent event) {
        var color = new Color(red.getValueObject().intValue(), green.getValueObject().intValue(), blue.getValueObject().intValue(), blur ? 150 : 255).getRGB();
        var version = Client.INSTANCE.isInDev() ? "Dev" : "v" + Client.INSTANCE.getClientVersion();
        var logo = new ResourceLocation("icons/logo3.png");
        var fps = "FPS: \247F" + Minecraft.getDebugFPS();
        var title = Client.INSTANCE.getClientName();
        switch (getMode("Mode")) {
            case "Alive":
                arial19.drawStringWithShadow(title, 3, 3, color);
                arial11.drawStringWithShadow(version, 24, 3, color);
                //arial17.drawStringWithShadow(fps, 3, tabgui.getValueObject() ? 117 : 13, color);
                break;
            case "Logo":
                RenderingUtils.drawImg(logo, 0, 2, 64, 64);
                //arial17.drawStringWithShadow(fps, 3, tabgui.getValueObject() ? 170 : 73, color);
                break;
        }
    }

    private void drawTabgui() {
        switch (getMode("Mode")) {
            case "Alive":
                Client.INSTANCE.getTabGui().draw(3, -6);
                break;
            case "Logo":
                Client.INSTANCE.getTabGui().draw(3, 48);
                break;
        }
    }

    private void drawArraylist(boolean blur, Render2DEvent event) {
        var color = new Color(red.getValueObject().intValue(), green.getValueObject().intValue(), blue.getValueObject().intValue(), blur ? 150 : 255).getRGB();
        var scaledWidth = event.getScaledResolution().getScaledWidth();
        List<Module> sortedModules = new ArrayList<>(Client.INSTANCE.getModuleManager().getModuleList().values());
        sortedModules.sort(Comparator.comparingDouble(mod -> -arial17.getWidth(mod.getDisplayName())));
        switch (getMode("Mode")) {
            case "Alive":
            case "Logo":
                offset = 0;
                for (Module module : sortedModules) {
                    var moduleWidth = arial17.getWidth(module.getDisplayName());
                    if (module.isEnabled()) {
                        module.animationX = (float) RenderingUtils.progressiveAnimation(module.animationX, scaledWidth - moduleWidth - 3, 1);
                        module.animationY = (float) RenderingUtils.progressiveAnimation(module.animationY, 10, 1);
                        arial17.drawStringWithShadow(module.getDisplayName(), module.animationX, offset + 3, color);
                    } else {
                        module.animationX = (float) RenderingUtils.progressiveAnimation(module.animationX, scaledWidth + moduleWidth, 1);
                        module.animationY = (float) RenderingUtils.progressiveAnimation(module.animationY, 0, 1);
                    }
                    offset += module.animationY;
                }
                break;
        }
    }
}
