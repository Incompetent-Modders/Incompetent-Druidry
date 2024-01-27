package com.incompetent_modders.druidry.casting.spell;

import com.incompetent_modders.druidry.casting.spell_crystal.SpellCrystalItem;
import com.incompetent_modders.druidry.setup.Capabilities;
import com.incompetent_modders.druidry.setup.ModRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class SpellUtils {
    
    public static void giveItems(Level level, Player player, int amount, Item item) {
        ItemStack stack = new ItemStack(item);
        stack.setCount(amount);
        if (!level.isClientSide) {
            player.getInventory().add(stack);
        }
    }
    public static boolean isInventoryFull(Player player) {
        return player.getInventory().getFreeSlot() == -1;
    }
    
    public static void removeMana(Player player, int amount) {
        if (player.getCapability(Capabilities.MANA_CAPABILITY).isPresent()) {
            player.getCapability(Capabilities.MANA_CAPABILITY).ifPresent(cap -> cap.removeMana(amount));
        }
    }
    
    public static Spell deserializeFromSlot(CompoundTag tag, int slot) {
        if (tag.contains("spellSlot_" + slot)) {
            return ModRegistries.SPELLS.get().getValue(new ResourceLocation(tag.getString("spellSlot_" + slot)));
        }
        return null;
        
    }
}
