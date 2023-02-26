package net.kuwulkid94.kuwulkid.blocks;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.kuwulkid94.kuwulkid.JustaFantasyAddon;
import net.kuwulkid94.kuwulkid.blocks.custom.*;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModBlocks {

    public static final Block SANDSTONE_FIRE_BOWL = registerBlock("sandstone_fire_bowl",
            new FireBowl(FabricBlockSettings.of(Material.STONE).luminance(3).strength(0.5f).requiresTool().nonOpaque().ticksRandomly()), ItemGroup.BUILDING_BLOCKS);

    public static final Block RED_SANDSTONE_FIRE_BOWL = registerBlock("red_sandstone_fire_bowl",
            new FireBowl(FabricBlockSettings.of(Material.STONE).luminance(3).strength(0.5f).requiresTool().nonOpaque().ticksRandomly()), ItemGroup.BUILDING_BLOCKS);

    public static final Block STONE_FIRE_BOWL = registerBlock("stone_fire_bowl",
            new FireBowl(FabricBlockSettings.of(Material.STONE).luminance(3).strength(0.75f).requiresTool().nonOpaque().ticksRandomly()), ItemGroup.BUILDING_BLOCKS);

    public static final Block BEACH_FERN = registerBlock("beach_fern",
            new BeachFern(FabricBlockSettings.of(Material.PLANT).nonOpaque().sounds(BlockSoundGroup.GRASS).collidable(false)), ItemGroup.DECORATIONS);

    public static final Block LUSH_BRUSH = registerBlock("lush_brush",
            new LushBrush(FabricBlockSettings.of(Material.PLANT).nonOpaque().sounds(BlockSoundGroup.GRASS).collidable(false)), ItemGroup.DECORATIONS);

    public static final Block GEYSER = registerBlock("geyser",
            new Geyser(FabricBlockSettings.of(Material.STONE).nonOpaque().luminance(3).strength(3.75f).requiresTool().sounds(BlockSoundGroup.STONE).collidable(true).ticksRandomly()), ItemGroup.BUILDING_BLOCKS);

    public static final Block TROPICAL_FLOWER = registerBlock("tropical_flower",
            new TropicalFlower(FabricBlockSettings.of(Material.PLANT).nonOpaque().sounds(BlockSoundGroup.GRASS).collidable(false)), ItemGroup.DECORATIONS);

    public static final Block CRUDE_ALTAR = registerBlock("crude_altar",
            new CrudeAltar(FabricBlockSettings.of(Material.STONE).strength(0.5f).requiresTool().nonOpaque().ticksRandomly()), ItemGroup.BUILDING_BLOCKS);

    private static Block registerBlockWithoutBlockItem(String name, Block block, ItemGroup group) {
        return Registry.register(Registry.BLOCK, new Identifier(JustaFantasyAddon.MOD_ID, name), block);
    }

    private static Block registerBlock(String name, Block block, ItemGroup group){
        registerBlockItem(name, block, group);
        return Registry.register(Registry.BLOCK, new Identifier(JustaFantasyAddon.MOD_ID, name), block);
    }

    private static Item registerBlockItem(String name, Block block, ItemGroup group) {
        return Registry.register(Registry.ITEM, new Identifier(JustaFantasyAddon.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings().group(group)));
    }

    public static void registerModBlocks(){
        JustaFantasyAddon.LOGGER.info("Registering ModBlocks for " + JustaFantasyAddon.MOD_ID);
    }
}
