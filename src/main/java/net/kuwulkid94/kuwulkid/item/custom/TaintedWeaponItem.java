package net.kuwulkid94.kuwulkid.item.custom;

import net.kuwulkid94.kuwulkid.effect.ModEffects;
import net.kuwulkid94.kuwulkid.item.ModArmorMaterials;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;

public class TaintedWeaponItem extends HeavyWeaponItem{

    public TaintedWeaponItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker){
        stack.damage(1, attacker, (e) -> {
            e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND);
        });
        target.addStatusEffect(new StatusEffectInstance(ModEffects.TAINTED, 40, 0), attacker);
        if(attacker instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) attacker;
            ArmorItem helmet = ((ArmorItem)player.getInventory().getArmorStack(3).getItem());
            if(helmet.getMaterial().equals(ModArmorMaterials.bone) || helmet.toString().equals("skull_mask"))
            {
                player.getInventory().damageArmor(DamageSource.MAGIC, 1f, new int[]{0, 1, 2, 3});
                target.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 100, 0), attacker);
                return true;
            }
        }
        return true;
    }
}
