package com.incompetent_modders.druidry.effect;

import com.incompetent_modders.druidry.Druidry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;


public class DruidryEffects {
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(BuiltInRegistries.MOB_EFFECT, Druidry.MODID);
    public static final DeferredHolder<MobEffect, MobEffect> BLINDNESS_CURSE;
    
    static {
        BLINDNESS_CURSE = EFFECTS.register("blindness_curse", () -> new MagicallyAppliedEffect(MobEffectCategory.HARMFUL, 0x000000));
    }
    
    public static void register(IEventBus eventBus) {
        EFFECTS.register(eventBus);
    }
}
