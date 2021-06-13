package com.gudboinero.coolmod.registry;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ai.goal.MoveToTargetPosGoal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.apache.http.cookie.SM;

public class SunbatheGoal extends MoveToTargetPosGoal {
    public static World WORLD;
    public static int sunMeter;

    private final com.gudboinero.coolmod.registry.BeardedDragonEntity bearded_dragon;

    public SunbatheGoal(BeardedDragonEntity bearded_dragon, double speed, int SM) {
        super(bearded_dragon, speed, 40);
        this.bearded_dragon = bearded_dragon;
        sunMeter = SM;
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
        } else if ((sunMeter) == 0) {
            return false;
        }else {
            BlockState blockState = world.getBlockState(pos);
            if (blockState.isOf(Blocks.WHITE_WOOL)
                    || blockState.isOf(Blocks.BLACK_WOOL)
                    || blockState.isOf(Blocks.BLUE_WOOL)
                    || blockState.isOf(Blocks.BROWN_WOOL)
                    || blockState.isOf(Blocks.CYAN_WOOL)
                    || blockState.isOf(Blocks.GRAY_WOOL)
                    || blockState.isOf(Blocks.GREEN_WOOL)
                    || blockState.isOf(Blocks.LIGHT_BLUE_WOOL)
                    || blockState.isOf(Blocks.LIGHT_GRAY_WOOL)
                    || blockState.isOf(Blocks.LIME_WOOL)
                    || blockState.isOf(Blocks.MAGENTA_WOOL)
                    || blockState.isOf(Blocks.ORANGE_WOOL)
                    || blockState.isOf(Blocks.PINK_WOOL)
                    || blockState.isOf(Blocks.PURPLE_WOOL)
                    || blockState.isOf(Blocks.RED_WOOL)
                    || blockState.isOf(Blocks.YELLOW_WOOL) )
            {
                return true;
            } else {
                return false;
            }
        }
    }
}