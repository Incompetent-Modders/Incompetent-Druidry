package com.incompetent_modders.druidry;

import com.incompetent_modders.incomp_core.ModRegistries;
import com.incompetent_modders.incomp_core.api.class_type.ClassType;
import com.incompetent_modders.incomp_core.registry.ModAttributes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;

import java.util.UUID;

import static com.incompetent_modders.incomp_core.api.player.PlayerDataCore.DATA_ID;

//@Mod.EventBusSubscriber(modid = Druidry.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class DruidryCommon {
    
    //static AttributeModifier DRUIDRY_PACIFIST = new AttributeModifier(UUID.fromString("70eeca5e-46ed-4b8a-bf75-f102419395cc"), "Pacifist", 0.25F, AttributeModifier.Operation.MULTIPLY_TOTAL);
    //@SubscribeEvent(priority = EventPriority.HIGH)
    //public static void onEntityAdded(EntityJoinLevelEvent event) {
    //    Entity entity = event.getEntity();
    //    if (entity instanceof Player player) {
    //        CompoundTag tag = player.getPersistentData().getCompound(DATA_ID);
    //        ClassType classType = ModRegistries.CLASS_TYPE.get(new ResourceLocation(tag.getString("class_type")));
    //        AttributeInstance inst = player.getAttribute(ModAttributes.MAX_MANA.get());
    //        boolean hasClassMana = classType != null && inst != null && inst.getBaseValue() == classType.getMaxMana();
    //        if (inst != null && classType != null) {
    //            if (!hasClassMana)
    //                inst.setBaseValue(classType.getMaxMana());
    //        }
    //        AttributeInstance damage = player.getAttribute(Attributes.ATTACK_DAMAGE);
    //        if (classType != null && damage != null) {
    //            if (classType.isPacifist()) {
    //                damage.addPermanentModifier(DRUIDRY_PACIFIST);
    //            }
    //        }
    //    }
    //}
}
