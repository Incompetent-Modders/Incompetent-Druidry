package com.incompetent_modders.druidry.setup;

import com.incompetent_modders.druidry.casting.spell.EmptySpell;
import com.incompetent_modders.druidry.casting.spell.GrantItemSpell;
import com.incompetent_modders.druidry.casting.spell.Spell;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.incompetent_modders.druidry.Druidry.MODID;

public class DruidrySpells {
    public static final DeferredRegister<Spell> SPELLS = DeferredRegister.create(ModRegistries.SPELL, MODID);
    
    public static final DeferredHolder<Spell, Spell> EMPTY = SPELLS.register("empty", EmptySpell::new);
    public static final DeferredHolder<Spell, Spell> GOODBERRY = SPELLS.register("goodberry", () -> new GrantItemSpell(DruidryItems.GOODBERRY.get(), 10, 20, 72000));
    
}
