package com.incompetent_modders.druidry;

import com.incompetent_modders.druidry.client.ClientEventHandler;
import com.incompetent_modders.druidry.effect.DruidryEffects;
import com.incompetent_modders.druidry.network.AbstractPacket;
import com.incompetent_modders.druidry.network.Networking;
import com.incompetent_modders.druidry.network.SpellSlotScrollMessage;
import com.incompetent_modders.druidry.setup.*;
import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.javafmlmod.FMLJavaModLoadingContext;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.network.NetworkRegistry;
import net.neoforged.neoforge.network.PlayNetworkDirection;
import net.neoforged.neoforge.network.simple.SimpleChannel;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.slf4j.Logger;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

import static net.neoforged.neoforge.network.PlayNetworkDirection.PLAY_TO_SERVER;

@Mod(Druidry.MODID)
public class Druidry
{
    public static final String MODID = "incompetent_druidry";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> druidryMisc = CREATIVE_MODE_TABS.register("incompetent_druidry_misc", () -> CreativeModeTab.builder()
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> DruidryItems.GOODBERRY.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(DruidryItems.GOODBERRY.get()); // Add the example item to the tab. For your own tabs, this method is preferred over the event
            }).build());
    public Druidry()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::commonSetup);
        ModRegistries.register(modEventBus);
        CREATIVE_MODE_TABS.register(modEventBus);
        NeoForge.EVENT_BUS.register(this);
        DruidryItems.ITEMS.register(modEventBus);
        DruidryEffects.EFFECTS.register(modEventBus);
        DruidryEntities.ENTITIES.register(modEventBus);
        Attributes.ATTRIBUTES.register(modEventBus);
        DruidrySpells.SPELLS.register(modEventBus);
        modEventBus.addListener(this::addCreative);
        modEventBus.addListener(this::clientSetup);
        //modEventBus.addListener(DruidryCommands::onCommandsRegistered);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
        ClientEventHandler handler = new ClientEventHandler();
        NeoForge.EVENT_BUS.register(handler);
        new Networking().init();
    }
    
    public void clientSetup(final FMLClientSetupEvent event) {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(DruidryClient::init);
    }
    private void commonSetup(final FMLCommonSetupEvent event)
    {
        LOGGER.info("HELLO FROM COMMON SETUP");

        if (Config.logDirtBlock)
            LOGGER.info("GOODBERRY >> {}", ModRegistries.SPELL.getKey(DruidrySpells.GOODBERRY.get()));

        LOGGER.info(Config.magicNumberIntroduction + Config.magicNumber);

        Config.items.forEach((item) -> LOGGER.info("ITEM >> {}", item.toString()));
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {
        if (event.getTabKey() == druidryMisc.getKey())
            event.accept(DruidryItems.STAFF.get());
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        LOGGER.info("HELLO from server starting");
    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
        }
    }
}
