package net.kuwulkid94.kuwulkid.entity.client;

import net.kuwulkid94.kuwulkid.entity.custom.ThornEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class ThornRenderer extends GeoEntityRenderer<ThornEntity>{

    public ThornRenderer(EntityRendererFactory.Context ctx){

        super(ctx, new ThornModel());
        this.shadowRadius = 0.3f;

    }


}