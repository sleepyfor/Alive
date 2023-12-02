package net.alive.api.notification;

import lombok.var;
import net.alive.Client;
import net.alive.utils.gui.CustomFontRenderer;
import net.alive.utils.gui.RenderingUtils;
import net.alive.utils.world.TimeUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.Collections;

public class Notification {
    public TimeUtils timer = new TimeUtils(), remove = new TimeUtils(), anim = new TimeUtils();
    public float x, y, width, height, animation, animationY, dur;
    public NotificationType type;
    public String title, text;
    public boolean active;
    public long duration;

    public Notification(NotificationType notificationType, String title, String text, long duration){
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        width = Client.INSTANCE.getArial15().getWidth(text) + 10;
        animationY = sr.getScaledHeight() - 60;
        this.type = notificationType;
        this.duration = duration;
        this.title = title;
        this.text = text;
        animation = -1;
        dur = width;
        active = true;
        height = 21;
    }

    public void drawNotification(float x, float y){
        this.x = x;
        this.y = y;
        if(active) {
            animation = (float) RenderingUtils.progressiveAnimation(animation, width, 0.6f);
            animationY = (float) RenderingUtils.progressiveAnimation(animationY, y, 0.6f);
        }
        else
            animation = (float) RenderingUtils.progressiveAnimation(animation, -1, 0.6f);
        if (dur >= -1 && anim.time(1)) {
            dur -= (width / (duration - 675)) * 2;
            anim.reset();
        }
        if(timer.time(duration)){
            active = false;
            timer.reset();
        }
        if(remove.time(duration + 450)){
            Client.INSTANCE.getNotificationManager().notifications.remove(0);
            remove.reset();
        }
        RenderingUtils.scissorBox(x - animation - 1, animationY, x, animationY + height);
        RenderingUtils.drawBlurredRect(RenderingUtils.BlurType.NORMAL, x - animation, animationY, x, animationY + height, new Color(10, 10, 10, 65).getRGB());
        RenderingUtils.drawRectangle(x - animation, animationY, x, animationY + height, new Color(10, 10, 10, 65).getRGB());
        RenderingUtils.drawRectangle(x - animation - 1, animationY, x - animation, animationY + height, type.color);
        RenderingUtils.drawRectangle(x - dur, animationY + height - 1, x, animationY + height, type.color);
        Client.INSTANCE.getArial15().drawStringWithShadow(title, x - animation +3 , animationY + 3, type.color);
        Client.INSTANCE.getArial15().drawStringWithShadow(text, x - animation +3 , animationY + 12, new Color(255, 255, 255, 185).getRGB());
        GL11.glDisable(GL11.GL_SCISSOR_TEST);
    }
}
