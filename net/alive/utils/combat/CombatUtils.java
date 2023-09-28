package net.alive.utils.combat;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CombatUtils {

    private static Minecraft mc = Minecraft.getMinecraft();

    public static Entity killauraTarget(boolean players, boolean monsters, boolean animals, boolean villagers, boolean invisibles, double range) {
        for (Object ent : mc.theWorld.loadedEntityList) {
            if (ent instanceof EntityLivingBase) {
                EntityLivingBase entity = (EntityLivingBase) ent;
                if (entity.isDead)
                    continue;
                if(entity.getDistanceToEntity(mc.thePlayer) > range)
                    continue;
                if(entity == mc.thePlayer)
                    continue;
                if (!players && entity instanceof EntityPlayer)
                    continue;
                if (!monsters && entity instanceof EntityMob)
                    continue;
                if (!animals && entity instanceof EntityAnimal)
                    continue;
                if (!villagers && entity instanceof EntityVillager)
                    continue;
                if (!invisibles && entity.isInvisible())
                    continue;
                if(entity.getHealth() <= 0)
                    continue;
                return entity;
            }
        }
        return null;
    }

    public static Entity sortedTarget(int targets, boolean players, boolean monsters, boolean animals, boolean villagers, boolean invisibles, double range) {
        List<EntityLivingBase> entities = new ArrayList<>();
        for (Object ent : mc.theWorld.loadedEntityList) {
            if (ent instanceof EntityLivingBase) {
                EntityLivingBase entity = (EntityLivingBase) ent;
                if (entity.isDead)
                    continue;
                if(entity.getDistanceToEntity(mc.thePlayer) > range)
                    continue;
                if(entity == mc.thePlayer)
                    continue;
                if (!players && entity instanceof EntityPlayer)
                    continue;
                if (!monsters && entity instanceof EntityMob)
                    continue;
                if (!animals && entity instanceof EntityAnimal)
                    continue;
                if (!villagers && entity instanceof EntityVillager)
                    continue;
                if (!invisibles && entity.isInvisible())
                    continue;
                if(entity.getHealth() <= 0)
                    continue;
                entities.add(entity);
                if(entities.size() > targets)
                    continue;
                entities.sort(Comparator.comparingDouble(EntityLivingBase::getHealth).reversed());
                return entities.get(0);
            }
        }
        return null;
    }
}
