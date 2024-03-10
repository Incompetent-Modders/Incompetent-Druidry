package com.incompetent_modders.druidry;

import com.incompetent_modders.druidry.client.ClientEventHandler;
import com.incompetent_modders.druidry.command.arguments.SpellArgument;
import com.incompetent_modders.druidry.effect.DruidryEffects;
import com.incompetent_modders.druidry.network.Networking;
import com.incompetent_modders.druidry.setup.*;
import com.incompetent_modders.incomp_core.ModRegistries;
import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.arguments.item.ItemArgument;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.commands.synchronization.ArgumentTypeInfos;
import net.minecraft.commands.synchronization.SingletonArgumentInfo;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
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
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.slf4j.Logger;

@Mod(Druidry.MODID)
public class Druidry
{
    public static final String MODID = "incompetent_druidry";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);
    private static final DeferredRegister<ArgumentTypeInfo<?, ?>> ARG_TYPE = DeferredRegister.create(
            Registries.COMMAND_ARGUMENT_TYPE, MODID
    );
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> druidryMisc = CREATIVE_MODE_TABS.register("incompetent_druidry_misc", () -> CreativeModeTab.builder()
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> DruidryItems.GOODBERRY.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                for (DeferredHolder<Item, ? extends Item> registry : DruidryItems.getItems()) {
                    output.accept(registry.get());
                }
            }).build());
    
    public static final DeferredHolder<ArgumentTypeInfo<?, ?>, SingletonArgumentInfo<SpellArgument>> SPELL_ARG = ARG_TYPE.register(
            "spell", () -> ArgumentTypeInfos.registerByClass(
                    SpellArgument.class, SingletonArgumentInfo.contextFree(SpellArgument::new)
            )
    );
    public Druidry()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::commonSetup);
        CREATIVE_MODE_TABS.register(modEventBus);
        NeoForge.EVENT_BUS.register(this);
        DruidryItems.ITEMS.register(modEventBus);
        DruidryEffects.EFFECTS.register(modEventBus);
        DruidryEntities.ENTITIES.register(modEventBus);
        DruidrySpells.SPELLS.register(modEventBus);
        ARG_TYPE.register(modEventBus);
        Networking.register();
        modEventBus.addListener(this::addCreative);
        modEventBus.addListener(DruidryClient::init);
        
        //modEventBus.addListener(DruidryCommands::onCommandsRegistered);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
        ClientEventHandler handler = new ClientEventHandler();
        NeoForge.EVENT_BUS.register(handler);
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
        if (event.getTabKey() == druidryMisc.getKey()) {
            event.accept(DruidryItems.STAFF.get());
            event.accept(DruidryItems.PLANT_GROWTH_TABLET.get());
            event.accept(DruidryItems.GOODBERRY_TABLET.get());
        }
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
