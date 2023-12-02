package net.alive.api.gui.flushgui;

import lombok.var;
import net.alive.Client;
import net.alive.api.gui.click.component.value.DoubleComponent;
import net.alive.api.gui.flushgui.components.Component;
import net.alive.api.gui.flushgui.components.values.Slider;
import net.alive.api.value.Value;
import net.alive.utils.gui.RenderingUtils;
import net.alive.utils.math.MathUtils;

import java.awt.*;
import java.text.DecimalFormat;

public class FlushUtils {
    public double animation, slideAnimation;

    public FlushUtils(){
    }

    public void drawAnimatedButton(String text, float x, float y, float width, float height, int color, int toggledColor, boolean condition, boolean hovered) {
        animation = RenderingUtils.progressiveAnimation(animation, hovered ? width : 0, 0.6);
        RenderingUtils.drawRectangle(x, y, x + width, y + height, new Color(40, 40, 40, 65).getRGB());
        RenderingUtils.drawRectangle(x, y + height - 0.5f, (float) (x + animation), y + height, condition ? toggledColor : color);
        Client.INSTANCE.getArial17().drawCenteredStringWithShadow(text, x + width / 2,
                y + (height / 2) - 3, condition ? toggledColor : color);
    }

    public void drawAnimatedCheckbox(String text, String text2, float x, float y, float width, float height, Color color, Color toggledColor, boolean condition, boolean hovered) {
        animation = RenderingUtils.progressiveAnimation(animation, hovered ? 200 : 185, 0.6);
        RenderingUtils.drawRectangle(x, y, x + width, y + height, new Color(40, 40, 40, 65).getRGB());
        Client.INSTANCE.getArial17().drawStringWithShadow(text, x + 2, y + (height / 2) - 3, hovered ? new Color(toggledColor.getRed(), toggledColor.getGreen(),
                toggledColor.getBlue(), (int) animation).getRGB() : new Color(color.getRed(), color.getGreen(), color.getBlue(), (int) animation).getRGB());
        Client.INSTANCE.getArial17().drawStringWithShadow(text2, x + width - Client.INSTANCE.getArial17().getWidth(text2) - 2,
                y + (height / 2) - 3, hovered ? new Color(toggledColor.getRed(), toggledColor.getGreen(), toggledColor.getBlue(),
                        (int) animation).getRGB() : new Color(color.getRed(), color.getGreen(), color.getBlue(), (int) animation).getRGB());
    }

    public void drawAnimatedCombobox(String text, String text2, float x, float y, float width, float height, Color color, Color toggledColor, boolean hovered) {
        animation = RenderingUtils.progressiveAnimation(animation, hovered ? 200 : 185, 0.6);
        RenderingUtils.drawRectangle(x, y, x + width, y + height, new Color(40, 40, 40, 65).getRGB());
        Client.INSTANCE.getArial17().drawStringWithShadow(text, x + 2, y + (height / 2) - 3, hovered ? new Color(toggledColor.getRed(), toggledColor.getGreen(),
                toggledColor.getBlue(), (int) animation).getRGB() : new Color(color.getRed(), color.getGreen(), color.getBlue(), (int) animation).getRGB());
        Client.INSTANCE.getArial17().drawStringWithShadow(text2, x + width - Client.INSTANCE.getArial17().getWidth(text2) - 2,
                y + (height / 2) - 3, hovered ? new Color(toggledColor.getRed(), toggledColor.getGreen(), toggledColor.getBlue(),
                        (int) animation).getRGB() : new Color(color.getRed(), color.getGreen(), color.getBlue(), (int) animation).getRGB());
    }

    public void drawAnimatedSlider(String text, String text2, float x, float y, float width, float height, Color color, Color toggledColor, boolean hovered, Value<Double> value, Component component) {
        var max = value.getMax();
        var min = value.getMin();
        var comX = component.x + 9;
        var comWid = component.width - 11;
        var inc = value.getInc();
        if (component instanceof Slider && !((Slider) component).sliding)
            slideAnimation = (float) (comX + (value.getValueObject() - min) / (max - min) * comWid) - 5;
        slideAnimation = RenderingUtils.progressiveAnimation(slideAnimation, (float) (comX + (value.getValueObject() - min) / (max - min) * comWid) - 5, 1);
        animation = RenderingUtils.progressiveAnimation(animation, hovered ? 200 : 185, 0.6);
        RenderingUtils.drawRectangle(x, y, x + width, y + height, new Color(40, 40, 40, 65).getRGB());
        RenderingUtils.drawRectangle(x + 4.5f, y + 17, x + width - 4.75f, y + 18, new Color(255, 255, 255, (int) animation).getRGB());
        RenderingUtils.drawRectangle((float) slideAnimation, component.y + 15, (float) slideAnimation + 2, component.y + 20, new Color(255, 255, 255, (int) animation).getRGB());
        Client.INSTANCE.getArial17().drawStringWithShadow(text, x + 2, y + (height / 2) - 6, hovered ? new Color(toggledColor.getRed(), toggledColor.getGreen(),
                toggledColor.getBlue(), (int) animation).getRGB() : new Color(color.getRed(), color.getGreen(), color.getBlue(), (int) animation).getRGB());
        Client.INSTANCE.getArial17().drawStringWithShadow(text2, x + width - Client.INSTANCE.getArial17().getWidth(text2) - 2,
                y + (height / 2) - 6, hovered ? new Color(toggledColor.getRed(), toggledColor.getGreen(), toggledColor.getBlue(),
                        (int) animation).getRGB() : new Color(color.getRed(), color.getGreen(), color.getBlue(), (int) animation).getRGB());
    }
}
