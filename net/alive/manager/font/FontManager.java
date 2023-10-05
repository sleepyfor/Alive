package net.alive.manager.font;

import lombok.Getter;
import lombok.var;
import net.alive.utils.gui.CustomFontRenderer;

import java.awt.*;
import java.io.IOException;
import java.util.Objects;

@Getter
public class FontManager {

    public CustomFontRenderer arial17, arial15;

    public FontManager() throws IOException, FontFormatException {
        var fileLocation = "fonts/arial.ttf";
        arial17 = new CustomFontRenderer(Font.createFont(0, Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResourceAsStream(fileLocation)))
                .deriveFont(0, 17f));
        arial15 = new CustomFontRenderer(Font.createFont(0, Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResourceAsStream(fileLocation)))
                .deriveFont(0, 15f));
    }
}
