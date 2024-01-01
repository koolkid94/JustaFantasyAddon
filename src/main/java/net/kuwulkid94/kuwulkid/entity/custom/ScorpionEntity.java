/*package net.kuwulkid94.kuwulkid.entity.custom;

import net.kuwulkid94.kuwulkid.entity.ModEntities;
import net.kuwulkid94.kuwulkid.entity.helper.Bundleable;
import net.kuwulkid94.kuwulkid.item.ModItems;
import net.kuwulkid94.kuwulkid.sounds.ModSounds;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.mob.SpiderEntity;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class ScorpionEntity extends AnimalEntity implements Bundleable, Monster, IAnimatable {

    MobEntity owner;
    private static final Ingredient BREEDING_INGREDIENT;
    private static final TrackedData<Boolean> FROM_BUCKET;
    static {
        BREEDING_INGREDIENT = Ingredient.ofItems(new ItemConvertible[]{ModItems.CACTUS_FRUIT});
        FROM_BUCKET = DataTracker.registerData(ScorpionEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    }
    private AnimationFactory factory = new AnimationFactory(this);

    public ScorpionEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return HostileEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 4.0)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 2.0f)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 2.0f)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 35.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.16f);

    }

    protected void initGoals() {
        this.goalSelector.add(0, new EscapeDangerGoal(this, 1.25));
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new TemptGoal(this, 1.2, BREEDING_INGREDIENT, false));
        this.goalSelector.add(2, new PounceAtTargetGoal(this, 0.5F));
        this.goalSelector.add(3, new WanderAroundFarGoal(this, 1.0));
        this.goalSelector.add(4, new LookAroundGoal(this));
        this.goalSelector.add(5, new MeleeAttackGoal(this,2.0, false));
        this.initCustomGoals();

        this.targetSelector.add(0, new AnimalMateGoal(this, 1d));
    }

    protected void initCustomGoals() {
        this.targetSelector.add(0, new RevengeGoal(this, new Class[0]));
        this.targetSelector.add(1, new ActiveTargetGoal(this, PlayerEntity.class, true));
        this.targetSelector.add(3, new ActiveTargetGoal(this, MerchantEntity.class, true));
        this.targetSelector.add(3, new ActiveTargetGoal(this, IronGolemEntity.class, true));

        this.targetSelector.add(3, new ActiveTargetGoal(this, SpiderEntity.class, true));
        this.targetSelector.add(3, new ActiveTargetGoal(this, CatEntity.class, true));
    }

    @Nullable
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return ModEntities.SCORPION.create(world);
    }

    public boolean isBreedingItem(ItemStack stack){
        return stack.getItem() == ModItems.CACTUS_FRUIT;
    }

    private <E extends IAnimatable> PlayState predicate(@NotNull AnimationEvent<E> event) {
        if(event.isMoving()){
            event.getController().setAnimation((new AnimationBuilder().addAnimation("animation.scorpion.walk", true)));
            return PlayState.CONTINUE;
        }
        else if(isInAir()){
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.scorpion.attack", true));
            return PlayState.CONTINUE;
        }

        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.scorpion.idle", true));
        return PlayState.CONTINUE;

    }

    public boolean isInAir() {
        return !this.onGround;
    }

    public EntityGroup getGroup() {
        return EntityGroup.ARTHROPOD;
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

    protected SoundEvent getAmbientSound() {
        return ModSounds.SCORPION_IDLE;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return ModSounds.SCORPION_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.SCORPION_HURT;
    }

    protected SoundEvent getAttackSound(){return ModSounds.SCORPION_ATTACK;}

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(ModSounds.SCORPION_WALK, 0.15f, 1.0f);
    }

    public boolean tryAttack(Entity target) {
        System.out.println("ATTACKING:" + target);
        if (super.tryAttack(target)) {
            if (target instanceof LivingEntity) {
                int i = 0;
                if (this.world.getDifficulty() == Difficulty.NORMAL) {
                    i = 7;
                } else if (this.world.getDifficulty() == Difficulty.HARD) {
                    i = 15;
                }

                if (i > 0) {
                    ((LivingEntity)target).addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, i * 20, 2), this);
                }
            }

            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean isFromBundle() {
        return (Boolean)this.dataTracker.get(FROM_BUCKET);
    }

    @Override
    public void setFromBundle(boolean fromBucket) {
        this.dataTracker.set(FROM_BUCKET, fromBucket);
    }

    public void copyDataToStack(ItemStack stack) {
        Bucketable.copyDataToStack(this, stack);
    }

    public void copyDataFromNbt(NbtCompound nbt) {
        Bucketable.copyDataFromNbt(this, nbt);
    }

    @Override
    public ItemStack getBundleItem() {
        return new ItemStack(ModItems.SCORPION_BAG);
    }

    @Override
    public SoundEvent getBundleedSound() {
        return ModSounds.SCORPION_ATTACK;
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        return (ActionResult) Bundleable.tryBundle(player, hand, this).orElse(super.interactMob(player, hand));
    }

    public void setOwner(MobEntity owner) {
        this.owner = owner;
    }


}
 */
