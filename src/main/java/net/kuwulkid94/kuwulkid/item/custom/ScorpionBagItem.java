package net.kuwulkid94.kuwulkid.item.custom;

import net.kuwulkid94.kuwulkid.entity.projectile.ThrownScorpionBagEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.SnowballEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class ScorpionBagItem extends Item {
    public ScorpionBagItem(Settings settings) {
        super(settings);
    }

    //public boolean onClicked(ItemStack stack, ItemStack otherStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference) {
        //return false;
    //}

    //public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        //System.out.println("JOHN BIDETTE");
        //return ActionResult.PASS;
    //}

    //public UseAction getUseAction(ItemStack stack) {
        //System.out.println("2BT2");
        //return stack.getItem().isFood() ? UseAction.EAT : UseAction.NONE;
    //}

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        world.playSound((PlayerEntity)null, user.getX(), user.getY(), user.getZ(), SoundEvents.BLOCK_SAND_BREAK, SoundCategory.NEUTRAL, 0.5F, 0.4F / (world.getRandom().nextFloat() * 0.4F + 0.8F));
        System.out.println("SAND!!");
        if (!world.isClient) {
            SnowballEntity snowballEntity = new SnowballEntity(world, user);
            ThrownScorpionBagEntity scorpion_bag = new ThrownScorpionBagEntity(world, user);
            scorpion_bag.setItem(itemStack);
            scorpion_bag.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F, 1.5F, 1.0F);
            snowballEntity.setItem(itemStack);
            snowballEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F, 1.5F, 0F);
            world.spawnEntity(scorpion_bag);
            world.spawnEntity(snowballEntity);
            System.out.println("SAND THROWN!!");
        }

        user.incrementStat(Stats.USED.getOrCreateStat(this));
        if (!user.getAbilities().creativeMode) {
            itemStack.decrement(1);
        }

        return TypedActionResult.success(itemStack, world.isClient());
    }
}
