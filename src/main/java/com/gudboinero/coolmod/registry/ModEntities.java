package com.gudboinero.coolmod.registry;

import com.gudboinero.coolmod.CoolMod;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.fabricmc.loader.FabricLoader;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import software.bernie.example.registry.EntityRegistryBuilder;
import software.bernie.geckolib3.GeckoLib;

public class ModEntities {

    public static EntityType<?> BEARDED_DRAGON_ENTITY;


    public static final EntityType<CubeEntity> CUBE = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier("coolmod", "cube"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, CubeEntity::new).dimensions(EntityDimensions.fixed(0.75f, 0.75f)).build()
    );

    public static final EntityType<BeardedDragonEntity> BEARDED_DRAGON = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier("coolmod", "bearded_dragon"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, BeardedDragonEntity::new).dimensions(EntityDimensions.fixed(0.75f, 0.75f)).build()
    );

    public static <T extends Entity> EntityType<T> buildEntity(EntityType.EntityFactory<T> entity, Class<T> entityClass,
                                                               float width, float height) {
        if (FabricLoader.INSTANCE.isDevelopmentEnvironment()) {
            String name = entityClass.getSimpleName().toLowerCase();
            return EntityRegistryBuilder.<T>createBuilder(new Identifier(CoolMod.MOD_ID, name)).entity(entity)
                    .category(SpawnGroup.CREATURE).dimensions(EntityDimensions.changing(width, height)).build();



        }
        return null;
    }
}
