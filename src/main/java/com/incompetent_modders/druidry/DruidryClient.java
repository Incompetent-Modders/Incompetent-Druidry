package com.incompetent_modders.druidry;

import com.incompetent_modders.druidry.casting.spell.sunburst.SunburstRenderer;
import com.incompetent_modders.druidry.effect.curse.BlindnessCurse;
import com.incompetent_modders.druidry.setup.DruidryEntities;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = Druidry.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DruidryClient {
    public static void init(final FMLClientSetupEvent evt) {
        FogRenderer.MOB_EFFECT_FOG.add(new BlindnessCurse.BlindnessFogFunction());
    }
    @SubscribeEvent
    public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(DruidryEntities.SUNBURST_PROJECTILE.get(),
                renderManager -> new SunburstRenderer(renderManager, new ResourceLocation(Druidry.MODID, "textures/entity/sunburst.png")));
    }
}
