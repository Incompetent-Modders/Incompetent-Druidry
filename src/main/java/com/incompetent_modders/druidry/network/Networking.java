package com.incompetent_modders.druidry.network;

import com.incompetent_modders.druidry.Druidry;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.NetworkRegistry;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.simple.SimpleChannel;

public class Networking {
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel MAIN = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(Druidry.MODID, "incompetent_druidry_network"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            NetworkRegistry.acceptMissingOr(PROTOCOL_VERSION::equals)
    );
    public void init() {
        MAIN.messageBuilder(SpellSlotScrollMessage.class, 0).encoder(SpellSlotScrollMessage::toBytes).decoder(SpellSlotScrollMessage::new).consumerMainThread(SpellSlotScrollMessage::handle).add();
    }
}
