package net.alive.api.module;

import lombok.Getter;
import lombok.Setter;
import lombok.var;
import net.alive.Client;
import net.alive.api.notification.Notification;
import net.alive.api.notification.NotificationType;
import net.alive.api.value.Value;
import net.alive.utils.gui.CustomFontRenderer;
import net.minecraft.client.Minecraft;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import net.alive.implement.modules.render.Hud;
import net.minecraft.client.gui.ScaledResolution;

@Getter @Setter
public class Module {
    public List<Value> values = new ArrayList<>();
    public Minecraft mc = Minecraft.getMinecraft();
    private String name, displayName;
    private ModuleInfo moduleInfo;
    private Category category;
    public float animationX, animationY;
    private boolean enabled;
    private String suffix;
    private int keybind;

    public Module() {
        Class<?> info = this.getClass();
        if (!info.isAnnotationPresent(ModuleInfo.class))
            throw new RuntimeException(info.getName() + "ModuleInfo annotation not found! Please make sure to initialize!");

        moduleInfo = info.getDeclaredAnnotation(ModuleInfo.class);
        displayName = moduleInfo.displayName();
        category = moduleInfo.category();
        keybind = moduleInfo.keyBind();
        name = moduleInfo.name();
    }

    public void onEnable() {
        Client.INSTANCE.getEventBus().register(this);
        ScaledResolution sr = new ScaledResolution(mc);
        animationY = 4;
        animationX = sr.getScaledWidth() + Client.INSTANCE.getArial17().getWidth(getDisplayName());
        if (mc.theWorld != null)
            Client.INSTANCE.getNotificationManager().addNotification(new Notification(NotificationType.MODULE_ENABLED, name + " has been enabled!", 4000));
    }

    public void onDisable() {
        Client.INSTANCE.getEventBus().unregister(this);
        mc.timer.timerSpeed = 1.0f;
        if (mc.theWorld != null)
            Client.INSTANCE.getNotificationManager().addNotification(new Notification(NotificationType.MODULE_DISABLED, name + " has been disabled!", 4000));
    }

    public List<Value> getValues() {
        List<Value> values = new ArrayList<>();

        for (Field field : getClass().getDeclaredFields()) {
            try {
                field.setAccessible(true);

                Object o = field.get(this);

                if (o instanceof Value)
                    values.add((Value) o);
            } catch (IllegalAccessException ignored) {
            }
        }
        return values;
    }

    public Value getValue(String valueName) {
        for (Field field : getClass().getDeclaredFields()) {
            try {
                field.setAccessible(true);

                final Object o = field.get(this);

                if (o instanceof Value) {
                    Value value = (Value) o;

                    if (value.getValueName().equalsIgnoreCase(valueName))
                        return value;
                }
            } catch (IllegalAccessException e) {
            }
        }

        return null;
    }

    public String getDisplayName() {
        return Hud.suffix.getValueObject() ? (suffix != null ? name + " \247f" + suffix : name) : name;
    }

    public String getMode(String mode){
        return (String) getValue(mode).getValueObject();
    }

    public void setState(boolean enabled) {
        if (enabled) onEnable();
        if (!enabled) onDisable();
        this.enabled = enabled;
    }

    public void toggle() {
        enabled = !enabled;
        if (enabled)
            onEnable();
        else
            onDisable();
    }
}
