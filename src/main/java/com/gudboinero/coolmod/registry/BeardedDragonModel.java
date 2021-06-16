package com.gudboinero.coolmod.registry;

import com.gudboinero.coolmod.CoolMod;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

import java.awt.*;
import java.util.Random;

import static com.google.common.primitives.Ints.constrainToRange;

public class BeardedDragonModel extends AnimatedGeoModel<BeardedDragonEntity> implements IAnimatable {

    private static final Identifier[] TEX = {
            new Identifier(CoolMod.MOD_ID, "textures/models/bearded_dragon.png"),
            new Identifier(CoolMod.MOD_ID, "textures/models/blue_bearded_dragon.png")};
    public BeardedDragonModel() {
    }

    @Override
    public Identifier getModelLocation(BeardedDragonEntity object) {
        return new Identifier(CoolMod.MOD_ID, "geo/bearded_dragon.geo.json");
    }
    @Override
    public Identifier getTextureLocation(BeardedDragonEntity object) {
        return TEX[object.DRAGON_COLOR];
    }



    @Override
    public Identifier getAnimationFileLocation(BeardedDragonEntity animatable) {
        return new Identifier(CoolMod.MOD_ID, "animations/bearded_dragon.animation.json");
    }

    @Override
    public void registerControllers(AnimationData animationData) {

    }

    @Override
    public AnimationFactory getFactory() {
        return null;
    }

    @Override
    public void setLivingAnimations(BeardedDragonEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        IBone head = this.getAnimationProcessor().getBone("head");
        getBone("bb_main").setRotationY(89.5f);

        LivingEntity entityIn = (LivingEntity) entity;
        EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
        if (head != null) {
            head.setRotationZ(extraData.headPitch * ((float) Math.PI / 180f));
            head.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180f));
        }
    }

    public final Identifier assignColorByID(int id) {
        id = constrainToRange(id,0,TEX.length);
        return TEX[id];
    }
}
