package net.kuwulkid94.kuwulkid.mixin;

import net.kuwulkid94.kuwulkid.blocks.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.BubbleColumnBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import static net.minecraft.block.BubbleColumnBlock.DRAG;

@Mixin(BubbleColumnBlock.class)
public class BubbleColumnBlockMixin {
    @Overwrite()
    private static BlockState getBubbleState(BlockState state) {
        if (state.isOf(Blocks.BUBBLE_COLUMN)) {
            return state;
        } else if (state.isOf(Blocks.SOUL_SAND)) {
            return (BlockState) Blocks.BUBBLE_COLUMN.getDefaultState().with(DRAG, false);
        } else if(state.isOf(ModBlocks.SANDSTONE_FIRE_BOWL) || state.isOf(ModBlocks.RED_SANDSTONE_FIRE_BOWL) || state.isOf(ModBlocks.STONE_FIRE_BOWL) || state.isOf(ModBlocks.GEYSER)) {
            return (BlockState)Blocks.BUBBLE_COLUMN.getDefaultState().with(DRAG, true);
        } else {
            return state.isOf(Blocks.MAGMA_BLOCK) ? (BlockState)Blocks.BUBBLE_COLUMN.getDefaultState().with(DRAG, true) : Blocks.WATER.getDefaultState();
        }
    }

    @Overwrite()
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockState blockState = world.getBlockState(pos.down());
        return blockState.isOf(Blocks.BUBBLE_COLUMN) || blockState.isOf(Blocks.MAGMA_BLOCK) || blockState.isOf(Blocks.SOUL_SAND) || blockState.isOf(ModBlocks.SANDSTONE_FIRE_BOWL) || state.isOf(ModBlocks.RED_SANDSTONE_FIRE_BOWL) || state.isOf(ModBlocks.STONE_FIRE_BOWL);
    }

}
