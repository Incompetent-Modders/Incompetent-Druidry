package com.incompetent_modders.druidry.casting.spell;

import com.incompetent_modders.druidry.setup.DruidryClassTypes;
import com.incompetent_modders.incomp_core.api.spell.Spell;
import com.incompetent_modders.incomp_core.api.spell.SpellCategory;

public class DruidSpell extends Spell {
    public DruidSpell(boolean isRangedAttack, int manaCost, int drawTime, int coolDown, SpellCategory category) {
        super(isRangedAttack, manaCost, drawTime, coolDown, category, DruidryClassTypes.DRUID.get().getClassTypeIdentifier());
    }
}
