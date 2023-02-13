package net.kuwulkid94.kuwulkid;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.kuwulkid94.kuwulkid.blocks.ModBlocks;
import net.kuwulkid94.kuwulkid.blocks.entity.ModBlockEntities;
import net.kuwulkid94.kuwulkid.blocks.entity.client.TropicalFlowerRenderer;
import net.kuwulkid94.kuwulkid.entity.ModEntities;
import net.kuwulkid94.kuwulkid.entity.client.*;
import net.kuwulkid94.kuwulkid.entity.client.armor.BoneArmorRenderer;
import net.kuwulkid94.kuwulkid.entity.client.armor.NecklaceArmorRenderer;
import net.kuwulkid94.kuwulkid.entity.client.armor.VanguardArmorRenderer;
import net.kuwulkid94.kuwulkid.item.ModItems;
import net.kuwulkid94.kuwulkid.screen.CrudeAltarScreen;
import net.kuwulkid94.kuwulkid.screen.ModScreenHandler;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

public class JustaFantasyAddonClientMod implements ClientModInitializer {
    public static final Identifier PacketID = new Identifier(JustaFantasyAddon.MOD_ID, "spawn_packet");

    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(ModEntities.CROW, CrowRenderer::new);
        EntityRendererRegistry.register(ModEntities.SCORPION, ScorpionRenderer::new);
        EntityRendererRegistry.register(ModEntities.SHRIMP, ShrimpRenderer::new);
        EntityRendererRegistry.register(ModEntities.NAUTILUS, NautilusRenderer::new);
        EntityRendererRegistry.register(ModEntities.SHAMAN, ShamanRenderer::new);
        EntityRendererRegistry.register(ModEntities.TAINTED_ENDERMAN, TaintedEndermanRenderer::new);
        //GeoItemRenderer.registerItemRenderer(ModItems.SPIKED_SHIELD, new SpikedShieldRenderer());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SANDSTONE_FIRE_BOWL, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.RED_SANDSTONE_FIRE_BOWL, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.STONE_FIRE_BOWL, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CRUDE_ALTAR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.BEACH_FERN, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.LUSH_BRUSH, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.GEYSER, RenderLayer.getCutout());
        HandledScreens.register(ModScreenHandler.CRUDE_ALTAR_SCREEN_HANDLER, CrudeAltarScreen::new);
        GeoArmorRenderer.registerArmorRenderer(new BoneArmorRenderer(), ModItems.SKULL_MASK);
        GeoArmorRenderer.registerArmorRenderer(new NecklaceArmorRenderer(), ModItems.NECKLACE);

        BlockEntityRendererRegistry.register(ModBlockEntities.TROPICAL_FLOWER_ENTITY, TropicalFlowerRenderer::new);

        GeoArmorRenderer.registerArmorRenderer(new VanguardArmorRenderer(), ModItems.VANGUARD_HELMET,ModItems.VANGUARD_CHESTPLATE, ModItems.VANGUARD_LEGGINGS,ModItems.VANGUARD_BOOTS);

        EntityRendererRegistry.register(ModEntities.SCORPION_BAG_ENTITY, (context) ->
                new FlyingItemEntityRenderer(context));


    }


}
