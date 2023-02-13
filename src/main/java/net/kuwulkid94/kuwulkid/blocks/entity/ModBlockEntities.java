package net.kuwulkid94.kuwulkid.blocks.entity;


import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.kuwulkid94.kuwulkid.JustaFantasyAddon;
import net.kuwulkid94.kuwulkid.blocks.ModBlocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModBlockEntities {
    public static BlockEntityType<TropicalFlowerEntity> TROPICAL_FLOWER_ENTITY;
    public static BlockEntityType<CrudeAltarBlockEntity> CRUDE_ALTAR;

    public static void registerAllBlockEntities() {
        TROPICAL_FLOWER_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE,
                new Identifier(JustaFantasyAddon.MOD_ID, "tropical_flower_entity"),
                FabricBlockEntityTypeBuilder.create(TropicalFlowerEntity::new,
                        ModBlocks.TROPICAL_FLOWER).build(null));
        CRUDE_ALTAR = Registry.register(Registry.BLOCK_ENTITY_TYPE,
                new Identifier(JustaFantasyAddon.MOD_ID, "crude_altar"),
                FabricBlockEntityTypeBuilder.create(CrudeAltarBlockEntity::new,
                        ModBlocks.CRUDE_ALTAR).build(null));
    }
}
