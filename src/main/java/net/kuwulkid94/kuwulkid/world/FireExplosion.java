package net.kuwulkid94.kuwulkid.world;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

import java.util.*;

public class FireExplosion extends Explosion {
    public final Explosion.DestructionType destructionType;
    public final Random random;
    public final World world;
    public final double x;
    public final double y;
    public final double z;
    public final Entity entity;
    public final float power;
    public final DamageSource damageSource;
    public final List<BlockPos> affectedBlocks;
    public final Map<PlayerEntity, Vec3d> affectedPlayers;
    private boolean createFire = true;

    public FireExplosion(World world, Entity entity, double x, double y, double z, float power, Explosion.DestructionType destructionType) {
        super(world, entity, null, null, x, y, z, power, false, destructionType);
        this.random = new Random();
        this.affectedBlocks = Lists.newArrayList();
        this.affectedPlayers = Maps.newHashMap();
        this.world = world;
        this.entity = entity;
        this.power = power;
        this.x = x;
        this.y = y;
        this.z = z;
        this.destructionType = destructionType;
        this.damageSource = DamageSource.explosion(this);
    }
    public void affectWorld(boolean particles) {
        if (this.world.isClient) {
            this.world.playSound(this.x, this.y, this.z, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.BLOCKS, 4.0F, (1.0F + (this.world.random.nextFloat() - this.world.random.nextFloat()) * 0.2F) * 0.7F, false);
        }

        boolean bl = this.destructionType != DestructionType.NONE;
        if (particles) {
            if (!(this.power < 2.0F) && bl) {
                this.world.addParticle(ParticleTypes.EXPLOSION_EMITTER, this.x, this.y, this.z, 1.0, 0.0, 0.0);
            } else {
                this.world.addParticle(ParticleTypes.EXPLOSION, this.x, this.y, this.z, 1.0, 0.0, 0.0);
            }
        }

        if (bl) {
            ObjectArrayList<Pair<ItemStack, BlockPos>> objectArrayList = new ObjectArrayList();
            Collections.shuffle(this.affectedBlocks, (Random) this.world.random);
            Iterator var4 = this.affectedBlocks.iterator();

            while(var4.hasNext()) {
                BlockPos blockPos = (BlockPos)var4.next();
                BlockState blockState = this.world.getBlockState(blockPos);
                Block block = blockState.getBlock();
                if (!blockState.isAir()) {
                    BlockPos blockPos2 = blockPos.toImmutable();
                    this.world.getProfiler().push("explosion_blocks");
                    if (block.shouldDropItemsOnExplosion(this) && this.world instanceof ServerWorld) {
                        BlockEntity blockEntity = blockState.hasBlockEntity() ? this.world.getBlockEntity(blockPos) : null;
                        LootContext.Builder builder = (new LootContext.Builder((ServerWorld)this.world)).random(this.world.random).parameter(LootContextParameters.ORIGIN, Vec3d.ofCenter(blockPos)).parameter(LootContextParameters.TOOL, ItemStack.EMPTY).optionalParameter(LootContextParameters.BLOCK_ENTITY, blockEntity).optionalParameter(LootContextParameters.THIS_ENTITY, this.entity);
                        if (this.destructionType == DestructionType.DESTROY) {
                            builder.parameter(LootContextParameters.EXPLOSION_RADIUS, this.power);
                        }
                    }

                    this.world.setBlockState(blockPos, Blocks.AIR.getDefaultState(), 3);
                    block.onDestroyedByExplosion(this.world, blockPos, this);
                    this.world.getProfiler().pop();
                }
            }

            ObjectListIterator<Pair<ItemStack, BlockPos>> var12 = objectArrayList.iterator();

            while(var12.hasNext()) {
                Pair<ItemStack, BlockPos> pair = (Pair)var12.next();
                Block.dropStack(this.world, (BlockPos)pair.getSecond(), (ItemStack)pair.getFirst());
            }
        }

        if (this.createFire) {
            Iterator var11 = this.affectedBlocks.iterator();

            while(var11.hasNext()) {
                BlockPos blockPos3 = (BlockPos)var11.next();
                if (this.random.nextInt(3) == 0 && this.world.getBlockState(blockPos3).isAir() && this.world.getBlockState(blockPos3.down()).isOpaqueFullCube(this.world, blockPos3.down())) {
                    this.world.setBlockState(blockPos3, AbstractFireBlock.getState(this.world, blockPos3));
                }
            }
        }

            for (BlockPos blockPos3 : this.affectedBlocks) {
            if (this.random.nextInt(9) == 0 && this.world.getBlockState(blockPos3).isAir() && this.world.getBlockState(blockPos3.down()).isOpaqueFullCube(this.world, blockPos3.down())) {
                this.world.setBlockState(blockPos3, AbstractFireBlock.getState(this.world, blockPos3));
            }
        }

    }


}
