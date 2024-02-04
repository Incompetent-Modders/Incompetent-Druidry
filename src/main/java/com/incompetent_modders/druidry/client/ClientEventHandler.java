package com.incompetent_modders.druidry.client;

import com.incompetent_modders.druidry.casting.staff.StaffItem;
import com.incompetent_modders.druidry.foundation.util.ClientUtils;
import com.incompetent_modders.druidry.network.Networking;
import com.incompetent_modders.druidry.network.SpellSlotScrollMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.network.PacketDistributor;

public class ClientEventHandler {
    @SubscribeEvent
    public void onMouseEvent(InputEvent.MouseScrollingEvent event) {
        var minecraft = Minecraft.getInstance();
        Player player = minecraft.player;
        if (player == null)
            return;
        if(event.getScrollDeltaY() != 0 && ClientUtils.mc().screen == null)
        {
            ItemStack equipped = player.getItemInHand(InteractionHand.MAIN_HAND);
            if(player.isShiftKeyDown())
            {
                if(equipped.getItem() instanceof StaffItem)
                {
                    Networking.sendToServer(new SpellSlotScrollMessage(event.getScrollDeltaY() < 0));
                    event.setCanceled(true);
                }
            }
        }
    }
}
