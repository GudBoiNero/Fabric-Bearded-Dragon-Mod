package com.gudboinero.coolmod.registry;

import com.gudboinero.coolmod.CoolMod;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModItems {

    //Items
    public static final Item AQUAMARINE = new Item(new Item.Settings().group(ItemGroup.MATERIALS));
    public static final Item PYRITE = new Item(new Item.Settings().group(ItemGroup.MATERIALS));
    public static final Item PYRAQUERUNE = new Item(new Item.Settings().group(ItemGroup.MATERIALS));
    public static final Item PRICKLY_PEAR = new Item(new Item.Settings().group(ItemGroup.FOOD).food(new FoodComponent.Builder().hunger(3).saturationModifier(0.3f).build()));
    public static final Item PRICKLY_PEAR_SEEDS = new AliasedBlockItem(ModBlocks.PRICKLY_PEAR_CACTUS, new Item.Settings().group(ItemGroup.MATERIALS));
    public static final Item BLOWGUN = new BlowgunItem(ToolMaterials.IRON,
            new Item.Settings().group(ItemGroup.COMBAT).maxCount(1).maxDamage(350),3.0f);


    //Block Items
    public static final BlockItem AQUAMARINE_BLOCK = new BlockItem(ModBlocks.AQUAMARINE_BLOCK, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS));
    public static final BlockItem DRAGON_SCALE_BLOCK = new BlockItem(ModBlocks.DRAGON_SCALE_BLOCK, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS));


    public static void registerItems() {
        Registry.register(Registry.ITEM, new Identifier(CoolMod.MOD_ID, "prickly_pear"), PRICKLY_PEAR);
        Registry.register(Registry.ITEM, new Identifier(CoolMod.MOD_ID, "prickly_pear_seeds"), PRICKLY_PEAR_SEEDS);
        Registry.register(Registry.ITEM, new Identifier(CoolMod.MOD_ID, "blowgun"), BLOWGUN);
        Registry.register(Registry.ITEM, new Identifier(CoolMod.MOD_ID, "dragon_scale_block"), DRAGON_SCALE_BLOCK);
    }
}
