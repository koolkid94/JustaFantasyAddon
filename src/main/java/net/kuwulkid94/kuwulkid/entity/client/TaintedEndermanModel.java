package net.kuwulkid94.kuwulkid.entity.client;

import net.kuwulkid94.kuwulkid.JustaFantasyAddon;
import net.kuwulkid94.kuwulkid.entity.custom.TaintedEndermanEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class TaintedEndermanModel extends AnimatedGeoModel<TaintedEndermanEntity> {
    @Override
    public Identifier getModelResource(TaintedEndermanEntity object) {
        return new Identifier(JustaFantasyAddon.MOD_ID, "geo/tainted_enderman.geo.json");
    }

    @Override
    public Identifier getTextureResource(TaintedEndermanEntity object) {
        return new Identifier(JustaFantasyAddon.MOD_ID, "textures/entity/tainted_enderman/corrupted_enderman.png");
    }

    @Override
    public Identifier getAnimationResource(TaintedEndermanEntity animatable) {
        return  new Identifier(JustaFantasyAddon.MOD_ID, "animations/tainted_enderman.animation.json");
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void setLivingAnimations(TaintedEndermanEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        IBone head = this.getAnimationProcessor().getBone("head");

        EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
        if (head != null) {
            head.setRotationX(extraData.headPitch * ((float) Math.PI / 180F));
            head.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F));
        }
    }

}
