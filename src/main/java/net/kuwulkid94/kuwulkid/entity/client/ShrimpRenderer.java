package net.kuwulkid94.kuwulkid.entity.client;

import net.kuwulkid94.kuwulkid.entity.custom.ShrimpEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class ShrimpRenderer extends GeoEntityRenderer<ShrimpEntity>{

    public ShrimpRenderer(EntityRendererFactory.Context ctx){

        super(ctx, new ShrimpModel());
    }
}