package com.aetherteam.aetherii.advancement.trigger;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

import java.util.Optional;

public class OutpostCampfireTrigger extends SimpleCriterionTrigger<OutpostCampfireTrigger.Instance> {
    @Override
    public Codec<Instance> codec() {
        return Instance.CODEC;
    }

    public void trigger(ServerPlayer player, ItemStack stack) {
        this.trigger(player, (instance) -> instance.test(stack));
    }

    public record Instance(Optional<ContextAwarePredicate> player, Optional<ItemPredicate> item) implements SimpleInstance {
        public static final Codec<Instance> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                        EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(OutpostCampfireTrigger.Instance::player),
                        ItemPredicate.CODEC.optionalFieldOf("item").forGetter(OutpostCampfireTrigger.Instance::item))
                .apply(instance, OutpostCampfireTrigger.Instance::new));

        public static Criterion<Instance> forItem(ItemPredicate item) {
            return AetherIIAdvancementTriggers.OUTPOST_CAMPFIRE_TRIGGER.get().createCriterion(new OutpostCampfireTrigger.Instance(Optional.empty(), Optional.of(item)));
        }

        public boolean test(ItemStack stack) {
            return this.item.isEmpty() || this.item.get().test(stack);
        }
    }
}