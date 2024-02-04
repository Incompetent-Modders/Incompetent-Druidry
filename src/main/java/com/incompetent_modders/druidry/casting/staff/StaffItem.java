package com.incompetent_modders.druidry.casting.staff;

import com.incompetent_modders.druidry.casting.spell.Spell;
import com.incompetent_modders.druidry.casting.spell.SpellUtils;
import com.incompetent_modders.druidry.casting.spell_crystal.SpellCrystalItem;
import com.incompetent_modders.druidry.setup.DruidrySpells;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
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

import static com.incompetent_modders.druidry.Druidry.MODID;
import static com.incompetent_modders.druidry.casting.spell.DruidryTablet.DESCRIPTION_FORMAT;
import static com.incompetent_modders.druidry.casting.spell.DruidryTablet.TITLE_FORMAT;

public class StaffItem extends Item {
    private final String selSpellSlot = "selectedSpellSlot";
    private final String spellSlot = "spellSlot_";
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
        return UseAnim.BLOCK;
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
            case 2 -> 2;
            case 3 -> 3;
            case 4 -> 4;
            default -> 1;
        };
    }
    public static String getSpellNameInSlot(CompoundTag tag, int slot) {
        return SpellUtils.deserializeFromSlot(tag, slot).getDisplayName().getString();
    }
    private static final Component SELECTED_SPELL_TITLE = Component.translatable(
                    Util.makeDescriptionId("item", new ResourceLocation(MODID,"staff.selected_spell"))
            )
            .withStyle(TITLE_FORMAT);
    private static final Component AVAILABLE_SPELLS_TITLE = Component.translatable(
                    Util.makeDescriptionId("item", new ResourceLocation(MODID,"staff.available_spells"))
            )
            .withStyle(TITLE_FORMAT);
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        CompoundTag tag = stack.getTag();
        if (tag == null) {
            return;
        }
        tooltip.add(CommonComponents.EMPTY);
        tooltip.add(SELECTED_SPELL_TITLE);
        tooltip.add(CommonComponents.space().append(getSelectedSpell(stack).getDisplayName()).withStyle(DESCRIPTION_FORMAT));
        tooltip.add(CommonComponents.EMPTY);
        tooltip.add(AVAILABLE_SPELLS_TITLE);
        for (int i = 0; i <= getSpellSlots(this.getLevel()); i++) {
            if (i == selectedSpellSlot) {
                return;
            }
            else
                tooltip.add(CommonComponents.space().append(getSpellNameInSlot(tag, i)).withStyle(DESCRIPTION_FORMAT));
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
            return;
        }
        for (int i = 0; i <= getSpellSlots(this.getLevel()); i++) {
            if (!tag.contains(spellSlot + i) || tag.getString(spellSlot + i).isEmpty()) {
                tag.putString(spellSlot + i, DruidrySpells.EMPTY.get().getSpellIdentifier().toString());
            }
        }
        if (!tag.contains(selSpellSlot))
            tag.putInt(selSpellSlot, 1);
    }
    public void changeSelectedSpell(ItemStack stack, boolean up) {
        CompoundTag tag = stack.getTag();
        if (tag == null) {
            return;
        }
        int selectedSpellSlotTag = tag.getInt(selSpellSlot);
        if (up) {
            if (selectedSpellSlotTag == getSpellSlots(this.getLevel())) {
                selectedSpellSlot = 0;
            } else {
                selectedSpellSlot++;
            }
        }
        if (!up) {
            if (selectedSpellSlotTag == 0) {
                selectedSpellSlot = getSpellSlots(this.getLevel());
            } else {
                selectedSpellSlot--;
            }
        }
        tag.putInt(selSpellSlot, selectedSpellSlot);
    }
    public Spell getSelectedSpell(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        if (tag != null) {
            return SpellUtils.deserializeFromSlot(tag, selectedSpellSlot);
        }
        return DruidrySpells.EMPTY.get();
    }
}
