package com.incompetent_modders.druidry.setup;

import com.incompetent_modders.druidry.Druidry;
import com.incompetent_modders.incomp_core.ModRegistries;
import com.incompetent_modders.incomp_core.api.class_type.ClassType;
import com.incompetent_modders.incomp_core.registry.ModClassTypes;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class DruidryClassTypes {
    public static final DeferredRegister<ClassType> CLASS_TYPES;
    public static final DeferredHolder<ClassType, ClassType> DRUID;
    
    public DruidryClassTypes() {
    }
    
    public static void register(IEventBus eventBus) {
        CLASS_TYPES.register(eventBus);
    }
    
    static {
        CLASS_TYPES = DeferredRegister.create(ModRegistries.CLASS_TYPE, Druidry.MODID);
        DRUID = CLASS_TYPES.register("druid", () -> new ClassType(true, 100, false));
    }
}
