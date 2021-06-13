package com.gudboinero.coolmod.registry;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.EscapeDangerGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.Random;

public class BeardedDragonEntity extends PathAwareEntity implements IAnimatable {
    private AnimationFactory factory = new AnimationFactory(this);
    public int SUN_METER;
    public boolean SLEEPING;
    public int COLOR = random.nextInt(2);
    //public Identifier color_ident = new BeardedDragonModel().assignColorByID(COLOR);

    public BeardedDragonEntity(EntityType<? extends PathAwareEntity> type, World worldIn) {
        super(type, worldIn);
        this.ignoreCameraFrustum = true;
    }



    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new EscapeDangerGoal(this, 0.8D));
        //this.goalSelector.add(2, new SunbatheGoal(this, .7F, SUN_METER));
        this.goalSelector.add(3, new WanderAroundFarGoal(this, 0.45D));
        this.goalSelector.add(3, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
    }


    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.bearded_dragon.run", true));
        } else {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.bearded_dragon.idle", true));
        }
        return PlayState.CONTINUE;
    }




    @Override
    public void readCustomDataFromTag(CompoundTag tag) {
        super.readCustomDataFromTag(tag);
        this.COLOR = tag.getInt("color");
    }

    @Override
    public void writeCustomDataToTag(CompoundTag tag) {
        super.readCustomDataFromTag(tag);
        tag.putInt("color", COLOR);
    }

    @Override
    public void registerControllers(AnimationData data)
    {
        data.addAnimationController(new AnimationController(this, "controller", 0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory()
    {
        return this.factory;
    }



    @Override
    public boolean isInvulnerableTo(DamageSource damageSource) {
        return damageSource == DamageSource.CACTUS || super.isInvulnerableTo(damageSource);
    }
}