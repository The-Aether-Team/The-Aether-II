package com.aetherteam.aetherii.advancement.trigger;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.criterion.ContextAwarePredicate;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.advancements.criterion.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

import java.util.Optional;

public class ForgingCharmTrigger extends SimpleCriterionTrigger<ForgingCharmTrigger.Instance> {
    @Override
    public Codec<Instance> codec() {
        return Instance.CODEC;
    }

    public void trigger(ServerPlayer player, ItemStack stack) {
        this.trigger(player, (instance) -> instance.test(stack));
    }

    public record Instance(Optional<ContextAwarePredicate> player, Optional<ItemPredicate> item) implements SimpleInstance {
        public static final Codec<Instance> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                        EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(ForgingCharmTrigger.Instance::player),
                        ItemPredicate.CODEC.optionalFieldOf("item").forGetter(ForgingCharmTrigger.Instance::item))
                .apply(instance, ForgingCharmTrigger.Instance::new));

        public static Criterion<Instance> charm() {
            return AetherIIAdvancementTriggers.FORGING_CHARM.get().createCriterion(new ForgingCharmTrigger.Instance(Optional.empty(), Optional.empty()));
        }

        public static Criterion<Instance> charmItem(ItemPredicate item) {
            return AetherIIAdvancementTriggers.FORGING_CHARM.get().createCriterion(new ForgingCharmTrigger.Instance(Optional.empty(), Optional.of(item)));
        }

        public boolean test(ItemStack stack) {
            return this.item.isEmpty() || this.item.get().test(stack);
        }
    }
}