package com.aetherteam.aetherii.loot.conditions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

public record TierCompare(String items) implements LootItemCondition {

    @Override
    public boolean test(LootContext context) {
        ItemStack stack = context.getParamOrNull(LootContextParams.TOOL);
        if (stack != null) {
            return compareStack(stack, this.items());
        }
        return false;
    }

    public static boolean compareStack(ItemStack stack, String items) {
        if (items.startsWith("#")) {
            return stack.is(TagKey.create(Registries.ITEM, new ResourceLocation(items.substring(1))));
        }
        return stack.is(BuiltInItem.item(new ResourceLocation(items)));
    }

    @Override
    public LootItemConditionType getType() {
        return AetherIILootConditions.TIER_COMPARE.get();
    }

    public static class Serializer implements net.minecraft.world.level.storage.loot.Serializer<TierCompare> {
        @Override
        public void serialize(JsonObject jsonObject, TierCompare condition, JsonSerializationContext context) {
            jsonObject.addProperty("items", condition.items());
        }

        @Override
        public TierCompare deserialize(JsonObject jsonObject, JsonDeserializationContext context) {
            return new TierCompare(GsonHelper.getAsString(jsonObject, "items"));
        }
    }

    private static class BuiltInItem {
        private static Item item(ResourceLocation id) {
            return net.minecraft.core.registries.BuiltInRegistries.ITEM.get(id);
        }
    }
}
