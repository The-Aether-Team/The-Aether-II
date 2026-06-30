package com.aetherteam.aetherii.loot.functions;

import com.aetherteam.aetherii.entity.monster.Swet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;

import java.util.Set;

public class SugarDropsFunction  extends LootItemConditionalFunction {
    private final NumberProvider value;

    private SugarDropsFunction(LootItemCondition[] conditions, NumberProvider value) {
        super(conditions);
        this.value = value;
    }

    @Override
    public ItemStack run(ItemStack stack, LootContext context) {
        Entity entity = context.getParamOrNull(LootContextParams.THIS_ENTITY);
        if (entity instanceof Swet swet) {
            if (swet.isWaterDamaged() || swet.getSwetScale() > 0.95F) {
                stack.setCount(stack.getCount() + this.value.getInt(context));
            }
        }
        return stack;
    }

    public static LootItemConditionalFunction.Builder<?> extra(NumberProvider countValue) {
        return simpleBuilder((conditions) -> new SugarDropsFunction(conditions, countValue));
    }

    @Override
    public Set<net.minecraft.world.level.storage.loot.parameters.LootContextParam<?>> getReferencedContextParams() {
        return this.value.getReferencedContextParams();
    }

    @Override
    public LootItemFunctionType getType() {
        return AetherIILootFunctions.SUGAR_DROPS.get();
    }

    public static class Serializer extends LootItemConditionalFunction.Serializer<SugarDropsFunction> {
        @Override
        public void serialize(JsonObject jsonObject, SugarDropsFunction function, JsonSerializationContext context) {
            super.serialize(jsonObject, function, context);
            jsonObject.add("count", context.serialize(function.value));
        }

        @Override
        public SugarDropsFunction deserialize(JsonObject jsonObject, JsonDeserializationContext context, LootItemCondition[] conditions) {
            NumberProvider count = GsonHelper.getAsObject(jsonObject, "count", context, NumberProvider.class);
            return new SugarDropsFunction(conditions, count);
        }
    }
}
