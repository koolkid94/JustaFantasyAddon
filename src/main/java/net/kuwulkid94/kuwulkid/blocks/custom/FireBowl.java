package net.kuwulkid94.kuwulkid.blocks.custom;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.kuwulkid94.kuwulkid.blocks.BlockProperties;
import net.kuwulkid94.kuwulkid.item.ModItems;
import net.kuwulkid94.kuwulkid.sounds.ModSounds;
import net.minecraft.block.*;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.Items;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.ToIntFunction;
import java.util.stream.Stream;


public class FireBowl extends Block implements Waterloggable{

    public static final DirectionProperty FACING = Properties.FACING;
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    public static final BooleanProperty FIRE = BlockProperties.FIRE;
    public static final BooleanProperty DYED = BlockProperties.DYED;
    int numOne = 0, numTwo = 8;


    public FireBowl(Settings settings)
    {
        super(FabricBlockSettings.copyOf(settings).luminance(getLuminance()));
        setDefaultState(this.stateManager.getDefaultState().with(Properties.FACING, Direction.NORTH).with(WATERLOGGED, false).with(FIRE, false).with(DYED, false));
    }

    private static final VoxelShape SHAPE = Stream.of(
            Block.createCuboidShape(0, 9, 0, 16, 12, 16),
            Block.createCuboidShape(3, 3, 3, 13, 5, 13),
            Block.createCuboidShape(2, 1, 2, 5, 5, 5),
            Block.createCuboidShape(1, 0, 1, 4, 2, 4),
            Block.createCuboidShape(2, 1, 11, 5, 5, 14),
            Block.createCuboidShape(1, 0, 12, 4, 2, 15),
            Block.createCuboidShape(11, 1, 2, 14, 5, 5),
            Block.createCuboidShape(12, 0, 1, 15, 2, 4),
            Block.createCuboidShape(11, 1, 11, 14, 5, 14),
            Block.createCuboidShape(1, 5, 1, 15, 9, 15),
            Block.createCuboidShape(12, 0, 12, 15, 2, 15)
    ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context){
        return SHAPE;
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx){
        FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
        return this.getDefaultState().with(WATERLOGGED, ctx.getWorld().getFluidState(ctx.getBlockPos()).getFluid() == Fluids.WATER).with(FACING, ctx.getPlayerFacing().getOpposite());
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation){
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror){
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder){
        //builder.add(FACING);
        builder.add(Properties.FACING, WATERLOGGED, FIRE, DYED);
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

    @Override
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        if (!entity.isFireImmune() && entity instanceof LivingEntity && !EnchantmentHelper.hasFrostWalker((LivingEntity)entity)) {
            entity.damage(DamageSource.HOT_FLOOR, 1.0F);
        }
        if(state.get(FIRE)) {
            entity.setOnFire(true);
            entity.setFireTicks(80);
        }

        super.onSteppedOn(world, pos, state, entity);
    }

    @Environment(EnvType.CLIENT)
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        BubbleColumnBlock.update(world, pos.up(), state);
        BlockPos blockPos = pos.up();
        if (world.getFluidState(pos).isIn(FluidTags.WATER) || state.get(WATERLOGGED) && state.get(FIRE)) {
            world.playSound((PlayerEntity)null, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 0.5F, 2.6F + (world.random.nextFloat() - world.random.nextFloat()) * 0.8F);
            world.setBlockState(pos, state.with(FIRE, false));
        }
        if(state.get(FIRE)) {
            world.addParticle(ParticleTypes.SMOKE, (double) pos.getX() + Math.random(), (double) pos.getY() + 1, (double) pos.getZ() + Math.random(), Math.random() * (0.001), Math.random() * (0.2), Math.random() * (0.001));
            world.addParticle(ParticleTypes.LARGE_SMOKE, (double) pos.getX() + Math.random(), (double) pos.getY() + 1, (double) pos.getZ() + Math.random(), Math.random() * (0.001), Math.random() * (0.2), Math.random() * (0.001));
            world.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, (double) pos.getX() + Math.random(), (double) pos.getY() + 1, (double) pos.getZ() + Math.random(), Math.random() * (0.001), Math.random() * (0.2), Math.random() * (0.001));
            world.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, (double) pos.getX() + Math.random(), (double) pos.getY() + 1, (double) pos.getZ() + Math.random(), Math.random() * (0.001), Math.random() * (0.2), Math.random() * (0.001));
        }
    }

    public boolean hasRandomTicks(BlockState state) {
        return this.randomTicks;
    }

    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        BlockPos blockPos = pos.up();
        if (world.getFluidState(pos).isIn(FluidTags.WATER   )) {
            world.playSound((PlayerEntity)null, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 0.5F, 2.6F + (world.random.nextFloat() - world.random.nextFloat()) * 0.8F);
            world.spawnParticles(ParticleTypes.LARGE_SMOKE, (double)blockPos.getX() + 0.5, (double)blockPos.getY() + 1.25, (double)blockPos.getZ() + 0.5, 8, 0.2, 0.3, 0.1, 0.1);
        }

    }

    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        world.createAndScheduleBlockTick(pos, this, 20);
    }

    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        BubbleColumnBlock.update(world, pos.up(), state);
    }

//line//

    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, @NotNull BlockHitResult hit) {
        if (hit.getSide() == Direction.UP || hit.getSide() == Direction.WEST || hit.getSide() == Direction.EAST || hit.getSide() == Direction.NORTH || hit.getSide() == Direction.SOUTH && !state.get(WATERLOGGED)  ) {
            if (player.getMainHandStack().getItem() == Items.FLINT_AND_STEEL || player.getMainHandStack().getItem() == Items.FIRE_CHARGE && !state.get(FIRE)) { //Add Items
                world.setBlockState(pos, state.with(FIRE, true));
                if (!player.isCreative() && !world.isClient && player.getMainHandStack().getItem() == Items.FLINT_AND_STEEL && player.getMainHandStack().getDamage() > 0) {
                    player.getMainHandStack().damage(1, world.random, (ServerPlayerEntity) player);
                }

                if(player.getMainHandStack().getItem() == Items.FLINT_AND_STEEL)
                    world.playSound(player, pos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, world.random.nextFloat() * 0.4F + 0.8F);

                if (!player.isCreative() && !world.isClient && player.getMainHandStack().getItem() == Items.FIRE_CHARGE) {
                    ItemUsageContext ctx = new ItemUsageContext(player, hand, hit);
                    ctx.getStack().decrement(1);
                }

                if(player.getMainHandStack().getItem() == Items.FIRE_CHARGE)
                    world.playSound(player, pos, SoundEvents.ITEM_FIRECHARGE_USE, SoundCategory.BLOCKS, 1.0F, world.random.nextFloat() * 0.4F + 0.8F);

                return ActionResult.SUCCESS;
            }
            else if (player.getMainHandStack().isEmpty() && state.get(FIRE)) {
                world.setBlockState(pos, state.with(FIRE, false));
                world.playSound(player, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1.0F, world.random.nextFloat() * 0.4F + 0.8F);
                return ActionResult.SUCCESS;
            }
        }
        //items to burn
        if (hit.getSide() == Direction.NORTH ||hit.getSide() == Direction.WEST || hit.getSide() == Direction.EAST || hit.getSide() == Direction.SOUTH || hit.getSide() == Direction.UP && state.get(FIRE)) {
            if (player.getMainHandStack().getItem() == Items.BONE && state.get(FIRE)){
                world.playSound(player, pos, SoundEvents.ITEM_FIRECHARGE_USE, SoundCategory.BLOCKS, 1.0F, world.random.nextFloat() * 0.4F + 0.8F);
                ItemUsageContext ctx = new ItemUsageContext(player, hand, hit);
                if(!player.isCreative() && !world.isClient) {
                    ctx.getStack().decrement(1);
                }
                spawnSmokeParticle(world, pos);
                world.addParticle(ParticleTypes.LARGE_SMOKE, (double)pos.getX()+ Math.random(), (double)pos.getY()+ 1, (double)pos.getZ() + Math.random(), Math.random() * (0.001) , Math.random()  * (0.2), Math.random()  * (0.001));
                world.addParticle(ParticleTypes.LARGE_SMOKE, (double)pos.getX()+ Math.random(), (double)pos.getY()+ 1, (double)pos.getZ() + Math.random(), Math.random() * (0.001) , Math.random()  * (0.2), Math.random()  * (0.001));
                world.addParticle(ParticleTypes.LARGE_SMOKE, (double)pos.getX()+ Math.random(), (double)pos.getY()+ 1, (double)pos.getZ() + Math.random(), Math.random() * (0.001) , Math.random()  * (0.2), Math.random()  * (0.001));
                world.addParticle(ParticleTypes.LARGE_SMOKE, (double)pos.getX()+ Math.random(), (double)pos.getY()+ 1, (double)pos.getZ() + Math.random(), Math.random() * (0.001) , Math.random()  * (0.2), Math.random()  * (0.001));
                world.addParticle(ParticleTypes.LARGE_SMOKE, (double)pos.getX()+ Math.random(), (double)pos.getY()+ 1, (double)pos.getZ() + Math.random(), Math.random() * (0.001) , Math.random()  * (0.2), Math.random()  * (0.001));
                world.addParticle(ParticleTypes.LARGE_SMOKE, (double)pos.getX()+ Math.random(), (double)pos.getY()+ 1, (double)pos.getZ() + Math.random(), Math.random() * (0.001) , Math.random()  * (0.2), Math.random()  * (0.001));
                if(2 == (int) ((numTwo - numOne + 1) * Math.random() + numOne)) {
                    player.addStatusEffect(new StatusEffectInstance(StatusEffects.LUCK, 2400, 1), player);
                    world.playSound(player, pos, SoundEvents.BLOCK_BONE_BLOCK_BREAK, SoundCategory.BLOCKS, 1.0F, world.random.nextFloat() * 0.4F + 0.8F);
                }

                float yaw = player.getHeadYaw();
                Vec3d vector = player.getOppositeRotationVector(yaw);
                player.takeKnockback(0.6,vector.getX(),vector.getZ());
                BlockPos posPlayer = player.getBlockPos();
                if(1 == (int) ((numTwo - numOne + 1) * Math.random() + numOne)) {
                    world.playSound(player, pos, ModSounds.FIREBOWL_WOOSH_1, SoundCategory.PLAYERS, 1.0F, world.random.nextFloat() * 0.4F + 0.8F);
                }
                else if(2 == (int) ((numTwo - numOne + 1) * Math.random() + numOne)) {
                    world.playSound(player, pos, ModSounds.FIREBOWL_WOOSH_2, SoundCategory.PLAYERS, 1.0F, world.random.nextFloat() * 0.4F + 0.8F);
                }
                else {
                    world.playSound(player, pos, ModSounds.FIREBOWL_WOOSH, SoundCategory.PLAYERS, 1.0F, world.random.nextFloat() * 0.4F + 0.8F);
                }

                world.addParticle(ParticleTypes.SWEEP_ATTACK, pos.getX(), pos.getY(), pos.getZ(), posPlayer.getX(), posPlayer.getY(), posPlayer.getZ());

                //     ⣞⢽⢪⢣⢣⢣⢫⡺⡵⣝⡮⣗⢷⢽⢽⢽⣮⡷⡽⣜⣜⢮⢺⣜⢷⢽⢝⡽⣝
//                    ⠸⡸⠜⠕⠕⠁⢁⢇⢏⢽⢺⣪⡳⡝⣎⣏⢯⢞⡿⣟⣷⣳⢯⡷⣽⢽⢯⣳⣫⠇
//⠀                   ⠀⢀⢀⢄⢬⢪⡪⡎⣆⡈⠚⠜⠕⠇⠗⠝⢕⢯⢫⣞⣯⣿⣻⡽⣏⢗⣗⠏⠀
//⠀                   ⠪⡪⡪⣪⢪⢺⢸⢢⢓⢆⢤⢀⠀⠀⠀⠀⠈⢊⢞⡾⣿⡯⣏⢮⠷⠁⠀⠀
//                    ⠀⠀⠀⠈⠊⠆⡃⠕⢕⢇⢇⢇⢇⢇⢏⢎⢎⢆⢄⠀⢑⣽⣿⢝⠲⠉⠀⠀⠀⠀
//                    ⠀⠀⠀⠀⠀⡿⠂⠠⠀⡇⢇⠕⢈⣀⠀⠁⠡⠣⡣⡫⣂⣿⠯⢪⠰⠂⠀⠀⠀⠀
//                    ⠀⠀⠀⠀⡦⡙⡂⢀⢤⢣⠣⡈⣾⡃⠠⠄⠀⡄⢱⣌⣶⢏⢊⠂⠀⠀⠀⠀⠀⠀
//                    ⠀⠀⠀⠀⢝⡲⣜⡮⡏⢎⢌⢂⠙⠢⠐⢀⢘⢵⣽⣿⡿⠁⠁⠀⠀⠀⠀⠀⠀⠀
//                    ⠀⠀⠀⠀⠨⣺⡺⡕⡕⡱⡑⡆⡕⡅⡕⡜⡼⢽⡻⠏⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
//                    ⠀⠀⠀⠀⣼⣳⣫⣾⣵⣗⡵⡱⡡⢣⢑⢕⢜⢕⡝⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
//                    ⠀⠀⠀⣴⣿⣾⣿⣿⣿⡿⡽⡑⢌⠪⡢⡣⣣⡟⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
//                    ⠀⠀⠀⡟⡾⣿⢿⢿⢵⣽⣾⣼⣘⢸⢸⣞⡟⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
//                    ⠀⠀⠀⠀⠁⠇⠡⠩⡫⢿⣝⡻⡮⣒⢽⠋⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
//                   No Constructor?


                return ActionResult.SUCCESS;
            }
            if (player.getMainHandStack().getItem() == Items.SUGAR && state.get(FIRE)){
                world.playSound(player, pos, SoundEvents.ITEM_FIRECHARGE_USE, SoundCategory.BLOCKS, 1.0F, world.random.nextFloat() * 0.4F + 0.8F);
                ItemUsageContext ctx = new ItemUsageContext(player, hand, hit);
                if(!player.isCreative() && !world.isClient) {
                    ctx.getStack().decrement(1);
                }
                spawnSmokeParticle(world, pos);
                world.addParticle(ParticleTypes.LARGE_SMOKE, (double)pos.getX()+ Math.random(), (double)pos.getY()+ 1, (double)pos.getZ() + Math.random(), Math.random() * (0.001) , Math.random()  * (0.2), Math.random()  * (0.001));
                world.addParticle(ParticleTypes.LARGE_SMOKE, (double)pos.getX()+ Math.random(), (double)pos.getY()+ 1, (double)pos.getZ() + Math.random(), Math.random() * (0.001) , Math.random()  * (0.2), Math.random()  * (0.001));
                world.addParticle(ParticleTypes.LARGE_SMOKE, (double)pos.getX()+ Math.random(), (double)pos.getY()+ 1, (double)pos.getZ() + Math.random(), Math.random() * (0.001) , Math.random()  * (0.2), Math.random()  * (0.001));
                world.addParticle(ParticleTypes.LARGE_SMOKE, (double)pos.getX()+ Math.random(), (double)pos.getY()+ 1, (double)pos.getZ() + Math.random(), Math.random() * (0.001) , Math.random()  * (0.2), Math.random()  * (0.001));
                world.addParticle(ParticleTypes.LARGE_SMOKE, (double)pos.getX()+ Math.random(), (double)pos.getY()+ 1, (double)pos.getZ() + Math.random(), Math.random() * (0.001) , Math.random()  * (0.2), Math.random()  * (0.001));
                world.addParticle(ParticleTypes.LARGE_SMOKE, (double)pos.getX()+ Math.random(), (double)pos.getY()+ 1, (double)pos.getZ() + Math.random(), Math.random() * (0.001) , Math.random()  * (0.2), Math.random()  * (0.001));
                if(2 == (int) ((numTwo - numOne + 1) * Math.random() + numOne)) {
                    player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 2400, 1), player);
                }

                float yaw = player.getHeadYaw();
                Vec3d vector = player.getOppositeRotationVector(yaw);
                player.takeKnockback(0.6,vector.getX(),vector.getZ());
                BlockPos posPlayer = player.getBlockPos();
                if(1 == (int) ((numTwo - numOne + 1) * Math.random() + numOne)) {
                    world.playSound(player, pos, ModSounds.FIREBOWL_WOOSH_1, SoundCategory.PLAYERS, 1.0F, world.random.nextFloat() * 0.4F + 0.8F);
                }
                else if(2 == (int) ((numTwo - numOne + 1) * Math.random() + numOne)) {
                    world.playSound(player, pos, ModSounds.FIREBOWL_WOOSH_2, SoundCategory.PLAYERS, 1.0F, world.random.nextFloat() * 0.4F + 0.8F);
                }
                else {
                    world.playSound(player, pos, ModSounds.FIREBOWL_WOOSH, SoundCategory.PLAYERS, 1.0F, world.random.nextFloat() * 0.4F + 0.8F);
                }

                world.addParticle(ParticleTypes.SWEEP_ATTACK, pos.getX(), pos.getY(), pos.getZ(), posPlayer.getX(), posPlayer.getY(), posPlayer.getZ());

                return ActionResult.SUCCESS;
            }
            if (player.getMainHandStack().getItem() == ModItems.SCORPION_STINGER && state.get(FIRE)){
                world.playSound(player, pos, SoundEvents.ITEM_FIRECHARGE_USE, SoundCategory.BLOCKS, 1.0F, world.random.nextFloat() * 0.4F + 0.8F);
                ItemUsageContext ctx = new ItemUsageContext(player, hand, hit);
                if(!player.isCreative() && !world.isClient) {
                    ctx.getStack().decrement(1);
                }
                spawnSmokeParticle(world, pos);
                world.addParticle(ParticleTypes.LARGE_SMOKE, (double)pos.getX()+ Math.random(), (double)pos.getY()+ 1, (double)pos.getZ() + Math.random(), Math.random() * (0.001) , Math.random()  * (0.2), Math.random()  * (0.001));
                world.addParticle(ParticleTypes.LARGE_SMOKE, (double)pos.getX()+ Math.random(), (double)pos.getY()+ 1, (double)pos.getZ() + Math.random(), Math.random() * (0.001) , Math.random()  * (0.2), Math.random()  * (0.001));
                world.addParticle(ParticleTypes.LARGE_SMOKE, (double)pos.getX()+ Math.random(), (double)pos.getY()+ 1, (double)pos.getZ() + Math.random(), Math.random() * (0.001) , Math.random()  * (0.2), Math.random()  * (0.001));
                world.addParticle(ParticleTypes.LARGE_SMOKE, (double)pos.getX()+ Math.random(), (double)pos.getY()+ 1, (double)pos.getZ() + Math.random(), Math.random() * (0.001) , Math.random()  * (0.2), Math.random()  * (0.001));
                world.addParticle(ParticleTypes.LARGE_SMOKE, (double)pos.getX()+ Math.random(), (double)pos.getY()+ 1, (double)pos.getZ() + Math.random(), Math.random() * (0.001) , Math.random()  * (0.2), Math.random()  * (0.001));
                world.addParticle(ParticleTypes.LARGE_SMOKE, (double)pos.getX()+ Math.random(), (double)pos.getY()+ 1, (double)pos.getZ() + Math.random(), Math.random() * (0.001) , Math.random()  * (0.2), Math.random()  * (0.001));
                if(2 == (int) ((numTwo - numOne + 1) * Math.random() + numOne)) {
                    player.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 2400, 1), player);
                }

                float yaw = player.getHeadYaw();
                Vec3d vector = player.getOppositeRotationVector(yaw);
                BlockPos posPlayer = player.getBlockPos();
                player.takeKnockback(0.6,vector.getX(),vector.getZ());

                if(1 == (int) ((numTwo - numOne + 1) * Math.random() + numOne)) {
                    world.playSound(player, pos, ModSounds.FIREBOWL_WOOSH_1, SoundCategory.PLAYERS, 1.0F, world.random.nextFloat() * 0.4F + 0.8F);
                }
                else if(2 == (int) ((numTwo - numOne + 1) * Math.random() + numOne)) {
                    world.playSound(player, pos, ModSounds.FIREBOWL_WOOSH_2, SoundCategory.PLAYERS, 1.0F, world.random.nextFloat() * 0.4F + 0.8F);
                }
                else {
                    world.playSound(player, pos, ModSounds.FIREBOWL_WOOSH, SoundCategory.PLAYERS, 1.0F, world.random.nextFloat() * 0.4F + 0.8F);
                }

                world.addParticle(ParticleTypes.SWEEP_ATTACK, pos.getX(), pos.getY(), pos.getZ(), posPlayer.getX(), posPlayer.getY(), posPlayer.getZ());


                return ActionResult.SUCCESS;
            }
            if (player.getMainHandStack().getItem() == Items.GLOWSTONE_DUST && state.get(FIRE)){
                world.playSound(player, pos, SoundEvents.ITEM_FIRECHARGE_USE, SoundCategory.BLOCKS, 1.0F, world.random.nextFloat() * 0.4F + 0.8F);
                ItemUsageContext ctx = new ItemUsageContext(player, hand, hit);
                if(!player.isCreative() && !world.isClient) {
                    ctx.getStack().decrement(1);
                }
                spawnSmokeParticle(world, pos);
                world.addParticle(ParticleTypes.LARGE_SMOKE, (double)pos.getX()+ Math.random(), (double)pos.getY()+ 1, (double)pos.getZ() + Math.random(), Math.random() * (0.001) , Math.random()  * (0.2), Math.random()  * (0.001));
                world.addParticle(ParticleTypes.LARGE_SMOKE, (double)pos.getX()+ Math.random(), (double)pos.getY()+ 1, (double)pos.getZ() + Math.random(), Math.random() * (0.001) , Math.random()  * (0.2), Math.random()  * (0.001));
                world.addParticle(ParticleTypes.LARGE_SMOKE, (double)pos.getX()+ Math.random(), (double)pos.getY()+ 1, (double)pos.getZ() + Math.random(), Math.random() * (0.001) , Math.random()  * (0.2), Math.random()  * (0.001));
                world.addParticle(ParticleTypes.LARGE_SMOKE, (double)pos.getX()+ Math.random(), (double)pos.getY()+ 1, (double)pos.getZ() + Math.random(), Math.random() * (0.001) , Math.random()  * (0.2), Math.random()  * (0.001));
                world.addParticle(ParticleTypes.LARGE_SMOKE, (double)pos.getX()+ Math.random(), (double)pos.getY()+ 1, (double)pos.getZ() + Math.random(), Math.random() * (0.001) , Math.random()  * (0.2), Math.random()  * (0.001));
                world.addParticle(ParticleTypes.LARGE_SMOKE, (double)pos.getX()+ Math.random(), (double)pos.getY()+ 1, (double)pos.getZ() + Math.random(), Math.random() * (0.001) , Math.random()  * (0.2), Math.random()  * (0.001));
                if(2 == (int) ((numTwo - numOne + 1) * Math.random() + numOne)) {
                    player.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 2400, 1), player);
                }

                float yaw = player.getHeadYaw();
                Vec3d vector = player.getOppositeRotationVector(yaw);
                BlockPos posPlayer = player.getBlockPos();
                player.takeKnockback(0.6,vector.getX(),vector.getZ());

                if(1 == (int) ((numTwo - numOne + 1) * Math.random() + numOne)) {
                    world.playSound(player, pos, ModSounds.FIREBOWL_WOOSH_1, SoundCategory.PLAYERS, 1.0F, world.random.nextFloat() * 0.4F + 0.8F);
                }
                else if(2 == (int) ((numTwo - numOne + 1) * Math.random() + numOne)) {
                    world.playSound(player, pos, ModSounds.FIREBOWL_WOOSH_2, SoundCategory.PLAYERS, 1.0F, world.random.nextFloat() * 0.4F + 0.8F);
                }
                else {
                    world.playSound(player, pos, ModSounds.FIREBOWL_WOOSH, SoundCategory.PLAYERS, 1.0F, world.random.nextFloat() * 0.4F + 0.8F);
                }

                world.addParticle(ParticleTypes.SWEEP_ATTACK, pos.getX(), pos.getY(), pos.getZ(), posPlayer.getX(), posPlayer.getY(), posPlayer.getZ());


                return ActionResult.SUCCESS;
            }

        }

        //dye
        return ActionResult.FAIL;
    }

    protected static ToIntFunction<BlockState> getLuminance() {
        return (state) -> {
            return state.get(FIRE) ? 15 : 3;
        };
    }

    public static void spawnSmokeParticle(World world, BlockPos pos) {
        Random random = world.getRandom();
        DefaultParticleType defaultParticleType = ParticleTypes.CAMPFIRE_COSY_SMOKE;
        world.addImportantParticle(defaultParticleType, true, (double)pos.getX() + 0.5 + random.nextDouble() / 2.0 * (double)(random.nextBoolean() ? 1 : -1), (double)pos.getY() + random.nextDouble() + random.nextDouble(), (double)pos.getZ() + 0.5 + random.nextDouble() / 2.0 * (double)(random.nextBoolean() ? 1 : -1), 0.0, 0.07, 0.0);


    }

    public void onProjectileHit(World world, BlockState state, BlockHitResult hit, ProjectileEntity projectile) {
        BlockPos blockPos = hit.getBlockPos();
        if (!world.isClient && projectile.isOnFire() && projectile.canModifyAt(world, blockPos) && !(Boolean)state.get(FIRE) && !(Boolean)state.get(WATERLOGGED)) {
            world.setBlockState(blockPos, (BlockState)state.with(BlockProperties.FIRE, true), 11);
        }

    }

    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return false;
    }

}


