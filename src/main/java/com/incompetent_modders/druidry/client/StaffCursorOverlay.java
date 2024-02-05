package com.incompetent_modders.druidry.client;

import com.incompetent_modders.druidry.Druidry;
import com.incompetent_modders.druidry.casting.spell.Spell;
import com.incompetent_modders.druidry.casting.staff.StaffItem;
import com.incompetent_modders.druidry.foundation.util.Utils;
import com.incompetent_modders.druidry.setup.DruidryItems;
import com.incompetent_modders.druidry.setup.DruidrySpells;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.neoforged.neoforge.client.gui.overlay.ExtendedGui;
import net.neoforged.neoforge.client.gui.overlay.IGuiOverlay;

public class StaffCursorOverlay implements IGuiOverlay {
    
    public static final StaffCursorOverlay INSTANCE = new StaffCursorOverlay();
    public final static ResourceLocation TEXTURE = new ResourceLocation(Druidry.MODID, "textures/gui/icons.png");
    static final int IMAGE_WIDTH = 54;
    static final int COMPLETION_BAR_WIDTH = 46;
    static final int IMAGE_HEIGHT = 21;
    @Override
    public void render(ExtendedGui gui, GuiGraphics guiGraphics, float partialTick, int screenWidth, int screenHeight) {
        
        Minecraft mc = Minecraft.getInstance();
        if (mc.options.hideGui || mc.gameMode.getPlayerMode() == GameType.SPECTATOR)
            return;
        
        LocalPlayer player = mc.player;
        
        if (player == null)
            return;
        
        if (!(player.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof StaffItem) && !(player.getItemInHand(InteractionHand.OFF_HAND).getItem() instanceof StaffItem))
            return;
        
        ItemStack staffMainHand = player.getItemInHand(InteractionHand.MAIN_HAND);
        ItemStack staffOffHand = player.getItemInHand(InteractionHand.OFF_HAND);
        
        boolean isInOffHand = staffOffHand.getItem() instanceof StaffItem && !(staffMainHand.getItem() instanceof StaffItem);
        boolean isInMainHand = staffMainHand.getItem() instanceof StaffItem && !(staffOffHand.getItem() instanceof StaffItem);
        
        
        float castCompletionPercentOff = getCastCompletionPercent(staffOffHand);
        String castTimeStringOff = Utils.timeFromTicks((1 - castCompletionPercentOff) * getCastDuration(staffOffHand), 1);
        
        float castCompletionPercentMain = getCastCompletionPercent(staffMainHand);
        String castTimeStringMain = Utils.timeFromTicks((1 - castCompletionPercentMain) * getCastDuration(staffMainHand), 1);
        
        
        if (castCompletionPercentMain == 0 && isInMainHand)
            return;
        
        if (castCompletionPercentOff == 0 && isInOffHand)
            return;
        
        
        int barX, barY;
        barX = screenWidth / 2 - IMAGE_WIDTH / 2;
        barY = screenHeight / 2 + screenHeight / 8;
        
        guiGraphics.blit(TEXTURE, barX, barY, 0, IMAGE_HEIGHT * 2, IMAGE_WIDTH, IMAGE_HEIGHT, 256, 256);
        if (isInMainHand)
            guiGraphics.blit(TEXTURE, barX, barY - 1, 0, IMAGE_HEIGHT * 3, (int) (COMPLETION_BAR_WIDTH * castCompletionPercentMain + (IMAGE_WIDTH - COMPLETION_BAR_WIDTH) / 2), IMAGE_HEIGHT);
        if (isInOffHand)
            guiGraphics.blit(TEXTURE, barX, barY - 1, 0, IMAGE_HEIGHT * 3, (int) (COMPLETION_BAR_WIDTH * castCompletionPercentOff + (IMAGE_WIDTH - COMPLETION_BAR_WIDTH) / 2), IMAGE_HEIGHT);
        int textX, textY;
        var font = gui.getFont();
        
        if (isInMainHand)
            textX = barX + (IMAGE_WIDTH - font.width(castTimeStringMain)) / 2;
        else
            textX = barX + (IMAGE_WIDTH - font.width(castTimeStringOff)) / 2;
        
        textY = barY + IMAGE_HEIGHT / 2 - font.lineHeight / 2 + 1;
        
        if (isInMainHand)
            guiGraphics.drawString(font, castTimeStringMain, textX, textY, 0xFFFFFF);
        
        if (isInOffHand)
            guiGraphics.drawString(font, castTimeStringOff, textX, textY, 0xFFFFFF);
        
        
        
    }
    
    public float getCastCompletionPercent(ItemStack stack) {
        if (!(stack.getItem() instanceof StaffItem staffItem))
            return 0;
        if (staffItem.getSelectedSpell(stack).getDrawTime() == 0) {
            return 0;
        }
        
        return 1 - (staffItem.spellRemainingDrawTime(stack) / (float) staffItem.getUseDuration(stack));
    }
    
    public float getCastDuration(ItemStack stack) {
        if (!(stack.getItem() instanceof StaffItem staffItem))
            return 0;
        return staffItem.getSelectedSpell(stack).getDrawTime();
    }
}
