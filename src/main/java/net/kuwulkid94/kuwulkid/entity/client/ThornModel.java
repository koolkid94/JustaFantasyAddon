package net.kuwulkid94.kuwulkid.entity.client;

import net.kuwulkid94.kuwulkid.JustaFantasyAddon;
import net.kuwulkid94.kuwulkid.entity.custom.ThornEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ThornModel extends AnimatedGeoModel<ThornEntity>
{

    @Override
    public Identifier getModelResource(ThornEntity object) {
        return new Identifier(JustaFantasyAddon.MOD_ID, "geo/thorns.geo.json");
    }

    @Override
    public Identifier getTextureResource(ThornEntity object) {
        return new Identifier(JustaFantasyAddon.MOD_ID, "textures/entity/thorns/thorns.png");
    }

    @Override
    public Identifier getAnimationResource(ThornEntity animatable) {
        return  new Identifier(JustaFantasyAddon.MOD_ID, "animations/thorn.animation.json");
    }



}
