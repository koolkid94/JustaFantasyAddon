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

public class RapierItem extends SwordItem {

    private int addition;

    public RapierItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
        this.addition = addition;
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker){
        stack.damage(1, attacker, (e) -> {
            e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND);
        });


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
        if (!target.isDead() && attacker.world.random.nextFloat() <= 0.01F + ((float) addition / 10F)) {
            int amplifier = 0;
            if (target.hasStatusEffect(StatusEffects.POISON))
                amplifier = target.getStatusEffect(StatusEffects.POISON).getAmplifier() + 1;
            target.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 60 + addition * 20, amplifier > 2 ? 2 : amplifier, false, false, true));
        }
        return super.postHit(stack, target, attacker);
    }

}
//print statements for debugging
