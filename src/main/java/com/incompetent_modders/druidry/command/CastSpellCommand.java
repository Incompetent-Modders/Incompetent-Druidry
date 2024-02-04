package com.incompetent_modders.druidry.command;

import com.incompetent_modders.druidry.casting.spell.Spell;
import com.incompetent_modders.druidry.command.arguments.SpellArgument;
import com.incompetent_modders.druidry.command.arguments.SpellInput;
import com.mojang.brigadier.builder.ArgumentBuilder;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;

import java.util.Collection;

public class CastSpellCommand {
    public static ArgumentBuilder<CommandSourceStack, ?> register(CommandBuildContext commandBuildContext) {
        return Commands.literal("cast_spell")
                        .requires(player -> player.hasPermission(2))
                        .then(
                                Commands.argument("targets", EntityArgument.players())
                                        .then(
                                                Commands.argument("spell", new SpellArgument())
                                                        .executes(
                                                                exec -> castSpell(
                                                                        exec.getSource(), SpellArgument.getSpell(exec, "spell"), EntityArgument.getPlayers(exec, "targets"), 1
                                                                )
                                                        )
                                        )
                        );
    }
    
    private static int castSpell(CommandSourceStack source, Spell spell, Collection<ServerPlayer> targets, int count) {
        for (ServerPlayer serverplayer : targets) {
            for (int i = 0; i < count; ++i) {
                spell.cast(source.getLevel(), serverplayer, InteractionHand.MAIN_HAND);
            }
        }
        if (targets.size() == 1) {
            source.sendSuccess(() -> Component.translatable("commands.cast_spell.success.single", count, spell.getDisplayName(), targets.iterator().next().getDisplayName()), true);
        } else {
            source.sendSuccess(() -> Component.translatable("commands.cast_spell.success.single", count, spell.getDisplayName(), targets.size()), true);
        }
        return targets.size();
    }
    
}
