package net.kuwulkid94.kuwulkid.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.kuwulkid94.kuwulkid.JustaFantasyAddon;
import net.kuwulkid94.kuwulkid.entity.custom.*;
//import net.kuwulkid94.kuwulkid.entity.projectile.ThrownOilJarEntity;
//import net.kuwulkid94.kuwulkid.entity.projectile.ThrownScorpionBagEntity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModEntities {
    public static final EntityType<CrowEntity> CROW = Registry.register(
            Registry.ENTITY_TYPE, new Identifier(JustaFantasyAddon.MOD_ID, "crow"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, CrowEntity::new)
                    .dimensions(EntityDimensions.fixed(0.5f,1.00f)).build());


    public static final EntityType<ThornEntity> THORN = Registry.register(
            Registry.ENTITY_TYPE, new Identifier(JustaFantasyAddon.MOD_ID, "thorn"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, ThornEntity::new)
                    .dimensions(EntityDimensions.fixed(0.75f,0.40f)).build());

    //public static final EntityType<ScorpionEntity> SCORPION = Registry.register(
            //Registry.ENTITY_TYPE, new Identifier(JustaFantasyAddon.MOD_ID, "scorpion"),
            //FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, ScorpionEntity::new)
                    //.dimensions(EntityDimensions.fixed(0.5f,0.50f)).build());

    public static final EntityType<ShrimpEntity> SHRIMP = Registry.register(
            Registry.ENTITY_TYPE, new Identifier(JustaFantasyAddon.MOD_ID, "shrimp"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, ShrimpEntity::new)
                    .dimensions(EntityDimensions.fixed(0.5f,0.50f)).build());

    public static final EntityType<NautilusEntity> NAUTILUS = Registry.register(
            Registry.ENTITY_TYPE, new Identifier(JustaFantasyAddon.MOD_ID, "nautilus"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, NautilusEntity::new)
                    .dimensions(EntityDimensions.fixed(0.55f,0.55f)).build());

    public static final EntityType<ShamanEntity> SHAMAN = Registry.register(
            Registry.ENTITY_TYPE, new Identifier(JustaFantasyAddon.MOD_ID, "shaman"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, ShamanEntity::new)
                    .dimensions(EntityDimensions.fixed(0.6f,1.95f)).build());

    public static final EntityType<TaintedEndermanEntity> TAINTED_ENDERMAN = Registry.register(
            Registry.ENTITY_TYPE, new Identifier(JustaFantasyAddon.MOD_ID, "tainted_enderman"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, TaintedEndermanEntity::new)
                    .dimensions(EntityDimensions.fixed(0.6f,3f)).build());

    /*public static final EntityType<ThrownOilJarEntity> OIL_JAR_ENTITY = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(JustaFantasyAddon.MOD_ID, "oil_jar"),
            FabricEntityTypeBuilder.<ThrownOilJarEntity>create(SpawnGroup.MISC, ThrownOilJarEntity::new)
                    .dimensions(EntityDimensions.fixed(0.25F, 0.25F)) // dimensions in Minecraft units of the projectile
                    .trackRangeBlocks(4).trackedUpdateRate(10)
                    .build()
    ); */

    //public static final EntityType<ThrownScorpionBagEntity> SCORPION_BAG_ENTITY = Registry.register(
            //Registry.ENTITY_TYPE,
            //new Identifier(JustaFantasyAddon.MOD_ID, "scorpion_bag"),
            //FabricEntityTypeBuilder.<ThrownScorpionBagEntity>create(SpawnGroup.MISC, ThrownScorpionBagEntity::new)
                    //.dimensions(EntityDimensions.fixed(0.25F, 0.25F)) // dimensions in Minecraft units of the projectile
                    //.trackRangeBlocks(4).trackedUpdateRate(10) // necessary for all thrown projectiles (as it prevents it from breaking, lol)
                   // .build()
    //); // VERY IMPORTANT DONT DELETE FOR THE LOVE OF GOD PSLSSSSSS
    //actually i dont think this does anything lol...
    //tysm fabric modding wiki <3
}
