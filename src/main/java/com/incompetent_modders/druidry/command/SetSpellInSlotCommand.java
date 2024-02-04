package com.incompetent_modders.druidry.command;

import com.incompetent_modders.druidry.casting.staff.StaffItem;
import com.incompetent_modders.druidry.command.arguments.SpellArgument;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.commands.Commands;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.util.FakePlayerFactory;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

@Mod.EventBusSubscriber
public class SetSpellInSlotCommand {
    @SubscribeEvent
    public static void registerCommand(RegisterCommandsEvent event) {
        event.getDispatcher().register(Commands.literal("setSpell").requires(s -> s.hasPermission(2)).then(Commands.argument("spellSlot", IntegerArgumentType.integer(0, 5)).then(Commands.argument("spell", new SpellArgument()).executes(arguments -> {
                    ServerLevel world = arguments.getSource().getLevel();
                    Player player = (Player) arguments.getSource().getEntity();
                    if (player == null)
                        player = FakePlayerFactory.getMinecraft(world);
                    if (player.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof StaffItem) {
                        ItemStack staff = player.getItemInHand(InteractionHand.MAIN_HAND);
                        CompoundTag tag = staff.getOrCreateTag();
                        tag.putString("spellSlot_" + IntegerArgumentType.getInteger(arguments, "spellSlot"), SpellArgument.getSpell(arguments, "spell").getSpellIdentifier().toString());
                    }
                    return 0;
                })))
        );
    }
}
