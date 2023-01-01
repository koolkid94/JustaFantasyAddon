package net.kuwulkid94.kuwulkid.blocks.custom;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.kuwulkid94.kuwulkid.blocks.BlockProperties;
import net.kuwulkid94.kuwulkid.item.ModItems;
import net.minecraft.block.*;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Random;
import java.util.stream.Stream;

public class Geyser extends Block implements Waterloggable {

    public static final DirectionProperty FACING = Properties.FACING;
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    public static final BooleanProperty TAPPED = BlockProperties.TAPPED;
    public static final PistonBehavior BEHAVIOR = PistonBehavior.DESTROY;
    int output, numOne = 0, numTwo = 6;
    public Geyser(Settings settings) {
        super(settings);
        setDefaultState(this.stateManager.getDefaultState().with(Properties.FACING, Direction.NORTH).with(WATERLOGGED, false).with(TAPPED, false));
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context){
        return SHAPE;
    }

    @Override
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        if (!entity.isFireImmune() && entity instanceof LivingEntity && !EnchantmentHelper.hasFrostWalker((LivingEntity)entity)) {
            entity.damage(DamageSource.HOT_FLOOR, 1.0F);
        }
        super.onSteppedOn(world, pos, state, entity);
    }

    private static final VoxelShape SHAPE = Stream.of(
            Block.createCuboidShape(5, 0, 1, 10, 3, 6),
            Block.createCuboidShape(10, 0, 3.5, 15.5, 4, 8.5),
            Block.createCuboidShape(6, 0, 10, 11, 2, 14),
            Block.createCuboidShape(10, 0, 7.5, 14, 3, 11.5),
            Block.createCuboidShape(3, 0, 8, 7, 3, 12),
            Block.createCuboidShape(2, 0, 3, 6, 4, 8),
            Block.createCuboidShape(4, 0, 0, 6.5, 2, 3),
            Block.createCuboidShape(10, 0, 2, 12.5, 2, 5),
            Block.createCuboidShape(6, 0, 6, 10, 2, 10),
            Block.createCuboidShape(16, 0, 13, 20, 1, 17),
            Block.createCuboidShape(2, 0, 15, 5, 1, 19),
            Block.createCuboidShape(13, 0, -2, 16, 1, 2),
            Block.createCuboidShape(11, 0, -4, 14.5, 3, 0),
            Block.createCuboidShape(7, 0, -3, 11, 2, 0),
            Block.createCuboidShape(17, 0, 3, 23, 1, 8),
            Block.createCuboidShape(-3, 0, 7, 1, 1, 13),
            Block.createCuboidShape(2, 0, -7, 6, 1, -2),
            Block.createCuboidShape(8, 0, 15, 13, 1, 21),
            Block.createCuboidShape(23, 0, 0, 24, 1, 7),
            Block.createCuboidShape(19, 0, 0, 23, 1, 3)
            ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation){
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror){
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx){
        FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
        return this.getDefaultState().with(WATERLOGGED, ctx.getWorld().getFluidState(ctx.getBlockPos()).getFluid() == Fluids.WATER).with(FACING, ctx.getPlayerFacing().getOpposite());
    }
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder){
        //builder.add(FACING);
        builder.add(Properties.FACING, WATERLOGGED, TAPPED);
    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if ((Boolean) state.get(WATERLOGGED)) {
            world.createAndScheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }
    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, @NotNull BlockHitResult hit) {
            if (player.getMainHandStack().getItem() == ModItems.OIL_RIG ) {
                world.setBlockState(pos, state.with(TAPPED, true));
                world.playSound(player, pos, SoundEvents.BLOCK_WOOD_PLACE, SoundCategory.BLOCKS, 1.0F, world.random.nextFloat() * 0.4F + 0.8F);
                if (!player.isCreative() && !world.isClient && player.getMainHandStack().getItem() == ModItems.OIL_RIG) {
                    ItemUsageContext ctx = new ItemUsageContext(player, hand, hit);
                    ctx.getStack().decrement(1);
                }
                return ActionResult.SUCCESS;
            }

        if (player.getMainHandStack().getItem() == Items.AIR && state.get(TAPPED)) {
            world.setBlockState(pos, state.with(TAPPED, false));
            world.playSound(null, pos, SoundEvents.ENTITY_ITEM_BREAK, SoundCategory.BLOCKS, 1.0F, world.random.nextFloat() * 0.4F + 0.8F);
            world.spawnEntity(new ItemEntity(world, (double) pos.getX() + Math.random(), (double) pos.getY() + 1, (double) pos.getZ() + Math.random(), new ItemStack(Items.COAL, 1)));
            world.addParticle(ParticleTypes.SMOKE, (double) pos.getX() + Math.random(), (double) pos.getY() + 0.25, (double) pos.getZ() + Math.random(), Math.random() * (0.001), Math.random() * (0.2), Math.random() * (0.001));
            world.addParticle(ParticleTypes.LARGE_SMOKE, (double) pos.getX() + Math.random(), (double) pos.getY() + 0.25, (double) pos.getZ() + Math.random(), Math.random() * (0.001), Math.random() * (0.2), Math.random() * (0.001));
            return ActionResult.SUCCESS;
        }

        if ((player.getMainHandStack().getItem() == Items.WOODEN_PICKAXE || player.getMainHandStack().getItem() == Items.STONE_PICKAXE || player.getMainHandStack().getItem() == Items.IRON_PICKAXE ||
                player.getMainHandStack().getItem() == Items.GOLDEN_PICKAXE || player.getMainHandStack().getItem() == Items.DIAMOND_PICKAXE || player.getMainHandStack().getItem() == Items.NETHERITE_PICKAXE)
                && !state.get(TAPPED)) {
            world.playSound(null, pos, SoundEvents.BLOCK_STONE_BREAK, SoundCategory.BLOCKS, 1.0F, world.random.nextFloat() * 0.4F + 0.8F);
            output = (int) ((3 - numOne + 1) * Math.random() + numOne);
            world.spawnEntity(new ItemEntity(world, (double) pos.getX() + Math.random(), (double) pos.getY() + 1, (double) pos.getZ() + Math.random(), new ItemStack(ModItems.OIL, output + 1)));
            world.removeBlock(pos, false);
            if (!player.isCreative() && !world.isClient) {
                player.getMainHandStack().damage(1, world.random, (ServerPlayerEntity) player);
            }
            world.addParticle(ParticleTypes.SMOKE, (double) pos.getX() + Math.random(), (double) pos.getY() + 0.25, (double) pos.getZ() + Math.random(), Math.random() * (0.001), Math.random() * (0.2), Math.random() * (0.001));
            world.addParticle(ParticleTypes.LARGE_SMOKE, (double) pos.getX() + Math.random(), (double) pos.getY() + 0.25, (double) pos.getZ() + Math.random(), Math.random() * (0.001), Math.random() * (0.2), Math.random() * (0.001));

            return ActionResult.SUCCESS;
        }


        return ActionResult.FAIL;
    }

    public boolean hasRandomTicks(BlockState state) {
        return this.randomTicks;
    }

    @Environment(EnvType.CLIENT)
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        BubbleColumnBlock.update(world, pos.up(), state);
        BlockPos blockPos = pos.up();
            world.addParticle(ParticleTypes.SMOKE, (double) pos.getX() + Math.random(), (double) pos.getY() + 0.25, (double) pos.getZ() + Math.random(), Math.random() * (0.001), Math.random() * (0.2), Math.random() * (0.001));
        if(state.get(TAPPED) == true)
        {
            world.spawnEntity(new ItemEntity(world, (double) pos.getX() + Math.random(), (double) pos.getY() + 1, (double) pos.getZ() + Math.random(), new ItemStack(ModItems.OIL, 1)));
            output = (int) ((numTwo - numOne + 1) * Math.random() + numOne);
            if(output == 2)
            {
                world.setBlockState(pos, state.with(TAPPED, false));
                world.playSound(null, pos, SoundEvents.ENTITY_ITEM_BREAK, SoundCategory.BLOCKS, 1.0F, world.random.nextFloat() * 0.4F + 0.8F);
                world.spawnEntity(new ItemEntity(world, (double) pos.getX() + Math.random(), (double) pos.getY() + 1, (double) pos.getZ() + Math.random(), new ItemStack(Items.COAL, 1)));
                world.addParticle(ParticleTypes.SMOKE, (double) pos.getX() + Math.random(), (double) pos.getY() + 0.25, (double) pos.getZ() + Math.random(), Math.random() * (0.001), Math.random() * (0.2), Math.random() * (0.001));
                world.addParticle(ParticleTypes.LARGE_SMOKE, (double) pos.getX() + Math.random(), (double) pos.getY() + 0.25, (double) pos.getZ() + Math.random(), Math.random() * (0.001), Math.random() * (0.2), Math.random() * (0.001));

            }
        }
            //world.addParticle(ParticleTypes.LARGE_SMOKE, (double) pos.getX() + Math.random(), (double) pos.getY() + 0.25, (double) pos.getZ() + Math.random(), Math.random() * (0.001), Math.random() * (0.2), Math.random() * (0.001));
    }

    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        world.playSound(null, pos, SoundEvents.BLOCK_FIRE_AMBIENT, SoundCategory.BLOCKS, 2.0F, 1);
        world.addParticle(ParticleTypes.LARGE_SMOKE, (double) pos.getX() + Math.random(), (double) pos.getY() + 0.25, (double) pos.getZ() + Math.random(), Math.random() * (0.001), Math.random() * (0.2), Math.random() * (0.001));
        if(state.get(TAPPED) == true)
        {
            world.spawnEntity(new ItemEntity(world, (double) pos.getX() + Math.random(), (double) pos.getY() + 1, (double) pos.getZ() + Math.random(), new ItemStack(ModItems.OIL, 1)));
            output = (int) ((numTwo - numOne + 1) * Math.random() + numOne);
            if(output == 2)
            {
                world.setBlockState(pos, state.with(TAPPED, false));
                world.playSound(null, pos, SoundEvents.ENTITY_ITEM_BREAK, SoundCategory.BLOCKS, 1.0F, world.random.nextFloat() * 0.4F + 0.8F);
                world.spawnEntity(new ItemEntity(world, (double) pos.getX() + Math.random(), (double) pos.getY() + 1, (double) pos.getZ() + Math.random(), new ItemStack(Items.COAL, 1)));
                world.addParticle(ParticleTypes.SMOKE, (double) pos.getX() + Math.random(), (double) pos.getY() + 0.25, (double) pos.getZ() + Math.random(), Math.random() * (0.001), Math.random() * (0.2), Math.random() * (0.001));
                world.addParticle(ParticleTypes.LARGE_SMOKE, (double) pos.getX() + Math.random(), (double) pos.getY() + 0.25, (double) pos.getZ() + Math.random(), Math.random() * (0.001), Math.random() * (0.2), Math.random() * (0.001));

            }
        }
    }

    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        if (!world.isClient && (world.getBlockState(pos.down()).isOf(Blocks.AIR))) {
                //dropStacks(state, world, pos);
                world.removeBlock(pos, notify);
        }
    }

    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return !world.isAir(pos.down());
    }

    public PistonBehavior getPistonBehavior(BlockState state) {
        return PistonBehavior.DESTROY;
    }

    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        world.createAndScheduleBlockTick(pos, this, 20);
    }

    

}


