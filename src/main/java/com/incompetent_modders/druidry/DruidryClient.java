package com.incompetent_modders.druidry;

import com.incompetent_modders.druidry.effect.curse.BlindnessCurse;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = Druidry.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DruidryClient {
    public static void init(final FMLClientSetupEvent evt) {
        FogRenderer.MOB_EFFECT_FOG.add(new BlindnessCurse.BlindnessFogFunction());
    }
}
