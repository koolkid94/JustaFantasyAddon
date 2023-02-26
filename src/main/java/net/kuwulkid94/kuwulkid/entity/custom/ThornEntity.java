package net.kuwulkid94.kuwulkid.entity.custom;

import net.kuwulkid94.kuwulkid.effect.ModEffects;
import net.kuwulkid94.kuwulkid.sounds.ModSounds;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class ThornEntity extends PathAwareEntity implements IAnimatable {

    private LivingEntity owner;
    private int warmup;

    private int ticksLeft = 120;
    @Nullable
    private UUID ownerUuid;

    private static TrackedData<Integer> SPAWN_TIMER = null;
    private static final TrackedData<Integer> ANIMATION;
    private final AnimationBuilder DEATH_ANIMATION = new AnimationBuilder().addAnimation("animation.thorn.death", ILoopType.EDefaultLoopTypes.HOLD_ON_LAST_FRAME);
    private final AnimationBuilder SPAWN_ANIMATION = new AnimationBuilder().addAnimation("animation.thorn.spawn", ILoopType.EDefaultLoopTypes.HOLD_ON_LAST_FRAME);
    private final AnimationBuilder ATTACK_ANIMATION = new AnimationBuilder().addAnimation("animation.thorn.attack", ILoopType.EDefaultLoopTypes.LOOP);

    int output, numOne = 0, numTwo = 2;
    private AnimationFactory factory = GeckoLibUtil.createFactory(this);
    public ThornEntity(EntityType<? extends ThornEntity> entityType, World world) {
        super(entityType, world);
    }

    @Nullable
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        if (entityData == null) {
            entityData = new PassiveEntity.PassiveData(false);
        }

        return super.initialize(world, difficulty, spawnReason, (EntityData)entityData, entityNbt);
    }

    protected void initDataTracker() {
        this.dataTracker.startTracking(SPAWN_TIMER, 32);
        super.initDataTracker();
    }

    public boolean isBaby() {
        return false;
    }

    protected void initGoals() {

    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (getSpawnTimer() > 0) {
            event.getController().setAnimation(SPAWN_ANIMATION);
            return PlayState.CONTINUE;
        }
        else if (this.isDead() || this.getHealth() < 0.1) {
            event.getController().setAnimation(DEATH_ANIMATION);
            return PlayState.CONTINUE;
        }

        event.getController().setAnimation(ATTACK_ANIMATION);
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

    @Override
    protected SoundEvent getAmbientSound() {
        output = (int) ((numTwo - numOne + 1) * Math.random() + numOne);
        if(output == 0)
            return ModSounds.THORN;
        if(output == 1)
            return ModSounds.THORN_1;
        else
            return ModSounds.THORN_0;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        output = (int) ((numTwo - numOne + 1) * Math.random() + numOne);
        if(output == 1)
            return ModSounds.UPROOT;
        else
            return ModSounds.UPROOT_1;
    }

    @Override
    protected SoundEvent getDeathSound() {
        output = (int) ((numTwo - numOne + 1) * Math.random() + numOne);
        if(output == 1)
            return ModSounds.UPROOT_0;
        else
            return ModSounds.UPROOT;
    }

    @Override
    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        return false;
    }

    public boolean isAffectedBySplashPotions() {
        return false;
    }

    public boolean hasNoGravity() {
        return true;
    }

    public boolean isFireImmune() {
        return true;
    }

    protected boolean shouldSwimInFluids() {
        return false;
    }

    @Override
    public void takeKnockback(double strength, double x, double z) {
        //sussys
    }

    public boolean isPushedByFluids() {
        return false;
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 6.0);
    }

    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return dimensions.height * 0.6F;
    }

    public static float getSoundPitch(Random random) {
        return (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F;
    }

    public SoundCategory getSoundCategory() {
        return SoundCategory.NEUTRAL;
    }

    public boolean isPushable() {
        return false;
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
   //dababy


    protected void mobTick(){
        if (this.getSpawnTimer() > 0) {
            this.setSpawnTimer(getSpawnTimer() - 1);
        }
    }

    public void tick() {
        if(ticksLeft <= 120) {
            super.tick();
            List<LivingEntity> list = this.world.getNonSpectatingEntities(LivingEntity.class, this.getBoundingBox().expand(0.2, 0.0, 0.2));
            Iterator var15 = list.iterator();
            while (var15.hasNext()) {
                LivingEntity livingEntity = (LivingEntity) var15.next();
                this.addEffect(livingEntity,this);
                if(ticksLeft % 30 == 0) {
                    this.damage(livingEntity, this);
                }
            }
        }
        if(ticksLeft <= 0 || this.isDead())
        {
            List<LivingEntity> list = this.world.getNonSpectatingEntities(LivingEntity.class, this.getBoundingBox().expand(0.2, 0.0, 0.2));
            Iterator var15 = list.iterator();
            while (var15.hasNext())
            {
                LivingEntity livingEntity = (LivingEntity) var15.next();
                if(livingEntity.isAlive()) {
                    livingEntity.removeStatusEffect(ModEffects.ENSNARED);
                }
            }
            this.kill();
            //this.discard();
        }
        ticksLeft--;
    }

    private void damage(LivingEntity target, ThornEntity thorny) {
        LivingEntity livingEntity = this.getOwner();
        if (target.isAlive() && !target.isInvulnerable() && target != thorny ) {
            if (livingEntity == null) {
                target.damage(DamageSource.MAGIC, 2.0F);
                //target.addStatusEffect(new StatusEffectInstance(ModEffects.ENSNARED, 1, 1), livingEntity);
            } else {
                if (livingEntity.isTeammate(target)) {
                    return;
                }

                target.damage(DamageSource.magic(this, livingEntity), 2.0F);
            }

        }
    }

    private void addEffect(LivingEntity target, ThornEntity thorny){
        LivingEntity livingEntity = this.getOwner();
        if (target.isAlive() && !target.isInvulnerable() && target != thorny ) {
            if (livingEntity == null) {
                target.addStatusEffect(new StatusEffectInstance(ModEffects.ENSNARED, 240, 0), livingEntity);
            } else {
                if (livingEntity.isTeammate(target)) {
                    return;
                }

                target.addStatusEffect(new StatusEffectInstance(ModEffects.ENSNARED, 240, 0), livingEntity);
            }

        }

    }

    public void setOwner(@Nullable LivingEntity owner) {
        this.owner = owner;
        this.ownerUuid = owner == null ? null : owner.getUuid();
    }

    @Nullable
    public LivingEntity getOwner() {
        if (this.owner == null && this.ownerUuid != null && this.world instanceof ServerWorld) {
            Entity entity = ((ServerWorld)this.world).getEntity(this.ownerUuid);
            if (entity instanceof LivingEntity) {
                this.owner = (LivingEntity)entity;
            }
        }

        return this.owner;
    }
    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        this.warmup = nbt.getInt("Warmup");
        if (nbt.containsUuid("Owner")) {
            this.ownerUuid = nbt.getUuid("Owner");
        }

    }
    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        nbt.putInt("Warmup", this.warmup);
        if (this.ownerUuid != null) {
            nbt.putUuid("Owner", this.ownerUuid);
        }

    }
    public void setSpawnTimer(int ticks) {
        this.dataTracker.set(SPAWN_TIMER, ticks);
    }

    public int getSpawnTimer() {
        return (Integer) this.dataTracker.get(SPAWN_TIMER);
    }

    public int getAnimationState() {
        return this.dataTracker.get(ANIMATION);
    }

    public void setAnimationState(int state) {
        this.dataTracker.set(ANIMATION, state);
    }

    @Override
    public void onDeath(DamageSource damageSource) {

        if (!this.isRemoved() && !this.dead) {
            Entity entity = damageSource.getAttacker();
            LivingEntity livingEntity = this.getPrimeAdversary();
            if (this.scoreAmount >= 0 && livingEntity != null) {
                livingEntity.updateKilledAdvancementCriterion(this, this.scoreAmount, damageSource);
            }

            this.dead = true;
            this.getDamageTracker().update();
            if (this.world instanceof ServerWorld) {
                if (entity == null || entity.onKilledOther((ServerWorld)this.world, this)) {
                    this.emitGameEvent(GameEvent.ENTITY_DIE);
                    this.drop(damageSource);
                    this.onKilledBy(livingEntity);
                }

                this.world.sendEntityStatus(this, (byte)3);
            }
        }
        this.setPose(EntityPose.STANDING);
    }

    static {
        SPAWN_TIMER = DataTracker.registerData(ThornEntity.class, TrackedDataHandlerRegistry.INTEGER);
        ANIMATION = DataTracker.registerData(ThornEntity.class, TrackedDataHandlerRegistry.INTEGER);
    }
}
