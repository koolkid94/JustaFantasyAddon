package net.kuwulkid94.kuwulkid.entity.client.armor;

import net.kuwulkid94.kuwulkid.JustaFantasyAddon;
import net.kuwulkid94.kuwulkid.item.custom.NecklaceArmorItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class NecklaceArmorModel extends AnimatedGeoModel<NecklaceArmorItem> {



    @Override
    public Identifier getModelResource(NecklaceArmorItem object) {
        return new Identifier (JustaFantasyAddon.MOD_ID, "geo/necklace.geo.json");
    }

    @Override
    public Identifier getTextureResource(NecklaceArmorItem object) {
        return new Identifier (JustaFantasyAddon.MOD_ID, "textures/models/armor/gems.png");
    }

    @Override
    public Identifier getAnimationResource(NecklaceArmorItem animatable) {
        return new Identifier (JustaFantasyAddon.MOD_ID, "animations/necklace.animation.json");
    }
}
