package net.alive;

import lombok.Getter;
import net.alive.api.event.EventBus;
import net.alive.api.event.IEvent;
import net.alive.api.gui.click.ClickGUI;
import net.alive.api.module.Category;
import net.alive.implement.modules.movement.Sprint;
import net.alive.implement.modules.render.Hud;
import net.alive.manager.font.FontManager;
import net.alive.manager.module.ModuleManager;

import java.awt.*;
import java.io.IOException;

@Getter
public enum Client {
    INSTANCE;

    private final String clientName = "Alive", clientVersion = "0.1.1", devVersion = "(Dev 1)";
    private EventBus<? super IEvent> eventBus;
    private final boolean isInDev = true;
    private ModuleManager moduleManager;
    private FontManager fontManager;
    private ClickGUI clickGUI;

    public void initClient() {
        eventBus = new EventBus<>();
        try {
            fontManager = new FontManager();
        } catch (IOException | FontFormatException ignored) {
        }
        clickGUI = new ClickGUI();
        moduleManager = new ModuleManager();
        moduleManager.getModule(Hud.class).setState(true);
        moduleManager.getModule(Sprint.class).setState(true);
        clickGUI.initClickGUI();
    }
}
