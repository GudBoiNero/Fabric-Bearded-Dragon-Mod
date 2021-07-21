package com.gudboinero.coolmod.registry;

import com.google.common.collect.Sets;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.TameableShoulderEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
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

import java.util.Set;

public class BeardedDragonEntity extends TameableShoulderEntity implements IAnimatable {
    private final AnimationFactory factory = new AnimationFactory(this);
    public static final TrackedData<Integer> COLOR;
    public static final TrackedData<Integer> SUN_METER;
    private static final Set<Item> TAMING_INGREDIENTS;
    public int NUM_TYPES = 3; // Number of Bearded Dragon Types
    public int sunAbsorbTime;


    public BeardedDragonEntity(EntityType<? extends TameableShoulderEntity> type, World worldIn) {
        super(type, worldIn);
        this.ignoreCameraFrustum = true;
        this.sunAbsorbTime = this.random.nextInt(600) + 600;
    }

    @Nullable
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable CompoundTag entityTag) {
        this.setVariant(this.random.nextInt(NUM_TYPES));
        if (entityData == null) {
            entityData = new PassiveEntity.PassiveData(false);
        }

        return super.initialize(world, difficulty, spawnReason, entityData, entityTag);
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }

    protected void initGoals() {
        //this.goalSelector.add(2, new SunbatheGoal(this, .7F, SUN_METER));

        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new EscapeDangerGoal(this, 0.8D));
        this.goalSelector.add(2, new SitOnOwnerShoulderGoal(this));
        this.goalSelector.add(2, new FollowOwnerGoal(this, 0.5D, 5.0F, 1.0F, true));
        this.goalSelector.add(3, new WanderAroundFarGoal(this, 0.45D));
        this.goalSelector.add(3, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
    }

    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(COLOR, 0);
        this.dataTracker.startTracking(SUN_METER, 0);
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

    public void tick() {
        super.tick();
        if (!this.world.isClient && this.isAlive() && --this.sunAbsorbTime <= 0) {
            if (this.world.isSkyVisible(this.getBlockPos().up()) && this.world.isDay()) {
                this.addSunMeter(1);
            } else {
                this.addSunMeter(-1);
            }
            this.sunAbsorbTime = this.random.nextInt(600) + 600;
        }
    }

    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (!this.isTamed() && TAMING_INGREDIENTS.contains(itemStack.getItem())) {
            if (!player.abilities.creativeMode) {
                itemStack.decrement(1);
            }

            if (!this.world.isClient) {
                if (this.random.nextInt(10) == 0) {
                    this.setOwner(player);
                    this.world.sendEntityStatus(this, (byte) 7);
                } else {
                    this.world.sendEntityStatus(this, (byte) 6);
                }
            }
            return ActionResult.success(this.world.isClient);
        }  else {
            return super.interactMob(player, hand);
        }
    }

    public int getVariant() {
        return MathHelper.clamp(this.dataTracker.get(COLOR), 0, 2);
    }
    public void setVariant(int variant) {
        this.dataTracker.set(COLOR, variant);
    }
    public int getSunMeter() {
        return MathHelper.clamp(this.dataTracker.get(SUN_METER), 0, 10);
    }
    public void setSunMeter(int sunMeter) {
        this.dataTracker.set(SUN_METER, sunMeter);
    }
    public void addSunMeter(int sunMeter) { int sunM = (getSunMeter()+sunMeter); this.dataTracker.set(SUN_METER, sunM);}

    public int getTintedColor() {
        int color = getVariant();
        int sunMeter = getSunMeter();
        int tint;
        int result;

        if (sunMeter >= 0 && sunMeter <= 3){
            tint = 0;
        } else if (sunMeter >= 4 && sunMeter <= 6){
            tint = 1;
        } else {
            tint = 2;
        }
        if (tint == 0){
            result = color + 6;
        } else if (tint == 1) {
            result = color;
        } else {
            result = color + 3;
        }
        return result;
    }

    @Override
    public void writeCustomDataToTag(CompoundTag tag) {
        super.writeCustomDataToTag(tag);
        tag.putInt("Variant", this.getVariant());
        tag.putInt("SunMeter", this.getSunMeter());
    }

    @Override
    public void readCustomDataFromTag(CompoundTag tag) {
        super.readCustomDataFromTag(tag);
        this.setVariant(tag.getInt("Variant"));
        this.setSunMeter(tag.getInt("SunMeter"));
    }

    @Override public void registerControllers(AnimationData data) {data.addAnimationController(new AnimationController(this, "controller", 0, this::predicate)); }
    @Override public AnimationFactory getFactory() {return this.factory;}

    @Override
    public boolean isInvulnerableTo(DamageSource damageSource) {
        return damageSource == DamageSource.CACTUS || super.isInvulnerableTo(damageSource);
    }
    static {
        TAMING_INGREDIENTS = Sets.newHashSet((ModItems.PRICKLY_PEAR), (Items.SPIDER_EYE));
        COLOR = DataTracker.registerData(BeardedDragonEntity.class, TrackedDataHandlerRegistry.INTEGER);
        SUN_METER = DataTracker.registerData(BeardedDragonEntity.class, TrackedDataHandlerRegistry.INTEGER);
    }
}