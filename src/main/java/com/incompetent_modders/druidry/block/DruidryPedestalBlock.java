package com.incompetent_modders.druidry.block;

import com.incompetent_modders.druidry.foundation.block_entity.IBE;
import com.incompetent_modders.druidry.foundation.block_entity.IncompetentEntityBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Containers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class DruidryPedestalBlock extends IncompetentEntityBlock implements IBE<DruidryPedestalBlockEntity> {
    public DruidryPedestalBlock(Properties p) {
        super(p);
    }
    
    
    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof DruidryPedestalBlockEntity pedestalBlockEntity) {
                Containers.dropContents(level, pos, pedestalBlockEntity.getDroppableInventory());
                level.updateNeighbourForOutputSignal(pos, this);
            }
            
            super.onRemove(state, level, pos, newState, isMoving);
        }
    }
    @Override
    public BlockEntityType<? extends DruidryPedestalBlockEntity> getBlockEntityType() {
        return null;
    }
    @Override
    public Class<DruidryPedestalBlockEntity> getBlockEntityClass() {
        return null;
    }
}
