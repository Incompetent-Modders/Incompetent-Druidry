package com.incompetent_modders.druidry.casting.spell;

import com.incompetent_modders.druidry.casting.AbstractMagicalEffectProjectile;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;


@OnlyIn(Dist.CLIENT)
public abstract class MagicalProjectileRenderer<T extends AbstractMagicalEffectProjectile> extends EntityRenderer<T> {
    private final ResourceLocation entityTexture;
    
    public MagicalProjectileRenderer(EntityRendererProvider.Context renderManagerIn, ResourceLocation entityTexture) {
        super(renderManagerIn);
        this.entityTexture = entityTexture;
        
    }
    
    @Override
    public void render(AbstractMagicalEffectProjectile proj, float entityYaw, float partialTicks, PoseStack p_225623_4_, MultiBufferSource p_225623_5_, int p_225623_6_) {
    }
    
    
    @Override
    public ResourceLocation getTextureLocation(AbstractMagicalEffectProjectile entity) {
        return this.entityTexture;
    }
    
}
