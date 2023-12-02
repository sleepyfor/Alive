package net.alive.implement.modules.combat;

import lombok.var;
import net.alive.api.event.annotation.Subscribe;
import net.alive.api.event.listener.impl.Listener;
import net.alive.api.event.state.EventState;
import net.alive.api.module.Category;
import net.alive.api.module.Module;
import net.alive.api.module.ModuleInfo;
import net.alive.api.value.Value;
import net.alive.implement.events.player.PlayerUpdateEvent;
import net.alive.utils.combat.CombatUtils;
import net.alive.utils.player.PlayerUtils;
import net.alive.utils.world.TimeUtils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;

@ModuleInfo(name = "Kill Aura", keyBind = Keyboard.KEY_R, category = Category.COMBAT)
public class KillAura extends Module {

    public Value<ArrayList<String>> mode = new Value<>("Mode", "Basic", "Sorted", "Basic");
    public Value<Boolean> players = new Value<>("Players", true);
    public Value<Boolean> monsters = new Value<>("Monsters", false);
    public Value<Boolean> animals = new Value<>("Animals", false);
    public Value<Boolean> villagers = new Value<>("Villagers", false);
    public Value<Boolean> invisible = new Value<>("Invisible", false);
    public Value<Boolean> blockhit = new Value<>("Block Hit", true);
    public Value<Double> speed = new Value<>("Attack Speed", 14., 1, 20, 1);
    public Value<Double> range = new Value<>("Range", 4., 2.5, 7., .1);
    public TimeUtils timer = new TimeUtils();
    public EntityLivingBase target;

    @Subscribe
    public Listener<PlayerUpdateEvent> update = new Listener<>(event -> {
        setSuffix((String) getValue("Mode").getValueObject());
        switch (getMode("Mode")) {
            case "Basic":
                target = (EntityLivingBase) CombatUtils.killauraTarget(players.getValueObject(), monsters.getValueObject(), animals.getValueObject(), villagers.getValueObject(),
                        invisible.getValueObject(), range.getValueObject());
                break;
            case "Sorted":
                target = (EntityLivingBase) CombatUtils.sortedTarget(4, players.getValueObject(), monsters.getValueObject(), animals.getValueObject(), villagers.getValueObject(),
                        invisible.getValueObject(), range.getValueObject());
                break;
        }
        if (target != null) {
            var rots = PlayerUtils.getRotations(target);
//            mc.thePlayer.rotationYaw = rots[0];
//            mc.thePlayer.rotationPitch = rots[1];
            event.setYaw(rots[0]);
            event.setPitch(rots[1]);
            if (event.getState() == EventState.PRE) {
                if (timer.time((float) (1000 / speed.getValueObject()))) {
                    if (blockhit.getValueObject() && (mc.thePlayer.isUsingItem() && mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemSword))
                        mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
//                    event.setY(event.getY() + 0.003234);
//                    event.setY(event.getY() + 0.001234);
//                    event.setY(event.getY() + 0.0001234);
//                    event.setY(event.getY() + 0.001234);
//                    event.setY(event.getY() + 0.0001234);
//                    mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.0020145,
//                            mc.thePlayer.posZ, false));
//                    mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.0010145, mc.thePlayer.posZ, true));
//                    mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.00010145,
//                            mc.thePlayer.posZ, false));
                    double x = mc.thePlayer.posX;
                    double y = mc.thePlayer.posY;
                    double z = mc.thePlayer.posZ;
//                    mc.thePlayer.sendQueue
//                            .addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(x, y + 0.11, z, false));
//                    mc.thePlayer.sendQueue
//                            .addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(x, y + 0.11021421, z, false));
//                    mc.thePlayer.sendQueue
//                            .addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(x, y + 0.000000000011, z, false));
//                    if (mc.thePlayer.isCollidedVertically && mc.thePlayer.onGround) {
                        event.setGround(false);
//                        event.setY(event.getY() + 0.069);
//                    }
//                    event.setY(event.getY() + 0.011021421);
//                    event.setY(event.getY() + 0.000000000011);
//                    event.setGround(true);
                    mc.thePlayer.swingItem();
                    mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(target, C02PacketUseEntity.Action.ATTACK));
                    timer.reset();
                }
            }
            if (event.getState() == EventState.POST) {
                if (blockhit.getValueObject() && mc.thePlayer != null && mc.thePlayer.getHeldItem() != null && mc.thePlayer.getHeldItem().getItem() instanceof ItemSword)
                    mc.playerController.sendUseItem(mc.thePlayer, mc.theWorld, mc.thePlayer.getCurrentEquippedItem());
            }
        }
    });

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }
}
