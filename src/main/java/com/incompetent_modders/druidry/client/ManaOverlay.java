package com.incompetent_modders.druidry.client;

import com.incompetent_modders.druidry.setup.Attributes;
import com.incompetent_modders.druidry.setup.Capabilities;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.GameType;
import net.neoforged.neoforge.client.gui.overlay.ExtendedGui;
import net.neoforged.neoforge.client.gui.overlay.IGuiOverlay;

public class ManaOverlay implements IGuiOverlay {
    public static final ManaOverlay INSTANCE = new ManaOverlay();
    @Override
    public void render(ExtendedGui gui, GuiGraphics guiGraphics, float partialTick, int screenWidth, int screenHeight) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.options.hideGui || mc.gameMode.getPlayerMode() == GameType.SPECTATOR)
            return;
        
        LocalPlayer player = mc.player;
        //If the player has an inventory screen open, don't render the overlay
        
        if (player == null)
            return;
        
        double mana = Capabilities.getMana(player).orElseThrow(() -> new IllegalStateException("Mana capability not found!")).getCurrentMana();
        double maxMana = player.getAttributeValue(Attributes.MAX_MANA.get());
        PoseStack poseStack = guiGraphics.pose();
        poseStack.pushPose();
        poseStack.translate((double) screenWidth / 2 + 120, screenHeight - 53, 0);
        Component value = Component.literal(mana + " / " + maxMana);
        int color = 0x00FF00;
        guiGraphics.drawString(mc.font, value, 16, 5, color);
        poseStack.popPose();
    }
}
