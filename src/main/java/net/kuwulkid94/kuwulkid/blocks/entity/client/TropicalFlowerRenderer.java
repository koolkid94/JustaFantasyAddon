package net.kuwulkid94.kuwulkid.blocks.entity.client;

import net.kuwulkid94.kuwulkid.blocks.entity.TropicalFlowerEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

public class TropicalFlowerRenderer extends GeoBlockRenderer<TropicalFlowerEntity> {
    public TropicalFlowerRenderer(BlockEntityRendererFactory.Context context) {
        super(new TropicalFlowerModel());
    }

    @Override
    public RenderLayer getRenderType(TropicalFlowerEntity animatable, float partialTicks, MatrixStack stack,
                                     VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder,
                                     int packedLightIn, Identifier textureLocation) {
        return RenderLayer.getEntityTranslucent(getTextureResource(animatable));
    }
}
