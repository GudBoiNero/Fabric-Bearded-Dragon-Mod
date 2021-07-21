package com.gudboinero.coolmod.registry;

import com.gudboinero.coolmod.CoolMod;
import com.gudboinero.coolmod.CoolModClient;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.util.Identifier;
import software.bernie.example.client.model.entity.ExampleEntityModel;
import software.bernie.example.client.model.entity.ReplacedCreeperModel;
import software.bernie.example.entity.ReplacedCreeperEntity;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.renderer.geo.GeoEntityRenderer;
import software.bernie.geckolib3.renderer.geo.GeoReplacedEntityRenderer;

import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BeardedDragonRenderer extends GeoEntityRenderer<BeardedDragonEntity>
{
    public static final Identifier[] TEXTURES = {
            new Identifier(CoolMod.MOD_ID, "textures/models/bearded_dragon.png"),
            new Identifier(CoolMod.MOD_ID, "textures/models/blue_bearded_dragon.png"),
            new Identifier(CoolMod.MOD_ID, "textures/models/green_crested_bearded_dragon.png"),
            new Identifier(CoolMod.MOD_ID, "textures/models/bearded_dragon_bright.png"),
            new Identifier(CoolMod.MOD_ID, "textures/models/blue_bearded_dragon_bright.png"),
            new Identifier(CoolMod.MOD_ID, "textures/models/green_crested_bearded_dragon_bright.png"),
            new Identifier(CoolMod.MOD_ID, "textures/models/bearded_dragon_dark.png"),
            new Identifier(CoolMod.MOD_ID, "textures/models/blue_bearded_dragon_dark.png"),
            new Identifier(CoolMod.MOD_ID, "textures/models/green_crested_bearded_dragon_dark.png")
    };

    public BeardedDragonRenderer(EntityRenderDispatcher renderManager)
    {
        super(renderManager, new BeardedDragonModel());
        this.shadowRadius = 0.1F; //change 0.7 to the desired shadow size.
    }
}
