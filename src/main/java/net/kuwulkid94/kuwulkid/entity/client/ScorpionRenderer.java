package net.kuwulkid94.kuwulkid.entity.client;

import net.kuwulkid94.kuwulkid.entity.custom.ScorpionEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class ScorpionRenderer extends GeoEntityRenderer<ScorpionEntity>{

    public ScorpionRenderer(EntityRendererFactory.Context ctx){

        super(ctx, new ScorpionModel());
        this.shadowRadius = 0.25f;

    }



    public RenderLayer getRenderType(ScorpionEntity animatable, float partialTicks, MatrixStack stack,
                                     VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder,
                                     int packedLightIn, Identifier textureLocation){

        if(animatable.isBaby())
        {
            stack.scale(0.75f, 0.75f, 0.75f);
        } else{
            stack.scale(1.1f, 1.1f, 1.1f);
        }

        return super.getRenderType(animatable, partialTicks, stack, renderTypeBuffer, vertexBuilder, packedLightIn, textureLocation);
    }
}