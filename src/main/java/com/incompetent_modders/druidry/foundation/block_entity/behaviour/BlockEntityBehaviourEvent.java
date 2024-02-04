package com.incompetent_modders.druidry.foundation.block_entity.behaviour;

import com.incompetent_modders.druidry.foundation.block_entity.SmartBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.GenericEvent;

import java.lang.reflect.Type;
import java.util.Map;

public class BlockEntityBehaviourEvent<T extends SmartBlockEntity> extends GenericEvent<T> {
    
    private T smartBlockEntity;
    private Map<BehaviourType<?>, BlockEntityBehaviour> behaviours;
    
    public BlockEntityBehaviourEvent(T blockEntity, Map<BehaviourType<?>, BlockEntityBehaviour> behaviours) {
        smartBlockEntity = blockEntity;
        this.behaviours = behaviours;
    }
    
    @Override
    public Type getGenericType() {
        return smartBlockEntity.getClass();
    }
    
    public void attach(BlockEntityBehaviour behaviour) {
        behaviours.put(behaviour.getType(), behaviour);
    }
    
    public BlockEntityBehaviour remove(BehaviourType<?> type) {
        return behaviours.remove(type);
    }
    
    public T getBlockEntity() {
        return smartBlockEntity;
    }
    
    public BlockState getBlockState() {
        return smartBlockEntity.getBlockState();
    }
    
}
