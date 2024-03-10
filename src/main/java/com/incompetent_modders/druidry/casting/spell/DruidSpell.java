package com.incompetent_modders.druidry.casting.spell;

import com.incompetent_modders.druidry.setup.DruidryClassTypes;
import com.incompetent_modders.incomp_core.ModRegistries;
import com.incompetent_modders.incomp_core.api.spell.Spell;
import com.incompetent_modders.incomp_core.api.spell.SpellCategory;
import net.minecraft.resources.ResourceLocation;

public class DruidSpell extends Spell {
    public DruidSpell(boolean isRangedAttack, int manaCost, int drawTime, int coolDown, SpellCategory category) {
        super(isRangedAttack, manaCost, drawTime, coolDown, category, ModRegistries.CLASS_TYPE.get(new ResourceLocation("incompetent_druidry:druid")));
    }
}
