package com.incompetent_modders.druidry.client;

import com.incompetent_modders.druidry.foundation.util.ClientUtils;
import com.incompetent_modders.druidry.network.Networking;
import com.incompetent_modders.druidry.network.SpellSlotScrollMessage;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.network.PacketDistributor;

public class ClientEventHandler {
    @SubscribeEvent
    public void onMouseEvent(InputEvent.MouseScrollingEvent event) {
        Player player = ClientUtils.mc().player;
        if(player.isShiftKeyDown())
        {
            Networking.MAIN.send(PacketDistributor.ALL.noArg(), new SpellSlotScrollMessage(event.getScrollDeltaY() < 0));
            event.setCanceled(true);
        }
    }
}
