package net.kuwulkid94.kuwulkid.world.gen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.kuwulkid94.kuwulkid.entity.ModEntities;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.WaterCreatureEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.BiomeKeys;

public class ModEntitySpawn {
    public static void addEntitySpawn() {
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.PLAINS),
                SpawnGroup.CREATURE, ModEntities.CROW, 5, 2, 5);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.TAIGA),
                SpawnGroup.CREATURE, ModEntities.CROW, 1, 1, 3);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.FOREST),
                SpawnGroup.CREATURE, ModEntities.CROW, 3, 2, 4);

        SpawnRestriction.register(ModEntities.CROW, SpawnRestriction.Location.NO_RESTRICTIONS,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::isValidNaturalSpawn);

        // SpawnRestriction.register(ModEntities.CHOMPER, SpawnRestriction.Location.ON_GROUND,
        //                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, HostileEntity::canSpawnInDark);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.DESERT),
                SpawnGroup.CREATURE, ModEntities.SCORPION, 15,2,5);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.BADLANDS),
                SpawnGroup.CREATURE, ModEntities.SCORPION, 10,2,7);

        SpawnRestriction.register(ModEntities.SCORPION, SpawnRestriction.Location.ON_GROUND,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::isValidNaturalSpawn);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.OCEAN),
                SpawnGroup.WATER_CREATURE, ModEntities.SHRIMP, 3, 2, 3);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.BEACH),
                SpawnGroup.WATER_CREATURE, ModEntities.SHRIMP, 3, 2, 3);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.RIVER),
                SpawnGroup.WATER_CREATURE, ModEntities.SHRIMP, 1, 1, 3);

        SpawnRestriction.register(ModEntities.SHRIMP, SpawnRestriction.Location.IN_WATER,
                Heightmap.Type.OCEAN_FLOOR, WaterCreatureEntity::canSpawn);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.OCEAN),
                SpawnGroup.WATER_CREATURE, ModEntities.NAUTILUS, 2, 2, 3);

        SpawnRestriction.register(ModEntities.NAUTILUS, SpawnRestriction.Location.IN_WATER,
                Heightmap.Type.OCEAN_FLOOR, WaterCreatureEntity::canSpawn);

        SpawnRestriction.register(ModEntities.TAINTED_ENDERMAN, SpawnRestriction.Location.ON_GROUND,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, HostileEntity::canSpawnIgnoreLightLevel);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.END_HIGHLANDS),
                SpawnGroup.CREATURE, ModEntities.TAINTED_ENDERMAN, 3, 2, 4);

    }
}
