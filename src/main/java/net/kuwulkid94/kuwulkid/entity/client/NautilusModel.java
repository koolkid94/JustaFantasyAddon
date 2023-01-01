package net.kuwulkid94.kuwulkid.entity.client;

import net.kuwulkid94.kuwulkid.JustaFantasyAddon;
import net.kuwulkid94.kuwulkid.entity.custom.NautilusEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class NautilusModel extends AnimatedGeoModel<NautilusEntity>
{

    @Override
    public Identifier getModelResource(NautilusEntity object) {
        return  new Identifier(JustaFantasyAddon.MOD_ID, "geo/nautilus.geo.json");
    }

    @Override
    public Identifier getTextureResource(NautilusEntity object) {
        return  new Identifier(JustaFantasyAddon.MOD_ID, "textures/entity/nautilus/nautilus.png");
    }

    @Override
    public Identifier getAnimationResource(NautilusEntity animatable) {
        return  new Identifier(JustaFantasyAddon.MOD_ID, "animations/nautilus.animation.json");
    }
}
