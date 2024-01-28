package com.incompetent_modders.druidry.network;

import com.incompetent_modders.druidry.casting.staff.StaffItem;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.NetworkEvent;
import net.neoforged.neoforge.network.NetworkEvent.Context;

import java.util.function.Supplier;

public class SpellSlotScrollMessage {
    private boolean up;
    
    public SpellSlotScrollMessage(boolean forward)
    {
        up = up;
    }
    
    public SpellSlotScrollMessage(FriendlyByteBuf buf)
    {
        this.up = buf.readBoolean();
    }
    
    public void toBytes(FriendlyByteBuf buf)
    {
        buf.writeBoolean(up);
    }
    public void handle(NetworkEvent.Context ctx) {
        ServerPlayer player = ctx.getSender();
        assert player!=null;
        ctx.enqueueWork(() -> {
            ItemStack equipped = player.getItemInHand(InteractionHand.MAIN_HAND);
            ItemStack offHand = player.getItemInHand(InteractionHand.OFF_HAND);
            if(equipped.getItem() instanceof StaffItem)
                ((StaffItem)equipped.getItem()).changeSelectedSpell(equipped, up);
            else if(offHand.getItem() instanceof StaffItem)
                ((StaffItem)offHand.getItem()).changeSelectedSpell(offHand, up);
        });
    }
}
