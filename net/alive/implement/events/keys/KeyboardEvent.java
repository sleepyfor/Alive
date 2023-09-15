package net.alive.implement.events.keys;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.alive.Client;
import net.alive.api.event.IEvent;
import net.alive.api.module.Module;

@Getter
public class KeyboardEvent implements IEvent {
    private int key;

    public KeyboardEvent(int key) {
        this.key = key;
        for (Module module : Client.INSTANCE.getModuleManager().getModuleList().values())
            if (key != 0 && key != 256 && module.getKeybind() == key)
                module.toggle();
    }
}
