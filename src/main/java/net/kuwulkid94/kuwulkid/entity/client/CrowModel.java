package net.kuwulkid94.kuwulkid.entity.client;

import net.kuwulkid94.kuwulkid.JustaFantasyAddon;
import net.kuwulkid94.kuwulkid.entity.custom.CrowEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class CrowModel extends AnimatedGeoModel<CrowEntity>
{

    @Override
    public Identifier getModelResource(CrowEntity object) {
        return new Identifier(JustaFantasyAddon.MOD_ID, "geo/crow.geo.json");
    }

    @Override
    public Identifier getTextureResource(CrowEntity object) {
        return new Identifier(JustaFantasyAddon.MOD_ID, "textures/entity/crow/crow.png");
    }

    @Override
    public Identifier getAnimationResource(CrowEntity animatable) {
        return  new Identifier(JustaFantasyAddon.MOD_ID, "animations/crow.animation.json");
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void setLivingAnimations(CrowEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        IBone head = this.getAnimationProcessor().getBone("head");

        EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
        if (head != null) {
            head.setRotationX(extraData.headPitch * ((float) Math.PI / 180F));
            head.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F));
        }
    }

}
