package net.alive.api.notification;

import lombok.AllArgsConstructor;

import java.awt.*;

@AllArgsConstructor
public enum NotificationType {
    MODULE_ENABLED(new Color(25, 255, 50, 255).getRGB()),
    MODULE_DISABLED(new Color(255, 25, 25, 255).getRGB()),
    ERROR(new Color(255, 0, 0, 255).getRGB()),
    SUCCESS(new Color(0, 255, 0, 255).getRGB());

    public int color;
}
