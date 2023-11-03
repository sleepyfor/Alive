package net.alive.api.notification;

import lombok.var;
import net.alive.Client;
import net.alive.utils.gui.CustomFontRenderer;
import net.alive.utils.gui.RenderingUtils;
import net.alive.utils.world.TimeUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

import java.awt.*;
import java.util.Collections;

public class Notification {
    CustomFontRenderer font = Client.INSTANCE.getFontManager().createFont(15);
    public TimeUtils timer = new TimeUtils(), remove = new TimeUtils();
    public float x, y, width, height, animation, animationY;
    public NotificationType type;
    public boolean active;
    public long duration;
    public String text;

    public Notification(NotificationType notificationType, String text, long duration){
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        animationY = sr.getScaledHeight() - 60;
        this.type = notificationType;
        this.duration = duration;
        this.text = text;
        animation = -1;
        active = true;
        width = 120;
        height = 21;
    }

    public void drawNotification(float x, float y){
        this.x = x;
        this.y = y;
        var title = "";
        if(active) {
            animation = (float) RenderingUtils.progressiveAnimation(animation, width, 0.6f);
            animationY = (float) RenderingUtils.progressiveAnimation(animationY, y, 0.6f);
        }
        else
            animation = (float) RenderingUtils.progressiveAnimation(animation, -1, 0.6f);
        if(timer.time(duration)){
            active = false;
            timer.reset();
        }
        if(remove.time(duration + 450)){
            Client.INSTANCE.getNotificationManager().notifications.remove(0);
            remove.reset();
        }
        RenderingUtils.drawRectangle(x - animation, animationY, x, animationY + height, new Color(10, 10, 10, 225).getRGB());
        RenderingUtils.drawRectangle(x - animation - 1, animationY, x - animation, animationY + height, type.color);
        if(type == NotificationType.MODULE_ENABLED)
            title = "Module Enabled!";
        if(type == NotificationType.MODULE_DISABLED)
            title = "Module Disabled!";
        font.drawStringWithShadow(title, x - animation +3 , animationY + 3, type.color);
        font.drawStringWithShadow(text, x - animation +3 , animationY + 12, -1);
    }
}
