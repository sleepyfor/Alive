package net.alive.implement.modules.world;

import lombok.AllArgsConstructor;
import lombok.var;
import net.alive.api.event.annotation.Subscribe;
import net.alive.api.event.listener.impl.Listener;
import net.alive.api.event.state.EventState;
import net.alive.api.module.Category;
import net.alive.api.module.Module;
import net.alive.api.module.ModuleInfo;
import net.alive.implement.events.player.PlayerUpdateEvent;
import net.alive.implement.events.player.SafewalkEvent;
import net.alive.utils.math.MathUtils;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.potion.Potion;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ModuleInfo(name = "Bridge", keyBind = 0, category = Category.WORLD)
public class Bridge extends Module {
    private Data data;

    @Subscribe
    public Listener<PlayerUpdateEvent> update = new Listener<>(event -> {
        if (event.getState() == EventState.PRE) {
            data = null;
            event.setPitch(81.2124551f);
            int block;
            for (block = 0; block < 45; ++block)
                mc.thePlayer.inventoryContainer.getSlot(block).getStack();
            double x = mc.thePlayer.posX;
            double y = mc.thePlayer.posY - 1.0D;
            double z = mc.thePlayer.posZ;
            BlockPos blockBelow = new BlockPos(x, y, z);
            BlockPos blockBelow2 = new BlockPos(x, y - 1, z);
            if (material(blockBelow).isReplaceable()) {
                data = getData(blockBelow);
                if (data == null)
                    data = getData(blockBelow2);
            }
            if (data != null)
                event.setYaw(event.getYaw() + 180);
        } else if (event.getState() == EventState.POST && this.data != null && mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld,
                mc.thePlayer.inventoryContainer.getSlot(36 + mc.thePlayer.inventory.currentItem).getStack(), data.pos, data.facing,
                new Vec3(data.pos.getX() + MathUtils.random(0.4900124, 0.5100124), data.pos.getY() + MathUtils.random(0.4900124, 0.5100124),
                        data.pos.getZ() + MathUtils.random(0.4900124, 0.5100124)))) {
            event.setYaw(event.getYaw() + 180);
            mc.thePlayer.sendQueue.addToSendQueue(new C0APacketAnimation());
        }
    });

    private Data getData(BlockPos pos) {
        if (!blacklist().contains(mc.theWorld.getBlockState(pos.add(0, -1, 0)).getBlock()))
            return new Data(pos.add(0, -1, 0), EnumFacing.UP);
        if (!blacklist().contains(mc.theWorld.getBlockState(pos.add(-1, 0, 0)).getBlock()))
            return new Data(pos.add(-1, 0, 0), EnumFacing.EAST);
        if (!blacklist().contains(mc.theWorld.getBlockState(pos.add(1, 0, 0)).getBlock()))
            return new Data(pos.add(1, 0, 0), EnumFacing.WEST);
        if (!blacklist().contains(mc.theWorld.getBlockState(pos.add(0, 0, -1)).getBlock()))
            return new Data(pos.add(0, 0, -1), EnumFacing.SOUTH);
        if (!blacklist().contains(mc.theWorld.getBlockState(pos.add(0, 0, 1)).getBlock()))
            return new Data(pos.add(0, 0, 1), EnumFacing.NORTH);
        var pos1 = pos.add(-1, 0, 0);
        if (!blacklist().contains(mc.theWorld.getBlockState(pos1.add(-1, 0, 0)).getBlock()))
            return new Data(pos1.add(-1, 0, 0), EnumFacing.EAST);
        if (!blacklist().contains(mc.theWorld.getBlockState(pos1.add(1, 0, 0)).getBlock()))
            return new Data(pos1.add(1, 0, 0), EnumFacing.WEST);
        if (!blacklist().contains(mc.theWorld.getBlockState(pos1.add(0, 0, -1)).getBlock()))
            return new Data(pos1.add(0, 0, -1), EnumFacing.SOUTH);
        if (!blacklist().contains(mc.theWorld.getBlockState(pos1.add(0, 0, 1)).getBlock()))
            return new Data(pos1.add(0, 0, 1), EnumFacing.NORTH);
        var pos2 = pos.add(1, 0, 0);
        if (!blacklist().contains(mc.theWorld.getBlockState(pos2.add(-1, 0, 0)).getBlock()))
            return new Data(pos2.add(-1, 0, 0), EnumFacing.EAST);
        if (!blacklist().contains(mc.theWorld.getBlockState(pos2.add(1, 0, 0)).getBlock()))
            return new Data(pos2.add(1, 0, 0), EnumFacing.WEST);
        if (!blacklist().contains(mc.theWorld.getBlockState(pos2.add(0, 0, -1)).getBlock()))
            return new Data(pos2.add(0, 0, -1), EnumFacing.SOUTH);
        if (!blacklist().contains(mc.theWorld.getBlockState(pos2.add(0, 0, 1)).getBlock()))
            return new Data(pos2.add(0, 0, 1), EnumFacing.NORTH);
        var pos3 = pos.add(0, 0, -1);
        if (!blacklist().contains(mc.theWorld.getBlockState(pos3.add(-1, 0, 0)).getBlock()))
            return new Data(pos3.add(-1, 0, 0), EnumFacing.EAST);
        if (!blacklist().contains(mc.theWorld.getBlockState(pos3.add(1, 0, 0)).getBlock()))
            return new Data(pos3.add(1, 0, 0), EnumFacing.WEST);
        if (!blacklist().contains(mc.theWorld.getBlockState(pos3.add(0, 0, -1)).getBlock()))
            return new Data(pos3.add(0, 0, -1), EnumFacing.SOUTH);
        if (!blacklist().contains(mc.theWorld.getBlockState(pos3.add(0, 0, 1)).getBlock()))
            return new Data(pos3.add(0, 0, 1), EnumFacing.NORTH);
        var pos4 = pos.add(0, 0, 1);
        if (!blacklist().contains(mc.theWorld.getBlockState(pos4.add(-1, 0, 0)).getBlock()))
            return new Data(pos4.add(-1, 0, 0), EnumFacing.EAST);
        if (!blacklist().contains(mc.theWorld.getBlockState(pos4.add(1, 0, 0)).getBlock()))
            return new Data(pos4.add(1, 0, 0), EnumFacing.WEST);
        if (!blacklist().contains(mc.theWorld.getBlockState(pos4.add(0, 0, -1)).getBlock()))
            return new Data(pos4.add(0, 0, -1), EnumFacing.SOUTH);
        if (!blacklist().contains(mc.theWorld.getBlockState(pos4.add(0, 0, 1)).getBlock()))
            return new Data(pos4.add(0, 0, 1), EnumFacing.NORTH);
        return null;
    }

    private boolean isReplaceable(BlockPos pos, BlockPos pos2) {
        return mc.theWorld.getBlockState(pos).getBlock() == Blocks.air || mc.theWorld.getBlockState(pos2).getBlock() == Blocks.air;
    }

    private Block block(BlockPos pos) {
        return state(pos).getBlock();
    }

    private Material material(BlockPos pos) {
        return block(pos).getMaterial();
    }

    private IBlockState state(BlockPos pos) {
        return mc.theWorld.getBlockState(pos);
    }

    public float[] getAnglesVec(Vec3 blockPos) {
        double difX = (blockPos.xCoord + 0.5D) - mc.thePlayer.posX, difY = (blockPos.yCoord + 0.5D) - (mc.thePlayer.posY +
                mc.thePlayer.getEyeHeight()), difZ = (blockPos.zCoord + 0.5D) - mc.thePlayer.posZ;
        double helper = Math.sqrt(difX * difX + difZ * difZ);
        float yaw = (float) (Math.atan2(difZ, difX) * 180 / Math.PI) - 90;
        float pitch = (float) -(Math.atan2(difY, helper) * 180 / Math.PI);
        return (new float[]{yaw, pitch});
    }

    private List<Block> blacklist() {
        return Arrays.asList(Blocks.air, Blocks.water, Blocks.tnt, Blocks.chest,
                Blocks.flowing_water, Blocks.lava, Blocks.flowing_lava, Blocks.tnt, Blocks.enchanting_table, Blocks.carpet,
                Blocks.glass_pane, Blocks.stained_glass_pane, Blocks.iron_bars, Blocks.snow_layer, Blocks.ice,
                Blocks.packed_ice, Blocks.coal_ore, Blocks.diamond_ore, Blocks.emerald_ore, Blocks.chest, Blocks.torch,
                Blocks.anvil, Blocks.trapped_chest, Blocks.noteblock, Blocks.jukebox, Blocks.tnt, Blocks.gold_ore,
                Blocks.iron_ore, Blocks.lapis_ore, Blocks.sand, Blocks.lit_redstone_ore, Blocks.quartz_ore,
                Blocks.redstone_ore, Blocks.wooden_pressure_plate, Blocks.stone_pressure_plate,
                Blocks.light_weighted_pressure_plate, Blocks.heavy_weighted_pressure_plate, Blocks.stone_button,
                Blocks.wooden_button, Blocks.lever, Blocks.enchanting_table);
    }

    private static class Data {
        private final BlockPos pos;
        private final EnumFacing facing;

        private Data(BlockPos pos, EnumFacing facing) {
            this.pos = pos;
            this.facing = facing;
        }
    }
}
