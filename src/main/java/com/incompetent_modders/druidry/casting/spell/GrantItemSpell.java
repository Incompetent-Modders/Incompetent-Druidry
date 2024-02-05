package com.incompetent_modders.druidry.casting.spell;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class GrantItemSpell extends Spell {
    
    private final Item item;
    private final int itemAmount;
    public GrantItemSpell(Item item, int itemAmount, int manaCost, int drawTime, int coolDown, SpellCategory category) {
        super(false, manaCost, drawTime, coolDown, category);
        this.item = item;
        this.itemAmount = itemAmount;
    }
    
    @Override
    public void onCast(Level level, Player player, InteractionHand hand) {
        super.onCast(level, player, hand);
        SpellUtils.giveItems(level, player, itemAmount, item);
    }
    
    @Override
    public void onFail(Level level, Player player, InteractionHand hand) {
        super.onFail(level, player, hand);
        doNothing(level, player);
    }
    
    @Override
    public boolean shouldFail(Level level, Player player, InteractionHand hand) {
        return SpellUtils.isInventoryFull(player);
    }
}
