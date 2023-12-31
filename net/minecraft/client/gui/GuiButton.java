package net.minecraft.client.gui;

import lombok.var;
import net.alive.Client;
import net.alive.implement.modules.render.Hud;
import net.alive.utils.gui.CustomFontRenderer;
import net.alive.utils.gui.RenderingUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

import java.awt.*;

public class GuiButton extends Gui
{
    protected static final ResourceLocation buttonTextures = new ResourceLocation("textures/gui/widgets.png");

    /** Button width in pixels */
    protected int width;

    /** Button height in pixels */
    protected int height;

    /** The x position of this control. */
    public int xPosition;

    /** The y position of this control. */
    public int yPosition;

    /** The string displayed on this control. */
    public String displayString;
    public int id;

    /** True if this control is enabled, false to disable. */
    public boolean enabled;

    /** Hides the button completely if false. */
    public boolean visible;
    protected boolean hovered;
    public float animate;
    public int red, green, blue;

    public GuiButton(int buttonId, int x, int y, String buttonText)
    {
        this(buttonId, x, y, 200, 20, buttonText);
    }

    public GuiButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText)
    {
        this.width = 200;
        this.height = 20;
        this.enabled = true;
        this.visible = true;
        this.id = buttonId;
        this.xPosition = x;
        this.yPosition = y;
        this.width = widthIn;
        this.height = heightIn;
        this.displayString = buttonText;
        animate = xPosition;
        red = 255;
        green = 255;
        blue = 255;
    }

    /**
     * Returns 0 if the button is disabled, 1 if the mouse is NOT hovering over this button and 2 if it IS hovering over
     * this button.
     */
    protected int getHoverState(boolean mouseOver)
    {
        int i = 1;

        if (!this.enabled)
        {
            i = 0;
        }
        else if (mouseOver)
        {
            i = 2;
        }

        return i;
    }

    /**
     * Draws this button to the screen.
     */
    public void drawButton(Minecraft mc, int mouseX, int mouseY)
    {
        if (this.visible) {
            int color = new Color(Hud.red.getValueObject().intValue(), Hud.green.getValueObject().intValue(), Hud.blue.getValueObject().intValue(), 255).getRGB();
            int color2 = new Color(Hud.red.getValueObject().intValue(), Hud.green.getValueObject().intValue(), Hud.blue.getValueObject().intValue(), 150).getRGB();
            FontRenderer fontrenderer = mc.fontRendererObj;
            mc.getTextureManager().bindTexture(buttonTextures);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
            int i = this.getHoverState(this.hovered);
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            GlStateManager.blendFunc(770, 771);
            if (hovered) {
                animate = (float) RenderingUtils.progressiveAnimation(animate, xPosition + width, 0.6);
                red = (int) RenderingUtils.progressiveAnimation(red, Hud.red.getValueObject().intValue(), 5);
                green = (int) RenderingUtils.progressiveAnimation(red, Hud.green.getValueObject().intValue(), 5);
                blue = (int) RenderingUtils.progressiveAnimation(red, Hud.blue.getValueObject().intValue(), 5);
            }else {
                animate = (float) RenderingUtils.progressiveAnimation(animate, xPosition, 0.6);
                red = (int) RenderingUtils.progressiveAnimation(red, 255, 0.6);
                green = (int) RenderingUtils.progressiveAnimation(red, 255, 0.6);
                blue = (int) RenderingUtils.progressiveAnimation(red, 255, 0.6);
            }
//            this.drawTexturedModalRect(this.xPosition, this.yPosition, 0, 46 + i * 20, this.width / 2, this.height);
//            this.drawTexturedModalRect(this.xPosition + this.width / 2, this.yPosition, 200 - this.width / 2, 46 + i * 20, this.width / 2, this.height);
            RenderingUtils.drawBlurredRect(RenderingUtils.BlurType.NORMAL, xPosition, yPosition, xPosition + width, yPosition + height, new Color(0, 0, 0, 90).getRGB());
            RenderingUtils.drawRectangle(xPosition, yPosition, xPosition + width, yPosition + height, new Color(0, 0, 0, 60).getRGB());
            RenderingUtils.drawRectangle(xPosition, yPosition + height - 1, animate, yPosition + height, color2);
            this.mouseDragged(mc, mouseX, mouseY);
            int j = Color.white.getRGB();

            if (!this.enabled)
            {
                j = Color.white.getRGB();
            }
            else if (this.hovered)
            {
                j = new Color(red, green, blue, 255).getRGB();
            }
            Client.INSTANCE.getArial17().drawCenteredStringWithShadow(displayString, this.xPosition + ((float) this.width / 2),
                    this.yPosition + (float) (this.height - 8) / 2, j);
        }
    }

    /**
     * Fired when the mouse button is dragged. Equivalent of MouseListener.mouseDragged(MouseEvent e).
     */
    protected void mouseDragged(Minecraft mc, int mouseX, int mouseY)
    {
    }

    /**
     * Fired when the mouse button is released. Equivalent of MouseListener.mouseReleased(MouseEvent e).
     */
    public void mouseReleased(int mouseX, int mouseY)
    {
    }

    /**
     * Returns true if the mouse has been pressed on this control. Equivalent of MouseListener.mousePressed(MouseEvent
     * e).
     */
    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY)
    {
        return this.enabled && this.visible && mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
    }

    /**
     * Whether the mouse cursor is currently over the button.
     */
    public boolean isMouseOver()
    {
        return this.hovered;
    }

    public void drawButtonForegroundLayer(int mouseX, int mouseY)
    {
    }

    public void playPressSound(SoundHandler soundHandlerIn)
    {
        soundHandlerIn.playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
    }

    public int getButtonWidth()
    {
        return this.width;
    }

    public void setWidth(int width)
    {
        this.width = width;
    }
}
