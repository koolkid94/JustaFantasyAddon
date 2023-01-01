package net.kuwulkid94.kuwulkid.item.custom;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Position;
import net.minecraft.world.World;

public class GuardianLaserItem extends Item {

    public GuardianLaserItem(Item.Settings settings) {
        super(settings.maxDamage(201));
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity entityLiving, int remainingUseTicks) {
        //working on this right now, laser implementation
        if(remainingUseTicks <= 20) {
            Position pos = entityLiving.getEyePos();
            world.addParticle(ParticleTypes.ELECTRIC_SPARK, (double) pos.getX(), (double) pos.getY(), (double) pos.getZ(), 0, 0, 0);
            HitResult target = entityLiving.raycast(20, 0, false);
            System.out.println("SUS" + HitResult.Type.values());
            //working on detecting entity hits
            if(EntityHitResult.Type.ENTITY == target.getType())
            {
                System.out.println("HEWWO");
                EntityHitResult targetEntity = (EntityHitResult) target;
                targetEntity.getEntity().damage(DamageSource.MAGIC,6);
            }
            Position targetPos = target.getPos();
            world.addParticle(ParticleTypes.GLOW, (double) targetPos.getX(), (double) targetPos.getY(), (double) targetPos.getZ(), 0, 0, 0);
            world.playSound((PlayerEntity) null, entityLiving.getBlockPos(), SoundEvents.ENTITY_GUARDIAN_ATTACK, SoundCategory.PLAYERS, 0.5F, 2.6F + (world.random.nextFloat() - world.random.nextFloat()) * 0.8F);

        }
        }

    @Override //this is what lets the on stopped using method work
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        user.setCurrentHand(hand);
        return TypedActionResult.consume(itemStack);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        System.out.println("LASER USE ON BLOCK");
        return super.useOnBlock(context);
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        System.out.println("LASER USE ON ENTITY");
        return super.useOnEntity(stack, user, entity, hand);
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.CROSSBOW;
    }

    public boolean isUsedOnRelease(ItemStack stack) {
        return stack.isOf(this);
    }

    public int getMaxUseTime(ItemStack stack) {
        return 40;
    }


}

