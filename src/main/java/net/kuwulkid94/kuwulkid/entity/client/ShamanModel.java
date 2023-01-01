package net.kuwulkid94.kuwulkid.entity.client;

import net.kuwulkid94.kuwulkid.JustaFantasyAddon;
import net.kuwulkid94.kuwulkid.entity.custom.ShamanEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class ShamanModel extends AnimatedGeoModel<ShamanEntity>
{


    @Override
    public Identifier getModelResource(ShamanEntity object) {
        return new Identifier(JustaFantasyAddon.MOD_ID, "geo/shaman.geo.json");
    }

    @Override
    public Identifier getTextureResource(ShamanEntity object) {
        return new Identifier(JustaFantasyAddon.MOD_ID, "textures/entity/shaman/shaman.png");
    }

    @Override
    public Identifier getAnimationResource(ShamanEntity animatable) {
        return  new Identifier(JustaFantasyAddon.MOD_ID, "animations/shaman.animation.json");
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void setLivingAnimations(ShamanEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        IBone head = this.getAnimationProcessor().getBone("head");

        EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
        if (head != null) {
            head.setRotationX(extraData.headPitch * ((float) Math.PI / 180F));
            head.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F));
        }
    }

}
