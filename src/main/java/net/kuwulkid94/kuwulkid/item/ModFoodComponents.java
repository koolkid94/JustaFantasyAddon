package net.kuwulkid94.kuwulkid.item;

import net.minecraft.item.FoodComponent;

public class ModFoodComponents {

    public static final FoodComponent CACTUS_FRUIT = (new FoodComponent.Builder()).hunger(4).saturationModifier(0.2F).build();

    public static final FoodComponent SHRIMP_TAIL = (new FoodComponent.Builder()).hunger(3).saturationModifier(0.1F).snack().build();

    public static final FoodComponent COOKED_SHRIMP_TAIL = (new FoodComponent.Builder()).hunger(9).saturationModifier(0.3F).snack().build();

    public static final FoodComponent SHRIMP_FRIED_RICE = createStew(12).build();

    private static FoodComponent.Builder createStew(int hunger) {
        return (new FoodComponent.Builder()).hunger(hunger).saturationModifier(0.6F);
    }
}
