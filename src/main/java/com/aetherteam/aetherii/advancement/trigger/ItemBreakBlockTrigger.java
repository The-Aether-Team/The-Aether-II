package com.aetherteam.aetherii.advancement.trigger;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.LocationPredicate;
import net.minecraft.advancements.critereon.SerializationContext;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
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
    public static final ResourceLocation ID = AetherIIAdvancementTriggers.id("item_break_block");

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    protected Instance createInstance(JsonObject json, ContextAwarePredicate player, DeserializationContext context) {
        ContextAwarePredicate location = ContextAwarePredicate.fromElement("location", context, json.get("location"), LootContextParamSets.ADVANCEMENT_LOCATION);
        if (location == null) {
            throw new JsonParseException("Failed to parse 'location' field");
        }
        return new Instance(player, Optional.of(location));
    }

    public void trigger(ServerPlayer player, BlockPos pos, ItemStack stack) {
        ServerLevel level = player.serverLevel();
        BlockState state = level.getBlockState(pos);
        LootParams parameters = new LootParams.Builder(level)
                .withParameter(LootContextParams.ORIGIN, pos.getCenter())
                .withParameter(LootContextParams.THIS_ENTITY, player)
                .withParameter(LootContextParams.BLOCK_STATE, state)
                .withParameter(LootContextParams.TOOL, stack)
                .create(LootContextParamSets.ADVANCEMENT_LOCATION);
        LootContext context = new LootContext.Builder(parameters).create(ID);
        this.trigger(player, instance -> instance.matches(context));
    }

    public static class Instance extends AbstractCriterionTriggerInstance {
        private final Optional<ContextAwarePredicate> location;

        public Instance(ContextAwarePredicate player, Optional<ContextAwarePredicate> location) {
            super(ID, player);
            this.location = location;
        }

        public static Criterion itemBrokeBlock(LocationPredicate.Builder location, ItemPredicate.Builder tool) {
            ContextAwarePredicate predicate = ContextAwarePredicate.create(LocationCheck.checkLocation(location).build(), MatchTool.toolMatches(tool).build());
            return new Criterion(new Instance(ContextAwarePredicate.ANY, Optional.of(predicate)));
        }

        public boolean matches(LootContext context) {
            return this.location.isEmpty() || this.location.get().matches(context);
        }

        @Override
        public JsonObject serializeToJson(SerializationContext conditions) {
            JsonObject jsonObject = super.serializeToJson(conditions);
            this.location.ifPresent(location -> jsonObject.add("location", location.toJson(conditions)));
            return jsonObject;
        }
    }
}
