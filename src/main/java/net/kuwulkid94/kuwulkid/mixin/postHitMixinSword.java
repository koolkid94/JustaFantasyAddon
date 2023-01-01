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
import net.minecraft.item.SwordItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(SwordItem.class)
public class postHitMixinSword {
    @Overwrite()
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker){
        stack.damage(1, attacker, (e) -> {
            e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND);
        });
        if(attacker instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) attacker;
            ArmorItem helmet = ((ArmorItem)player.getInventory().getArmorStack(2).getItem());
            if(helmet.getMaterial().equals(ModArmorMaterials.abyssal) || helmet.toString().equals("necklace"))
            {
                System.out.println("SMACKMIXIN!! SWORD!");
                player.getInventory().damageArmor(DamageSource.MAGIC, 1f, new int[]{0, 1, 2, 3});
                target.addStatusEffect(new StatusEffectInstance(ModEffects.TAINTED, 100, 0), attacker);
                return true;
            }
        }
        return true;
    }
}
