package net.kuwulkid94.kuwulkid.effect;

import net.kuwulkid94.kuwulkid.JustaFantasyAddon;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModEffects {
    public static StatusEffect TAINTED;

    public static StatusEffect registerStatusEffect(String name){
        return Registry.register(Registry.STATUS_EFFECT, new Identifier(JustaFantasyAddon.MOD_ID, name),
                new TaintedEffect(StatusEffectCategory.HARMFUL, 14577094));
    }

    public static void registerEffects() {
       TAINTED = registerStatusEffect("tainted");
    }
}
