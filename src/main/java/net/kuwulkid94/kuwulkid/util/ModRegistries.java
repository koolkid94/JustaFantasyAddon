package net.kuwulkid94.kuwulkid.util;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.kuwulkid94.kuwulkid.JustaFantasyAddon;
import net.kuwulkid94.kuwulkid.entity.ModEntities;
import net.kuwulkid94.kuwulkid.entity.custom.*;
import net.kuwulkid94.kuwulkid.item.ModItems;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.VillagerProfession;

public class ModRegistries {
        public static void registerModStuffs() {
            registerAttributes();
            registerCustomTrades();
            registerFuels();
        }


        private static void registerAttributes() {
            FabricDefaultAttributeRegistry.register(ModEntities.CROW, CrowEntity.setAttributes());
            FabricDefaultAttributeRegistry.register(ModEntities.THORN, ThornEntity.setAttributes());
            //FabricDefaultAttributeRegistry.register(ModEntities.SCORPION, ScorpionEntity.setAttributes());
            //FabricDefaultAttributeRegistry.register(ModEntities.SHRIMP, ShrimpEntity.setAttributes());
            //FabricDefaultAttributeRegistry.register(ModEntities.NAUTILUS, NautilusEntity.setAttributes());
            FabricDefaultAttributeRegistry.register(ModEntities.SHAMAN, ShamanEntity.setAttributes());
            //FabricDefaultAttributeRegistry.register(ModEntities.TAINTED_ENDERMAN, TaintedEndermanEntity.setAttributes());

        }

        private static void registerCustomTrades(){

            TradeOfferHelper.registerVillagerOffers(VillagerProfession.ARMORER, 3,
                    factories -> {
                        factories.add((entity, random) -> new TradeOffer(
                                new ItemStack(Items.EMERALD, 12),
                                new ItemStack(ModItems.VANGUARD_BOOTS, 1),
                                1,7,0.08f));

                        factories.add((entity, random) -> new TradeOffer(
                                new ItemStack(Items.EMERALD, 13),
                                new ItemStack(ModItems.VANGUARD_LEGGINGS, 1),
                                1,7,0.08f));

                        factories.add((entity, random) -> new TradeOffer(
                                new ItemStack(Items.EMERALD, 21),
                                new ItemStack(ModItems.VANGUARD_CHESTPLATE, 1),
                                1,7,0.08f));

                        factories.add((entity, random) -> new TradeOffer(
                                new ItemStack(Items.EMERALD, 13),
                                new ItemStack(ModItems.VANGUARD_HELMET, 1),
                                1,7,0.08f));
            });
        }

        public static void registerFuels(){
            JustaFantasyAddon.LOGGER.info("Registering Fuels for " + JustaFantasyAddon.MOD_ID);
            FuelRegistry registry = FuelRegistry.INSTANCE;

            registry.add(ModItems.OIL, 1200);
            registry.add(ModItems.SPARK_POWDER, 1800);
        }
    }
