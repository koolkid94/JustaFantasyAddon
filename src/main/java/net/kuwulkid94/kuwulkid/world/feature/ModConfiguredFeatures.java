package net.kuwulkid94.kuwulkid.world.feature;

import net.kuwulkid94.kuwulkid.JustaFantasyAddon;
import net.kuwulkid94.kuwulkid.blocks.ModBlocks;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

public class ModConfiguredFeatures {

    public static final RegistryEntry<ConfiguredFeature<RandomPatchFeatureConfig, ?>> BEACH_FERN =
            ConfiguredFeatures.register("beach_fern", Feature.FLOWER,
                    ConfiguredFeatures.createRandomPatchFeatureConfig(32, PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK,
                            new SimpleBlockFeatureConfig(BlockStateProvider.of(ModBlocks.BEACH_FERN)))));

    public static final RegistryEntry<ConfiguredFeature<RandomPatchFeatureConfig, ?>> LUSH_BRUSH =
            ConfiguredFeatures.register("lush_brush", Feature.FLOWER,
                    ConfiguredFeatures.createRandomPatchFeatureConfig(32, PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK,
                            new SimpleBlockFeatureConfig(BlockStateProvider.of(ModBlocks.LUSH_BRUSH)))));



    public static void registerConfiguredFeatures() {
        System.out.println("Registering ModConfiguredFeatures for " + JustaFantasyAddon.MOD_ID);
    }
}