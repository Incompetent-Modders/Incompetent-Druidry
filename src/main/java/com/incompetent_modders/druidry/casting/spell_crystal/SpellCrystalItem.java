package com.incompetent_modders.druidry.casting.spell_crystal;

import com.incompetent_modders.druidry.casting.spell.SpellUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

public class SpellCrystalItem extends Item {
    private final int tier;
    public SpellCrystalItem(Properties properties, int tier) {
        super(properties);
        this.tier = tier;
    }
    
    public int getTier() {
        return tier;
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
        String translationKey = SpellUtils.deserializeFromSlot(tag, slot).getTranslationKey();
        return Objects.requireNonNullElse(translationKey, "spell.incompetent_druidry.null");
    }
    
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        CompoundTag tag = stack.getTag();
        for (int i = 1; i <= getSpellSlots(this.getTier()); i++) {
            tooltip.add(Component.translatable(getSpellNameInSlot(tag, i)));
        }
    }
    
}
