package com.incompetent_modders.druidry.casting.staff;

import com.incompetent_modders.druidry.casting.spell.sunburst.SunburstProjectile;
import com.incompetent_modders.druidry.mana.ManaCap;
import com.incompetent_modders.druidry.setup.Capabilities;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class StaffItem extends Item {
    private final int level;
    public StaffItem(Properties p, int level) {
        super(p);
        this.level = level;
    }
    public int getLevel() {
        return this.level;
    }
    
    public void releaseUsing(ItemStack stack, Level level, LivingEntity entity, int timeLeft) {
        //Check if the player has enough mana
        if (!level.isClientSide) {
            if (entity.getCapability(Capabilities.MANA_CAPABILITY).isPresent()) {
                ManaCap cap = (ManaCap) entity.getCapability(Capabilities.MANA_CAPABILITY).orElseThrow(NullPointerException::new);
                if (cap.getCurrentMana() >= 10) {
                    cap.removeMana(10);
                    SunburstProjectile projectile = new SunburstProjectile(level);
                    projectile.shootFromRotation(entity, entity.getXRot(), entity.getYRot(), 0.0F, 1.5F, 1.0F);
                    level.addFreshEntity(projectile);
                }
            }
        }
    }
}
