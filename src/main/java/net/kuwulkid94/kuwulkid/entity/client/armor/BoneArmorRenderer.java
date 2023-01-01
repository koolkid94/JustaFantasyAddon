package net.kuwulkid94.kuwulkid.entity.client.armor;

import net.kuwulkid94.kuwulkid.item.custom.BoneArmorItem;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

public class BoneArmorRenderer extends GeoArmorRenderer<BoneArmorItem> {
    public BoneArmorRenderer() {
        super(new BoneArmorModel());

        this.headBone = "armorHead";
        this.bodyBone = "armorBody";
        this.rightArmBone = "armorRightArm";
        this.leftArmBone = "armorLeftArm";
        this.rightLegBone = "armorLeftLeg";
        this.leftLegBone = "armorRightLeg";
        this.rightBootBone = "armorLeftBoot";
        this.leftBootBone = "armorRightBoot";

    }
}
