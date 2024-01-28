package com.incompetent_modders.druidry.command.arguments;

import com.incompetent_modders.druidry.casting.spell.Spell;
import com.incompetent_modders.druidry.setup.ModRegistries;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.arguments.item.ItemParser;
import net.minecraft.core.HolderLookup;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;

public class SpellArgument implements ArgumentType<SpellInput> {
    private static final Collection<String> EXAMPLES = Arrays.asList("goodberry", "incompetent_druidry:goodberry", "goodberry{foo=bar}");
    private final HolderLookup<Spell> spells;
    public SpellArgument(CommandBuildContext commandBuildContext) {
        this.spells = commandBuildContext.holderLookup(ModRegistries.SPELL.key());
    }
    
    public static SpellArgument spell(CommandBuildContext commandBuildContext) {
        return new SpellArgument(commandBuildContext);
    }
    
    public SpellInput parse(StringReader stringReader) throws CommandSyntaxException {
        SpellParser.SpellResult spellParser$spellResult = SpellParser.parseForSpell(this.spells, stringReader);
        return new SpellInput(spellParser$spellResult.spell());
    }
    
    public static <S> SpellInput getSpell(CommandContext<S> commandContext, String p_120965_) {
        return commandContext.getArgument(p_120965_, SpellInput.class);
    }
    
    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> commandContext, SuggestionsBuilder suggestionsBuilder) {
        return SpellParser.fillSuggestions(this.spells, suggestionsBuilder);
    }
    
    @Override
    public Collection<String> getExamples() {
        return EXAMPLES;
    }
}
