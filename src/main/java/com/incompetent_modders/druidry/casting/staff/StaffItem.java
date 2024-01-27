package com.incompetent_modders.druidry.casting.staff;

import com.incompetent_modders.druidry.setup.DruidrySpells;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
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
    public int getUseDuration(ItemStack stack) {
        return 72000;
    }
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.CROSSBOW;
    }
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        return InteractionResultHolder.success(player.getItemInHand(hand));
    }
    public void releaseUsing(ItemStack stack, Level level, LivingEntity entity, int timeLeft) {
        if (entity instanceof Player player) {
            DruidrySpells.GOODBERRY.get().cast(level, player, InteractionHand.MAIN_HAND);
        }
    }
}
