package com.gudboinero.coolmod;

import com.gudboinero.coolmod.registry.*;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.toast.SystemToast;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import software.bernie.example.client.renderer.entity.ExampleGeoRenderer;
import software.bernie.example.client.renderer.entity.ReplacedCreeperRenderer;
import software.bernie.example.registry.EntityRegistry;

@Environment(EnvType.CLIENT)

public class CoolModClient implements ClientModInitializer {
    public static void registerBowPredicates(BlowgunItem blowgun) {
        FabricModelPredicateProviderRegistry.register(blowgun, new Identifier("pull"), (itemStack, clientWorld, livingEntity) -> {
            if (livingEntity == null) {
                return 0.0F;
            } else {
                return livingEntity.getActiveItem() != itemStack ? 0.0F : (float) (itemStack.getMaxUseTime() - livingEntity.getItemUseTimeLeft()) / blowgun.getMaxUseTime(itemStack);
            }
        });

        FabricModelPredicateProviderRegistry.register(blowgun, new Identifier("pulling"), (itemStack, clientWorld, livingEntity) -> {
            return livingEntity != null && livingEntity.isUsingItem() && livingEntity.getActiveItem() == itemStack ? 1.0F : 0.0F;
        });
    }
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.PRICKLY_PEAR_CACTUS, RenderLayer.getCutout());
        registerBowPredicates((BlowgunItem) ModItems.BLOWGUN);

        // Entities
        EntityRendererRegistry.INSTANCE.register(CoolMod.BEARDED_DRAGON,
                (entityRenderDispatcher, context) -> new BeardedDragonRenderer(entityRenderDispatcher));
    }
}