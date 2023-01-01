package net.kuwulkid94.kuwulkid.entity.client;

import net.kuwulkid94.kuwulkid.entity.custom.TaintedEndermanEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class TaintedEndermanRenderer extends GeoEntityRenderer<TaintedEndermanEntity> {
    public TaintedEndermanRenderer(EntityRendererFactory.Context ctx){
        super(ctx, new TaintedEndermanModel());
        this.shadowRadius = 0.3f;
    }

}
