package com.incompetent_modders.druidry.casting.spell.sunburst;

import com.incompetent_modders.druidry.casting.spell.MagicalProjectileRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class SunburstRenderer extends MagicalProjectileRenderer<SunburstProjectile> {
    public SunburstRenderer(EntityRendererProvider.Context renderManagerIn, ResourceLocation entityTexture) {
        super(renderManagerIn, entityTexture);
    }
}
