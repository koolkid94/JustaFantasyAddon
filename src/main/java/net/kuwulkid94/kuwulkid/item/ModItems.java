package net.kuwulkid94.kuwulkid.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.kuwulkid94.kuwulkid.JustaFantasyAddon;
import net.kuwulkid94.kuwulkid.entity.ModEntities;
import net.kuwulkid94.kuwulkid.item.custom.*;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.*;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModItems {

    public static final Item CROW_FEATHER = registerItem("crow_feather",
            new Item(new FabricItemSettings().group(ItemGroup.MISC)));
    public static final Item SPARK_POWDER = registerItem("spark_powder",
            new Item(new FabricItemSettings().group(ItemGroup.MISC)));
    public static final Item OIL = registerItem("oil",
            new Item(new FabricItemSettings().group(ItemGroup.MISC)));

    public static final Item OIL_RIG = registerItem("oil_rig",
            new Item(new FabricItemSettings().group(ItemGroup.MISC)));
    public static final Item SCORPION_STINGER = registerItem("scorpion_stinger",
            new Item(new FabricItemSettings().group(ItemGroup.MISC)));

    public static final Item ABYSSAL_STONE = registerItem("abyssal_stone",
            new Item(new FabricItemSettings().group(ItemGroup.MISC)));
    public static final Item CACTUS_FRUIT = registerItem("cactus_fruit",
            new Item(new FabricItemSettings().group(ItemGroup.FOOD).food(ModFoodComponents.CACTUS_FRUIT)));

    public static final Item SHRIMP_TAIL = registerItem("shrimp_tail",
            new Item(new FabricItemSettings().group(ItemGroup.FOOD).food(ModFoodComponents.SHRIMP_TAIL)));

    public static final Item COOKED_SHRIMP_TAIL = registerItem("cooked_shrimp_tail",
            new Item(new FabricItemSettings().group(ItemGroup.FOOD).food(ModFoodComponents.COOKED_SHRIMP_TAIL)));

    public static final Item SHRIMP_FRIED_RICE = registerItem("shrimp_fried_rice",
            new StewItem(new FabricItemSettings().group(ItemGroup.FOOD).maxCount(1).food(ModFoodComponents.SHRIMP_FRIED_RICE)));

    public static final Item CROW_SPAWN_EGG = registerItem("crow_spawn_egg",
            new SpawnEggItem(ModEntities.CROW,0x958e8d, 0x3b3635,
                    new FabricItemSettings().group(ItemGroup.MISC).maxCount(64)));

    public static final Item SCORPION_SPAWN_EGG = registerItem("scorpion_spawn_egg",
            new SpawnEggItem(ModEntities.SCORPION,0x444444, 0x8e7911,
                    new FabricItemSettings().group(ItemGroup.MISC).maxCount(64)));

    public static final Item SHRIMP_SPAWN_EGG = registerItem("shrimp_spawn_egg",
            new SpawnEggItem(ModEntities.SHRIMP,0xff9ab8, 0xffbd9a,
                    new FabricItemSettings().group(ItemGroup.MISC).maxCount(64)));

    public static final Item SHAMAN_SPAWN_EGG = registerItem("shaman_spawn_egg",
            new SpawnEggItem(ModEntities.SHAMAN,0x31badc, 0xdc319a,
                    new FabricItemSettings().group(ItemGroup.MISC).maxCount(64)));

    public static final Item TAINTED_ENDERMAN_SPAWN_EGG = registerItem("tainted_enderman_spawn_egg",
            new SpawnEggItem(ModEntities.TAINTED_ENDERMAN,0x000000, 0x2d6087,
                    new FabricItemSettings().group(ItemGroup.MISC).maxCount(64)));

    public static final Item NAUTILUS_SPAWN_EGG = registerItem("nautilus_spawn_egg",
            new SpawnEggItem(ModEntities.NAUTILUS,0xf4dec2, 0xd8692e,
                    new FabricItemSettings().group(ItemGroup.MISC).maxCount(64)));
    public static final Item SKULL_MASK = registerItem("skull_mask",
            new BoneArmorItem(ModArmorMaterials.vanguard, EquipmentSlot.HEAD,
                    new FabricItemSettings().group(ItemGroup.COMBAT)));
    public static final Item VANGUARD_HELMET = registerItem("vanguard_helmet",
            new VanguardArmorItem(ModArmorMaterials.vanguard, EquipmentSlot.HEAD,
                    new FabricItemSettings().group(ItemGroup.COMBAT)));
    public static final Item VANGUARD_CHESTPLATE = registerItem("vanguard_chestplate",
            new VanguardArmorItem(ModArmorMaterials.vanguard, EquipmentSlot.CHEST,
                    new FabricItemSettings().group(ItemGroup.COMBAT)));
    public static final Item VANGUARD_LEGGINGS = registerItem("vanguard_leggings",
            new VanguardArmorItem(ModArmorMaterials.vanguard, EquipmentSlot.LEGS,
                    new FabricItemSettings().group(ItemGroup.COMBAT)));
    public static final Item VANGUARD_BOOTS = registerItem("vanguard_boots",
            new VanguardArmorItem(ModArmorMaterials.vanguard, EquipmentSlot.FEET,
                    new FabricItemSettings().group(ItemGroup.COMBAT)));
    public static final Item NECKLACE = registerItem("necklace",
            new NecklaceArmorItem(ModArmorMaterials.abyssal, EquipmentSlot.CHEST,
                    new FabricItemSettings().group(ItemGroup.COMBAT)));

    public static final Item PIKE = registerItem("pike", new PikeItem(ModToolMaterials.PRIMITIVE, 2, -3f, new FabricItemSettings().group(ItemGroup.COMBAT)));

    public static final Item SCORPION_BAG = registerItem("scorpion_bag", new ScorpionBagItem(new FabricItemSettings().group(ItemGroup.COMBAT).maxCount(16)));

    public static final Item GUARDIAN_LASER = registerItem("guardian_laser", new GuardianLaserItem(new FabricItemSettings().group(ItemGroup.COMBAT).maxCount(1)));

    public static final Item OIL_JAR = registerItem("oil_jar", new OilJarItem(new FabricItemSettings().group(ItemGroup.COMBAT).maxCount(16)));
    public static final Item NAUTILUS_BUCKET = registerItem("nautilus_bucket", new EntityBucketItem(ModEntities.NAUTILUS, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_FISH, new FabricItemSettings().group(ItemGroup.MISC).maxCount(1)));

    public static final Item SCYTHE = registerItem("scythe", new ScytheItem(ModToolMaterials.MYSTIC, 6, -3.1f, new FabricItemSettings().group(ItemGroup.COMBAT)));

    //public static final Item HAMMER = registerItem("hammer", new HammerItem(ModToolMaterials.METALLIC, 8, -3.3f, new FabricItemSettings()));

    public static final Item SOUL_DAGGER = registerItem("soul_dagger", new SoulDaggerItem(ToolMaterials.IRON, 4, -2.5f, new FabricItemSettings().group(ItemGroup.COMBAT)));

    public static final Item VORPAL_GEM = registerItem("vorpal_gem", new VorpalGemItem(ModToolMaterials.AMETHYST, 5, -2.4f, new FabricItemSettings().group(ItemGroup.COMBAT)));

    public static final Item SCORPION_FANG = registerItem("scorpion_fang", new ScorpionFangItem(ModToolMaterials.PRIMITIVE, 3, -2.5f, new FabricItemSettings().group(ItemGroup.COMBAT)));

    //public static final Item SPIKED_SHIELD = registerItem("spiked_shield", new SpikedShield(ModToolMaterials.PRIMITIVE, new FabricItemSettings()));

    public static final Item GLAIVE = registerItem("glaive", new GlaiveItem(ModToolMaterials.PRIMITIVE, 6, -2.5f, new FabricItemSettings().group(ItemGroup.COMBAT)));

    public static final Item BLAZE_CUTLASS = registerItem("blaze_cutlass", new BlazeCutlassItem(ToolMaterials.GOLD, 3, -2.4f, new FabricItemSettings().group(ItemGroup.COMBAT)));

    public static final Item BOUNTIFUL_SICKLE = registerItem("bountiful_sickle", new SickleItem(ToolMaterials.GOLD, 2, -2.5f, new FabricItemSettings().group(ItemGroup.COMBAT)));

    public static final Item HEAVY_AXE = registerItem("heavy_axe", new HeavyWeaponItem(ToolMaterials.IRON, 7, -3.3f, new FabricItemSettings().group(ItemGroup.COMBAT)));

    //public static final Item BATTLESTAFF = registerItem("staff_battlestaff", new BladeItem(ModToolMaterials.PRIMITIVE, 8, -3.3f, new FabricItemSettings().group(ItemGroup.COMBAT)));

    public static final Item SUNKEN_ANCHOR = registerItem("sunken_anchor", new HeavyWeaponItem(ToolMaterials.IRON, 8, -3.3f, new FabricItemSettings().group(ItemGroup.COMBAT)));

    public static final Item CORAL_BLADE = registerItem("sword_coral_blade", new BladeItem(ModToolMaterials.CORAL, 3, -2.5f, new FabricItemSettings().group(ItemGroup.COMBAT)));

    public static final Item DANCERS_SWORD = registerItem("sword_dancers_sword", new HeavyWeaponItem(ToolMaterials.IRON, 5, -3f, new FabricItemSettings().group(ItemGroup.COMBAT)));

    public static final Item RAPIER = registerItem("sword_rapier", new RapierItem(ToolMaterials.IRON, 0, -1f, new FabricItemSettings().group(ItemGroup.COMBAT)));

    public static final Item TAINTED_HALBERD = registerItem("tainted_halberd", new TaintedWeaponItem(ModToolMaterials.TAINTED, 7, -3.4f, new FabricItemSettings().group(ItemGroup.COMBAT)));


    private static Item registerItem(String name, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(JustaFantasyAddon.MOD_ID, name), item);
    }

    public static void registerModItems() {
        JustaFantasyAddon.LOGGER.info("Registering Mod Items for " + JustaFantasyAddon.MOD_ID);
    }
}
