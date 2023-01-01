package net.kuwulkid94.kuwulkid.potion;

import net.kuwulkid94.kuwulkid.JustaFantasyAddon;
import net.kuwulkid94.kuwulkid.item.ModItems;
import net.kuwulkid94.kuwulkid.mixin.BrewingRecipeRegistryMixin;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModPotions {
    public static Potion POTENT_POISON;

    public static Potion registerPotion(String name){
        return  Registry.register(Registry.POTION, new Identifier(JustaFantasyAddon.MOD_ID, name),
                new Potion(new StatusEffectInstance(StatusEffects.POISON, 400, 2)));


    }

    public static void registerPotions(){
        POTENT_POISON = registerPotion("potent_poison");


        registerPotionRecipes();
    }

    private static void registerPotionRecipes(){
        BrewingRecipeRegistryMixin.invokeRegisterPotionRecipe(Potions.AWKWARD, ModItems.SCORPION_STINGER, ModPotions.POTENT_POISON);
    }

}
