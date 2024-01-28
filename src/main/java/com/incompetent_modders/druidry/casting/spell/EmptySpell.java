package com.incompetent_modders.druidry.casting.spell;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class EmptySpell extends Spell {
    public EmptySpell() {
        super(false, 0, 0);
    }
    
    @Override
    protected void onCast(Level level, Player player, InteractionHand hand) {
        doNothing(level, player);
    }
    
    @Override
    protected void onFail(Level level, Player player, InteractionHand hand) {
        doNothing(level, player);
    }
}
