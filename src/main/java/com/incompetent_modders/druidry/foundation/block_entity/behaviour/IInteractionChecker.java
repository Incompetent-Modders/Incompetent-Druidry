package com.incompetent_modders.druidry.foundation.block_entity.behaviour;

import net.minecraft.world.entity.player.Player;

public interface IInteractionChecker {
    boolean canPlayerUse(Player player);
}
