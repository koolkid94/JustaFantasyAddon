package net.kuwulkid94.kuwulkid.entity.client.armor;

import net.kuwulkid94.kuwulkid.JustaFantasyAddon;
import net.kuwulkid94.kuwulkid.item.custom.VanguardArmorItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class VanguardArmorModel extends AnimatedGeoModel<VanguardArmorItem> {

    @Override
    public Identifier getModelResource(VanguardArmorItem object) {
        return new Identifier (JustaFantasyAddon.MOD_ID, "geo/vanguard_armor.geo.json");
    }

    @Override
    public Identifier getTextureResource(VanguardArmorItem object) {
        return new Identifier (JustaFantasyAddon.MOD_ID, "textures/models/armor/vanguard_armor.png");
    }

    @Override
    public Identifier getAnimationResource(VanguardArmorItem animatable) {
        return new Identifier (JustaFantasyAddon.MOD_ID, "animations/vanguard.animation.json");
    }
}
