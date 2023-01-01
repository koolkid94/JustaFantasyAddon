package net.kuwulkid94.kuwulkid.world.feature;

import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.PlacedFeatures;
import net.minecraft.world.gen.placementmodifier.BiomePlacementModifier;
import net.minecraft.world.gen.placementmodifier.RarityFilterPlacementModifier;
import net.minecraft.world.gen.placementmodifier.SquarePlacementModifier;

public class ModPlacedFeatures {

    public static final RegistryEntry<PlacedFeature> BEACH_FERN_PLACED = PlacedFeatures.register("beach_fern_placed",
            ModConfiguredFeatures.BEACH_FERN, RarityFilterPlacementModifier.of(4), SquarePlacementModifier.of(),
            PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());

    public static final RegistryEntry<PlacedFeature> LUSH_BRUSH_PLACED = PlacedFeatures.register("lush_brush_placed",
            ModConfiguredFeatures.LUSH_BRUSH, RarityFilterPlacementModifier.of(4), SquarePlacementModifier.of(),
            PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());

    //public static final RegistryEntry<PlacedFeature> GEYSER_PLACED = PlacedFeatures.register("geyser_placed",
            //ModConfiguredFeatures.GEYSER, RarityFilterPlacementModifier.of(1), SquarePlacementModifier.of(),
            //PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());


}