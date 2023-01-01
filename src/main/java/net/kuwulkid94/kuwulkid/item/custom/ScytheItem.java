package net.kuwulkid94.kuwulkid.item.custom;

import net.kuwulkid94.kuwulkid.sounds.ModSounds;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;

public class ScytheItem extends SwordItem {
    public ScytheItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker){
        stack.damage(1, attacker, (e) -> {
            e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND);
        });
        if(target.getHealth() <= 0) {
            attacker.addStatusEffect(new StatusEffectInstance(StatusEffects.INVISIBILITY, 100, 0), attacker);
            attacker.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 40, 2), attacker);
            attacker.getWorld().playSound((PlayerEntity) attacker, attacker.getBlockPos(), ModSounds.FIREBOWL_WOOSH_1, SoundCategory.PLAYERS, 1.0F, attacker.getWorld().random.nextFloat() * 0.4F + 0.8F);
            attacker.getWorld().addParticle(ParticleTypes.LARGE_SMOKE, (double)attacker.getBlockPos().getX()+ Math.random(), (double)attacker.getBlockPos().getY()+ 1, (double)attacker.getBlockPos().getZ() + Math.random(), Math.random() * (0.001) , Math.random()  * (0.2), Math.random()  * (0.001));
            attacker.getWorld().addParticle(ParticleTypes.LARGE_SMOKE, (double)attacker.getBlockPos().getX()+ Math.random(), (double)attacker.getBlockPos().getY()+ 1, (double)attacker.getBlockPos().getZ() + Math.random(), Math.random() * (0.001) , Math.random()  * (0.2), Math.random()  * (0.001));
            attacker.getWorld().addParticle(ParticleTypes.LARGE_SMOKE, (double)attacker.getBlockPos().getX()+ Math.random(), (double)attacker.getBlockPos().getY()+ 1, (double)attacker.getBlockPos().getZ() + Math.random(), Math.random() * (0.001) , Math.random()  * (0.2), Math.random()  * (0.001));
            attacker.getWorld().addParticle(ParticleTypes.LARGE_SMOKE, (double)attacker.getBlockPos().getX()+ Math.random(), (double)attacker.getBlockPos().getY()+ 1, (double)attacker.getBlockPos().getZ() + Math.random(), Math.random() * (0.001) , Math.random()  * (0.2), Math.random()  * (0.001));

            target.getWorld().addParticle(ParticleTypes.SOUL, (double)attacker.getBlockPos().getX()+ Math.random(), (double)attacker.getBlockPos().getY()+ 1, (double)attacker.getBlockPos().getZ() + Math.random(), Math.random() * (0.001) , Math.random()  * (0.2), Math.random()  * (0.001));
            target.getWorld().addParticle(ParticleTypes.SOUL, (double)attacker.getBlockPos().getX()+ Math.random(), (double)attacker.getBlockPos().getY()+ 1, (double)attacker.getBlockPos().getZ() + Math.random(), Math.random() * (0.001) , Math.random()  * (0.2), Math.random()  * (0.001));
            target.getWorld().addParticle(ParticleTypes.SOUL, (double)attacker.getBlockPos().getX()+ Math.random(), (double)attacker.getBlockPos().getY()+ 1, (double)attacker.getBlockPos().getZ() + Math.random(), Math.random() * (0.001) , Math.random()  * (0.2), Math.random()  * (0.001));
        }
        return true;
    }
}
