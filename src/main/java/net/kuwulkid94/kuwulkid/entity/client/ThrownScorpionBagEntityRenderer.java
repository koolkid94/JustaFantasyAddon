/*package net.kuwulkid94.kuwulkid.entity.client;

import net.kuwulkid94.kuwulkid.entity.projectile.ThrownScorpionBagEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

public class ThrownScorpionBagEntityRenderer extends EntityRenderer<ThrownScorpionBagEntity> {

    public static final ItemStack STACK = new ItemStack(Items.SAND);
    public ThrownScorpionBagEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Override
    public Identifier getTexture(ThrownScorpionBagEntity entity) {
        return null;
    }

    //ItemStack stack, ModelTransformation.Mode transformationType, int light, int overlay, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int seed
    public void render(ThrownScorpionBagEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light){
        MinecraftClient.getInstance().getItemRenderer().renderItem(STACK, ModelTransformation.Mode.FIXED, light, OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, 3);
    }

}

 */
