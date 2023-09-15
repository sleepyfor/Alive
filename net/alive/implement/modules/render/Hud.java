package net.alive.implement.modules.render;

import com.sun.org.apache.xpath.internal.operations.Mod;
import lombok.var;
import net.alive.Client;
import net.alive.api.event.annotation.Subscribe;
import net.alive.api.event.listener.impl.Listener;
import net.alive.api.module.Category;
import net.alive.api.module.Module;
import net.alive.api.module.ModuleInfo;
import net.alive.api.value.Value;
import net.alive.api.value.types.BooleanValue;
import net.alive.implement.events.render.Render2DEvent;
import net.alive.manager.module.ModuleManager;
import net.alive.manager.value.ValueManager;
import net.alive.utils.gui.RenderingUtils;
import net.minecraft.client.Minecraft;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

@ModuleInfo(name = "Hud", displayName = "Hud", keyBind = 0, category = Category.RENDER)
public class Hud extends Module {

    public Value arraylist = new BooleanValue("Arraylist", true);
    public Value watermark = new BooleanValue("Watermark", true);

    public Hud() {
        addValue(arraylist);
        addValue(watermark);
    }

    @Subscribe
    public Listener<Render2DEvent> renderHud = new Listener<>(event -> {
        var scaledWidth = event.getScaledResolution().getScaledWidth();
        var font = Client.INSTANCE.getFontManager().getArial17();
        var title = Client.INSTANCE.getClientName() + " \247Fv" + Client.INSTANCE.getClientVersion() + " " + Client.INSTANCE.getDevVersion();
        var author = "By \247FJustMeDark";
        var fps = "FPS: \247F" + Minecraft.getDebugFPS();
        var color = new Color(255, 161, 205, 255).getRGB();
        var nameWidth = font.getWidth(title);
        var authWidth = font.getWidth(author);
        var fpsWidth = font.getWidth(fps);
        font.drawStringWithShadow(title, scaledWidth - nameWidth - 3, 3, color);
        font.drawStringWithShadow(author, scaledWidth - authWidth - 3, 13, color);
        font.drawStringWithShadow(fps, scaledWidth - fpsWidth - 3, 23, color);
        var offset = 0;
        List<Module> sortedModules = new ArrayList<>(Client.INSTANCE.getModuleManager().getModuleList().values());
        sortedModules.sort(Comparator.comparingDouble(mod -> -font.getWidth(mod.getDisplayName())));
        for (Module module : sortedModules) {
            if (module.isEnabled()) {
                font.drawStringWithShadow(module.getDisplayName(), 3, offset + 3, color);
                offset += 10;
            }
        }
    });
}
