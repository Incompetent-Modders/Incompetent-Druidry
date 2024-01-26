package com.incompetent_modders.druidry.casting;

import net.minecraft.core.Holder;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;

import java.util.List;

public abstract class AbstractMagicalEffectProjectile extends Projectile {
    private static double BASE_DAMAGE;
    private static List<EntityType<?>> TARGET_ENTITIES;
    private static boolean AOE;
    private static int EFFECT_RADIUS;
    private static final EntityDataAccessor<Byte> ID_FLAGS = SynchedEntityData.defineId(AbstractMagicalEffectProjectile.class, EntityDataSerializers.BYTE);
    private static final EntityDataAccessor<Byte> PIERCE_LEVEL = SynchedEntityData.defineId(AbstractMagicalEffectProjectile.class, EntityDataSerializers.BYTE);
    private static MobEffect APPLIED_EFFECT;
    private static int EFFECT_DURATION;
    private static int EFFECT_AMPLIFIER;
    private SoundEvent soundEvent = this.getDefaultHitGroundSoundEvent();
    private Level level;
    
    protected AbstractMagicalEffectProjectile(EntityType<? extends Projectile> type, Level level, double baseDamage, MobEffect appliedEffect, int duration, int amplifier, int effectRadius, boolean aoe, List<EntityType<?>> targetEntities) {
        super(type, level);
        BASE_DAMAGE = baseDamage;
        APPLIED_EFFECT = appliedEffect;
        EFFECT_DURATION = duration;
        EFFECT_AMPLIFIER = amplifier;
        EFFECT_RADIUS = effectRadius;
        AOE = aoe;
        TARGET_ENTITIES = targetEntities;
    }
    
    protected List<EntityType<?>> getTargetCategories() {
        return TARGET_ENTITIES;
    }
    protected SoundEvent getDefaultHitGroundSoundEvent() {
        return SoundEvents.BREWING_STAND_BREW;
    }
    protected final SoundEvent getHitGroundSoundEvent() {
        return this.soundEvent;
    }
    
    protected boolean isAOE() {
        return AOE;
    }
    protected int getEffectRadius() {
        return EFFECT_RADIUS;
    }
    protected void onHitBlock(BlockHitResult hitResult) {
        super.onHitBlock(hitResult);
        this.discard();
        this.playSound(this.getHitGroundSoundEvent(), 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
        this.setSoundEvent(SoundEvents.BREWING_STAND_BREW);
        if (this.isAOE()) {
            if (!this.level().isClientSide) {
                AABB aabb = this.getBoundingBox().inflate(this.getEffectRadius());
                this.makeAreaOfEffectCloud(APPLIED_EFFECT, EFFECT_DURATION, EFFECT_AMPLIFIER, EFFECT_RADIUS);
                this.damageEntity(aabb, this.getTargetCategories());
            }
        }
    }
    protected void damageEntity(AABB aabb, List<EntityType<?>> targets) {
        for (LivingEntity livingentity : this.level().getEntitiesOfClass(LivingEntity.class, aabb, (entity) -> {
            return entity != null && entity.isAlive();
        })) {
            boolean flag = false;
            for (EntityType entityType : targets) {
                if (livingentity.getType() == entityType) {
                    flag = true;
                }
            }
            if (flag) {
                livingentity.hurt(this.damageSources().magic, (float) BASE_DAMAGE);
            }
        }
        
    }
    private void makeAreaOfEffectCloud(MobEffect effect, int duration, int amplifier, int radius) {
        AreaEffectCloud areaeffectcloud = new AreaEffectCloud(this.level(), this.getX(), this.getY(), this.getZ());
        Entity entity = this.getOwner();
        if (entity instanceof LivingEntity) {
            areaeffectcloud.setOwner((LivingEntity)entity);
        }
        areaeffectcloud.setRadius(radius);
        areaeffectcloud.setRadiusOnUse(-0.5F);
        areaeffectcloud.setWaitTime(10);
        areaeffectcloud.setRadiusPerTick(-areaeffectcloud.getRadius() / (float)areaeffectcloud.getDuration());
        areaeffectcloud.addEffect(new MobEffectInstance(effect, duration, amplifier));
        areaeffectcloud.setFixedColor(effect.getColor());
        this.level().addFreshEntity(areaeffectcloud);
    }
    public void setSoundEvent(SoundEvent soundEvent) {
        this.soundEvent = soundEvent;
    }
    @Override
    protected void defineSynchedData() {
    
    }
}
