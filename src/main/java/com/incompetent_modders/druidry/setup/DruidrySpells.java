package com.incompetent_modders.druidry.setup;

import com.incompetent_modders.druidry.casting.spell.GrantItemSpell;
import com.incompetent_modders.druidry.casting.spell.list.PlantGrowthSpell;
import com.incompetent_modders.incomp_core.ModRegistries;
import com.incompetent_modders.incomp_core.api.spell.Spell;
import com.incompetent_modders.incomp_core.api.spell.SpellCategory;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.incompetent_modders.druidry.Druidry.MODID;

public class DruidrySpells {
    public static final DeferredRegister<Spell> SPELLS = DeferredRegister.create(ModRegistries.SPELL, MODID);
    
    public static final DeferredHolder<Spell, Spell> GOODBERRY = SPELLS.register("goodberry", () -> new GrantItemSpell(DruidryItems.GOODBERRY.get(), 10, 20, 40, 24000, SpellCategory.SUMMON));
    public static final DeferredHolder<Spell, Spell> PLANT_GROWTH = SPELLS.register("plant_growth", () -> new PlantGrowthSpell( 50, 120));
    //public static final DeferredHolder<Spell, Spell> GERTRUDE = SPELLS.register("gertrude", () -> new GrantItemSpell(Items.CHERRY_WOOD, 128, 20, 5));
    
    public static void register(IEventBus eventBus) {
        SPELLS.register(eventBus);
    }
    
}
