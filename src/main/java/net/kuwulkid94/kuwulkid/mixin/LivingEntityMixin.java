package net.kuwulkid94.kuwulkid.mixin;

import net.kuwulkid94.kuwulkid.effect.ModEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;


@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Shadow
    public abstract boolean hasStatusEffect(StatusEffect effect);

    @Shadow
    public abstract void setHeadYaw(float headYaw);

    @Shadow
    public abstract void setSprinting(boolean sprinting);

    @Shadow
    @Nullable
    public abstract StatusEffectInstance getStatusEffect(StatusEffect effect);

    @Shadow
    public abstract void animateDamage();

    @Shadow
    public abstract boolean damage(DamageSource source, float amount);

    @Shadow
    public abstract void heal(float amount);

    @Shadow
    public abstract ItemStack getEquippedStack(EquipmentSlot slot);

    @Shadow
    public abstract void equipStack(EquipmentSlot slot, ItemStack stack);

    @Shadow public abstract float getHealth();

    @Shadow public abstract void setHealth(float health);

    @ModifyVariable(method = "damage", at = @At("HEAD"), argsOnly = true)
    private float multiplyDamageForVulnerability(float amount) {
        if (this.hasStatusEffect(ModEffects.TAINTED)) {
            return amount + (amount * (0.25f * (this.getStatusEffect(ModEffects.TAINTED).getAmplifier() + 1)));
        }
        return amount;
    }

}
