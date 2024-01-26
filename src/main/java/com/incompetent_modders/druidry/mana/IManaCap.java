package com.incompetent_modders.druidry.mana;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

public interface IManaCap extends INBTSerializable<CompoundTag> {
    double getCurrentMana();
    
    int getMaxMana();
    
    void setMaxMana(int max);
    
    double setMana(final double mana);
    
    double addMana(final double manaToAdd);
    
    double removeMana(final double manaToRemove);
}
