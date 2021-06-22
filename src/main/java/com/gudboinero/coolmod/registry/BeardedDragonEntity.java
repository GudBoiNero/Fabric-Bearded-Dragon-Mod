package com.gudboinero.coolmod.registry;

import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.EscapeDangerGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class BeardedDragonEntity extends PathAwareEntity implements IAnimatable {
    private final AnimationFactory factory = new AnimationFactory(this);
    public static final TrackedData<Integer> COLOR;
    public int NUM_TYPES = 3; // Number of Bearded Dragon Types
    public boolean SLEEPING;
    public int dragonColor;


    public BeardedDragonEntity(EntityType<? extends PathAwareEntity> type, World worldIn) {
        super(type, worldIn);
        this.ignoreCameraFrustum = true;
    }

    @Nullable
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable CompoundTag entityTag) {
        this.setVariant(this.random.nextInt(NUM_TYPES));
        if (entityData == null) {
            entityData = new PassiveEntity.PassiveData(false);
        }

        return super.initialize(world, difficulty, spawnReason, (EntityData)entityData, entityTag);
    }

    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new EscapeDangerGoal(this, 0.8D));
        //this.goalSelector.add(2, new SunbatheGoal(this, .7F, SUN_METER));
        this.goalSelector.add(3, new WanderAroundFarGoal(this, 0.45D));
        this.goalSelector.add(3, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
    }

    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(COLOR, 0);
    }

    public boolean isInWater() {
        return !this.firstUpdate && this.fluidHeight.getDouble(FluidTags.WATER) > 0.0D;
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (this.isInWater()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.bearded_dragon.swim", true));
        } else if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.bearded_dragon.run", true));
        } else {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.bearded_dragon.idle", true));
        }
        return PlayState.CONTINUE;
    }

    public int getVariant() {
        return MathHelper.clamp(this.dataTracker.get(COLOR), 0, 2);
    }
    public void setVariant(int variant) {
        this.dataTracker.set(COLOR, variant);
    }

    @Override
    public void writeCustomDataToTag(CompoundTag tag) {
        super.writeCustomDataToTag(tag);
        tag.putInt("Variant", this.getVariant());
    }

    @Override
    public void readCustomDataFromTag(CompoundTag tag) {
        super.readCustomDataFromTag(tag);
        this.setVariant(tag.getInt("Variant"));
    }

    @Override public void registerControllers(AnimationData data) {data.addAnimationController(new AnimationController(this, "controller", 0, this::predicate)); }
    @Override public AnimationFactory getFactory() {return this.factory;}

    @Override
    public boolean isInvulnerableTo(DamageSource damageSource) {
        return damageSource == DamageSource.CACTUS || super.isInvulnerableTo(damageSource);
    }
    static {
        COLOR = DataTracker.registerData(BeardedDragonEntity.class, TrackedDataHandlerRegistry.INTEGER);
    }
}