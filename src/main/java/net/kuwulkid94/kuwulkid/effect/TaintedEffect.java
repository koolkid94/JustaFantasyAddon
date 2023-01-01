package net.kuwulkid94.kuwulkid.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class TaintedEffect extends StatusEffect {
    public TaintedEffect(StatusEffectCategory statusEffectCategory, int color) {
        super(statusEffectCategory, color);
    }
    boolean damaged = false;

    @Override
    public void applyUpdateEffect(LivingEntity livingentity, int pAmplifier) {
            super.applyUpdateEffect(livingentity, pAmplifier);
    }

    @Override
    public boolean canApplyUpdateEffect(int pDuration, int pAmplifier) {
        return true;
    }
}
