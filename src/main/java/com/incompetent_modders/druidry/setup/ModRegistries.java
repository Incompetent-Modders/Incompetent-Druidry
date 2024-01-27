package com.incompetent_modders.druidry.setup;

import com.incompetent_modders.druidry.Druidry;
import com.incompetent_modders.druidry.casting.spell.Spell;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = Druidry.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModRegistries {
    public static final ResourceKey<Registry<Spell>> SPELLS_KEY = ResourceKey.createRegistryKey(new ResourceLocation(Druidry.MODID, "spell"));
    public static final DeferredRegister<Spell> SPELLS_DEFERRED_REGISTER = DeferredRegister.create(SPELLS_KEY, Druidry.MODID);
    public static final Supplier<IForgeRegistry<Spell>> SPELLS = SPELLS_DEFERRED_REGISTER.makeRegistry(() -> new RegistryBuilder<Spell>().disableSaving().disableSync());
    
}
