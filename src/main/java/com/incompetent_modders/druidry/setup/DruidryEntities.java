package com.incompetent_modders.druidry.setup;

import com.incompetent_modders.druidry.casting.spell.sunburst.SunburstProjectile;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.incompetent_modders.druidry.Druidry.MODID;

public class DruidryEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, MODID);
    static <T extends Entity> RegistryObject<EntityType<T>> registerEntity(String name, EntityType.Builder<T> builder) {
        return ENTITIES.register(name, () -> builder.build(MODID + ":" + name));
    }
    public static final RegistryObject<EntityType<SunburstProjectile>> SUNBURST_PROJECTILE = registerEntity(
            "sunburst_projectile",
            EntityType.Builder.<SunburstProjectile>of((entityType, world) -> new SunburstProjectile(world), MobCategory.MISC)
                    .sized(0.5f, 0.5f).noSave()
                    .setTrackingRange(20).fireImmune()
                    .setShouldReceiveVelocityUpdates(true)
                    .setUpdateInterval(120));
    
}
