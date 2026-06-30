package com.aetherteam.aetherii.integration.jei.interpreter;

import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import mezz.jei.api.ingredients.subtypes.IIngredientSubtypeInterpreter;
import mezz.jei.api.ingredients.subtypes.UidContext;
import net.minecraft.world.item.ItemStack;

public class HealingStoneInterpreter implements IIngredientSubtypeInterpreter<ItemStack> {
    @Override
    public String apply(ItemStack stack, UidContext context) {
        return Integer.toString(AetherIIDataComponents.getOrDefault(stack, AetherIIDataComponents.HEALING_STONE_CHARGES, 0));
    }
}
