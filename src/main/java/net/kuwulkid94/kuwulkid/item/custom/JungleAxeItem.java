package net.kuwulkid94.kuwulkid.item.custom;

import net.kuwulkid94.kuwulkid.entity.ModEntities;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.server.world.ServerWorld;

public class JungleAxeItem extends SwordItem {
    int numStrikes = 0;

    //public static final EntityType<ThornEntity> THORNY;
    public JungleAxeItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Item.Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker){
        System.out.println("NumStrikes" + numStrikes);
        if(attacker.getAttacking().isOnGround() && !attacker.getAttacking().equals(ModEntities.THORN) && numStrikes % 4 == 0) {
            ModEntities.THORN.spawn((ServerWorld) attacker.getWorld(), null, null, null, attacker.getAttacking().getBlockPos(), SpawnReason.TRIGGERED, true, true);
            numStrikes = 0;
        }
        stack.damage(1, attacker, (e) -> {
            e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND);
        });
        numStrikes++;
        return true;
    }
}
