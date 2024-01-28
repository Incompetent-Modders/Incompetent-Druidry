package com.incompetent_modders.druidry;

import com.incompetent_modders.druidry.casting.spell.sunburst.SunburstRenderer;
import com.incompetent_modders.druidry.client.ManaOverlay;
import com.incompetent_modders.druidry.client.SpellListOverlay;
import com.incompetent_modders.druidry.effect.curse.BlindnessCurse;
import com.incompetent_modders.druidry.setup.DruidryEntities;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterGuiOverlaysEvent;
import net.neoforged.neoforge.client.gui.overlay.VanillaGuiOverlay;

@SuppressWarnings("removal")
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
    
    @SubscribeEvent
    public static void registerGuiOverlays(RegisterGuiOverlaysEvent event) {
        event.registerAbove(VanillaGuiOverlay.AIR_LEVEL.id(), "mana", ManaOverlay.INSTANCE);
        event.registerAbove(VanillaGuiOverlay.CHAT_PANEL.id(), "selected_spell", SpellListOverlay.INSTANCE);
    }
}
