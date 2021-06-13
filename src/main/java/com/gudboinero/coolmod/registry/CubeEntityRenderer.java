package com.gudboinero.coolmod.registry;

import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class CubeEntityRenderer extends MobEntityRenderer<CubeEntity, CubeEntityModel> {

    public CubeEntityRenderer(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher, new CubeEntityModel(), 0.8f);
    }

    @Override
    public Identifier getTexture(CubeEntity entity) {
        return new Identifier("coolmod", "textures/entity/cube/cube_entity.png");
    }
}