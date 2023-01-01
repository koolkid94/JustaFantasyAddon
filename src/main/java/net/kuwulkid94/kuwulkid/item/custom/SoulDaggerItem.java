package net.kuwulkid94.kuwulkid.item.custom;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.particle.ParticleTypes;

public class SoulDaggerItem extends SwordItem {
    int numStrikes = 0;
    public SoulDaggerItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.damage(1, attacker, (e) -> {
            e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND);
        });
        if (numStrikes == 3) {
            attacker.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 40, 2), attacker);
            attacker.getWorld().addParticle(ParticleTypes.SOUL, (double) target.getBlockPos().getX() + Math.random(), (double) target.getBlockPos().getY() + 0.75, (double) target.getBlockPos().getZ() + Math.random(), Math.random() * (0.001), Math.random() * (0.2), Math.random() * (0.001));
            attacker.getWorld().addParticle(ParticleTypes.SOUL, (double) target.getBlockPos().getX() + Math.random(), (double) target.getBlockPos().getY() + 0.75, (double) target.getBlockPos().getZ() + Math.random(), Math.random() * (0.001), Math.random() * (0.2), Math.random() * (0.001));
            attacker.getWorld().addParticle(ParticleTypes.SOUL, (double) target.getBlockPos().getX() + Math.random(), (double) target.getBlockPos().getY() + 0.75, (double) target.getBlockPos().getZ() + Math.random(), Math.random() * (0.001), Math.random() * (0.2), Math.random() * (0.001));
            numStrikes = 0;
            return true;
        }
        numStrikes++;
        return true;
    }
}
