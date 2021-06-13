package com.gudboinero.coolmod.registry;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.Random;

public class PricklyPearCactus extends PlantBlock implements Fertilizable {

    public static IntProperty AGE = IntProperty.of("age", 0, 3);


    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager) {
        stateManager.add(AGE);
    }

    public PricklyPearCactus(Settings settings) {
        super(settings);
        //setDefaultState(getStateManager().getDefaultState().with(PICKABLE, true));
        setDefaultState(getStateManager().getDefaultState().with(AGE, 1));
    }
    @Override
    public ActionResult  onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if(state.get(AGE).equals(3)) {
            world.setBlockState(pos, state.with(AGE, 2));
            dropStack(world, pos, new ItemStack(ModItems.PRICKLY_PEAR, 1));
            return ActionResult.SUCCESS;
        }
        else {return ActionResult.FAIL;}
    }
    @Override
        public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext context) {
        if(state.get(AGE).equals(0)) {
            return VoxelShapes.cuboid(0.0f, 0f, 0.0f, 0.0f, 0.0f, 0.0f);
        }else if(state.get(AGE).equals(1)) {
            return VoxelShapes.cuboid(0.6f, 0f, 0.6f, 0.4f, 0.3f, 0.4f);
        }else if(state.get(AGE).equals(2)) {
            return VoxelShapes.cuboid(0.8f, 0f, 0.8f, 0.2f, 1.0f, 0.2f);
        }else if(state.get(AGE).equals(3)) {
            return VoxelShapes.cuboid(0.8f, 0f, 0.8f, 0.2f, 1.0f, 0.2f);
        }
        return super.getOutlineShape(state, view, pos, context);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        super.randomTick(state, world, pos, random);
        int i = (Integer)state.get(AGE);
        if (i < 3){
            world.setBlockState(pos, (BlockState)state.with(AGE, i+1), 2);
        }

    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return getOutlineShape(state, world, pos, context);
    }
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        entity.damage(DamageSource.CACTUS, 1.0F);
    }



    @Override
    public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) {
        return true;
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return false;
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {

    }

    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return floor.getBlock() == Blocks.SAND;
    }
}