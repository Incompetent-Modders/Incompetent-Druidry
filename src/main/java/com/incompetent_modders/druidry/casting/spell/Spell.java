package com.incompetent_modders.druidry.casting.spell;

import com.incompetent_modders.druidry.Druidry;
import com.incompetent_modders.druidry.setup.Capabilities;
import com.incompetent_modders.druidry.setup.DruidrySpells;
import com.incompetent_modders.druidry.setup.ModRegistries;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class Spell {
    private final boolean isRangedAttack;
    private final int manaCost;
    private final int drawTime;
    
    public Spell(boolean isRangedAttack, int manaCost, int drawTime) {
        this.isRangedAttack = isRangedAttack;
        this.manaCost = manaCost;
        this.drawTime = drawTime;
    }
    public final Spell getSpell(ResourceLocation rl) {
        if (rl.equals(this.getSpellIdentifier())) {
            return this;
        } else {
            Druidry.LOGGER.error("Spell " + rl + " does not exist!");
            return null;
        }
    }
    
    public ResourceLocation getSpellIconLocation() {
        return new ResourceLocation(this.getSpellIdentifier().getNamespace(), "textures/spells/" + this.getSpellIdentifier().getPath());
    }
    public int getDrawTime() {
        return drawTime;
    }
    public String getTranslationKey() {
        return "spell." + this.getSpellIdentifier().getNamespace() + "." + this.getSpellIdentifier().getPath();
    }
    
    public Component getDisplayName() {
        return Component.translatable(this.getTranslationKey());
    }
    
    public ResourceLocation getSpellIdentifier() {
        return ModRegistries.SPELL.getKey(this);
    }
    public int getManaCost() {
        return manaCost;
    }
    public boolean isRangedAttack() {
        return isRangedAttack;
    }
    protected void onCast(Level level, Player player, InteractionHand hand) {
        SpellUtils.removeMana(player, this.getManaCost());
    }
    protected void onFail(Level level, Player player, InteractionHand hand) {
        SpellUtils.removeMana(player, this.getManaCost() / 2);
    }
    public void cast(Level level, Player player, InteractionHand hand) {
        //if (!this.canCast(level, player, hand)) {
        //    return;
        //}
        if (this.shouldFail(level, player, hand)) {
            this.onFail(level, player, hand);
        } else {
            this.onCast(level, player, hand);
        }
    }
    protected boolean shouldFail(Level level, Player player, InteractionHand hand) {
        return false;
    }
    protected boolean canCast(Level level, Player player, InteractionHand hand) {
        if (player.getCapability(Capabilities.MANA_CAPABILITY).isPresent()) {
            return player.getCapability(Capabilities.MANA_CAPABILITY).orElseThrow(NullPointerException::new).getCurrentMana() >= this.getManaCost();
        } else {
            return false;
        }
    }
    protected void onHit(Level level, Player player) {
    }
    
    protected void doNothing(Level level, Player player) {
        player.displayClientMessage(Component.translatable("spell.druidry.do_nothing"), true);
    }
    
    
}
