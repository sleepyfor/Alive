package net.alive.utils.gui;

import lombok.var;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class RenderingUtils {

    public static void drawRectangle(float startX, float startY, float endX, float endY, int color) {
//        GL11.glPushMatrix();
//        GL11.glTranslatef(startX, startY, 0.0f);
//        GL11.glBegin(GL11.GL_QUADS);
//        glColor(color);
//        GL11.glVertex2f(0, 0);
//        GL11.glVertex2f(0, endY);
//        GL11.glVertex2f(endX, endY);
//        GL11.glVertex2f(endX, 0);
//        GL11.glEnd();
//        GL11.glPopMatrix();

        GL11.glDisable(2929);
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glDepthMask(true);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        GL11.glHint(3155, 4354);
        glColor(color);
        // drawRect((float) x, (float) y, (float) x1, (float) y1);
        GL11.glBegin(7);
        GL11.glVertex2f(startX, endY);
        GL11.glVertex2f(endX, endY);
        GL11.glVertex2f(endX, startY);
        GL11.glVertex2f(startX, startY);
        GL11.glEnd();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glEnable(2929);
        GL11.glDisable(2848);
        GL11.glHint(3154, 4352);
        GL11.glHint(3155, 4352);
    }

    public static void drawImg(ResourceLocation loc, double posX, double posY, double width, double height) {
        GlStateManager.pushMatrix();
        GlStateManager.enableAlpha();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(770, 771);
        Minecraft.getMinecraft().getTextureManager().bindTexture(loc);
        float f = 1.0F / (float)width;
        float f1 = 1.0F / (float)height;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldrenderer.pos(posX, posY + height, 0.0D).tex(0.0F * f, (0.0F + (float)height) * f1).endVertex();
        worldrenderer.pos(posX + width, posY + height, 0.0D).tex((0.0F + (float)width) * f, (0.0F + (float)height) * f1).endVertex();
        worldrenderer.pos(posX + width, posY, 0.0D).tex((0.0F + (float)width) * f, 0.0F * f1).endVertex();
        worldrenderer.pos(posX, posY, 0.0D).tex(0.0F * f, 0.0F * f1).endVertex();
        tessellator.draw();
        GlStateManager.popMatrix();
    }

    public static void glColor(int color) {
        var red = (color >> 16 & 0xFF) / 255.0f;
        var green = (color >> 8 & 0xFF) / 255.0f;
        var blue = (color & 0xFF) / 255.0f;
        var alpha = (color >> 24 & 0xFF) / 255.0f;
        GL11.glColor4f(red, green, blue, alpha);
    }
}
