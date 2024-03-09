package com.incompetent_modders.druidry.casting.spell;

import com.incompetent_modders.druidry.foundation.util.Utils;
import com.incompetent_modders.incomp_core.ModRegistries;
import com.incompetent_modders.incomp_core.api.spell.Spell;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

import static com.incompetent_modders.druidry.Druidry.MODID;

public class DruidryTablet extends Item {
    public static final ChatFormatting TITLE_FORMAT = ChatFormatting.GRAY;
    public static final ChatFormatting DESCRIPTION_FORMAT = ChatFormatting.BLUE;
    private static final Component SPELL_CAST_TIME_TITLE = Component.translatable(
                    Util.makeDescriptionId("item", new ResourceLocation(MODID,"spell_tablet.cast_time_title"))
            )
            .withStyle(TITLE_FORMAT);
    
    private static final Component MANA_COST_TITLE = Component.translatable(
                    Util.makeDescriptionId("item", new ResourceLocation(MODID,"spell_tablet.mana_cost_title"))
            )
            .withStyle(TITLE_FORMAT);
    private final ResourceLocation spell;
    
    public DruidryTablet(
            ResourceLocation spell
    ) {
        super(new Item.Properties().stacksTo(1));
        this.spell = spell;
    }
    
    public Spell getSpell() {
        return ModRegistries.SPELL.get(spell);
    }
    
    private Component getSpellDescription() {
        return getSpell().getDisplayName().copy().withStyle(TITLE_FORMAT);
    }
    
    public int getManaCost() {
        return getSpell().getManaCost();
    }
    
    private String manaCostString() {
        return String.valueOf(getManaCost());
    }
    
    private String spellCastTimeString() {
        return Utils.timeFromTicks(getSpell().getDrawTime(), 1);
    }
    
    
    
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        tooltip.add(this.getSpellDescription());
        tooltip.add(CommonComponents.EMPTY);
        tooltip.add(SPELL_CAST_TIME_TITLE);
        tooltip.add(CommonComponents.space().append(spellCastTimeString()).withStyle(DESCRIPTION_FORMAT));
        tooltip.add(MANA_COST_TITLE);
        tooltip.add(CommonComponents.space().append(manaCostString()).withStyle(DESCRIPTION_FORMAT));
    }
}
