package com.aetherteam.aetherii.advancement.trigger;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.criterion.*;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LocationCheck;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;

import java.util.Optional;

public class ItemBreakBlockTrigger extends SimpleCriterionTrigger<ItemBreakBlockTrigger.Instance> {
    @Override
    public Codec<Instance> codec() {
        return Instance.CODEC;
    }

    public void trigger(ServerPlayer player, BlockPos pos, ItemStack stack) {
        ServerLevel level = player.level();
        BlockState state = level.getBlockState(pos);
        LootParams parameters = new LootParams.Builder(level)
                .withParameter(LootContextParams.ORIGIN, pos.getCenter())
                .withParameter(LootContextParams.THIS_ENTITY, player)
                .withParameter(LootContextParams.BLOCK_STATE, state)
                .withParameter(LootContextParams.TOOL, stack)
                .create(LootContextParamSets.ADVANCEMENT_LOCATION);
        LootContext context = new LootContext.Builder(parameters).create(Optional.empty());
        this.trigger(player, (instance) -> instance.matches(context));
    }

    public record Instance(Optional<ContextAwarePredicate> player, Optional<ContextAwarePredicate> location) implements SimpleCriterionTrigger.SimpleInstance {
        public static final Codec<Instance> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
                EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(Instance::player),
                ContextAwarePredicate.CODEC.optionalFieldOf("location").forGetter(Instance::location)
        ).apply(instance, Instance::new));

        public static Criterion<Instance> itemBrokeBlock(LocationPredicate.Builder location, ItemPredicate.Builder tool) {
            ContextAwarePredicate contextawarepredicate = ContextAwarePredicate.create(LocationCheck.checkLocation(location).build(), MatchTool.toolMatches(tool).build());
            Instance instance = new Instance(Optional.empty(), Optional.of(contextawarepredicate));
            return AetherIIAdvancementTriggers.ITEM_BREAK_BLOCK.get().createCriterion(instance);
        }

        public boolean matches(LootContext context) {
            return this.location.isEmpty() || this.location.get().matches(context);
        }

        @Override
        public void validate(CriterionValidator validator) {
            SimpleInstance.super.validate(validator);
            this.location.ifPresent((predicate) -> validator.validate(predicate, LootContextParamSets.ADVANCEMENT_LOCATION, "location"));
        }
    }
}
