package net.kuwulkid94.kuwulkid.entity.client;

import net.kuwulkid94.kuwulkid.JustaFantasyAddon;
import net.kuwulkid94.kuwulkid.entity.custom.ShrimpEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ShrimpModel extends AnimatedGeoModel<ShrimpEntity>
{


    @Override
    public Identifier getModelResource(ShrimpEntity object) {
        return  new Identifier(JustaFantasyAddon.MOD_ID, "geo/shrimp.geo.json");
    }

    @Override
    public Identifier getTextureResource(ShrimpEntity object) {
        return  new Identifier(JustaFantasyAddon.MOD_ID, "textures/entity/shrimp/shrimp.png");
    }

    @Override
    public Identifier getAnimationResource(ShrimpEntity animatable) {
        return  new Identifier(JustaFantasyAddon.MOD_ID, "animations/shrimp.animation.json");
    }
}
