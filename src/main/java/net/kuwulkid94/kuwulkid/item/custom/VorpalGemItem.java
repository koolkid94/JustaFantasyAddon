package net.kuwulkid94.kuwulkid.item.custom;

import net.minecraft.entity.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;

public class VorpalGemItem extends SwordItem {
    int numOne = 0, numTwo = 6;
    public VorpalGemItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker){
        stack.damage(1, attacker, (e) -> {
            e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND);
        });

            EntityType.EXPERIENCE_ORB.spawn((ServerWorld) target.getWorld(), null, null, (PlayerEntity) attacker, attacker.getBlockPos(), SpawnReason.TRIGGERED, true, true);
            EntityType.EXPERIENCE_ORB.spawn((ServerWorld) target.getWorld(), null, null, (PlayerEntity) attacker, attacker.getBlockPos(), SpawnReason.TRIGGERED, true, true);

        if(1 == (int) ((numTwo - numOne + 1) * Math.random() + numOne)) {
            BlockPos pos = target.getBlockPos();
            attacker.getWorld().spawnEntity(new ItemEntity(attacker.getWorld(), (double) pos.getX() + Math.random(), (double) pos.getY() + 1, (double) pos.getZ() + Math.random(), new ItemStack(Items.AMETHYST_SHARD, 1)));
            stack.damage(10, attacker, (e) -> {
                e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND);
            });

            attacker.getWorld().playSound((PlayerEntity) attacker, attacker.getBlockPos(), SoundEvents.ENTITY_ITEM_BREAK, SoundCategory.PLAYERS, 1.0F, attacker.getWorld().random.nextFloat() * 0.4F + 0.8F);
        }
            target.getWorld().addParticle(ParticleTypes.TOTEM_OF_UNDYING, (double)attacker.getBlockPos().getX()+ Math.random(), (double)attacker.getBlockPos().getY()+ 1, (double)attacker.getBlockPos().getZ() + Math.random(), Math.random() * (0.001) , Math.random()  * (0.2), Math.random()  * (0.001));
            target.getWorld().addParticle(ParticleTypes.TOTEM_OF_UNDYING, (double)attacker.getBlockPos().getX()+ Math.random(), (double)attacker.getBlockPos().getY()+ 1, (double)attacker.getBlockPos().getZ() + Math.random(), Math.random() * (0.001) , Math.random()  * (0.2), Math.random()  * (0.001));
            target.getWorld().addParticle(ParticleTypes.TOTEM_OF_UNDYING, (double)attacker.getBlockPos().getX()+ Math.random(), (double)attacker.getBlockPos().getY()+ 1, (double)attacker.getBlockPos().getZ() + Math.random(), Math.random() * (0.001) , Math.random()  * (0.2), Math.random()  * (0.001));
        return true;
    }
}
