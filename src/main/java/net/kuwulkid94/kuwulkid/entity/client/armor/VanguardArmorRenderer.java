package net.kuwulkid94.kuwulkid.entity.client.armor;

import net.kuwulkid94.kuwulkid.item.custom.VanguardArmorItem;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

public class VanguardArmorRenderer extends GeoArmorRenderer<VanguardArmorItem> {
    public VanguardArmorRenderer() {
        super(new VanguardArmorModel());

        this.headBone = "armorHead";
        this.bodyBone = "armorBody";
        this.rightArmBone = "armorRightArm";
        this.leftArmBone = "armorLeftArm";
        this.rightLegBone = "armorRightLeg";
        this.leftLegBone = "armorLeftLeg";
        this.rightBootBone = "armorRightBoot";
        this.leftBootBone = "armorLeftBoot";

    }
}
