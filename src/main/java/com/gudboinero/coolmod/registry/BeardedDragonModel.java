package com.gudboinero.coolmod.registry;

import com.google.common.collect.ImmutableList;
import com.gudboinero.coolmod.CoolMod;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.ParrotEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.passive.ParrotEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
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

    public static final Identifier[] TEX = {
            new Identifier(CoolMod.MOD_ID, "textures/models/bearded_dragon.png"),
            new Identifier(CoolMod.MOD_ID, "textures/models/blue_bearded_dragon.png"),
            new Identifier(CoolMod.MOD_ID, "textures/models/green_crested_bearded_dragon.png"),
            new Identifier(CoolMod.MOD_ID, "textures/models/bearded_dragon_bright.png"),
            new Identifier(CoolMod.MOD_ID, "textures/models/blue_bearded_dragon_bright.png"),
            new Identifier(CoolMod.MOD_ID, "textures/models/green_crested_bearded_dragon_bright.png"),
            new Identifier(CoolMod.MOD_ID, "textures/models/bearded_dragon_dark.png"),
            new Identifier(CoolMod.MOD_ID, "textures/models/blue_bearded_dragon_dark.png"),
            new Identifier(CoolMod.MOD_ID, "textures/models/green_crested_bearded_dragon_dark.png")};
    public BeardedDragonModel() {
    }



    @Override
    public Identifier getModelLocation(BeardedDragonEntity object) {
        return new Identifier(CoolMod.MOD_ID, "geo/bearded_dragon.geo.json");
    }
    @Override
    public Identifier getTextureLocation(BeardedDragonEntity object) {
        return TEX[object.getTintedColor()];
    }

    public Iterable<ModelPart> getParts() {
        IBone head = this.getAnimationProcessor().getBone("head");
        return ImmutableList.of();
    }

    public void poseOnShoulder(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float limbAngle, float limbDistance, float headYaw, float headPitch, int danceAngle) {
        this.getParts().forEach((modelPart) -> {
            modelPart.render(matrices, vertexConsumer, light, overlay);
        });
    }

    @Override
    public Identifier getAnimationFileLocation(BeardedDragonEntity animatable) {
        return new Identifier(CoolMod.MOD_ID, "animations/bearded_dragon.animation.json");
    }

    @Override public void registerControllers(AnimationData animationData) {}
    @Override public AnimationFactory getFactory() {return null;}

    @Override
    public void setLivingAnimations(BeardedDragonEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        IBone head = this.getAnimationProcessor().getBone("head");
        getBone("bb_main").setRotationY(89.5f);

        //System.out.println(BeardedDragonEntity.SUN_METER.getId());

        LivingEntity entityIn = (LivingEntity) entity;
        EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
        if (head != null) {
            head.setRotationZ(extraData.headPitch * ((float) Math.PI / 180f));
            head.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180f));
        }
    }
}