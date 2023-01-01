package net.kuwulkid94.kuwulkid.item;

import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Lazy;

import java.util.function.Supplier;

public enum ModToolMaterials implements ToolMaterial {
   PRIMITIVE(1, 92, 3.0F, 1.0F, 3, () -> {
        return Ingredient.ofItems(Items.FLINT);
    }),
    METALLIC(1, 184, 3.0F, 1.0F, 4, () -> {
        return Ingredient.ofItems(Items.FLINT);
    }),
    MYSTIC(1, 262, 1F, 1F, 6, () -> {
        return Ingredient.ofItems(Items.COPPER_INGOT);
    }),
    AMETHYST(1, 262, 1F, 1F, 7, () -> {
        return Ingredient.ofItems(Items.AMETHYST_SHARD);
    }),
    CORAL(1, 59, 1F, 1F, 9, () -> {
        return Ingredient.ofItems(Items.BRAIN_CORAL_BLOCK);
    }),
    TAINTED(1, 184, 1F, 1F, 7, () -> {
        return Ingredient.ofItems(ModItems.ABYSSAL_STONE);
    });
//amethyst
    private final int miningLevel;
    private final int itemDurability;
    private final float miningSpeed;
    private final float attackDamage;
    private final int enchantability;
    private final Lazy<Ingredient> repairIngredient;

    private ModToolMaterials(int miningLevel, int itemDurability, float miningSpeed, float attackDamage, int enchantability, Supplier repairIngredient) {
        this.miningLevel = miningLevel;
        this.itemDurability = itemDurability;
        this.miningSpeed = miningSpeed;
        this.attackDamage = attackDamage;
        this.enchantability = enchantability;
        this.repairIngredient = new Lazy(repairIngredient);
    }

    public int getDurability() {
        return this.itemDurability;
    }

    public float getMiningSpeedMultiplier() {
        return this.miningSpeed;
    }

    public float getAttackDamage() {
        return this.attackDamage;
    }

    public int getMiningLevel() {
        return this.miningLevel;
    }

    public int getEnchantability() {
        return this.enchantability;
    }

    public Ingredient getRepairIngredient() {
        return (Ingredient)this.repairIngredient.get();
    }
}
