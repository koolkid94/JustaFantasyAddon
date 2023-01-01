
package net.kuwulkid94.kuwulkid.entity.client;


import net.kuwulkid94.kuwulkid.JustaFantasyAddon;
import net.kuwulkid94.kuwulkid.item.custom.SpikedShield;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SpikedShieldModel extends AnimatedGeoModel<SpikedShield> {

    @Override
    public Identifier getModelResource(SpikedShield object) {
        return new Identifier(JustaFantasyAddon.MOD_ID, "geo/spiked_shield.geo.json");
    }

    @Override
    public Identifier getTextureResource(SpikedShield object) {
        return new Identifier(JustaFantasyAddon.MOD_ID, "textures/entity/spiked_shield/shield_base_nopattern.png");
    }

    @Override
    public Identifier getAnimationResource(SpikedShield animatable) {
        return new Identifier(JustaFantasyAddon.MOD_ID, "animations/spiked_shield.json");
    }
}