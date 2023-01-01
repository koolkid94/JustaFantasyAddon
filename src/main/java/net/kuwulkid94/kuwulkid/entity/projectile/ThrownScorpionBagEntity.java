package net.kuwulkid94.kuwulkid.entity.projectile;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.kuwulkid94.kuwulkid.JustaFantasyAddonClientMod;
import net.kuwulkid94.kuwulkid.entity.ModEntities;
import net.kuwulkid94.kuwulkid.item.ModItems;
import net.kuwulkid94.kuwulkid.sounds.ModSounds;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.Packet;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ThrownScorpionBagEntity extends ThrownItemEntity {
    int numOne = 0, numTwo = 3;

    public ThrownScorpionBagEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    public ThrownScorpionBagEntity(World world, LivingEntity owner) {
        super(ModEntities.SCORPION_BAG_ENTITY, owner, world); // null will be changed later
    }

    public ThrownScorpionBagEntity(World world, double x, double y, double z) {
        super(ModEntities.SCORPION_BAG_ENTITY, x, y, z, world); // null will be changed later
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.SCORPION_BAG;
    }

    //Next 2 Methods Handle Particle Effects.
    @Environment(EnvType.CLIENT)
    private ParticleEffect getParticleParameters() { // Not entirely sure, but probably has do to with the thown entitie's particles. (OPTIONAL)
        ItemStack itemStack = this.getItem();
        return (ParticleEffect)(itemStack.isEmpty() ? ParticleTypes.FALLING_DUST : new ItemStackParticleEffect(ParticleTypes.ITEM, itemStack));
    }

    @Environment(EnvType.CLIENT)
    public void handleStatus(byte status) { // Also not entirely sure, but probably also has to do with the particles. This method (as well as the previous one) are optional, so if you don't understand, don't include this one.
        if (status == 3) {
            ParticleEffect particleEffect = this.getParticleParameters();

            for(int i = 0; i < 8; ++i) {
                this.world.addParticle(particleEffect, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
            }
        }

    }

    protected void onEntityHit(EntityHitResult entityHitResult) { // called on entity hit.
        super.onEntityHit(entityHitResult);
        Entity entity = entityHitResult.getEntity(); // sets a new Entity instance as the EntityHitResult (victim)
        entity.damage(DamageSource.thrownProjectile(this, this.getOwner()), (float)3); // deals damage

        if (entity instanceof LivingEntity livingEntity) { // checks if entity is an instance of LivingEntity (meaning it is not a boat or minecart)
            livingEntity.addStatusEffect((new StatusEffectInstance(StatusEffects.POISON, 100, 2))); // applies a status effect
            //ModEntities.SCORPION.spawn((ServerWorld) world, null, null, null, livingEntity.getBlockPos(), SpawnReason.TRIGGERED, true, true);
            livingEntity.playSound(ModSounds.SCORPION_ATTACK, (float) Math.random() * 2, (float) Math.random() * 2); // plays a sound for the entity hit only
        }
    }

    protected void onCollision(HitResult hitResult) { // called on collision with a block
        super.onCollision(hitResult);
        if (!this.world.isClient) { // checks if the world is client
            this.world.sendEntityStatus(this, (byte)3); // particle?
            ModEntities.SCORPION.spawn((ServerWorld) world, null, null, null, this.getBlockPos(), SpawnReason.TRIGGERED, true, true);
            BlockPos pos = this.getBlockPos();
            world.addParticle(ParticleTypes.LARGE_SMOKE, (double)pos.getX()+ Math.random(), (double)pos.getY()+ 1.5, (double)pos.getZ() + Math.random(),
                    Math.random() * (0.001) , Math.random()  * (0.2), Math.random()  * (0.001));
            world.addParticle(ParticleTypes.LARGE_SMOKE, (double)pos.getX()+ Math.random(), (double)pos.getY()+ 1.5, (double)pos.getZ() + Math.random(),
                    Math.random() * (0.001) , Math.random()  * (0.2), Math.random()  * (0.001));
            world.addParticle(ParticleTypes.LARGE_SMOKE, (double)pos.getX()+ Math.random(), (double)pos.getY()+ 1.5, (double)pos.getZ() + Math.random(),
                    Math.random() * (0.001) , Math.random()  * (0.2), Math.random()  * (0.001));
            EntityType.ITEM.spawn((ServerWorld) world, null, null, null, this.getBlockPos(), SpawnReason.TRIGGERED, true, true);

            System.out.println("SMOKING!!");

            if(1 == (int) ((numTwo - numOne + 1) * Math.random() + numOne)) {
                world.spawnEntity(new ItemEntity(world, (double) pos.getX() + Math.random(), (double) pos.getY() + 1, (double) pos.getZ() + Math.random(), new ItemStack(Items.BUNDLE, 1)));
            }
            this.kill(); // kills the projectile
        }

    }

    @Override
    public Packet<?> createSpawnPacket() {
        return EntitySpawnPacket.create(this, JustaFantasyAddonClientMod.PacketID);
    }
}
