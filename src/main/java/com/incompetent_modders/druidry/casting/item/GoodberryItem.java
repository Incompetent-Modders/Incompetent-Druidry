package com.incompetent_modders.druidry.casting.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class GoodberryItem extends Item {
    private static final int dayLength = 24000;
    public GoodberryItem(Properties properties) {
        super(properties);
    }
    
    public static void convertToSweetBerries(Player player, ItemStack stack) {
        int stackSize = stack.getCount();
        int slot = player.getInventory().findSlotMatchingItem(stack);
        player.getInventory().setItem(slot, new ItemStack(Items.SWEET_BERRIES, stackSize));
    }
    
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int p_41407_, boolean p_41408_) {
        if (level.getDayTime() == 0) {
            convertToSweetBerries((Player) entity, stack);
        }
    }
    
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable("item.druidry.goodberry.tooltip"));
    }
    
    
}
