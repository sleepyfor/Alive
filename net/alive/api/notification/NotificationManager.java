package net.alive.api.notification;

import lombok.var;
import net.alive.Client;
import net.alive.api.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class NotificationManager {

    public List<Notification> notifications;

    public NotificationManager(){
        notifications = new ArrayList<>();
    }

    public void drawNotifications(){
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        float y = 0;
//        List<Notification> sortedNotifications = new ArrayList<>(notifications);
//        notifications.sort(Comparator.comparingDouble(not -> not.duration));
        for(int i = 0; i > -notifications.size(); i--){
            var notification = notifications.get(-i);
            notification.drawNotification(sr.getScaledWidth(), y + sr.getScaledHeight() - 60);
            y -= notification.height + 1;
        }
    }

    public void addNotification(Notification notification){
        notifications.add(notification);
    }

    public void removeNotification(int number){
        notifications.remove(number);
    }
}
