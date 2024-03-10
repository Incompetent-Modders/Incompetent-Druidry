package com.incompetent_modders.druidry.setup;

import com.incompetent_modders.druidry.casting.spell.DruidryTablet;
import com.incompetent_modders.druidry.casting.staff.StaffItem;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SmithingTemplateItem;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;


import java.util.Collection;
import java.util.List;

import static com.incompetent_modders.druidry.Druidry.MODID;

public class DruidryItems {
    private static final ChatFormatting DESCRIPTION_FORMAT = ChatFormatting.BLUE;
    private static final ChatFormatting TITLE_FORMAT = ChatFormatting.GRAY;
    private static final ResourceLocation EMPTY_SLOT_STAFF = new ResourceLocation(MODID, "item/empty_slot_staff");
    private static final ResourceLocation EMPTY_SLOT_SCROLL = new ResourceLocation(MODID, "item/empty_slot_scroll");
    private static final Component SPELL_SLOT_INSERT_APPLIES_TO = Component.translatable(
                    Util.makeDescriptionId("item", new ResourceLocation(MODID, "smithing_template.spell_slot_insert.applies_to"))
            )
            .withStyle(DESCRIPTION_FORMAT);
    private static final Component SPELL_SLOT_INSERT_INGREDIENTS = Component.translatable(
                    Util.makeDescriptionId("item", new ResourceLocation(MODID, "smithing_template.spell_slot_insert.ingredients"))
            )
            .withStyle(DESCRIPTION_FORMAT);
    private static final Component SPELL_SLOT_INSERT_BASE_SLOT_DESCRIPTION = Component.translatable(
            Util.makeDescriptionId("item", new ResourceLocation(MODID, "smithing_template.spell_slot_insert.base_slot_description"))
    );
    private static final Component SPELL_SLOT_INSERT_ADDITIONS_SLOT_DESCRIPTION = Component.translatable(
            Util.makeDescriptionId("item", new ResourceLocation(MODID, "smithing_template.spell_slot_insert.additions_slot_description"))
    );
    private static final Component SPELL_SLOT_INSERT = Component.translatable(Util.makeDescriptionId("upgrade", new ResourceLocation(MODID, "spell_slot_insert")))
            .withStyle(TITLE_FORMAT);
    private static List<ResourceLocation> createSpellSlotInsertIconList() {
        return List.of(EMPTY_SLOT_STAFF);
    }
    private static List<ResourceLocation> createSpellSlotInsertMaterialList() {
        return List.of(EMPTY_SLOT_SCROLL);
    }
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(BuiltInRegistries.ITEM, MODID);
    
    public static final DeferredHolder<Item, Item> STAFF = ITEMS.register("basic_staff", () -> new StaffItem(new Item.Properties(), 1));
    public static final DeferredHolder<Item, Item> GOODBERRY = ITEMS.register("goodberry", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(10).saturationMod(1.0F).build())));
    public static final DeferredHolder<Item, Item> SPELL_SLOT_INSERT_TEMPLATE = ITEMS.register("spell_slot_insert_smithing_template", () -> new SmithingTemplateItem(
            SPELL_SLOT_INSERT_APPLIES_TO,
            SPELL_SLOT_INSERT_INGREDIENTS,
            SPELL_SLOT_INSERT,
            SPELL_SLOT_INSERT_BASE_SLOT_DESCRIPTION,
            SPELL_SLOT_INSERT_ADDITIONS_SLOT_DESCRIPTION,
            createSpellSlotInsertIconList(),
            createSpellSlotInsertMaterialList()
    ));
    
    public static final DeferredHolder<Item, Item> GOODBERRY_TABLET = ITEMS.register("spell_tablet_goodberry", () -> new DruidryTablet(new ResourceLocation(MODID, "goodberry")));
    public static final DeferredHolder<Item, Item> PLANT_GROWTH_TABLET = ITEMS.register("spell_tablet_plant_growth", () -> new DruidryTablet(new ResourceLocation(MODID, "plant_growth")));
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
    
    
    
    
    public static Collection<DeferredHolder<Item, ? extends Item>> getItems() {
        return ITEMS.getEntries();
    }
}
