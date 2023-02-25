package net.kuwulkid94.kuwulkid.effect;

import net.kuwulkid94.kuwulkid.JustaFantasyAddon;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModEffects {
    public static StatusEffect TAINTED;
    public static StatusEffect ENSNARED;


    public static StatusEffect registerStatusEffect(String name){
        return Registry.register(Registry.STATUS_EFFECT, new Identifier(JustaFantasyAddon.MOD_ID, name),
                new TaintedEffect(StatusEffectCategory.HARMFUL, 14577094))
                .addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED,
                "052f3166-8ae7-11ed-a1eb-0242ac120002",
                0F,
                EntityAttributeModifier.Operation.ADDITION);
    }


    public static StatusEffect registerStatusEffect2(String name){
        return Registry.register(Registry.STATUS_EFFECT, new Identifier(JustaFantasyAddon.MOD_ID, name),
                new EnsnaredEffect(StatusEffectCategory.HARMFUL, 5797459));
    }

    public static void registerEffects() {
       TAINTED = registerStatusEffect("tainted");
       ENSNARED = registerStatusEffect2("ensnared");
    }
}
