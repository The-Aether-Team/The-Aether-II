package com.aetherteam.aetherii.loot.functions;

import com.aetherteam.aetherii.entity.monster.Swet;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.context.ContextKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import net.minecraft.world.level.storage.loot.providers.number.NumberProviders;

import java.util.List;
import java.util.Set;

public class SugarDropsFunction  extends LootItemConditionalFunction {
    public static final MapCodec<SugarDropsFunction> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
            LootItemCondition.DIRECT_CODEC.listOf().optionalFieldOf("conditions", List.of()).forGetter((function) -> function.predicates),
            NumberProviders.CODEC.fieldOf("count").forGetter((function) -> function.value)
    ).apply(instance, SugarDropsFunction::new));
    private final NumberProvider value;

    private SugarDropsFunction(List<LootItemCondition> conditions, NumberProvider value) {
        super(conditions);
        this.value = value;
    }

    @Override
    public ItemStack run(ItemStack stack, LootContext context) {
        Entity entity = context.getOptionalParameter(LootContextParams.THIS_ENTITY);
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
    public Set<ContextKey<?>> getReferencedContextParams() {
        return this.value.getReferencedContextParams();
    }

    @Override
    public MapCodec<? extends LootItemConditionalFunction> codec() {
        return CODEC;
    }

}
