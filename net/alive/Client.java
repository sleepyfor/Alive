package net.alive;

import lombok.Getter;
import net.alive.api.event.EventBus;
import net.alive.api.event.IEvent;
import net.alive.api.file.Config;
import net.alive.api.gui.click.ClickGUI;
import net.alive.api.gui.flushgui.FlushGUI;
import net.alive.api.gui.tab.TabGui;
import net.alive.api.notification.NotificationManager;
import net.alive.implement.modules.movement.Sprint;
import net.alive.implement.modules.render.Hud;
import net.alive.manager.font.FontManager;
import net.alive.manager.module.ModuleManager;
import net.alive.utils.gui.CustomFontRenderer;
import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;
import net.arikia.dev.drpc.DiscordUser;
import net.arikia.dev.drpc.callbacks.ReadyCallback;
import net.minecraft.client.Minecraft;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;

@Getter
public enum Client {
    INSTANCE;

    private final String clientName = "Alive", clientVersion = "0.9.0", devVersion = "(Dev 1)";
    private final File DIR = new File(Minecraft.getMinecraft().mcDataDir, clientName);
    private CustomFontRenderer arial21, arial19, arial17, arial15, arial11;
    private NotificationManager notificationManager;
    private EventBus<? super IEvent> eventBus;
    private final boolean isInDev = false;
    private ModuleManager moduleManager;
    private FontManager fontManager;
    private boolean running = true;
    private FlushGUI flushGUI;
    private ClickGUI clickGUI;
    private Config config;
    private TabGui tabGui;
    private long started;

    public void initClient() {
        startRPC();
        eventBus = new EventBus<>();
        fontManager = new FontManager();
        arial21 = fontManager.createFont(21);
        arial19 = fontManager.createFont(19);
        arial17 = fontManager.createFont(17);
        arial15 = fontManager.createFont(15);
        arial11 = fontManager.createFont(11);
        clickGUI = new ClickGUI();
        flushGUI = new FlushGUI();
        notificationManager = new NotificationManager();
        tabGui = new TabGui();
        moduleManager = new ModuleManager();
        tabGui.initMate();
        clickGUI.initClickGUI();
        flushGUI.initializeGui();
        config = new Config();
        config.createDirectory();
        config.loadConfig();
    }

    private void startRPC(){
        started = System.currentTimeMillis();
        DiscordEventHandlers handlers = new DiscordEventHandlers.Builder().setReadyEventHandler(discordUser -> updateRPC("Loading Alive...", "")).build();

        DiscordRPC.discordInitialize("1162528311709270036", handlers, true);

        new Thread("DiscordRPC"){
            @Override
            public void run(){
                while (running)
                    DiscordRPC.discordRunCallbacks();
            }
        }.start();
    }

    public void stopRPC(){
        running = false;
        DiscordRPC.discordShutdown();
    }

    public void updateRPC(String title, String details){
        DiscordRichPresence.Builder builder = new DiscordRichPresence.Builder(title);
        builder.setBigImage("logo", "");
        builder.setDetails(details);
        builder.setStartTimestamps(started);
        DiscordRPC.discordUpdatePresence(builder.build());
    }
}
