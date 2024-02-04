package com.incompetent_modders.druidry.setup;

import com.incompetent_modders.druidry.casting.spell.DruidryTablet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.incompetent_modders.druidry.Druidry.MODID;

public class DruidryTablets {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(BuiltInRegistries.ITEM, MODID);
    
    public static final DeferredHolder<Item, Item> GOODBERRY = ITEMS.register("spell_tablet_goodberry", () -> new DruidryTablet(new ResourceLocation(MODID, "goodberry")));
    
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
