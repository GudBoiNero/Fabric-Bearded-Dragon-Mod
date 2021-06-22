package com.gudboinero.coolmod.registry;


import com.gudboinero.coolmod.CoolMod;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.block.*;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModBlocks {

    //BASALT = register("basalt", new PillarBlock(AbstractBlock.Settings.of(Material.STONE, MaterialColor.BLACK).requiresTool().strength(1.25F, 4.2F).sounds(BlockSoundGroup.BASALT)));

    public static final Block DRAGON_SCALE_BLOCK = new Block(FabricBlockSettings
            .of(Material.METAL)
            .breakByTool(FabricToolTags.PICKAXES, 1)
            .requiresTool()
            .strength(1.25F, 4.2F)
            .sounds(BlockSoundGroup.ANCIENT_DEBRIS));

    public static final Block AQUAMARINE_BLOCK = new Block(FabricBlockSettings
            .of(Material.METAL)
            .breakByTool(FabricToolTags.PICKAXES, 2)
            .requiresTool()
            .strength(5.0f, 30.0f)
            .sounds(BlockSoundGroup.METAL));

    public static final Block PRICKLY_PEAR_CACTUS = new PricklyPearCactus(FabricBlockSettings
            .of(Material.CACTUS)
            .resistance(0.4f)
            .hardness(0.4f)
            .sounds(BlockSoundGroup.WOOL)
            .ticksRandomly());


    public static void registerBlocks() {
        Registry.register(Registry.BLOCK, new Identifier(CoolMod.MOD_ID, "aquamarine_block"), AQUAMARINE_BLOCK);
        // Miscellaneous
        Registry.register(Registry.BLOCK, new Identifier(CoolMod.MOD_ID, "prickly_pear_cactus"), PRICKLY_PEAR_CACTUS);
        // Dragon Scale Blocks
        Registry.register(Registry.BLOCK, new Identifier(CoolMod.MOD_ID, "dragon_scale_block"), DRAGON_SCALE_BLOCK);
    }
}
