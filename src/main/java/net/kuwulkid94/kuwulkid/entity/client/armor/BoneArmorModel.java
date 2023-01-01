package net.kuwulkid94.kuwulkid.entity.client.armor;

import net.kuwulkid94.kuwulkid.JustaFantasyAddon;
import net.kuwulkid94.kuwulkid.item.custom.BoneArmorItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BoneArmorModel extends AnimatedGeoModel<BoneArmorItem> {
    @Override
    public Identifier getModelResource(BoneArmorItem object) {
        return new Identifier (JustaFantasyAddon.MOD_ID, "geo/mask.geo.json");
    }

    @Override
    public Identifier getTextureResource(BoneArmorItem object) {
        return new Identifier (JustaFantasyAddon.MOD_ID, "textures/models/armor/skullmask.png");
    }

    @Override
    public Identifier getAnimationResource(BoneArmorItem animatable) {
        return new Identifier (JustaFantasyAddon.MOD_ID, "animations/mask.animation.json");
    }
}
