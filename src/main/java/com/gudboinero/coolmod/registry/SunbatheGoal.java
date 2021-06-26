package com.gudboinero.coolmod.registry;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ai.goal.MoveToTargetPosGoal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Position;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.apache.http.cookie.SM;

public class SunbatheGoal extends MoveToTargetPosGoal {
    public static World WORLD;
    public static int sunMeter;

    private final com.gudboinero.coolmod.registry.BeardedDragonEntity bearded_dragon;

    public SunbatheGoal(BeardedDragonEntity bearded_dragon, double speed, int sun_meter) {
        super(bearded_dragon, speed, 40);
        this.bearded_dragon = bearded_dragon;
        sunMeter = sun_meter;
    }


    @Override
    protected boolean isTargetPos(WorldView world, BlockPos pos) {
        if (!world.getBlockState(pos).getBlock().isIn(BlockTags.WOOL)) {
            return false;
        } else if (!world.isAir(pos.up())) {
            return false;
        } else if (!((World) world).isDay()) {
            return false;
        } else if (!(world).isSkyVisible(pos.up())) {
            return false;
        } /*else if (BlockPos.iterate(BlockPos, BlockPos)) {
            return false;
        }*/ else {
            BlockState blockState = world.getBlockState(pos);
            if (blockState.isOf((Block) BlockTags.WOOL)) {
                return true;
            } else {
                return false;
            }
        }
    }
}