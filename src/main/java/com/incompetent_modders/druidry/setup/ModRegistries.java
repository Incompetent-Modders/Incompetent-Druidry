package com.incompetent_modders.druidry.setup;

import com.incompetent_modders.druidry.Druidry;
import com.incompetent_modders.druidry.casting.spell.Spell;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.neoforge.registries.RegistryBuilder;

public class ModRegistries {
    public static final ResourceKey<Registry<Spell>> SPELLS_KEY = ResourceKey.createRegistryKey(new ResourceLocation(Druidry.MODID, "spell"));
    public static final Registry<Spell> SPELL = new RegistryBuilder<>(SPELLS_KEY).create();
    
    public static void register(IEventBus bus) {
        bus.addListener(NewRegistryEvent.class, event -> event.register(SPELL));
    }
    
}
