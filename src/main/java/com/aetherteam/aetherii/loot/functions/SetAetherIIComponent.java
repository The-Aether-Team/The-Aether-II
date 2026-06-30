package com.aetherteam.aetherii.loot.functions;

import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import com.aetherteam.aetherii.item.components.DataComponentType;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class SetAetherIIComponent extends LootItemConditionalFunction {
    private final DataComponentType<?> component;
    private final Object value;

    protected SetAetherIIComponent(LootItemCondition[] conditions, DataComponentType<?> component, Object value) {
        super(conditions);
        this.component = component;
        this.value = value;
    }

    @Override
    protected ItemStack run(ItemStack stack, LootContext context) {
        this.set(stack);
        return stack;
    }

    @SuppressWarnings("unchecked")
    private <T> void set(ItemStack stack) {
        AetherIIDataComponents.set(stack, (DataComponentType<T>) this.component, (T) this.value);
    }

    public static <T> Builder<?> setComponent(DataComponentType<T> component, T value) {
        return simpleBuilder((conditions) -> new SetAetherIIComponent(conditions, component, value));
    }

    @Override
    public LootItemFunctionType getType() {
        return AetherIILootFunctions.SET_AETHER_II_COMPONENT.get();
    }

    public static class Serializer extends LootItemConditionalFunction.Serializer<SetAetherIIComponent> {
        @Override
        public void serialize(JsonObject jsonObject, SetAetherIIComponent function, JsonSerializationContext context) {
            super.serialize(jsonObject, function, context);
            jsonObject.addProperty("component", function.component.id().toString());
            jsonObject.add("value", encode(function.component, function.value));
        }

        @Override
        public SetAetherIIComponent deserialize(JsonObject jsonObject, JsonDeserializationContext context, LootItemCondition[] conditions) {
            AetherIIDataComponents.FEATHER_COLOR.id(); // Force registration of the local component registry.
            ResourceLocation id = new ResourceLocation(GsonHelper.getAsString(jsonObject, "component"));
            DataComponentType<?> component = DataComponentType.byId(id);
            if (component == null) {
                throw new JsonParseException("Unknown Aether II data component: " + id);
            }
            JsonElement value = jsonObject.get("value");
            if (value == null) {
                throw new JsonParseException("Missing value for Aether II data component: " + id);
            }
            return new SetAetherIIComponent(conditions, component, decode(component, value));
        }

        @SuppressWarnings("unchecked")
        private static <T> JsonElement encode(DataComponentType<T> component, Object value) {
            return unwrap(component.codecOrThrow().encodeStart(JsonOps.INSTANCE, (T) value), "Failed to encode Aether II data component " + component.id());
        }

        private static <T> T decode(DataComponentType<T> component, JsonElement value) {
            return unwrap(component.codecOrThrow().parse(JsonOps.INSTANCE, value), "Failed to decode Aether II data component " + component.id());
        }

        private static <T> T unwrap(DataResult<T> result, String prefix) {
            return result.result().orElseThrow(() -> new JsonParseException(prefix + ": " + result.error().map(DataResult.PartialResult::message).orElse("unknown error")));
        }
    }
}
