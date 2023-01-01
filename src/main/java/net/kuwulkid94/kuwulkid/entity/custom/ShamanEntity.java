package net.kuwulkid94.kuwulkid.entity.custom;

import net.kuwulkid94.kuwulkid.item.ModItems;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.passive.WanderingTraderEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.PotionEntity;
import net.minecraft.entity.raid.RaiderEntity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.village.TradeOffer;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class ShamanEntity extends WanderingTraderEntity implements IAnimatable, RangedAttackMob{
    private static final UUID DRINKING_SPEED_PENALTY_MODIFIER_ID = UUID.fromString("5CD17E52-A79A-43D3-A529-90FDE04B181E");
    private static final EntityAttributeModifier DRINKING_SPEED_PENALTY_MODIFIER;
    private static final TrackedData<Boolean> DRINKING;
    private int drinkTimeLeft;
    private AnimationFactory factory = new AnimationFactory (this);
    int numOne = 0, numTwo = 8;

    public ShamanEntity(EntityType<? extends WanderingTraderEntity > entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 20.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25);
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if(event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.shaman.walk", true));
            return PlayState.CONTINUE;
        }
        else if(this.isDrinking() == true){
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.shaman.drink", false));
            //System.out.println("Drinking!");
            return PlayState.CONTINUE;
        }
        else {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.shaman.idle", true));
            return PlayState.CONTINUE;
        }
    }


    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController(this, "controller", 0, this::predicate));

    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }


    protected void initGoals() {
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(2, new ProjectileAttackGoal(this, 1.0, 60, 10.0F));
        this.goalSelector.add(2, new TemptGoal(this, 1.2, BREEDING_INGREDIENT, false));
        this.goalSelector.add(5, new WanderAroundFarGoal(this, 1.0));
        this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(7, new LookAroundGoal(this));
        this.goalSelector.add(8, new LookAtEntityGoal(this, MobEntity.class, 8.0F));


        this.targetSelector.add(9, (new ActiveTargetGoal(this, MerchantEntity.class, true)).setMaxTimeWithoutVisibility(300));
        //this.targetSelector.add(2, (new ActiveTargetGoal(this, VillagerEntity.class, false)).setMaxTimeWithoutVisibility(300));
        this.targetSelector.add(2, (new ActiveTargetGoal(this, ZombieEntity.class, true)).setMaxTimeWithoutVisibility(300));
        //this.targetSelector.add(3, (new ActiveTargetGoal(this, ZombieVillagerEntity.class, false)).setMaxTimeWithoutVisibility(300));
        //this.targetSelector.add(3, (new ActiveTargetGoal(this, PillagerEntity.class, false)).setMaxTimeWithoutVisibility(300));
        //this.targetSelector.add(3, (new ActiveTargetGoal(this, VindicatorEntity.class, false)).setMaxTimeWithoutVisibility(300));
        this.targetSelector.add(2, (new ActiveTargetGoal(this, RaiderEntity.class, false)).setMaxTimeWithoutVisibility(300));
        //this.targetSelector.add(4, new ActiveTargetGoal(this, EvokerEntity.class, false));
        this.targetSelector.add(1, new RevengeGoal(this, new Class[]{MerchantEntity.class}));
    }

    @Override
    protected void afterUsing(TradeOffer offer) {
        if (offer.shouldRewardPlayerExperience()) {
            int i = 3 + this.random.nextInt(4);
            this.world.spawnEntity(new ExperienceOrbEntity(this.world, this.getX(), this.getY() + 0.5, this.getZ(), i));
        }

    }

    @Override
    protected void fillRecipes() {
    }


    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }

    protected void initDataTracker() {
        super.initDataTracker();
        this.getDataTracker().startTracking(DRINKING, false);
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_VILLAGER_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_VILLAGER_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_VILLAGER_DEATH;
    }

    public void setDrinking(boolean drinking) {
        this.getDataTracker().set(DRINKING, drinking);
    }

    public boolean isDrinking() {
        return (Boolean)this.getDataTracker().get(DRINKING);
    }

    public void tickMovement() {
        if (!this.world.isClient && this.isAlive()) {

            if (this.isDrinking()) {
                if (this.drinkTimeLeft-- <= 0) {
                    this.setDrinking(false);
                    ItemStack itemStack = this.getMainHandStack();
                    this.equipStack(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
                    if (itemStack.isOf(Items.POTION)) {
                        List<StatusEffectInstance> list = PotionUtil.getPotionEffects(itemStack);
                        if (list != null) {
                            Iterator var3 = list.iterator();

                            while(var3.hasNext()) {
                                StatusEffectInstance statusEffectInstance = (StatusEffectInstance)var3.next();
                                this.addStatusEffect(new StatusEffectInstance(statusEffectInstance));
                            }
                        }
                    }

                    this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).removeModifier(DRINKING_SPEED_PENALTY_MODIFIER);
                }
            } else {
                Potion potion = null;
                if (this.random.nextFloat() < 0.05F && this.getHealth() < this.getMaxHealth() && !this.hasStatusEffect(StatusEffects.REGENERATION))
                    potion = Potions.REGENERATION;
                else if(this.random.nextFloat() < 0.05F && this.getHealth() <= 5) {
                    potion = Potions.INVISIBILITY;
                }
                if (potion != null) {
                    this.equipStack(EquipmentSlot.MAINHAND, PotionUtil.setPotion(new ItemStack(Items.POTION), potion));
                    this.drinkTimeLeft = this.getMainHandStack().getMaxUseTime();
                    this.setDrinking(true);
                    if (!this.isSilent()) {
                        this.world.playSound((PlayerEntity)null, this.getX(), this.getY(), this.getZ(), SoundEvents.ENTITY_WITCH_DRINK, this.getSoundCategory(), 1.0F, 0.8F + this.random.nextFloat() * 0.4F);
                    }

                    EntityAttributeInstance entityAttributeInstance = this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
                    entityAttributeInstance.removeModifier(DRINKING_SPEED_PENALTY_MODIFIER);
                    entityAttributeInstance.addTemporaryModifier(DRINKING_SPEED_PENALTY_MODIFIER);
                }
            }

            if (this.random.nextFloat() < 7.5E-4F) {
                this.world.sendEntityStatus(this, (byte)15);
            }
        }

        super.tickMovement();
    }

    public void handleStatus(byte status) {
        if (status == 15) {
            for(int i = 0; i < this.random.nextInt(35) + 10; ++i) {
                this.world.addParticle(ParticleTypes.HAPPY_VILLAGER, this.getX() + this.random.nextGaussian() * 0.12999999523162842, this.getBoundingBox().maxY + 0.5 + this.random.nextGaussian() * 0.12999999523162842, this.getZ() + this.random.nextGaussian() * 0.12999999523162842, 0.0, 0.0, 0.0);
            }
        } else {
            super.handleStatus(status);
        }

    }



    public void attack(LivingEntity target, float pullProgress) {
        if (!this.isDrinking()) {
            Vec3d vec3d = target.getVelocity();
            double d = target.getX() + vec3d.x - this.getX();
            double e = target.getEyeY() - 1.100000023841858 - this.getY();
            double f = target.getZ() + vec3d.z - this.getZ();
            double g = Math.sqrt(d * d + f * f);
            Potion potion = Potions.SLOWNESS;
            if (target instanceof MerchantEntity || target instanceof VillagerEntity) {
                if (target.getHealth() <= target.getMaxHealth()/2.0 ) {
                    potion = Potions.HEALING;
                }
                if(target.hasStatusEffect(StatusEffects.SLOWNESS))
                {
                    target.clearStatusEffects();
                    this.world.addParticle(ParticleTypes.ELECTRIC_SPARK, this.getX() + this.random.nextGaussian() * 0.12999999523162842, this.getBoundingBox().maxY + 0.5 + this.random.nextGaussian() * 0.12999999523162842, this.getZ() + this.random.nextGaussian() * 0.12999999523162842, 0.0, 0.0, 0.0);

                }
                else if (target.getHealth() < target.getMaxHealth() && !this.hasStatusEffect(StatusEffects.REGENERATION)) {
                    potion = Potions.REGENERATION;
                }
                else{
                    potion = Potions.LUCK;
                }
                this.setTarget((LivingEntity)null);
            }
            if (target instanceof ZombieEntity) {
                    potion = Potions.HEALING;
                this.setTarget((LivingEntity)null);
            }

            PotionEntity potionEntity = new PotionEntity(this.world, this);
            potionEntity.setItem(PotionUtil.setPotion(new ItemStack(Items.SPLASH_POTION), potion));
            potionEntity.setPitch(potionEntity.getPitch() - -20.0F);
            potionEntity.setVelocity(d, e + g * 0.2, f, 0.75F, 8.0F);

            if((target instanceof MerchantEntity) && (potion == Potions.SLOWNESS || potion == Potions.LUCK)) {
                System.out.println(target);
                System.out.println(potion);
                //this.world.spawnEntity(potionEntity);
            }
            else
            {
                this.world.spawnEntity(potionEntity);
                if (!this.isSilent()) {
                    this.world.playSound((PlayerEntity)null, this.getX(), this.getY(), this.getZ(), SoundEvents.ENTITY_SPLASH_POTION_THROW, this.getSoundCategory(), 1.0F, 0.8F + this.random.nextFloat() * 0.4F);
                }
            }
        }
    }

    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return 1.62F;
    }

    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (!itemStack.isOf(Items.VILLAGER_SPAWN_EGG) && this.isAlive() && !this.isBaby()) {
            if (hand == Hand.MAIN_HAND) {
                player.incrementStat(Stats.TALKED_TO_VILLAGER);
                if(player.getMainHandStack().getItem() == Items.SKELETON_SKULL) {
                    this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
                    this.world.addParticle(ParticleTypes.HAPPY_VILLAGER, this.getX() + this.random.nextGaussian() * 0.12999999523162842, this.getBoundingBox().maxY + 0.5 + this.random.nextGaussian() * 0.12999999523162842, this.getZ() + this.random.nextGaussian() * 0.12999999523162842, 0.0, 0.0, 0.0);
                    this.world.playSound((PlayerEntity)null, this.getX(), this.getY(), this.getZ(), SoundEvents.ENTITY_VILLAGER_CELEBRATE, this.getSoundCategory(), 1.0F, 0.8F + this.random.nextFloat() * 0.4F);
                    player.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 1200, 1), player);
                    BlockPos pos = this.getBlockPos();
                    world.spawnEntity(new ItemEntity(world, (double) pos.getX() + Math.random(), (double) pos.getY() + 1, (double) pos.getZ() + Math.random(), new ItemStack(ModItems.SKULL_MASK, 1)));
                    if(!player.isCreative() && !world.isClient) {
                        player.getMainHandStack().decrement(1);
                    }
                }

                if(player.getMainHandStack().getItem() == Items.EMERALD) {
                    this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
                    this.world.addParticle(ParticleTypes.HAPPY_VILLAGER, this.getX() + this.random.nextGaussian() * 0.12999999523162842, this.getBoundingBox().maxY + 0.5 + this.random.nextGaussian() * 0.12999999523162842, this.getZ() + this.random.nextGaussian() * 0.12999999523162842, 0.0, 0.0, 0.0);
                    this.world.playSound((PlayerEntity)null, this.getX(), this.getY(), this.getZ(), SoundEvents.ENTITY_VILLAGER_CELEBRATE, this.getSoundCategory(), 1.0F, 0.8F + this.random.nextFloat() * 0.4F);
                    BlockPos pos = this.getBlockPos();

                    int rand = (int) (((numTwo - numOne + 1) * Math.random() + numOne));
                    int randCount = (int) ((3 - numOne + 1) * Math.random() + numOne) + 1;
                    if(rand == 0 && !player.hasStatusEffect(StatusEffects.LUCK)) {
                        world.spawnEntity(new ItemEntity(world, (double) pos.getX() + Math.random(), (double) pos.getY() + 1, (double) pos.getZ() + Math.random(), new ItemStack(ModItems.SCORPION_STINGER, randCount)));
                    }
                    else if(rand == 1 && !player.hasStatusEffect(StatusEffects.LUCK)){
                        world.spawnEntity(new ItemEntity(world, (double) pos.getX() + Math.random(), (double) pos.getY() + 1, (double) pos.getZ() + Math.random(), new ItemStack(ModItems.CROW_FEATHER, randCount)));
                    }
                    else if(rand == 2 && !player.hasStatusEffect(StatusEffects.LUCK)){
                        world.spawnEntity(new ItemEntity(world, (double) pos.getX() + Math.random(), (double) pos.getY() + 1, (double) pos.getZ() + Math.random(), new ItemStack(Items.NETHER_WART, randCount)));
                    }
                    else if(rand == 3 && !player.hasStatusEffect(StatusEffects.LUCK)){
                        world.spawnEntity(new ItemEntity(world, (double) pos.getX() + Math.random(), (double) pos.getY() + 1, (double) pos.getZ() + Math.random(), new ItemStack(Items.GLOW_INK_SAC, randCount)));
                    }
                    else if(rand == 4 && !player.hasStatusEffect(StatusEffects.LUCK)){
                        world.spawnEntity(new ItemEntity(world, (double) pos.getX() + Math.random(), (double) pos.getY() + 1, (double) pos.getZ() + Math.random(), new ItemStack(Items.COPPER_INGOT, randCount)));
                    }
                    else if(rand == 5 && !player.hasStatusEffect(StatusEffects.LUCK)){
                        world.spawnEntity(new ItemEntity(world, (double) pos.getX() + Math.random(), (double) pos.getY() + 1, (double) pos.getZ() + Math.random(), new ItemStack(Items.PRISMARINE_SHARD, randCount)));
                    }
                    else if(rand == 6 && !player.hasStatusEffect(StatusEffects.LUCK)){
                        world.spawnEntity(new ItemEntity(world, (double) pos.getX() + Math.random(), (double) pos.getY() + 1, (double) pos.getZ() + Math.random(), new ItemStack(Items.PRISMARINE_CRYSTALS, randCount)));
                    }
                    else if(rand == 7 && !player.hasStatusEffect(StatusEffects.LUCK)){
                        world.spawnEntity(new ItemEntity(world, (double) pos.getX() + Math.random(), (double) pos.getY() + 1, (double) pos.getZ() + Math.random(), new ItemStack(Items.GLOWSTONE_DUST, randCount)));
                    }
                    else if(rand == 8 && !player.hasStatusEffect(StatusEffects.LUCK)){
                        world.spawnEntity(new ItemEntity(world, (double) pos.getX() + Math.random(), (double) pos.getY() + 1, (double) pos.getZ() + Math.random(), new ItemStack(Items.JUNGLE_SAPLING, randCount)));
                    }

                    //lucky items

                    else if(rand == 0 && player.hasStatusEffect(StatusEffects.LUCK)) {
                        world.spawnEntity(new ItemEntity(world, (double) pos.getX() + Math.random(), (double) pos.getY() + 1, (double) pos.getZ() + Math.random(), new ItemStack(Items.SCUTE, randCount)));
                    }
                    else if(rand == 1 && player.hasStatusEffect(StatusEffects.LUCK)){
                        world.spawnEntity(new ItemEntity(world, (double) pos.getX() + Math.random(), (double) pos.getY() + 1, (double) pos.getZ() + Math.random(), new ItemStack(Items.EXPERIENCE_BOTTLE, randCount)));
                    }
                    else if(rand == 2 && player.hasStatusEffect(StatusEffects.LUCK)){
                        world.spawnEntity(new ItemEntity(world, (double) pos.getX() + Math.random(), (double) pos.getY() + 1, (double) pos.getZ() + Math.random(), new ItemStack(Items.BLAZE_POWDER, randCount)));
                    }
                    else if(rand == 3 && player.hasStatusEffect(StatusEffects.LUCK)){
                        world.spawnEntity(new ItemEntity(world, (double) pos.getX() + Math.random(), (double) pos.getY() + 1, (double) pos.getZ() + Math.random(), new ItemStack(Items.GHAST_TEAR, randCount)));
                    }
                    else if(rand == 4 && player.hasStatusEffect(StatusEffects.LUCK)){
                        world.spawnEntity(new ItemEntity(world, (double) pos.getX() + Math.random(), (double) pos.getY() + 1, (double) pos.getZ() + Math.random(), new ItemStack(Items.AMETHYST_SHARD, randCount)));
                    }
                    else if(rand == 5 && player.hasStatusEffect(StatusEffects.LUCK)){
                        world.spawnEntity(new ItemEntity(world, (double) pos.getX() + Math.random(), (double) pos.getY() + 1, (double) pos.getZ() + Math.random(), new ItemStack(Items.NAUTILUS_SHELL, randCount)));
                    }
                    else if(rand == 6 && player.hasStatusEffect(StatusEffects.LUCK)){
                        world.spawnEntity(new ItemEntity(world, (double) pos.getX() + Math.random(), (double) pos.getY() + 1, (double) pos.getZ() + Math.random(), new ItemStack(ModItems.SCORPION_BAG, randCount)));
                    }
                    else if(rand == 7 && player.hasStatusEffect(StatusEffects.LUCK)){
                        world.spawnEntity(new ItemEntity(world, (double) pos.getX() + Math.random(), (double) pos.getY() + 1, (double) pos.getZ() + Math.random(), new ItemStack(Items.AXOLOTL_BUCKET, 1)));
                    }
                    else if(rand == 8 && player.hasStatusEffect(StatusEffects.LUCK)){
                        world.spawnEntity(new ItemEntity(world, (double) pos.getX() + Math.random(), (double) pos.getY() + 1, (double) pos.getZ() + Math.random(), new ItemStack(ModItems.CACTUS_FRUIT, randCount)));
                    }

                    else {
                        world.spawnEntity(new ItemEntity(world, (double) pos.getX() + Math.random(), (double) pos.getY() + 1, (double) pos.getZ() + Math.random(), new ItemStack(Items.EMERALD,1 )));
                    }

                    if(!player.isCreative() && !world.isClient) {
                        player.getMainHandStack().decrement(1);
                    }
                }

                if(player.getMainHandStack().getItem() == Items.EMERALD_BLOCK) {
                    this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
                    this.world.addParticle(ParticleTypes.HAPPY_VILLAGER, this.getX() + this.random.nextGaussian() * 0.12999999523162842, this.getBoundingBox().maxY + 0.5 + this.random.nextGaussian() * 0.12999999523162842, this.getZ() + this.random.nextGaussian() * 0.12999999523162842, 0.0, 0.0, 0.0);
                    this.world.playSound((PlayerEntity)null, this.getX(), this.getY(), this.getZ(), SoundEvents.ENTITY_VILLAGER_CELEBRATE, this.getSoundCategory(), 1.0F, 0.8F + this.random.nextFloat() * 0.4F);
                    BlockPos pos = this.getBlockPos();
                    int rand = (int) (((numTwo - numOne + 1) * Math.random() + numOne));

                    if(rand == 0 && player.hasStatusEffect(StatusEffects.LUCK)) {
                        world.spawnEntity(new ItemEntity(world, (double) pos.getX() + Math.random(), (double) pos.getY() + 1, (double) pos.getZ() + Math.random(), new ItemStack(Items.BUDDING_AMETHYST, 1)));
                    }
                    else if(rand == 1 ) {
                        world.spawnEntity(new ItemEntity(world, (double) pos.getX() + Math.random(), (double) pos.getY() + 1, (double) pos.getZ() + Math.random(), new ItemStack(Items.DEEPSLATE_DIAMOND_ORE, 2)));
                    }
                    else if(rand == 2 ) {
                        world.spawnEntity(new ItemEntity(world, (double) pos.getX() + Math.random(), (double) pos.getY() + 1, (double) pos.getZ() + Math.random(), new ItemStack(Items.DEEPSLATE_EMERALD_ORE, 2)));
                    }
                    else if(rand == 3 ) {
                        world.spawnEntity(new ItemEntity(world, (double) pos.getX() + Math.random(), (double) pos.getY() + 1, (double) pos.getZ() + Math.random(), new ItemStack(Items.DEEPSLATE_GOLD_ORE, 2)));
                    }
                    else if(rand == 4 ) {
                        world.spawnEntity(new ItemEntity(world, (double) pos.getX() + Math.random(), (double) pos.getY() + 1, (double) pos.getZ() + Math.random(), new ItemStack(Items.DEEPSLATE_LAPIS_ORE, 2)));
                    }
                    else if(rand == 5) {
                        world.spawnEntity(new ItemEntity(world, (double) pos.getX() + Math.random(), (double) pos.getY() + 1, (double) pos.getZ() + Math.random(), new ItemStack(Items.DEEPSLATE_IRON_ORE, 2)));
                    }
                    else if(rand == 6) {
                        world.spawnEntity(new ItemEntity(world, (double) pos.getX() + Math.random(), (double) pos.getY() + 1, (double) pos.getZ() + Math.random(), new ItemStack(Items.DEEPSLATE_COPPER_ORE, 2)));
                    }
                    else if(rand == 7) {
                        world.spawnEntity(new ItemEntity(world, (double) pos.getX() + Math.random(), (double) pos.getY() + 1, (double) pos.getZ() + Math.random(), new ItemStack(Items.DEEPSLATE_REDSTONE_ORE, 2)));
                    }
                    else
                    {
                        world.spawnEntity(new ItemEntity(world, (double) pos.getX() + Math.random(), (double) pos.getY() + 1, (double) pos.getZ() + Math.random(), new ItemStack(Items.EMERALD_BLOCK, 1)));
                    }

                    if(!player.isCreative() && !world.isClient) {
                        player.getMainHandStack().decrement(1);
                    }
                }



            }
            return ActionResult.success(this.world.isClient);
        } else {
            return super.interactMob(player, hand);
        }
    }


    private static final Ingredient BREEDING_INGREDIENT;
    static {
        BREEDING_INGREDIENT = Ingredient.ofItems(new ItemConvertible[]{Items.EMERALD_BLOCK, Items.EMERALD, Items.SKELETON_SKULL});
    }
    static {
        DRINKING_SPEED_PENALTY_MODIFIER = new EntityAttributeModifier(DRINKING_SPEED_PENALTY_MODIFIER_ID, "Drinking speed penalty", -0.25, EntityAttributeModifier.Operation.ADDITION);
        DRINKING = DataTracker.registerData(ShamanEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    }

}