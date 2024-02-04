package com.incompetent_modders.druidry.network;

import com.incompetent_modders.druidry.casting.staff.StaffItem;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.NetworkEvent.Context;

public class SpellSlotScrollMessage {
    private final boolean forward;
    
    public SpellSlotScrollMessage(boolean forward)
    {
        this.forward = forward;
    }
    
    public SpellSlotScrollMessage(FriendlyByteBuf buf)
    {
        this.forward = buf.readBoolean();
    }
    
    public void toBytes(FriendlyByteBuf buf)
    {
        buf.writeBoolean(forward);
    }
    public void handle(Context ctx) {
        ServerPlayer serverPlayer = ctx.getSender();
        if (serverPlayer != null) {
            ctx.enqueueWork(() -> {
                ItemStack equipped = serverPlayer.getItemInHand(InteractionHand.MAIN_HAND);
                if (equipped.getItem() instanceof StaffItem)
                    ((StaffItem) equipped.getItem()).changeSelectedSpell(equipped, forward);
            });
        }
    }
}
