package net.kuwulkid94.kuwulkid.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.math.Vec3d;

public class EnsnaredEffect extends StatusEffect {
    public EnsnaredEffect(StatusEffectCategory statusEffectCategory, int color) {
        super(statusEffectCategory, color);
    }
    boolean damaged = false;

    public void applyUpdateEffect(LivingEntity pLivingEntity, int pAmplifier) {
        pLivingEntity.slowMovement(pLivingEntity.getSteppingBlockState(), new Vec3d(0.01, 0.75, 0.01));
        //haha only took like 19 hours haha im dumb lol
    }

    @Override
    public boolean canApplyUpdateEffect(int pDuration, int pAmplifier) {
        return true;
    }
}
