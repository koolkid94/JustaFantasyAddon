package net.kuwulkid94.kuwulkid.item.custom;

import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.particle.ParticleTypes;

public class ScorpionFangItem extends SwordItem {

    public ScorpionFangItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    //WaitTime:0,Duration:20,Radius:10,Potion:"minecraft:strong_poison"

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker){
        AreaEffectCloudEntity areaEffectCloudEntity = new AreaEffectCloudEntity(attacker.getWorld(), target.getX(), target.getY(), target.getZ());

        areaEffectCloudEntity.setColor(19);
        areaEffectCloudEntity.setDuration(120);
        areaEffectCloudEntity.addEffect(new StatusEffectInstance(StatusEffects.POISON));
        areaEffectCloudEntity.setParticleType(ParticleTypes.CAMPFIRE_COSY_SMOKE);

        //EntityType.AREA_EFFECT_CLOUD.spawn((ServerWorld) target.getWorld(), null, null,(PlayerEntity) attacker, target.getBlockPos(), SpawnReason.TRIGGERED, true, true);

        target.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 200, 2), attacker);
        return super.postHit(stack, target, attacker);
    }

}
//print statements for debugging