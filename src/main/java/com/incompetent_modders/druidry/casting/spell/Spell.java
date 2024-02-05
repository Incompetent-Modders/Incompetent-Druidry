package com.incompetent_modders.druidry.casting.spell;

import com.incompetent_modders.druidry.Druidry;
import com.incompetent_modders.druidry.setup.Capabilities;
import com.incompetent_modders.druidry.setup.ModRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class Spell {
    private final boolean isRangedAttack;
    private final int manaCost;
    private final int drawTime;
    private final int coolDown;
    private final SpellCategory category;
    
    public Spell(boolean isRangedAttack, int manaCost, int drawTime, int coolDown, SpellCategory category) {
        this.isRangedAttack = isRangedAttack;
        this.manaCost = manaCost;
        this.drawTime = drawTime;
        this.coolDown = coolDown;
        this.category = category;
    }
    public final Spell getSpell(ResourceLocation rl) {
        if (rl.equals(this.getSpellIdentifier())) {
            return this;
        } else {
            Druidry.LOGGER.error("Spell " + rl + " does not exist!");
            return null;
        }
    }
    
    public SpellCategory getCategory() {
        return category;
    }
    public SoundEvent getSpellSound() {
        return getSpellSound(this.getCategory());
    }
    private SoundEvent getSpellSound(SpellCategory category) {
        return switch (category) {
            case CURSE -> SoundEvents.ALLAY_DEATH;
            case PROJECTILE -> SoundEvents.ALLAY_ITEM_TAKEN;
            case BUFF -> SoundEvents.WARDEN_AGITATED;
            case DEBUFF -> SoundEvents.WARDEN_HURT;
            case HEALING -> SoundEvents.ALLAY_AMBIENT_WITH_ITEM;
            case SUMMON -> SoundEvents.ALLAY_ITEM_GIVEN;
            case UTILITY -> SoundEvents.WARDEN_AMBIENT;
            case DEFENSE -> SoundEvents.WARDEN_DEATH;
            case OFFENSE -> SoundEvents.WARDEN_ATTACK_IMPACT;
            case MOBILITY -> SoundEvents.ALLAY_THROW;
            case ENVIRONMENTAL -> SoundEvents.WARDEN_EMERGE;
            default -> SoundEvents.WARDEN_HEARTBEAT;
        };
    }
    public ResourceLocation getSpellIconLocation() {
        return new ResourceLocation(this.getSpellIdentifier().getNamespace(), "textures/spells/" + this.getSpellIdentifier().getPath());
    }
    public int getDrawTime() {
        return drawTime;
    }
    public int getCoolDown() {
        return coolDown;
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
        if (Capabilities.getMana(player).isPresent()) {
            return Capabilities.getMana(player).orElseThrow(NullPointerException::new).getCurrentMana() >= this.getManaCost();
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
