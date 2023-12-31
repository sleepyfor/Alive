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
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.*;
import java.util.List;

@ModuleInfo(name = "Hud", displayName = "Hud", keyBind = 0, category = Category.RENDER)
public class Hud extends Module {
    public Value<String> mode = new Value<>("Mode", "Alive", "Alive", "Logo");
    public Value<Boolean> notifications = new Value<>("Notifications", true);
    public Value<Boolean> watermark = new Value<>("Watermark", true);
    public Value<Boolean> arraylist = new Value<>("Arraylist", true);
    public Value<Boolean> tabgui = new Value<>("TabGUI", true);
    public static Value<Boolean> suffix = new Value<>("Suffix", true);
    public static Value<Boolean> blur = new Value<>("Blur", true);
    public static Value<Double> red = new Value<>("Red", 168., 0, 255, 1);
    public static Value<Double> green = new Value<>("Green", 204., 0, 255, 1);
    public static Value<Double> blue = new Value<>("Blue", 255., 0, 255, 1);
    public int offset;

    @Subscribe
    public Listener<Render2DEvent> renderHud = new Listener<>(event -> {
        var color = new Color(red.getValueObject().intValue(), green.getValueObject().intValue(), blue.getValueObject().intValue(), blur.getValueObject() ? 150 : 255).getRGB();
        setSuffix((String) getValue("Mode").getValueObject());
        if (tabgui.getValueObject()) drawTabgui();
        if (notifications.getValueObject()) Client.INSTANCE.getNotificationManager().drawNotifications();
        if (arraylist.getValueObject()) drawArraylist(blur.getValueObject(), event);
        if (watermark.getValueObject()) drawWatermark(blur.getValueObject(), event);
        if (mc.currentScreen instanceof GuiChat) {
            Client.INSTANCE.getArial17().drawStringWithShadow("FPS: " + Minecraft.getDebugFPS() + " X: " + mc.thePlayer.getPosition().getX() + " Y: " +
                            mc.thePlayer.getPosition().getY() + " Z: " + mc.thePlayer.getPosition().getZ(), 2, event.getScaledResolution().getScaledHeight() -
                    Client.INSTANCE.getArial17().FONT_HEIGHT - 16, color);
        } else {
            Client.INSTANCE.getArial17().drawStringWithShadow("FPS: " + Minecraft.getDebugFPS(), 2, event.getScaledResolution().getScaledHeight() -
                    Client.INSTANCE.getArial17().FONT_HEIGHT - 12, color);
            Client.INSTANCE.getArial17().drawStringWithShadow("X: " +
                    mc.thePlayer.getPosition().getX() + " Y: " + mc.thePlayer.getPosition().getY() + " Z: " +
                    mc.thePlayer.getPosition().getZ(), 2, event.getScaledResolution().getScaledHeight() - Client.INSTANCE.getArial17().FONT_HEIGHT - 2, color);
        }
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
                Client.INSTANCE.getArial21().drawStringWithShadow(title, 3, 3, color);
                Client.INSTANCE.getArial11().drawStringWithShadow(version, 26, 4, color);
                break;
            case "Logo":
                RenderingUtils.drawImg(logo, 0, 2, 64, 64);
                break;
        }
    }

    private void drawTabgui() {
        switch (getMode("Mode")) {
            case "Alive":
                Client.INSTANCE.getTabGui().draw(3, -4);
                break;
            case "Logo":
                Client.INSTANCE.getTabGui().draw(3, 48);
                break;
        }
    }

    private void drawArraylist(boolean blur, Render2DEvent event) {
//        var color = new Color(red.getValueObject().intValue(), green.getValueObject().intValue(), blue.getValueObject().intValue(), blur ? 150 : 255).getRGB();
        var clientColor = new Color(red.getValueObject().intValue(), green.getValueObject().intValue(), blue.getValueObject().intValue(), blur ? 150 : 255).brighter();
        var clientColor2 = new Color(red.getValueObject().intValue(), green.getValueObject().intValue(), blue.getValueObject().intValue(), blur ? 150 : 255);
        var scaledWidth = event.getScaledResolution().getScaledWidth();
        List<Module> sortedModules = new ArrayList<>(Client.INSTANCE.getModuleManager().getModuleList().values());
        sortedModules.sort(Comparator.comparingDouble(mod -> -Client.INSTANCE.getArial17().getWidth(mod.getDisplayName())));
        switch (getMode("Mode")) {
            case "Alive":
            case "Logo":
                offset = 0;
                for (Module module : sortedModules) {
                    var color = RenderingUtils.getGradientOffset(clientColor, clientColor2,
                            (Math.abs(((System.currentTimeMillis()) / 8)) / 100D) + (offset /
                                    (( Client.INSTANCE.getArial17().getHeight(module.getDisplayName()) - 123)))).getRGB();
                    var moduleWidth = Client.INSTANCE.getArial17().getWidth(module.getDisplayName());
                    if (module.isEnabled()) {
                        module.animationX = (float) RenderingUtils.progressiveAnimation(module.animationX, scaledWidth - moduleWidth - 3, 1);
                        module.animationY = (float) RenderingUtils.progressiveAnimation(module.animationY, 10, 1);
                        RenderingUtils.drawRectangle(module.animationX - 3, offset + 3, module.animationX +
                                        Client.INSTANCE.getArial17().getWidth(module.getDisplayName()) + 1, offset + 13, new Color(5, 5, 5, 100).getRGB());
                        RenderingUtils.drawRectangle(module.animationX + Client.INSTANCE.getArial17().getWidth(module.getDisplayName()), offset + 3,
                                module.animationX + Client.INSTANCE.getArial17().getWidth(module.getDisplayName()) + 1, offset + 13, color);
                        Client.INSTANCE.getArial17().drawStringWithShadow(module.getDisplayName(), module.animationX - 2, offset + 4.5f, color);
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
