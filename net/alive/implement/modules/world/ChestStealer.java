package net.alive.implement.modules.world;

import com.mojang.authlib.UserType;
import lombok.var;
import net.alive.api.event.annotation.Subscribe;
import net.alive.api.event.listener.impl.Listener;
import net.alive.api.module.Category;
import net.alive.api.module.Module;
import net.alive.api.module.ModuleInfo;
import net.alive.api.value.Value;
import net.alive.implement.events.player.PlayerUpdateEvent;
import net.alive.utils.world.TimeUtils;
import net.minecraft.block.*;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.item.*;

@ModuleInfo(name = "Chest Stealer", keyBind = 0, category = Category.WORLD)
public class ChestStealer extends Module {

    public Value<Boolean> weapons = new Value<>("Steal Weapons", true);
    public Value<Boolean> armor = new Value<>("Steal Armor", true);
    public Value<Boolean> pickaxes = new Value<>("Steal Pickaxes", true);
    public Value<Boolean> axes = new Value<>("Steal Axes", true);
    public Value<Boolean> shovels = new Value<>("Steal Shovels", true);
    public Value<Boolean> blocks = new Value<>("Steal Blocks", true);
    public Value<Boolean> food = new Value<>("Steal Food", true);
    public Value<Boolean> potions = new Value<>("Steal Potions", true);
    public Value<Boolean> close = new Value<>("Auto Close", true);
    public Value<Double> delay = new Value<>("Steal Delay", 100D, 0, 2000, 5);
    public TimeUtils delayTimer = new TimeUtils();
    public boolean empty;

    @Subscribe
    public Listener<PlayerUpdateEvent> update = new Listener<>(event -> {
        steal();
        close();
    });

    public void close() {
        if (empty && close.getValueObject() && mc.currentScreen instanceof GuiChest)
            mc.thePlayer.closeScreen();
    }

    public void steal() {
        if (mc.currentScreen instanceof GuiChest) {
            var chest = (GuiChest) mc.currentScreen;
            empty = true;
            for (int index = 0; index < chest.lowerChestInventory.getSizeInventory(); index++)
                if (chest.lowerChestInventory.getStackInSlot(index) != null && sort(chest.lowerChestInventory.getStackInSlot(index).getItem())) {
                    empty = false;
                    if (delayTimer.time(delay.getValueObject().floatValue())) {
                        mc.playerController.windowClick(chest.inventorySlots.windowId, index, 0, 1, mc.thePlayer);
                        delayTimer.reset();
                    }
                }
        }
    }

    public boolean sort(Item item) {
        return armor(item) || weapons(item) || axes(item) || blocks(item) || food(item) || pickaxes(item) || shovels(item) || potions(item);
    }

    public boolean weapons(Item item){
        return weapons.getValueObject() && (item instanceof ItemSword || item instanceof ItemBow);
    }

    public boolean armor(Item item){
        return armor.getValueObject() && item instanceof ItemArmor;
    }

    public boolean pickaxes(Item item){
        return pickaxes.getValueObject() && item instanceof ItemPickaxe;
    }

    public boolean shovels(Item item){
        return shovels.getValueObject() && item instanceof ItemSpade;
    }

    public boolean axes(Item item){
        return axes.getValueObject() && item instanceof ItemAxe;
    }

    public boolean food(Item item){
        return food.getValueObject() && item instanceof ItemFood;
    }

    public boolean potions(Item item){
        return pickaxes.getValueObject() && item instanceof ItemPotion;
    }

    public boolean blocks(Item item){
        return blocks.getValueObject() && (item instanceof ItemBlock && !(item.getUnlocalizedName().equalsIgnoreCase("tile.tnt")) &&
                !(item.getUnlocalizedName().equalsIgnoreCase("tile.chest")) && !(item.getUnlocalizedName().equalsIgnoreCase("tile.enderchest")) &&
                !(item.getUnlocalizedName().equalsIgnoreCase("tile.endportalframe")) && !(item.getUnlocalizedName().equalsIgnoreCase("tile.flower")) &&
                !(item.getUnlocalizedName().equalsIgnoreCase("tile.enchantmenttable")) && !(item.getUnlocalizedName().equalsIgnoreCase("tile.sand")) &&
                !(item.getUnlocalizedName().equalsIgnoreCase("tile.redsand")) && !(item.getUnlocalizedName().equalsIgnoreCase("tile.button")));
    }
}
