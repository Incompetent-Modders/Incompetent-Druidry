package com.incompetent_modders.druidry.casting.spell;

import com.incompetent_modders.druidry.casting.spell_crystal.SpellCrystalItem;
import com.incompetent_modders.druidry.setup.Capabilities;
import com.incompetent_modders.druidry.setup.DruidrySpells;
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
        Capabilities.getMana(player).ifPresent(cap -> cap.removeMana(amount));
    }
    
    public static Spell deserializeFromSlot(CompoundTag tag, int slot) {
        if (tag == null) {
            return DruidrySpells.EMPTY.get();
        }
        if (tag.contains("spellSlot_" + slot)) {
            //The NBT formats the spell as modid:spellname. We need to separate them into two strings.
            String spellModid = tag.getString("spellSlot_" + slot).split(":")[0];
            String spellName = tag.getString("spellSlot_" + slot).split(":")[1];
            return ModRegistries.SPELL.get(new ResourceLocation(spellModid, spellName));
        }
        return DruidrySpells.EMPTY.get();
        
    }
    public static void serializeToSlot(CompoundTag tag, int slot, Spell spell) {
        if (tag == null) {
            return;
        }
        tag.putString("spellSlot_" + slot, spell.getSpellIdentifier().toString());
    }
}
