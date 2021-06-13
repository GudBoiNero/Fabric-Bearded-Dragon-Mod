package com.gudboinero.coolmod.registry;

import net.minecraft.block.*;
import net.minecraft.entity.ai.goal.MoveToTargetPosGoal;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

public class SitOnBlockGoal extends MoveToTargetPosGoal {
    public static World WORLD ;

    private final com.gudboinero.coolmod.registry.CubeEntity cube;

    public SitOnBlockGoal(CubeEntity cube, double speed) {
        super(cube, speed, 20);
        this.cube = cube;
    }



    @Override
    protected boolean isTargetPos(WorldView world, BlockPos pos) {
        if (!world.getBlockState(pos).getBlock().isIn(BlockTags.WOOL)) {
            return false;
        } else if (!world.isAir(pos.up())) {
            return false;
        } else if (((World) world).isDay()){
            return false;
        } else {
            BlockState blockState = world.getBlockState(pos);
            if (blockState.isOf(Blocks.WHITE_WOOL)) {
                return true;
            } else if (blockState.isOf(Blocks.BLACK_WOOL)){
                return true;
            } else {
                return false;
            }
        }
    }
}
