package net.alive.manager.font;

import lombok.Getter;
import lombok.var;
import net.alive.utils.gui.CustomFontRenderer;

import java.awt.*;
import java.io.IOException;
import java.util.Objects;

@Getter
public class FontManager {
    public CustomFontRenderer createFont(int size) {
        var fileLocation = "fonts/arial.ttf";
        CustomFontRenderer font = null;
        try {
            font = new CustomFontRenderer(Font.createFont(0, Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResourceAsStream(fileLocation)))
                    .deriveFont(Font.PLAIN, size));
        } catch (IOException | FontFormatException error) {
            error.printStackTrace();
        }
        return font;
    }
}
