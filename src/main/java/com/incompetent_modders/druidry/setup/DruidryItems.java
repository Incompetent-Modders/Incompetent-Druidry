package com.incompetent_modders.druidry.setup;

import com.incompetent_modders.druidry.casting.staff.StaffItem;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.incompetent_modders.druidry.Druidry.MODID;

public class DruidryItems {
    
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    
    public static final RegistryObject<Item> STAFF = ITEMS.register("basic_staff", () -> new StaffItem(new Item.Properties(), 1));
    public static final RegistryObject<Item> GOODBERRY = ITEMS.register("goodberry", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(10).saturationMod(1.0F).build())));
    
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
