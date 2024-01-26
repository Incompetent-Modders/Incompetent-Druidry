package com.incompetent_modders.druidry.mixin.curse.blindness;

import com.incompetent_modders.druidry.effect.DruidryEffects;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LevelRenderer.class)
public class LevelRendererMixin {
    
    /**
     * @author PouffyDev
     * @reason Make the blindness curse Blind the player
     */
    @Overwrite
    private boolean doesMobEffectBlockSky(Camera p_234311_) {
        Entity entity = p_234311_.getEntity();
        if (!(entity instanceof LivingEntity livingentity)) {
            return false;
        } else {
            return livingentity.hasEffect(MobEffects.BLINDNESS) || livingentity.hasEffect(MobEffects.DARKNESS) || livingentity.hasEffect(DruidryEffects.BLINDNESS_CURSE.get());
        }
    }
}
