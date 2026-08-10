package com.aetherteam.aetherii.recipe.recipes.item.special;

import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import com.aetherteam.aetherii.item.components.BrokenStack;
import com.mojang.serialization.MapCodec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

import java.util.HashMap;
import java.util.Map;

public class LootRepairRecipe extends CustomRecipe {
    public static final LootRepairRecipe INSTANCE = new LootRepairRecipe();
    public static final MapCodec<LootRepairRecipe> MAP_CODEC = MapCodec.unit(INSTANCE);
    public static final StreamCodec<RegistryFriendlyByteBuf, LootRepairRecipe> STREAM_CODEC = StreamCodec.unit(INSTANCE);
    public static final RecipeSerializer<LootRepairRecipe> SERIALIZER = new RecipeSerializer<>(MAP_CODEC, STREAM_CODEC);

    private final Map<Item, Item> materials = new HashMap<>(Map.of(
            AetherIIItems.HAMMER_OF_DEMOLITION.get(), AetherIIItems.SENTRY_SERVO.get(),
            AetherIIItems.SENTRY_BOOTS.get(), AetherIIItems.SENTRY_SERVO.get(),
            AetherIIItems.NEPTUNE_BOOTS.get(), AetherIIItems.NEPTUNE_SCALE.get(),
            AetherIIItems.NEPTUNE_LEGGINGS.get(), AetherIIItems.NEPTUNE_SCALE.get(),
            AetherIIItems.NEPTUNE_CHESTPLATE.get(), AetherIIItems.NEPTUNE_SCALE.get(),
            AetherIIItems.NEPTUNE_HELMET.get(), AetherIIItems.NEPTUNE_SCALE.get(),
            AetherIIItems.NEPTUNE_GLOVES.get(), AetherIIItems.NEPTUNE_SCALE.get()
    ));

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
    public ItemStack assemble(CraftingInput craftingInput) {
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
        return SERIALIZER;
    }
}
