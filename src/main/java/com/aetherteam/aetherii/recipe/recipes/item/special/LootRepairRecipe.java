package com.aetherteam.aetherii.recipe.recipes.item.special;

import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import com.aetherteam.aetherii.item.components.BrokenStack;
import com.aetherteam.aetherii.recipe.serializer.AetherIIRecipeSerializers;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

import java.util.HashMap;
import java.util.Map;

public class LootRepairRecipe extends CustomRecipe {
    private final Map<Item, Item> materials = new HashMap<>(Map.of(
            AetherIIItems.HAMMER_OF_DEMOLITION.get(), AetherIIItems.SENTRY_SERVO.get(),
            AetherIIItems.SENTRY_BOOTS.get(), AetherIIItems.SENTRY_SERVO.get(),
            AetherIIItems.NEPTUNE_BOOTS.get(), AetherIIItems.NEPTUNE_SCALE.get(),
            AetherIIItems.NEPTUNE_LEGGINGS.get(), AetherIIItems.NEPTUNE_SCALE.get(),
            AetherIIItems.NEPTUNE_CHESTPLATE.get(), AetherIIItems.NEPTUNE_SCALE.get(),
            AetherIIItems.NEPTUNE_HELMET.get(), AetherIIItems.NEPTUNE_SCALE.get(),
            AetherIIItems.NEPTUNE_GLOVES.get(), AetherIIItems.NEPTUNE_SCALE.get()
    ));

    public LootRepairRecipe(CraftingBookCategory category) {
        super(category);
    }

    @Override
    public boolean matches(CraftingInput craftingInput, Level level) {
        int materialCount = 0;
        ItemStack input = ItemStack.EMPTY;

        for (int i = 0; i < craftingInput.size(); ++i) {
            ItemStack item = craftingInput.getItem(i);
            if (item.is(AetherIIItems.BROKEN_ITEM)) {
                BrokenStack brokenStack = item.get(AetherIIDataComponents.BROKEN_STACK);
                if (brokenStack != null && this.materials.containsKey(brokenStack.stack().getItem())) {
                    input = brokenStack.stack();
                }
            }
        }
        for (int i = 0; i < craftingInput.size(); ++i) {
            ItemStack item = craftingInput.getItem(i);
            if (item.getItem() == this.materials.get(input.getItem())) {
                materialCount++;
            }
        }

        return materialCount == 8 && !input.isEmpty();
    }

    @Override
    public ItemStack assemble(CraftingInput craftingInput, HolderLookup.Provider provider) {
        ItemStack result = ItemStack.EMPTY;
        for (int i = 0; i < craftingInput.size(); ++i) {
            ItemStack item = craftingInput.getItem(i);
            if (item.is(AetherIIItems.BROKEN_ITEM)) {
                BrokenStack brokenStack = item.get(AetherIIDataComponents.BROKEN_STACK);
                if (brokenStack != null && this.materials.containsKey(brokenStack.stack().getItem())) {
                    result = brokenStack.stack().copy();
                    result.setDamageValue(0);
                }
            }
        }
        return result;
    }

    @Override
    public RecipeSerializer<LootRepairRecipe> getSerializer() {
        return AetherIIRecipeSerializers.LOOT_REPAIR.get();
    }
}
