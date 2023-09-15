package net.alive.api.module;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Category {
    COMBAT("Combat"),
    MOVEMENT("Movement"),
    PLAYER("Player"),
    WORLD("World"),
    RENDER("Render");

    public String realName;
}
