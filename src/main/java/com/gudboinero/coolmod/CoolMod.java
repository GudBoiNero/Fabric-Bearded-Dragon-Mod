package com.gudboinero.coolmod;

import com.gudboinero.coolmod.registry.*;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import software.bernie.geckolib3.GeckoLib;

import static net.minecraft.entity.mob.MobEntity.createMobAttributes;

public class CoolMod implements ModInitializer {
    public static final String MOD_ID = "coolmod";

    /*public static final EntityType<CubeEntity> CUBE = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier("coolmod", "cube"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, CubeEntity::new).dimensions(EntityDimensions.fixed(0.75f, 0.75f)).build()
    );*/

    public static final EntityType<BeardedDragonEntity> BEARDED_DRAGON = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier("coolmod", "bearded_dragon"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, BeardedDragonEntity::new).dimensions(EntityDimensions.fixed(0.5f, 0.2f)).build()
    );

    @Override
    public void onInitialize() {
        //Full Registers
        GeckoLib.initialize();
        ModItems.registerItems();
        ModBlocks.registerBlocks();

        //Attributes
        //FabricDefaultAttributeRegistry.register(CUBE, createMobAttributes());
        FabricDefaultAttributeRegistry.register(BEARDED_DRAGON, createMobAttributes());
    }
}

