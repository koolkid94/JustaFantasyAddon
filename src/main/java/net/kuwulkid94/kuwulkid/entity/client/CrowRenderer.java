package net.kuwulkid94.kuwulkid.entity.client;

import net.kuwulkid94.kuwulkid.entity.custom.CrowEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class CrowRenderer extends GeoEntityRenderer<CrowEntity>{

    public CrowRenderer(EntityRendererFactory.Context ctx){

        super(ctx, new CrowModel());
        this.shadowRadius = 0.3f;

    }


}