package com.incompetent_modders.druidry.client;

import com.incompetent_modders.druidry.casting.spell.Spell;
import com.incompetent_modders.druidry.casting.staff.StaffItem;
import com.incompetent_modders.druidry.setup.DruidryItems;
import com.incompetent_modders.druidry.setup.DruidrySpells;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.GameType;
import net.neoforged.neoforge.client.gui.overlay.ExtendedGui;
import net.neoforged.neoforge.client.gui.overlay.IGuiOverlay;

public class SpellListOverlay implements IGuiOverlay {
    
    public static final SpellListOverlay INSTANCE = new SpellListOverlay();
    @Override
    public void render(ExtendedGui gui, GuiGraphics guiGraphics, float partialTick, int screenWidth, int screenHeight) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.options.hideGui || mc.gameMode.getPlayerMode() == GameType.SPECTATOR)
            return;
        
        LocalPlayer player = mc.player;
        //If the player has an inventory screen open, don't render the overlay
        
        if (player == null)
            return;
        
        if (!(player.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof StaffItem) && !(player.getItemInHand(InteractionHand.OFF_HAND).getItem() instanceof StaffItem))
            return;
        
        Item staffMainHand = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
        Spell spellMainHand = staffMainHand instanceof StaffItem staffItem ? staffItem.getSelectedSpell(player.getItemInHand(InteractionHand.MAIN_HAND)) : DruidrySpells.EMPTY.get();
        Item staffOffHand = player.getItemInHand(InteractionHand.OFF_HAND).getItem();
        Spell spellOffHand = staffOffHand instanceof StaffItem staffItem ? staffItem.getSelectedSpell(player.getItemInHand(InteractionHand.OFF_HAND)) : DruidrySpells.EMPTY.get();
        
        if (spellMainHand == null && spellOffHand == null)
            return;
        
        ResourceLocation spellMainHandIcon = spellMainHand.getSpellIconLocation();
        ResourceLocation spellOffHandIcon = spellOffHand.getSpellIconLocation();
        Component spellMainHandName = spellMainHand.getDisplayName();
        Component spellOffHandName = spellOffHand.getDisplayName();
        PoseStack poseStack = guiGraphics.pose();
        poseStack.pushPose();
        poseStack.translate((double) screenWidth / 2 - 120, screenHeight - 53, 0);
        if (player.getItemInHand(InteractionHand.MAIN_HAND).getItem() == DruidryItems.STAFF.get()) {
            guiGraphics.blitSprite(spellMainHandIcon, 16, 16, 0, 0, 16, screenHeight - 16, screenWidth / 2, 16);
            guiGraphics.drawString(mc.font, spellMainHandName, 16, 5, 0x00FF00);
        }
        if (player.getItemInHand(InteractionHand.OFF_HAND).getItem() == DruidryItems.STAFF.get()) {
            guiGraphics.blitSprite(spellOffHandIcon, 16, 16, 0, 0, 16, screenHeight - 16, screenWidth / 2, 16);
            guiGraphics.drawString(mc.font, spellOffHandName, 16, 5, 0x00FF00);
        }
        poseStack.popPose();
    }
}
