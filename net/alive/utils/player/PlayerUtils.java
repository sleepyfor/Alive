package net.alive.utils.player;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class PlayerUtils {

    private static Minecraft mc = Minecraft.getMinecraft();

    public static void setSpeed(double speed) {
        mc.thePlayer.motionX = -Math.sin(getDirection(mc.thePlayer)) * speed;
        mc.thePlayer.motionZ = Math.cos(getDirection(mc.thePlayer)) * speed;
    }

    public static float getDirection(EntityPlayer player) {
        double forward = player.moveForward;
        double strafe = player.moveStrafing;
        double yaw = player.rotationYaw;
        double strafeYaw = 45.0D;
        if (strafe != 0 && forward == 0) {
            yaw += 360.0F;
            if (strafe > 0)
                yaw -= 90F;
            else if (strafe < 0)
                yaw += 90F;
            forward = 0;
        } else {
            if (forward > 0) {
                if (strafe > 0)
                    yaw -= strafeYaw;
                if (strafe < 0)
                    yaw += strafeYaw;
            } else {
                if (strafe > 0)
                    yaw += strafeYaw;
                if (strafe < 0)
                    yaw -= strafeYaw;
            }
        }
        if (forward < 0)
            yaw -= 180;
        yaw *= 0.0174653292D;
        return (float) yaw;
    }

    public static double getBaseMoveSpeed() {
        double speed = 0.26D;
        if (mc.thePlayer.isPotionActive(1)) {
            int amp = mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1;
            speed = speed * (1.0 + 0.25D * amp);
        }
        return speed;
    }

    private static int getPotionModifier(EntityLivingBase entity, Potion potion) {
        PotionEffect effect = entity.getActivePotionEffect(potion);
        if (effect != null) return effect.getAmplifier() + 1;
        return 0;
    }

    public static double getJump(double jumpHeight) {
        return jumpHeight + getPotionModifier(mc.thePlayer, Potion.jump) * 0.1F;
    }
}
