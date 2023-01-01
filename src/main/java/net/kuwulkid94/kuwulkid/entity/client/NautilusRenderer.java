package net.kuwulkid94.kuwulkid.entity.client;

import net.kuwulkid94.kuwulkid.entity.custom.NautilusEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class NautilusRenderer extends GeoEntityRenderer<NautilusEntity>{

    public NautilusRenderer(EntityRendererFactory.Context ctx){

        super(ctx, new NautilusModel());
    }
}