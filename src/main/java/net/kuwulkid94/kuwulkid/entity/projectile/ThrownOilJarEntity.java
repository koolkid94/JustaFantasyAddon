package net.kuwulkid94.kuwulkid.entity.projectile;

import net.kuwulkid94.kuwulkid.entity.ModEntities;
import net.kuwulkid94.kuwulkid.item.ModItems;
import net.kuwulkid94.kuwulkid.world.FireExplosion;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

public class ThrownOilJarEntity extends ThrownItemEntity {

    int numOne = 0, numTwo = 3;

    public ThrownOilJarEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    public ThrownOilJarEntity(World world, LivingEntity owner) {
        super(ModEntities.OIL_JAR_ENTITY, owner, world); // null will be changed later
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.OIL_JAR;
    }



    protected void onEntityHit(EntityHitResult entityHitResult) { // called on entity hit.
        super.onEntityHit(entityHitResult);
        Entity entity = entityHitResult.getEntity(); // sets a new Entity instance as the EntityHitResult (victim)
        entity.damage(DamageSource.thrownProjectile(this, this.getOwner()), (float)8); // deals damage
        entity.setOnFire(true);
        entity.setFireTicks(160);

        if (entity instanceof LivingEntity livingEntity) { // checks if entity is an instance of LivingEntity (meaning it is not a boat or minecart)
            //livingEntity.addStatusEffect((new StatusEffectInstance(ModEffects.VULNERABILITY, 100, 2))); // applies a status effect
            livingEntity.playSound(SoundEvents.BLOCK_HONEY_BLOCK_SLIDE, (float) Math.random() * 2, (float) Math.random() * 2); // plays a sound for the entity hit only
        }
    }

    protected void onCollision(HitResult hitResult) { // called on collision with a block
        super.onCollision(hitResult);
        if (!this.world.isClient) { // checks if the world is client
            this.world.sendEntityStatus(this, (byte)3); // particle?
            BlockPos pos = this.getBlockPos();
            world.addParticle(ParticleTypes.FLAME, (double) pos.getX() + Math.random(), (double) pos.getY() + 0.2, (double) pos.getZ() + Math.random(), Math.random() * (0.001), Math.random() * (0.2), Math.random() * (0.001));
            world.addParticle(ParticleTypes.FLAME, (double) pos.getX() + Math.random(), (double) pos.getY() + 0.2, (double) pos.getZ() + Math.random(), Math.random() * (0.001), Math.random() * (0.2), Math.random() * (0.001));
            world.addParticle(ParticleTypes.FLAME, (double) pos.getX() + Math.random(), (double) pos.getY() + 0.2, (double) pos.getZ() + Math.random(), Math.random() * (0.001), Math.random() * (0.2), Math.random() * (0.001));

            this.world.createExplosion((Entity)null, this.getX(), this.getY(), this.getZ(), (float)2, true, true ? Explosion.DestructionType.NONE : Explosion.DestructionType.NONE);
            //this.world.createExplosion((Entity)null, this.getX(), this.getY(), this.getZ(), (float)0.75, true, true ? Explosion.DestructionType.DESTROY : Explosion.DestructionType.DESTROY);
            FireExplosion explosion = new FireExplosion(world, this, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 1f, Explosion.DestructionType.DESTROY);

            for (BlockPos blockPos3 : explosion.affectedBlocks) {
                if (this.random.nextInt(9) == 0 && this.world.getBlockState(blockPos3).isAir() && this.world.getBlockState(blockPos3.down()).isOpaqueFullCube(this.world, blockPos3.down())) {
                    this.world.setBlockState(blockPos3, AbstractFireBlock.getState(this.world, blockPos3));
                }
            }

            this.discard();

            this.kill(); // kills the projectile
        }

    }

    @Override
    public Packet<?> createSpawnPacket() {
        /*PacketByteBuf packet = new PacketByteBuf(Unpooled.buffer());

        //entity position
        packet.writeDouble(getX());
        packet.writeDouble(getY());
        packet.writeDouble(getZ());

        //entity id & uuid
        packet.writeInt(this.getEntityId());

        return super.createSpawnPacket(); */
        return new EntitySpawnS2CPacket(this);
    }


    
}
