package com.incompetent_modders.druidry.casting.spell.sunburst;

import com.incompetent_modders.druidry.casting.AbstractMagicalEffectProjectile;
import com.incompetent_modders.druidry.effect.DruidryEffects;
import com.incompetent_modders.druidry.setup.DruidryEntities;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

import java.util.List;

public class SunburstProjectile extends AbstractMagicalEffectProjectile {
    private static final List<EntityType<?>> TARGET_CATEGORIES = List.of(EntityType.ZOMBIE, EntityType.SKELETON, EntityType.STRAY, EntityType.HUSK, EntityType.DROWNED, EntityType.ZOMBIE_VILLAGER, EntityType.ZOMBIE_HORSE, EntityType.ZOMBIFIED_PIGLIN, EntityType.SKELETON_HORSE);
    public SunburstProjectile(Level level) {
        super(DruidryEntities.SUNBURST_PROJECTILE.get(), level, 5.0, DruidryEffects.BLINDNESS_CURSE.get(), 1200, 4, 50, true, TARGET_CATEGORIES);
    }
}
