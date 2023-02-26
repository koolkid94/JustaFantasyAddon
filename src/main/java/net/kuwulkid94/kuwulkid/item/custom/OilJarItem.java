package net.kuwulkid94.kuwulkid.item.custom;

import net.kuwulkid94.kuwulkid.entity.projectile.ThrownOilJarEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class OilJarItem extends Item {
    public OilJarItem(Settings settings) {
        super(settings);
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        user.getItemCooldownManager().set(this, 8);
        ItemStack itemStack = user.getStackInHand(hand);
        world.playSound((PlayerEntity)null, user.getX(), user.getY(), user.getZ(), SoundEvents.BLOCK_HONEY_BLOCK_PLACE, SoundCategory.NEUTRAL, 0.5F, 0.4F / (world.getRandom().nextFloat() * 0.4F + 0.8F));
        if (!world.isClient) {
            //SnowballEntity snowballEntity = new SnowballEntity(world, user);
            ThrownOilJarEntity oil_jar = new ThrownOilJarEntity(world, user);
            oil_jar.setItem(itemStack);
            oil_jar.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F, 0.5F, 1.0F);
            //snowballEntity.setItem(itemStack);
            //snowballEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F, 0.5F, 0.1F);
            //world.spawnEntity(snowballEntity);
            world.spawnEntity(oil_jar);
            System.out.println("OIL THROWN!!");
        }

        user.incrementStat(Stats.USED.getOrCreateStat(this));
        if (!user.getAbilities().creativeMode) {
            itemStack.decrement(1);
        }

        return TypedActionResult.success(itemStack, world.isClient());
    }
}
