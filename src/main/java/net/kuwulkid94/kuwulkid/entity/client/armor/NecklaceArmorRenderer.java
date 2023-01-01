package net.kuwulkid94.kuwulkid.entity.client.armor;

import net.kuwulkid94.kuwulkid.item.custom.NecklaceArmorItem;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

public class NecklaceArmorRenderer extends GeoArmorRenderer<NecklaceArmorItem> {

    public NecklaceArmorRenderer() {
        super(new NecklaceArmorModel());

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
