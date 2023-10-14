package net.alive.implement.modules.render;

import com.sun.org.apache.xpath.internal.operations.Mod;
import lombok.AllArgsConstructor;
import lombok.var;
import net.alive.Client;
import net.alive.api.event.annotation.Subscribe;
import net.alive.api.event.listener.impl.Listener;
import net.alive.api.module.Category;
import net.alive.api.module.Module;
import net.alive.api.module.ModuleInfo;
import net.alive.api.value.Value;
import net.alive.implement.events.keys.KeyboardEvent;
import net.alive.implement.events.render.Render2DEvent;
import net.alive.manager.module.ModuleManager;
import net.alive.manager.value.ValueManager;
import net.alive.utils.gui.RenderingUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

@ModuleInfo(name = "Hud", displayName = "Hud", keyBind = 0, category = Category.RENDER)
public class Hud extends Module {
    public ArrayList<String> modes = new ArrayList<>();
    public Value mode = new Value<>("Mode", "Alive", modes);
    public Value<Boolean> arraylist = new Value<>("Arraylist", true);
    public Value<Boolean> watermark = new Value<>("Watermark", true);
    public Value<Boolean> tabgui = new Value<>("TabGUI", true);
    public static Value<Double> red = new Value<>("Red", 168., 0, 255, 1);
    public static Value<Double> green = new Value<>("Green", 204., 0, 255, 1);
    public static Value<Double> blue = new Value<>("Blue", 255., 0, 255, 1);

    public Hud() {
        modes.add("Traditional");
        modes.add("Alive");
        modes.add("Logo");
    }

    @Subscribe
    public Listener<Render2DEvent> renderHud = new Listener<>(event -> {
        setSuffix((String) getValue("Mode").getValueObject());
        switch (getMode("Mode")) {
            case "Alive":
                aliveHud(event);
                break;
            case "Traditional":
                traditionalHud(event);
                break;
            case "Logo":
                logoHud(event);
                break;
        }
    });

    public void aliveHud(Render2DEvent event) {
        var scaledWidth = event.getScaledResolution().getScaledWidth();
        var font = Client.INSTANCE.getFontManager().getArial17();
        var title = Client.INSTANCE.getClientName() + " \247Fv" + Client.INSTANCE.getClientVersion() + "" + (Client.INSTANCE.isInDev() ? " " + Client.INSTANCE.getDevVersion() : "");
        var fps = "FPS: \247F" + Minecraft.getDebugFPS();
        var color = new Color(red.getValueObject().intValue(), green.getValueObject().intValue(), blue.getValueObject().intValue(), 255).getRGB();
        var nameWidth = font.getWidth(title);
        var fpsWidth = font.getWidth(fps);
        if (watermark.getValueObject()) {
            font.drawStringWithShadow(title, scaledWidth - nameWidth - 3, 3, color);
            font.drawStringWithShadow(fps, scaledWidth - fpsWidth - 3, 13, color);
        }
        var offset = 0;
        List<Module> sortedModules = new ArrayList<>(Client.INSTANCE.getModuleManager().getModuleList().values());
        sortedModules.sort(Comparator.comparingDouble(mod -> -font.getWidth(mod.getDisplayName())));
        if (arraylist.getValueObject()) {
            for (Module module : sortedModules) {
                if (module.isEnabled()) {
                    font.drawStringWithShadow(module.getDisplayName(), 3, offset + 3, color);
                    offset += 10;
                }
            }
        }
        Client.INSTANCE.getTabGui().draw(3, offset - 15);
    }

    public void traditionalHud(Render2DEvent event) {
        var scaledWidth = event.getScaledResolution().getScaledWidth();
        var font = Client.INSTANCE.getFontManager().getArial17();
        var title = Client.INSTANCE.getClientName() + " \247Fv" + Client.INSTANCE.getClientVersion() + " " + (Client.INSTANCE.isInDev() ? Client.INSTANCE.getDevVersion() : "");
        var fps = "FPS: \247F" + Minecraft.getDebugFPS();
        var color = new Color(red.getValueObject().intValue(), green.getValueObject().intValue(), blue.getValueObject().intValue(), 255).getRGB();

        if (tabgui.getValueObject())
            Client.INSTANCE.getTabGui().draw(3, 4);

        if (watermark.getValueObject()) {
            font.drawStringWithShadow(title, 3, 3, color);
            font.drawStringWithShadow(fps, 3, 13, color);
        }
        var offset = 0;
        List<Module> sortedModules = new ArrayList<>(Client.INSTANCE.getModuleManager().getModuleList().values());
        sortedModules.sort(Comparator.comparingDouble(mod -> -font.getWidth(mod.getDisplayName())));
        if (arraylist.getValueObject()) {
            for (Module module : sortedModules) {
                var moduleWidth = font.getWidth(module.getDisplayName());
                if (module.isEnabled()) {
                    font.drawStringWithShadow(module.getDisplayName(), scaledWidth - moduleWidth - 3, offset + 3, color);
                    offset += 10;
                }
            }
        }
    }

    public void logoHud(Render2DEvent event) {
        var scaledWidth = event.getScaledResolution().getScaledWidth();
        var font = Client.INSTANCE.getFontManager().getArial17();
        var title = Client.INSTANCE.getClientName() + " \247Fv" + Client.INSTANCE.getClientVersion() + " " + (Client.INSTANCE.isInDev() ? Client.INSTANCE.getDevVersion() : "");
        var fps = "FPS: \247F" + Minecraft.getDebugFPS();
        var color = new Color(red.getValueObject().intValue(), green.getValueObject().intValue(), blue.getValueObject().intValue(), 255).getRGB();

        if (tabgui.getValueObject())
            Client.INSTANCE.getTabGui().draw(3, 48);

        if (watermark.getValueObject()) {
            RenderingUtils.drawImg(new ResourceLocation("icons/logo2.png"), 0, 2, 64, 64);
        }
        var offset = 0;
        List<Module> sortedModules = new ArrayList<>(Client.INSTANCE.getModuleManager().getModuleList().values());
        sortedModules.sort(Comparator.comparingDouble(mod -> -font.getWidth(mod.getDisplayName())));
        if (arraylist.getValueObject()) {
            for (Module module : sortedModules) {
                var moduleWidth = font.getWidth(module.getDisplayName());
                if (module.isEnabled()) {
                    font.drawStringWithShadow(module.getDisplayName(), scaledWidth - moduleWidth - 3, offset + 3, color);
                    offset += 10;
                }
            }
        }
    }

    @Subscribe
    public Listener<KeyboardEvent> doKeyboard = new Listener<>(event -> {
//        if (tabgui.getValueObject() && (getMode("Mode").equalsIgnoreCase("Traditional")) || getMode("Mode").equalsIgnoreCase("Logo"))
            Client.INSTANCE.getTabGui().doKeys(event.getKey());
    });
}
