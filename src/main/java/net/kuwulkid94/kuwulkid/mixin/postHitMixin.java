package net.kuwulkid94.kuwulkid.mixin;

import net.kuwulkid94.kuwulkid.effect.ModEffects;
import net.kuwulkid94.kuwulkid.item.ModArmorMaterials;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MiningToolItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(MiningToolItem.class)
public class postHitMixin {
    @Overwrite()
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker){
        System.out.println("SMACKMIXIN!!");
        stack.damage(1, attacker, (e) -> {
            e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND);
        });
        if(attacker instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) attacker;
            System.out.println("PLAYER ENTITY!!" + player);
            ArmorItem helmet = ((ArmorItem)player.getInventory().getArmorStack(2).getItem());
            System.out.println("ARMOR" + helmet);
            if(helmet.getMaterial() == ModArmorMaterials.abyssal || helmet.toString().equals("necklace"))
            {
                System.out.println("BONE!!!");
                player.getInventory().damageArmor(DamageSource.MAGIC, 1f, new int[]{0, 1, 2, 3});
                target.addStatusEffect(new StatusEffectInstance(ModEffects.TAINTED, 100, 0), attacker);
                return true;
            }
        }
        return true;
    }
}
