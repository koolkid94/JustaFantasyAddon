package net.kuwulkid94.kuwulkid.entity.client;

import net.kuwulkid94.kuwulkid.item.custom.SpikedShield;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class SpikedShieldRenderer extends GeoItemRenderer<SpikedShield> {
    public SpikedShieldRenderer() {
        super(new SpikedShieldModel());
    }


}