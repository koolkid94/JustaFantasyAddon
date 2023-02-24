//hi
package net.kuwulkid94.kuwulkid;
//hi
//ZERATUL43 WAS HERE
import net.fabricmc.api.ModInitializer;
import net.kuwulkid94.kuwulkid.blocks.ModBlocks;
import net.kuwulkid94.kuwulkid.blocks.entity.ModBlockEntities;
import net.kuwulkid94.kuwulkid.effect.ModEffects;
import net.kuwulkid94.kuwulkid.item.ModItems;
import net.kuwulkid94.kuwulkid.networking.ModMessages;
import net.kuwulkid94.kuwulkid.potion.ModPotions;
import net.kuwulkid94.kuwulkid.screen.ModScreenHandler;
import net.kuwulkid94.kuwulkid.sounds.ModSounds;
import net.kuwulkid94.kuwulkid.util.ModRegistries;
import net.kuwulkid94.kuwulkid.world.gen.ModWorldGen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.bernie.geckolib3.GeckoLib;

public class JustaFantasyAddon implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger("modid");
	public static final String MOD_ID = "kuwulkid";

	@Override
	public void onInitialize() {
		ModWorldGen.generateModWorldGen();
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Hello Fabric world!");
		ModItems.registerModItems();
		ModBlocks.registerModBlocks();
		ModBlockEntities.registerAllBlockEntities();
		ModEffects.registerEffects();
		ModRegistries.registerModStuffs();
		ModSounds.registerSounds();
		GeckoLib.initialize();
		ModPotions.registerPotions();
		ModScreenHandler.registerALlScreenHandlers();
		ModMessages.registerS2CPackets();
	}
}
