package com.gudboinero.coolmod.registry;

import com.google.common.collect.Maps;
import it.unimi.dsi.fastutil.Hash;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.shadowed.eliotlash.mclib.math.functions.classic.Mod;

import java.util.HashMap;
import java.util.Map;

public class CubeEntity extends PathAwareEntity {
    private static final int AGE = 1;
    public static final Map TEXTURES;
    public static boolean SLEEPING;

    public CubeEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
    }

    static {
        TEXTURES = Util.make(Maps.newHashMap(), (hashMap) -> {
            hashMap.put(0, new Identifier("textures/entity/cube/cube_entity.png"));
        });
    }


    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new EscapeDangerGoal(this, 0.6D));
        //this.goalSelector.add(2, new AnimalMateGoal(this, 1.0D));
        this.goalSelector.add(2, new WanderAroundFarGoal(this, 0.3D));
        this.goalSelector.add(3, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.add(4, new SitOnBlockGoal(this, 0.7));
    }


    @Override
    public boolean isInvulnerableTo(DamageSource damageSource) {
        return damageSource == DamageSource.CACTUS || super.isInvulnerableTo(damageSource);
    }
}