package com.incompetent_modders.druidry.command.arguments;

import com.incompetent_modders.incomp_core.api.spell.Spell;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;

import java.util.function.Predicate;

public class SpellInput implements Predicate<Spell> {
    private final Holder<Spell> spell;
    
    public SpellInput(Holder<Spell> spell) {
        this.spell = spell;
    }
    public Spell getSpell() {
        return this.spell.value();
    }
    @Override
    public boolean test(Spell spell) {
        return spell.equals(this.getSpell());
    }
    public Spell createSpell() throws CommandSyntaxException {
        return this.getSpell();
    }
    public String serialize() {
        return this.getSpellName();
    }
    private String getSpellName() {
        return this.spell.unwrapKey().<Object>map(ResourceKey::location).orElseGet(() -> "unknown[" + this.spell + "]").toString();
    }
}
