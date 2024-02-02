package com.incompetent_modders.druidry.setup;

import com.incompetent_modders.druidry.Druidry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;


import java.util.HashMap;
import java.util.UUID;
import java.util.function.Function;

@Mod.EventBusSubscriber(modid = Druidry.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Attributes {
    public static final HashMap<DeferredHolder<Attribute, Attribute>, UUID> UUIDS = new HashMap<>();
    public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(BuiltInRegistries.ATTRIBUTE, Druidry.MODID);
    
    public static final DeferredHolder<Attribute, Attribute> MANA_REGEN_BONUS = registerAttribute("incompetent_druidry.mana_regen", (id) -> new RangedAttribute(id, 1.0D, 0.0D, 2000.0D).setSyncable(true), "ed69a04a-eb94-4828-88fc-dd366145ed46");
    
    public static final DeferredHolder<Attribute, Attribute> MAX_MANA = registerAttribute("incompetent_druidry.max_mana", (id) -> new RangedAttribute(id, 100.0D, 0.0D, 10000.0D).setSyncable(true), "6393df79-d450-4374-9826-b81c2db0f053");
    
    @Deprecated
    public static final DeferredHolder<Attribute, Attribute> MAX_MANA_BONUS = MAX_MANA, FLAT_MANA_BONUS = MAX_MANA;
    
    public static DeferredHolder<Attribute, Attribute> registerAttribute(String name, Function<String, Attribute> attribute, String uuid) {
        return registerAttribute(name, attribute, UUID.fromString(uuid));
    }
    
    public static DeferredHolder<Attribute, Attribute> registerAttribute(String name, Function<String, Attribute> attribute, UUID uuid) {
        DeferredHolder<Attribute, Attribute> registryObject = ATTRIBUTES.register(name, () -> attribute.apply(name));
        UUIDS.put(registryObject, uuid);
        return registryObject;
    }
    
    @SubscribeEvent
    public static void modifyEntityAttributes(EntityAttributeModificationEvent event) {
        event.getTypes().stream().filter(e -> e == EntityType.PLAYER).forEach(e -> {
            ATTRIBUTES.getEntries().forEach((v) -> {
                event.add(e, v.get());
            });
        });
    }
}
