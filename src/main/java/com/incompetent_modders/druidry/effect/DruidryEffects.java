package com.incompetent_modders.druidry.effect;

import com.incompetent_modders.druidry.Druidry;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class DruidryEffects {
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, Druidry.MODID);
    public static final RegistryObject<MobEffect> BLINDNESS_CURSE;
    
    static {
        BLINDNESS_CURSE = EFFECTS.register("blindness_curse", () -> new MagicallyAppliedEffect(MobEffectCategory.HARMFUL, 0x000000));
    }
    
    public static void register(IEventBus eventBus) {
        EFFECTS.register(eventBus);
    }
}
