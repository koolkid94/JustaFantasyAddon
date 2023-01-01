package net.kuwulkid94.kuwulkid.entity.client;

import net.kuwulkid94.kuwulkid.JustaFantasyAddon;
import net.kuwulkid94.kuwulkid.entity.custom.ScorpionEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ScorpionModel extends AnimatedGeoModel<ScorpionEntity>
{

    @Override
    public Identifier getModelResource(ScorpionEntity object) {
        return  new Identifier(JustaFantasyAddon.MOD_ID, "geo/scorpion.geo.json");
    }

    @Override
    public Identifier getTextureResource(ScorpionEntity object) {
        return  new Identifier(JustaFantasyAddon.MOD_ID, "textures/entity/scorpion/scorpion.png");
    }

    @Override
    public Identifier getAnimationResource(ScorpionEntity animatable) {
        return  new Identifier(JustaFantasyAddon.MOD_ID, "animations/scorpion.animation.json");
    }
}
