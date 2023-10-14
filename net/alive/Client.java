package net.alive;

import lombok.Getter;
import net.alive.api.event.EventBus;
import net.alive.api.event.IEvent;
import net.alive.api.file.Config;
import net.alive.api.gui.click.ClickGUI;
import net.alive.api.gui.tab.TabGui;
import net.alive.api.module.Category;
import net.alive.implement.modules.movement.Sprint;
import net.alive.implement.modules.render.Hud;
import net.alive.manager.font.FontManager;
import net.alive.manager.module.ModuleManager;
import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;
import net.arikia.dev.drpc.DiscordUser;
import net.arikia.dev.drpc.callbacks.ReadyCallback;
import net.minecraft.client.Minecraft;
import org.lwjgl.Sys;

import java.awt.*;
import java.io.File;
import java.io.IOException;

@Getter
public enum Client {
    INSTANCE;

    private final String clientName = "Alive", clientVersion = "0.3.0", devVersion = "(Dev 1)";
    private final File DIR = new File(Minecraft.getMinecraft().mcDataDir, clientName);
    private EventBus<? super IEvent> eventBus;
    private final boolean isInDev = false;
    private net.alive.api.gui.click2.ClickGUI newClickGUI;
    private ModuleManager moduleManager;
    private FontManager fontManager;
    private boolean running = true;
    private ClickGUI clickGUI;
    private Config config;
    private TabGui tabGui;
    private long started;

    public void initClient() {
        startRPC();
        eventBus = new EventBus<>();
        try {
            fontManager = new FontManager();
        } catch (IOException | FontFormatException ignored) {
        }
        clickGUI = new ClickGUI();
        tabGui = new TabGui();
        moduleManager = new ModuleManager();
        moduleManager.getModule(Hud.class).setState(true);
        moduleManager.getModule(Sprint.class).setState(true);
        tabGui.initMate();
        clickGUI.initClickGUI();
        newClickGUI = new net.alive.api.gui.click2.ClickGUI();
        newClickGUI.initClickGUI();
        config = new Config();
        config.createDirectory();
        try {
            config.loadConfig();
        } catch (IOException ignored) {
        }
    }

    private void startRPC(){
        started = System.currentTimeMillis();
        DiscordEventHandlers handlers = new DiscordEventHandlers.Builder().setReadyEventHandler(new ReadyCallback() {
            @Override
            public void apply(DiscordUser discordUser) {
                updateRPC("Loading Alive...", "");
            }
        }).build();

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
