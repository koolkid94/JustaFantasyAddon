package net.kuwulkid94.kuwulkid.entity.custom;

import net.kuwulkid94.kuwulkid.sounds.ModSounds;
import net.minecraft.block.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.FuzzyTargeting;
import net.minecraft.entity.ai.control.FlightMoveControl;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.RabbitEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.*;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.Iterator;
import java.util.Random;

public class CrowEntity extends AnimalEntity implements Flutterer, IAnimatable {

    int moreCarrotTicks;
    private Goal followChickenAndRabbitGoal;
    public float flapProgress;
    public float maxWingDeviation;
    public float prevMaxWingDeviation;
    public float prevFlapProgress;
    private float flapSpeed = 1.0F;
    private float field_28640 = 1.0F;

    //no clue if these goals even do anything lol
    int output, numOne = 0, numTwo = 4;
    private AnimationFactory factory = new AnimationFactory (this);
    public CrowEntity(EntityType<? extends CrowEntity> entityType, World world) {
        super(entityType, world);
        this.moveControl = new FlightMoveControl(this, 10, false);
        this.setPathfindingPenalty(PathNodeType.DANGER_FIRE, -1.0F);
        this.setPathfindingPenalty(PathNodeType.DAMAGE_FIRE, -1.0F);
    }

    @Nullable
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        if (entityData == null) {
            entityData = new PassiveEntity.PassiveData(false);
        }
        if (world instanceof ServerWorld) {
            this.addTypeSpecificGoals();
        }

        return super.initialize(world, difficulty, spawnReason, (EntityData)entityData, entityNbt);
    }

    //Follow Undead
    //Follow Crops...?

    public boolean isBaby() {
        return false;
    }

    protected void initGoals() {
        this.followChickenAndRabbitGoal = new ActiveTargetGoal(this, AnimalEntity.class, 10, false, false, (entity) -> {
            return entity instanceof ChickenEntity || entity instanceof RabbitEntity;
        });
        this.goalSelector.add(0, new EscapeDangerGoal(this, 1.25));
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(0, new EatBeetrootCropGoal(this));
        this.goalSelector.add(1, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(2, new TemptGoal(this, 1.2, BREEDING_INGREDIENT, false));
        this.goalSelector.add(3, new WanderAroundFarGoal(this, 1.0));
        this.goalSelector.add(4, new FlyOntoTreeGoal(this, 1.3));
        //this.goalSelector.add(5, new FollowMobGoal(this, 1.25, 3.0F, 20.0F));
        //removed because all crows in an area would fly to it
        this.goalSelector.add(6, new LookAroundGoal(this));
        this.goalSelector.add(7, new GoToVillageGoal(32, 1000));

    }

    private void addTypeSpecificGoals() {
            this.targetSelector.add(4, this.followChickenAndRabbitGoal);
        }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (event.isMoving() && !isInAir()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.crow.walk", true));
            return PlayState.CONTINUE;
        }
        else if(isInAir()){
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.crow.fly", true));
            return PlayState.CONTINUE;
        }
        else if(isBeingRainedOn()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.crow.wash", true));
            return PlayState.CONTINUE;
        }
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.crow.idle", true));
        return PlayState.CONTINUE;

    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController(this, "controller",
                0, this::predicate));
    }



    @Override
    public AnimationFactory getFactory() {
        return factory;
    }
    private static final Ingredient BREEDING_INGREDIENT;
    static {
        BREEDING_INGREDIENT = Ingredient.ofItems(new ItemConvertible[]{Items.BEETROOT_SEEDS, Items.ROTTEN_FLESH});
    }
    @Override
    protected SoundEvent getAmbientSound() {
        output = (int) ((numTwo - numOne + 1) * Math.random() + numOne);
        if(output == 0) return ModSounds.CROW_IDLE;
        if(output == 1) return ModSounds.CROW_IDLE_0;
        if(output == 2) return ModSounds.CROW_IDLE_1;
        if(output == 3) return ModSounds.CROW_IDLE_2;
        if(output == 4) return ModSounds.CROW_IDLE_3;
        else return ModSounds.CROW_IDLE;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return ModSounds.CROW_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.CROW_DEATH;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.ENTITY_CHICKEN_STEP, 0.15f, 1.0f);
    }

    @Override
    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        return false;
    }

    @Override
    public boolean isInAir() {
        return !this.onGround;
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 6.0)
                .add(EntityAttributes.GENERIC_FLYING_SPEED, 0.8000000059604645)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 32.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.18000000298023224);
    }

    protected EntityNavigation createNavigation(World world) {
        BirdNavigation birdNavigation = new BirdNavigation(this, world);
        birdNavigation.setCanPathThroughDoors(false);
        birdNavigation.setCanSwim(true);
        birdNavigation.setCanEnterOpenDoors(true);
        return birdNavigation;
    }

    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return dimensions.height * 0.6F;
    }

    public void tickMovement() {


        super.tickMovement();
        this.flapWings();
    }

    private boolean isBeingRainedOn() {
        BlockPos blockPos = this.getBlockPos();
        return this.world.hasRain(blockPos) || this.world.hasRain(new BlockPos((double)blockPos.getX(), this.getBoundingBox().maxY, (double)blockPos.getZ()));
    }

    private void flapWings() {
        this.prevFlapProgress = this.flapProgress;
        this.prevMaxWingDeviation = this.maxWingDeviation;
        this.maxWingDeviation += (float)(!this.onGround && !this.hasVehicle() ? 4 : -1) * 0.3F;
        this.maxWingDeviation = MathHelper.clamp(this.maxWingDeviation, 0.0F, 1.0F);
        if (!this.onGround && this.flapSpeed < 1.0F) {
            this.flapSpeed = 1.0F;
        }

        this.flapSpeed *= 0.9F;
        Vec3d vec3d = this.getVelocity();
        if (!this.onGround && vec3d.y < 0.0) {
            this.setVelocity(vec3d.multiply(1.0, 0.6, 1.0));
        }

        this.flapProgress += this.flapSpeed * 2.0F;
    }

    public void mobTick(){
        if (this.moreCarrotTicks > 0) {
            this.moreCarrotTicks -= this.random.nextInt(3);
            if (this.moreCarrotTicks < 0) {
                this.moreCarrotTicks = 0;
            }
        }
    }

    @Nullable
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }

    protected boolean hasWings() {
        return this.speed > this.field_28640;
    }

    protected void addFlapEffects() {
        this.playSound(SoundEvents.ENTITY_PARROT_FLY, 0.15F, 1.0F);
        this.field_28640 = this.speed + this.maxWingDeviation / 2.0F;
    }

    public static float getSoundPitch(Random random) {
        return (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F;
    }

    public SoundCategory getSoundCategory() {
        return SoundCategory.NEUTRAL;
    }

    public boolean isPushable() {
        return true;
    }

    protected void pushAway(Entity entity) {
        if (!(entity instanceof PlayerEntity)) {
            super.pushAway(entity);
        }
    }

    public boolean damage(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        } else {


            return super.damage(source, amount);
        }
    }

    public Vec3d getLeashOffset() {
        return new Vec3d(0.0, (double)(0.5F * this.getStandingEyeHeight()), (double)(this.getWidth() * 0.4F));
    }

    private static class FlyOntoTreeGoal extends FlyGoal {
        public FlyOntoTreeGoal(PathAwareEntity pathAwareEntity, double d) {
            super(pathAwareEntity, d);
        }

        @Nullable
        protected Vec3d getWanderTarget() {
            Vec3d vec3d = null;
            if (this.mob.isTouchingWater()) {
                vec3d = FuzzyTargeting.find(this.mob, 70, 70);
            }

            if (this.mob.getRandom().nextFloat() >= this.probability) {
                vec3d = this.locateTree();
            }

            return vec3d == null ? super.getWanderTarget() : vec3d;
        }

        @Nullable
        private Vec3d locateTree() {
            BlockPos blockPos = this.mob.getBlockPos();
            BlockPos.Mutable mutable = new BlockPos.Mutable();
            BlockPos.Mutable mutable2 = new BlockPos.Mutable();
            Iterable<BlockPos> iterable = BlockPos.iterate(MathHelper.floor(this.mob.getX() - 4.0), MathHelper.floor(this.mob.getY() - 7.0), MathHelper.floor(this.mob.getZ() - 4.0), MathHelper.floor(this.mob.getX() + 4.0), MathHelper.floor(this.mob.getY() + 7.0), MathHelper.floor(this.mob.getZ() + 4.0));
            Iterator var5 = iterable.iterator();

            BlockPos blockPos2;
            boolean bl;
            do {
                do {
                    if (!var5.hasNext()) {
                        return null;
                    }

                    blockPos2 = (BlockPos)var5.next();
                } while(blockPos.equals(blockPos2));

                BlockState blockState = this.mob.world.getBlockState(mutable2.set(blockPos2, Direction.DOWN));
                bl = blockState.getBlock() instanceof LeavesBlock || blockState.isIn(BlockTags.LOGS);
            } while(!bl || !this.mob.world.isAir(blockPos2) || !this.mob.world.isAir(mutable.set(blockPos2, Direction.UP)));

            return Vec3d.ofBottomCenter(blockPos2);
        }
    }

    private class GoToVillageGoal extends net.minecraft.entity.ai.goal.GoToVillageGoal {
        public GoToVillageGoal(int unused, int searchRange) {
            super(CrowEntity.this, searchRange);
        }

        public void start() {
            super.start();
        }

        public boolean canStart() {
            return super.canStart() && this.canGoToVillage();
        }

        public boolean shouldContinue() {
            return super.shouldContinue() && this.canGoToVillage();
        }

        private boolean canGoToVillage() {
            return CrowEntity.this.getTarget() == null;
        }

    }

    boolean wantsCarrots() {
        return this.moreCarrotTicks == 0;
    }

    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        if (this.world instanceof ServerWorld) {
            this.addTypeSpecificGoals();
        }
        this.moreCarrotTicks = nbt.getInt("MoreCarrotTicks");
    }
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        NbtList nbtList = new NbtList();
        nbt.putInt("MoreCarrotTicks", this.moreCarrotTicks);
    }

    static class EatBeetrootCropGoal extends MoveToTargetPosGoal {
        private final CrowEntity crow;
        private boolean wantsCarrots;
        private boolean hasTarget;

        public EatBeetrootCropGoal(CrowEntity crow) {
            super(crow, 0.699999988079071, 16);
            this.crow = crow;
        }

        public boolean canStart() {
            if (this.cooldown <= 0) {
                if (!this.crow.world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) {
                    return false;
                }

                this.hasTarget = false;
                this.wantsCarrots = this.crow.wantsCarrots();
                this.wantsCarrots = true;
            }

            return super.canStart();
        }

        public boolean shouldContinue() {
            return this.hasTarget && super.shouldContinue();
        }

        public void tick() {
            super.tick();
            this.crow.getLookControl().lookAt((double)this.targetPos.getX() + 0.5, (double)(this.targetPos.getY() + 1), (double)this.targetPos.getZ() + 0.5, 10.0F, (float)this.crow.getMaxLookPitchChange());
            if (this.hasReached()) {
                World world = this.crow.world;
                BlockPos blockPos = this.targetPos.up();
                BlockState blockState = world.getBlockState(blockPos);
                Block block = blockState.getBlock();
                if (this.hasTarget && block instanceof Block) {
                    int i = (Integer)blockState.get(BeetrootsBlock.AGE);
                    if (i == 0) {
                        world.setBlockState(blockPos, Blocks.AIR.getDefaultState(), 2);
                        world.breakBlock(blockPos, true, this.crow);
                    } else {
                        world.setBlockState(blockPos, (BlockState)blockState.with(BeetrootsBlock.AGE, i - 1), 2);
                        world.syncWorldEvent(2001, blockPos, Block.getRawIdFromState(blockState));
                    }

                    this.crow.moreCarrotTicks = 40;
                }

                this.hasTarget = false;
                this.cooldown = 10;
            }

        }

        protected boolean isTargetPos(WorldView world, BlockPos pos) {
            BlockState blockState = world.getBlockState(pos);
            if (blockState.isOf(Blocks.FARMLAND) && this.wantsCarrots && !this.hasTarget) {
                blockState = world.getBlockState(pos.up());
                if (blockState.getBlock() instanceof BeetrootsBlock && ((BeetrootsBlock)blockState.getBlock()).isMature(blockState)) {
                    this.hasTarget = true;
                    return true;
                }
            }

            return false;
        }
    }

}
