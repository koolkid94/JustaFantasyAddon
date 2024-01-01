/*package net.kuwulkid94.kuwulkid.item.custom;

//import net.kuwulkid94.kuwulkid.entity.projectile.ThrownScorpionBagEntity;
import net.kuwulkid94.kuwulkid.item.ModItems;
import net.minecraft.entity.player.PlayerEntity;
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
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        world.playSound((PlayerEntity)null, user.getX(), user.getY(), user.getZ(), SoundEvents.BLOCK_SAND_BREAK, SoundCategory.NEUTRAL, 0.5F, 0.4F / (world.getRandom().nextFloat() * 0.4F + 0.8F));
        user.getItemCooldownManager().set(this, 8);
        if (!world.isClient) {
            ThrownScorpionBagEntity scorpion_bag = new ThrownScorpionBagEntity(world, user);
            scorpion_bag.setItem(ModItems.SCORPION_BAG.getDefaultStack());
            scorpion_bag.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F, 1.5F, 1.0F);
            world.spawnEntity(scorpion_bag);
        }

        user.incrementStat(Stats.USED.getOrCreateStat(this));
        if (!user.getAbilities().creativeMode) {
            itemStack.decrement(1);
        }

        return TypedActionResult.success(itemStack, world.isClient());
    }
}
 */
