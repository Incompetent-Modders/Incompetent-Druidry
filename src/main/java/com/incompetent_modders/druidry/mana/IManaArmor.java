package com.incompetent_modders.druidry.mana;

import net.minecraft.world.item.ItemStack;

public interface IManaArmor {
    default int getMaxManaBoost(ItemStack i) {
        return 0;
    }
    
    default int getManaRegenBoost(ItemStack i) {
        return 0;
    }
    
}
