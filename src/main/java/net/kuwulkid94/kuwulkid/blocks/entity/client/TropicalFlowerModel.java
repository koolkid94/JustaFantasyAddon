package net.kuwulkid94.kuwulkid.blocks.entity.client;

import net.kuwulkid94.kuwulkid.JustaFantasyAddon;
import net.kuwulkid94.kuwulkid.blocks.entity.TropicalFlowerEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class TropicalFlowerModel extends AnimatedGeoModel<TropicalFlowerEntity> {


    @Override
    public Identifier getModelResource(TropicalFlowerEntity object) {
        return new Identifier(JustaFantasyAddon.MOD_ID, "geo/tropical_flower.geo.json");
    }

    @Override
    public Identifier getTextureResource(TropicalFlowerEntity object) {
        return new Identifier(JustaFantasyAddon.MOD_ID, "textures/block/tropical_flower.png");
    }

    @Override
    public Identifier getAnimationResource(TropicalFlowerEntity animatable) {
        return new Identifier(JustaFantasyAddon.MOD_ID, "animations/tropical_flower.animation.json");
    }
}