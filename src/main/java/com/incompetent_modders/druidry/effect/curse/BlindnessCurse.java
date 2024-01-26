package com.incompetent_modders.druidry.effect.curse;

import com.incompetent_modders.druidry.effect.DruidryEffects;
import com.incompetent_modders.druidry.effect.MagicallyAppliedEffect;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

public class BlindnessCurse extends MagicallyAppliedEffect {
    protected BlindnessCurse(MobEffectCategory category, int color) {
        super(category, color);
    }
    public static class BlindnessFogFunction implements FogRenderer.MobEffectFogFunction {
        public BlindnessFogFunction() {
        }
        
        public MobEffect getMobEffect() {
            return DruidryEffects.BLINDNESS_CURSE.get();
        }
        
        public void setupFog(FogRenderer.FogData p_234181_, LivingEntity p_234182_, MobEffectInstance p_234183_, float p_234184_, float p_234185_) {
            float f = p_234183_.isInfiniteDuration() ? 5.0F : Mth.lerp(Math.min(1.0F, (float)p_234183_.getDuration() / 20.0F), p_234184_, 5.0F);
            if (p_234181_.mode == FogRenderer.FogMode.FOG_SKY) {
                p_234181_.start = 0.0F;
                p_234181_.end = f * 0.8F;
            } else {
                p_234181_.start = f * 0.25F;
                p_234181_.end = f;
            }
            
        }
    }
}
