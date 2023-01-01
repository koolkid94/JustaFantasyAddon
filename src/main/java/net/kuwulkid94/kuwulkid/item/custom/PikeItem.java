package net.kuwulkid94.kuwulkid.item.custom;

import net.kuwulkid94.kuwulkid.item.ModArmorMaterials;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;

public class PikeItem extends SwordItem {

    public PikeItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }
    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.damage(1, attacker, (e) -> {
            e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND);
        });
        if(attacker instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) attacker;
            ArmorItem helmet = ((ArmorItem)player.getInventory().getArmorStack(3).getItem());
            if(helmet.getMaterial() == ModArmorMaterials.bone)
            {
                player.getInventory().damageArmor(DamageSource.MAGIC, 1f, new int[]{0, 1, 2, 3});
                target.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 100, 0), attacker);
                //return true;
            }
        }
        System.out.println("SMACK!");
        stack.damage(1, attacker, (e) -> {
            e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND);
        });
        int numTwo = 3;
        int numOne = 0;
        int output = (int) ((numTwo - numOne + 1) * Math.random() + numOne);
        //funnie
        if(output == 2 && target.getMaxHealth() <= 10)
        {
            System.out.println("INSTA KILL!");
            System.out.println("Target Health: " + target.getMaxHealth());
            target.kill();
        }
        attacker.takeKnockback(10,10 ,10);
        return true;
    }

}
//print statements for debugging