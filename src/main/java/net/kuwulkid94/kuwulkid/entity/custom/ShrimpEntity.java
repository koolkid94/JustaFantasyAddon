package net.kuwulkid94.kuwulkid.entity.custom;

import net.kuwulkid94.kuwulkid.sounds.ModSounds;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.WaterCreatureEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvent;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class ShrimpEntity extends WaterCreatureEntity implements IAnimatable {
    private AnimationFactory factory = new AnimationFactory(this);
    public float tiltAngle;
    public float prevTiltAngle;
    public float rollAngle;
    public float prevRollAngle;
    public float thrustTimer;
    public float prevThrustTimer;
    public float tentacleAngle;
    public float prevTentacleAngle;
    private float swimVelocityScale;
    private float thrustTimerSpeed;
    private float turningSpeed;
    private float swimX;
    private float swimY;
    private float swimZ;

    public ShrimpEntity(EntityType<? extends ShrimpEntity> entityType, World world) {
        super(entityType, world);
        this.random.setSeed((long)this.getId());
        this.thrustTimerSpeed = 1.0F / (this.random.nextFloat() + 1.0F) * 0.2F;
    }

    private <E extends IAnimatable> PlayState predicate(@NotNull AnimationEvent<E> event) {
        if (isTouchingWater())
        {
            event.getController().setAnimation((new AnimationBuilder().addAnimation("animation.shrimp.swim", true)));
            return PlayState.CONTINUE;
        }

            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.shrimp.idle", true));
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

    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new EscapeAttackerGoal());
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 4.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.18f);
    }

    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return dimensions.height * 0.5F;
    }

    protected SoundEvent getAmbientSound() {
        return ModSounds.SHRIMP_IDLE;
    }


    public boolean canBeLeashedBy(PlayerEntity player) {
        return !this.isLeashed();
    }

    protected float getSoundVolume() {
        return 0.4F;
    }

    protected Entity.MoveEffect getMoveEffect() {
        return MoveEffect.EVENTS;
    }

    public void tickMovement() {
        super.tickMovement();
        this.prevTiltAngle = this.tiltAngle;
        this.prevRollAngle = this.rollAngle;
        this.prevThrustTimer = this.thrustTimer;
        this.prevTentacleAngle = this.tentacleAngle;
        this.thrustTimer += this.thrustTimerSpeed;
        if ((double)this.thrustTimer > 6.283185307179586) {
            if (this.world.isClient) {
                this.thrustTimer = 6.2831855F;
            } else {
                this.thrustTimer -= 6.2831855F;
                if (this.random.nextInt(10) == 0) {
                    this.thrustTimerSpeed = 1.0F / (this.random.nextFloat() + 1.0F) * 0.2F;
                }

                this.world.sendEntityStatus(this, (byte)19);
            }
        }

        if (this.isInsideWaterOrBubbleColumn()) {
            if (this.thrustTimer < 3.1415927F) {
                float f = this.thrustTimer / 3.1415927F;
                this.tentacleAngle = MathHelper.sin(f * f * 3.1415927F) * 3.1415927F * 0.25F;
                if ((double)f > 0.75) {
                    this.swimVelocityScale = 1.0F;
                    this.turningSpeed = 1.0F;
                } else {
                    this.turningSpeed *= 0.8F;
                }
            } else {
                this.tentacleAngle = 0.0F;
                this.swimVelocityScale *= 0.9F;
                this.turningSpeed *= 0.99F;
            }

            if (!this.world.isClient) {
                this.setVelocity((double)(this.swimX * this.swimVelocityScale), (double)(this.swimY * this.swimVelocityScale), (double)(this.swimZ * this.swimVelocityScale));
            }

            Vec3d vec3d = this.getVelocity();
            double d = vec3d.horizontalLength();
            this.bodyYaw += (-((float)MathHelper.atan2(vec3d.x, vec3d.z)) * 57.295776F - this.bodyYaw) * 0.1F;
            this.setYaw(this.bodyYaw);
            this.rollAngle += 3.1415927F * this.turningSpeed * 1.5F;
            this.tiltAngle += (-((float)MathHelper.atan2(d, vec3d.y)) * 57.295776F - this.tiltAngle) * 0.1F;
        } else {
            this.tentacleAngle = MathHelper.abs(MathHelper.sin(this.thrustTimer)) * 3.1415927F * 0.25F;
            if (!this.world.isClient) {
                double e = this.getVelocity().y;
                if (this.hasStatusEffect(StatusEffects.LEVITATION)) {
                    e = 0.05 * (double)(this.getStatusEffect(StatusEffects.LEVITATION).getAmplifier() + 1);
                } else if (!this.hasNoGravity()) {
                    e -= 0.08;
                }

                this.setVelocity(0.0, e * 0.9800000190734863, 0.0);
            }

            this.tiltAngle += (-90.0F - this.tiltAngle) * 0.02F;
        }

    }

    public boolean damage(DamageSource source, float amount) {
        if (super.damage(source, amount) && this.getAttacker() != null) {
            if (!this.world.isClient) {
            }

            return true;
        } else {
            return false;
        }
    }

    private Vec3d applyBodyRotations(Vec3d shootVector) {
        Vec3d vec3d = shootVector.rotateX(this.prevTiltAngle * 0.017453292F);
        vec3d = vec3d.rotateY(-this.prevBodyYaw * 0.017453292F);
        return vec3d;
    }



    public void travel(Vec3d movementInput) {
        this.move(MovementType.SELF, this.getVelocity());
    }

    public void handleStatus(byte status) {
        if (status == 19) {
            this.thrustTimer = 0.0F;
        } else {
            super.handleStatus(status);
        }

    }

    public void setSwimmingVector(float x, float y, float z) {
        this.swimX = x;
        this.swimY = y;
        this.swimZ = z;
    }

    public boolean hasSwimmingVector() {
        return this.swimX != 0.0F || this.swimY != 0.0F || this.swimZ != 0.0F;
    }

    class SwimGoal extends Goal {
        private final ShrimpEntity shrimp;

        public SwimGoal(ShrimpEntity shrimp) {
            this.shrimp = shrimp;
        }

        public boolean canStart() {
            return true;
        }

        public void tick() {
            int i = this.shrimp.getDespawnCounter();
            if (i > 100) {
                this.shrimp.setSwimmingVector(0.0F, 0.0F, 0.0F);
            } else if (this.shrimp.getRandom().nextInt(toGoalTicks(50)) == 0 || !this.shrimp.touchingWater || !this.shrimp.hasSwimmingVector()) {
                float f = this.shrimp.getRandom().nextFloat() * 6.2831855F;
                float g = MathHelper.cos(f) * 0.2F;
                float h = -0.1F + this.shrimp.getRandom().nextFloat() * 0.2F;
                float j = MathHelper.sin(f) * 0.2F;
                this.shrimp.setSwimmingVector(g, h, j);
            }

        }
    }

    class EscapeAttackerGoal extends Goal {
        private static final float field_30375 = 3.0F;
        private static final float field_30376 = 5.0F;
        private static final float field_30377 = 10.0F;
        private int timer;

        EscapeAttackerGoal() {
        }

        public boolean canStart() {
            LivingEntity livingEntity = ShrimpEntity.this.getAttacker();
            if (ShrimpEntity.this.isTouchingWater() && livingEntity != null) {
                return ShrimpEntity.this.squaredDistanceTo(livingEntity) < 100.0;
            } else {
                return false;
            }
        }

        public void start() {
            this.timer = 0;
        }

        public boolean shouldRunEveryTick() {
            return true;
        }

        public void tick() {
            ++this.timer;
            LivingEntity livingEntity = ShrimpEntity.this.getAttacker();
            if (livingEntity != null) {
                Vec3d vec3d = new Vec3d(ShrimpEntity.this.getX() - livingEntity.getX(), ShrimpEntity.this.getY() - livingEntity.getY(), ShrimpEntity.this.getZ() - livingEntity.getZ());
                BlockState blockState = ShrimpEntity.this.world.getBlockState(new BlockPos(ShrimpEntity.this.getX() + vec3d.x, ShrimpEntity.this.getY() + vec3d.y, ShrimpEntity.this.getZ() + vec3d.z));
                FluidState fluidState = ShrimpEntity.this.world.getFluidState(new BlockPos(ShrimpEntity.this.getX() + vec3d.x, ShrimpEntity.this.getY() + vec3d.y, ShrimpEntity.this.getZ() + vec3d.z));
                if (fluidState.isIn(FluidTags.WATER) || blockState.isAir()) {
                    double d = vec3d.length();
                    if (d > 0.0) {
                        vec3d.normalize();
                        double e = 3.0;
                        if (d > 5.0) {
                            e -= (d - 5.0) / 5.0;
                        }

                        if (e > 0.0) {
                            vec3d = vec3d.multiply(e);
                        }
                    }

                    if (blockState.isAir()) {
                        vec3d = vec3d.subtract(0.0, vec3d.y, 0.0);
                    }

                    ShrimpEntity.this.setSwimmingVector((float)vec3d.x / 20.0F, (float)vec3d.y / 20.0F, (float)vec3d.z / 20.0F);
                }

                if (this.timer % 10 == 5) {
                    ShrimpEntity.this.world.addParticle(ParticleTypes.BUBBLE, ShrimpEntity.this.getX(), ShrimpEntity.this.getY(), ShrimpEntity.this.getZ(), 0.0, 0.0, 0.0);
                }

            }
        }
    }
}
