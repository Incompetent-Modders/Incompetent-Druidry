package com.incompetent_modders.druidry.setup;

import com.incompetent_modders.druidry.casting.spell.GrantItemSpell;
import com.incompetent_modders.druidry.casting.spell.Spell;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import static com.incompetent_modders.druidry.Druidry.MODID;

public class DruidrySpells {
    public static final DeferredRegister<Spell> SPELLS = DeferredRegister.create(ModRegistries.SPELLS.get(), MODID);
    
    public static final RegistryObject<Spell> GOODBERRY = SPELLS.register("goodberry", () -> new GrantItemSpell(DruidryItems.GOODBERRY.get(), 10, 20));
}
