package net.kuwulkid94.kuwulkid.entity.client;

import net.kuwulkid94.kuwulkid.blocks.custom.CrudeAltar;
import net.kuwulkid94.kuwulkid.blocks.entity.CrudeAltarBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3f;
import net.minecraft.world.World;

public class CrudeAltarBlockEntityRenderer implements BlockEntityRenderer<CrudeAltarBlockEntity> {
    public CrudeAltarBlockEntityRenderer(BlockEntityRendererFactory.Context context){

    }
    @Override
    public void render(CrudeAltarBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();

        ItemStack itemStack = entity.getRenderStack();
        matrices.push();
        matrices.translate(0.45f, 0.88f, 0.55f);
        matrices.scale(0.4f,0.4f,0.4f);
        matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(-90));

        switch (entity.getCachedState().get(CrudeAltar.FACING)) {
            case NORTH -> matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(180));
            case EAST -> matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(270));
            case SOUTH -> matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(0));
            case WEST -> matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(90));
        }

        itemRenderer.renderItem(itemStack, ModelTransformation.Mode.GUI, getLightLevel(entity.getWorld(), entity.getPos()),
                OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, 1);
        matrices.pop();
    }

    private int getLightLevel(World world, BlockPos pos) {
        Object LightType;
        int bLight = world.getLightLevel(net.minecraft.world.LightType.BLOCK, pos);
        int sLight = world.getLightLevel(net.minecraft.world.LightType.SKY, pos);
        return LightmapTextureManager.pack(bLight, sLight);

    }
}
