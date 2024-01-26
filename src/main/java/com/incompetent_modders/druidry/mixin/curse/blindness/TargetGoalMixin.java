package com.incompetent_modders.druidry.mixin.curse.blindness;

import com.incompetent_modders.druidry.effect.DruidryEffects;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TargetGoal.class)
public class TargetGoalMixin {

    @Final
    @Shadow
    protected net.minecraft.world.entity.Mob mob;
    
    @Inject(at = @At("HEAD"), method = "canContinueToUse", cancellable = true)
    private void canUse(CallbackInfoReturnable<Boolean> cir) {
        if (mob.hasEffect(DruidryEffects.BLINDNESS_CURSE.get())) {
            cir.setReturnValue(false);
        }
    }
}
