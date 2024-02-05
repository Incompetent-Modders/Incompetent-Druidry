package com.incompetent_modders.druidry.effect.curse;

import com.incompetent_modders.druidry.effect.DruidryEffects;
import com.incompetent_modders.druidry.effect.MagicallyAppliedEffect;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class BlindnessCurse extends MagicallyAppliedEffect {
    protected BlindnessCurse(MobEffectCategory category, int color) {
        super(category, color);
    }
    
    @OnlyIn(Dist.CLIENT)
    public static class BlindnessFogFunction implements FogRenderer.MobEffectFogFunction {
        public BlindnessFogFunction() {
        }
        @Override
        public MobEffect getMobEffect() {
            return DruidryEffects.BLINDNESS_CURSE.get();
        }
        @Override
        public void setupFog(FogRenderer.FogData fogData, LivingEntity entity, MobEffectInstance instance, float p_234184_, float p_234185_) {
            float f = instance.isInfiniteDuration() ? 5.0F : Mth.lerp(Math.min(1.0F, (float)instance.getDuration() / 20.0F), p_234184_, 5.0F);
            if (fogData.mode == FogRenderer.FogMode.FOG_SKY) {
                fogData.start = 0.0F;
                fogData.end = f * 0.8F;
            } else {
                fogData.start = f * 0.25F;
                fogData.end = f;
            }
            
        }
    }
}
