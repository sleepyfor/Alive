package net.alive.api.notification;

import lombok.AllArgsConstructor;

import java.awt.*;

@AllArgsConstructor
public enum NotificationType {
    MODULE_ENABLED(new Color(25, 255, 50, 185).getRGB()),
    MODULE_DISABLED(new Color(255, 25, 25, 185).getRGB()),
    ERROR(new Color(255, 0, 0, 185).getRGB()),
    SUCCESS(new Color(0, 255, 0, 185).getRGB());

    public int color;
}
