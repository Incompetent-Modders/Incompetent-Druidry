package com.incompetent_modders.druidry.casting.spell.list;

import com.incompetent_modders.druidry.casting.spell.DruidSpell;
import com.incompetent_modders.incomp_core.api.spell.SpellCategory;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;

public class PlantGrowthSpell extends DruidSpell {
    public PlantGrowthSpell(int manaCost, int drawTime) {
        super(false, manaCost, drawTime, 12000, SpellCategory.ENVIRONMENTAL);
    }
    
    @Override
    protected void onCast(Level level, Player player, InteractionHand hand) {
        super.onCast(level, player, hand);
        if (player instanceof ServerPlayer serverPlayer) {
            growCrops(level, serverPlayer);
        }
    }
    
    private void growCrops(Level level, ServerPlayer player) {
        //Crops are blocks
        if (level.isClientSide) {
            return;
        }
        BlockPos pos = player.blockPosition();
        int radius = 10;
        for (int x = -radius; x <= radius; ++x) {
            for (int y = -1; y <= 1; ++y) {
                for (int z = -radius; z <= radius; ++z) {
                    BlockPos offsetLocation = pos.offset(x, y, z);
                    BlockState offsetState = level.getBlockState(offsetLocation);
                    convertFoliage((ServerLevel) level, offsetLocation);
                    if ((level.getBlockState(offsetLocation).getBlock() instanceof BonemealableBlock && !(level.getBlockState(offsetLocation).getBlock() instanceof StemBlock)) || level.getBlockState(offsetLocation).getBlock() instanceof StemBlock && level.getBlockState(offsetLocation).getValue(StemBlock.AGE) != 7) {
                        if (offsetState.isAir()) {
                            continue;
                        }
                        BoneMealItem.growCrop(new ItemStack(Items.BONE_MEAL), level, offsetLocation);
                        BoneMealItem.growCrop(new ItemStack(Items.BONE_MEAL), level, offsetLocation);
                        BoneMealItem.growCrop(new ItemStack(Items.BONE_MEAL), level, offsetLocation);
                        BoneMealItem.growCrop(new ItemStack(Items.BONE_MEAL), level, offsetLocation);
                        BoneMealItem.growCrop(new ItemStack(Items.BONE_MEAL), level, offsetLocation);
                        BoneMealItem.growCrop(new ItemStack(Items.BONE_MEAL), level, offsetLocation);
                        addGrowthParticles((ServerLevel) level, offsetLocation, player);
                    }
                }
            }
        }
    }
    private static void convertFoliage(ServerLevel level, BlockPos pos) {
        BlockState blockstate = level.getBlockState(pos);
        BlockState aboveState = level.getBlockState(pos.above());
        BlockState belowState = level.getBlockState(pos.below());
        if (level.isClientSide) {
            return;
        }
        if (blockstate.is(Blocks.POPPY)) {
            if (aboveState.isAir()) {
                level.setBlockAndUpdate(pos, Blocks.ROSE_BUSH.defaultBlockState().setValue(BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.LOWER));
                level.setBlockAndUpdate(pos.above(), Blocks.ROSE_BUSH.defaultBlockState().setValue(BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.UPPER));
            }
        }
        if (blockstate.is(Blocks.DANDELION)) {
            if (aboveState.isAir()) {
                level.setBlockAndUpdate(pos, Blocks.SUNFLOWER.defaultBlockState().setValue(BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.LOWER));
                level.setBlockAndUpdate(pos.above(), Blocks.SUNFLOWER.defaultBlockState().setValue(BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.UPPER));
            }
        }
        if (blockstate.is(Blocks.BLUE_ORCHID)) {
            if (aboveState.isAir()) {
                level.setBlockAndUpdate(pos, Blocks.LILAC.defaultBlockState().setValue(BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.LOWER));
                level.setBlockAndUpdate(pos.above(), Blocks.LILAC.defaultBlockState().setValue(BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.UPPER));
            }
        }
        if (blockstate.is(Blocks.ALLIUM)) {
            if (aboveState.isAir()) {
                level.setBlockAndUpdate(pos, Blocks.PEONY.defaultBlockState().setValue(BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.LOWER));
                level.setBlockAndUpdate(pos.above(), Blocks.PEONY.defaultBlockState().setValue(BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.UPPER));
            }
        }
        if (blockstate.is(Blocks.AZURE_BLUET)) {
            level.setBlockAndUpdate(pos, Blocks.OXEYE_DAISY.defaultBlockState());
        }
        if (blockstate.is(Blocks.NETHER_SPROUTS)) {
            level.setBlockAndUpdate(pos, Blocks.WARPED_ROOTS.defaultBlockState());
        }
        if (blockstate.is(Blocks.HANGING_ROOTS)) {
            if (aboveState.is(BlockTags.DIRT) && aboveState != Blocks.ROOTED_DIRT.defaultBlockState()) {
                if (aboveState == Blocks.MUD.defaultBlockState())
                    level.setBlockAndUpdate(pos.above(), Blocks.MUDDY_MANGROVE_ROOTS.defaultBlockState());
                else
                    level.setBlockAndUpdate(pos.above(), Blocks.ROOTED_DIRT.defaultBlockState());
            }
        }
        if (blockstate.is(Blocks.GLOW_LICHEN)) {
            if (blockstate.hasProperty(BlockStateProperties.WATERLOGGED) && blockstate.getValue(BlockStateProperties.WATERLOGGED)) {
                level.setBlockAndUpdate(pos, Blocks.WATER.defaultBlockState());
            }
            level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
        }
    }
    
    
    private static void addGrowthParticles(ServerLevel level, BlockPos pos, ServerPlayer player) {
        var random = level.random;
        int numParticles = 2;
        
        BlockState blockstate = level.getBlockState(pos);
        if (!blockstate.isAir()) {
            double d0 = 0.5D; // Gaz what was this for?
            double d1;
            if (blockstate.is(Blocks.WATER)) {
                numParticles *= 3;
                d1 = 1.0D;
                d0 = 3.0D;
            } else if (blockstate.isSolidRender(level, pos)) {
                pos = pos.above();
                numParticles *= 3;
                d0 = 3.0D;
                d1 = 1.0D;
            } else {
                d1 = blockstate.getShape(level, pos).max(Direction.Axis.Y);
            }
            
            var randomPartialCount = random.nextInt(1, numParticles);
            
            // Something can cause this to mutate
            BlockPos immutablePos = pos.immutable();
            for (int i = 0; i < randomPartialCount; ++i) {
                double d2 = random.nextGaussian() * 0.2D;
                double d3 = random.nextGaussian() * 0.2D;
                double d4 = random.nextGaussian() * 0.2D;
                
                var randomY = Mth.clamp(random.nextDouble(), 0.1, 0.5);
                
                // Randomly place a particle somewhere within the blocks x and z
                double x = immutablePos.getX() + Mth.clamp(random.nextDouble(), -1D, 1D);
                double y = (immutablePos.getY() - .95D) + (d1 + randomY);
                double z = immutablePos.getZ() + Mth.clamp(random.nextDouble(), -1D, 1D);
                
                BlockState state = level.getBlockState(immutablePos);
                if (!state.isAir()) {
                    level.sendParticles(player, ParticleTypes.HAPPY_VILLAGER, false, x, y, z, numParticles, d2, d3, d4, 0.5);
                }
            }
            
            level.playSound(null, immutablePos, SoundEvents.BONE_MEAL_USE, SoundSource.MASTER, 0.5F, 1.0F);
        }
    }
}
