package net.kuwulkid94.kuwulkid.mixin;

import com.google.common.collect.Maps;
import net.kuwulkid94.kuwulkid.effect.ModEffects;
import net.minecraft.block.Blocks;
import net.minecraft.block.PowderSnowBlock;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.entity.attribute.EntityAttributes;

import java.util.Map;


@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

    @Shadow
    private final AttributeContainer attributes;

    @Shadow
    private final Map<StatusEffect, StatusEffectInstance> activeStatusEffects = Maps.newHashMap();

    @Shadow
    private float movementSpeed;

    @Shadow
    public float prevHeadYaw;
    float stuckYaw = 0;

    protected LivingEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
        this.attributes = new AttributeContainer(DefaultAttributeRegistry.get(entityType));
    }

    @Shadow
    public static DefaultAttributeContainer.Builder createLivingAttributes() {
        return DefaultAttributeContainer.builder().add(EntityAttributes.GENERIC_MAX_HEALTH).add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE).add(EntityAttributes.GENERIC_MOVEMENT_SPEED).add(EntityAttributes.GENERIC_ARMOR).add(EntityAttributes.GENERIC_ARMOR_TOUGHNESS);
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
    @Inject(method = "jump", at = @At("HEAD"), cancellable = true)
    public void jump_HEAD_NoJumpingWhileEnsnared(CallbackInfo ci) {
        if (hasStatusEffect(ModEffects.ENSNARED)) {
            ci.cancel();
        }
    }

    @Overwrite
    public void takeKnockback(double strength, double x, double z) {
        if(this.hasStatusEffect(ModEffects.ENSNARED))
        {
            //do nothing
        }
        else {
            strength *= 1.0 - this.getAttributeValue(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE);
            if (!(strength <= 0.0)) {
                this.velocityDirty = true;
                Vec3d vec3d = this.getVelocity();
                Vec3d vec3d2 = (new Vec3d(x, 0.0, z)).normalize().multiply(strength);
                this.setVelocity(vec3d.x / 2.0 - vec3d2.x, this.onGround ? Math.min(0.4, vec3d.y / 2.0 + strength) : vec3d.y, vec3d.z / 2.0 - vec3d2.z);
            }
        }
    }

    @Shadow
    public double getAttributeValue(EntityAttribute attribute) {
        return this.getAttributes().getValue(attribute);
    }

    @Shadow
    public AttributeContainer getAttributes() {
        return this.attributes;
    }

    @Overwrite
    public void setMovementSpeed(float movementSpeed) {
        if(this.hasStatusEffect(ModEffects.ENSNARED))
        {
            this.movementSpeed = 0.01F;
        }
        else {
            this.movementSpeed = movementSpeed;
        }
    }


}
