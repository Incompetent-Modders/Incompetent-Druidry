package com.incompetent_modders.druidry.casting.staff;

import com.incompetent_modders.druidry.casting.spell.Spell;
import com.incompetent_modders.druidry.casting.spell.SpellUtils;
import com.incompetent_modders.druidry.casting.spell_crystal.SpellCrystalItem;
import com.incompetent_modders.druidry.setup.DruidrySpells;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.client.event.InputEvent;

import javax.annotation.Nullable;
import java.util.List;

public class StaffItem extends Item {
    private final int level;
    private static int selectedSpellSlot;
    public StaffItem(Properties p, int level) {
        super(p);
        this.level = level;
    }
    public int getLevel() {
        return this.level;
    }
    public int getUseDuration(ItemStack stack) {
        return 72000;
    }
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.TOOT_HORN;
    }
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (!player.getAbilities().instabuild) {
            return InteractionResultHolder.fail(itemstack);
        } else {
            player.startUsingItem(hand);
            return InteractionResultHolder.consume(itemstack);
        }
    }
    public static int getSpellSlots(int tier) {
        return switch (tier) {
            case 2 -> 3;
            case 3 -> 4;
            case 4 -> 5;
            default -> 2;
        };
    }
    public static String getSpellNameInSlot(CompoundTag tag, int slot) {
        return SpellUtils.deserializeFromSlot(tag, slot).getDisplayName().getString();
    }
    
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        CompoundTag tag = stack.getTag();
        for (int i = 1; i <= getSpellSlots(this.getLevel()); i++) {
            tooltip.add(Component.literal(i + " > [").append(getSpellNameInSlot(tag, i)).append(" ]"));
        }
    }
    public void releaseUsing(ItemStack stack, Level level, LivingEntity entity, int timeLeft) {
        if (entity instanceof Player player) {
            Spell spell = getSelectedSpell(stack);
            if (spell != null) {
                int i = this.getUseDuration(stack) - timeLeft;
                if (i >= spell.getDrawTime()) {
                    spell.cast(level, player, InteractionHand.MAIN_HAND);
                }
            }
        }
    }
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int p_41407_, boolean p_41408_) {
        CompoundTag tag = stack.getTag();
        if (tag == null) {
            tag = new CompoundTag();
            tag.putInt("selectedSpellSlot", 1);
            stack.setTag(tag);
        }
        for (int i = 1; i <= getSpellSlots(this.getLevel()); i++) {
            if (!tag.contains("spellSlot_" + i) && tag.getString("spellSlot_" + i).isEmpty()) {
                tag.putString("spellSlot_" + i, DruidrySpells.EMPTY.get().getSpellIdentifier().toString());
            } else if (tag.getString("spellSlot_" + i).isEmpty()) {
                tag.putString("spellSlot_" + i, DruidrySpells.EMPTY.get().getSpellIdentifier().toString());
            }
        }
        if (!tag.contains("selectedSpellSlot"))
            tag.putInt("selectedSpellSlot", 1);
    }
    public void changeSelectedSpell(ItemStack stack, boolean up) {
        CompoundTag tag = stack.getTag();
        if (tag == null) {
            tag = new CompoundTag();
            tag.putInt("selectedSpellSlot", 1);
            stack.setTag(tag);
        }
        int selectedSpellSlotTag = tag.getInt("selectedSpellSlot");
        if (up) {
            if (selectedSpellSlotTag == getSpellSlots(this.getLevel())) {
                selectedSpellSlot = 1;
            } else {
                selectedSpellSlot++;
            }
        }
        if (!up) {
            if (selectedSpellSlotTag == 1) {
                selectedSpellSlot = getSpellSlots(this.getLevel());
            } else {
                selectedSpellSlot--;
            }
        }
        tag.putInt("selectedSpellSlot", selectedSpellSlot);
    }
    public Spell getSelectedSpell(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        if (tag != null) {
            return SpellUtils.deserializeFromSlot(tag, selectedSpellSlot);
        }
        return DruidrySpells.EMPTY.get();
    }
}
