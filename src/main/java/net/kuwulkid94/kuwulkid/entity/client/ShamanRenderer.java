package net.kuwulkid94.kuwulkid.entity.client;

import net.kuwulkid94.kuwulkid.entity.custom.ShamanEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class ShamanRenderer extends GeoEntityRenderer<ShamanEntity>{

    public ShamanRenderer(EntityRendererFactory.Context ctx){

        super(ctx, new ShamanModel());
        this.shadowRadius = 0.35f;

    }

    public RenderLayer getRenderType(ShamanEntity animatable, float partialTicks, MatrixStack stack,
                                     VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder,
                                     int packedLightIn, Identifier textureLocation){
        return super.getRenderType(animatable, partialTicks, stack, renderTypeBuffer, vertexBuilder, packedLightIn, textureLocation);
    }
}