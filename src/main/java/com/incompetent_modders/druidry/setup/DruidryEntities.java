package com.incompetent_modders.druidry.setup;

import com.incompetent_modders.druidry.casting.spell.sunburst.SunburstProjectile;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.incompetent_modders.druidry.Druidry.MODID;

public class DruidryEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, MODID);
    static <T extends Entity> DeferredHolder<EntityType<?>, EntityType<T>> registerEntity(String name, EntityType.Builder<T> builder) {
        return ENTITIES.register(name, () -> builder.build(MODID + ":" + name));
    }
    public static final DeferredHolder<EntityType<?>, EntityType<SunburstProjectile>> SUNBURST_PROJECTILE = registerEntity(
            "sunburst_projectile",
            EntityType.Builder.<SunburstProjectile>of((entityType, world) -> new SunburstProjectile(world), MobCategory.MISC)
                    .sized(0.5f, 0.5f).noSave()
                    .setTrackingRange(20).fireImmune()
                    .setShouldReceiveVelocityUpdates(true)
                    .setUpdateInterval(120));
    
}
