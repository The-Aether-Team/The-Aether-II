package com.aetherteam.aetherii.advancement.trigger;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

import java.util.Optional;

public class SentryBootsFallTrigger extends SimpleCriterionTrigger<SentryBootsFallTrigger.Instance> {
    @Override
    public Codec<Instance> codec() {
        return Instance.CODEC;
    }

    public void trigger(ServerPlayer player, ItemStack stack) {
        this.trigger(player, (instance) -> instance.test(player, stack));
    }

    public record Instance(Optional<ContextAwarePredicate> player, Optional<ItemPredicate> item) implements SimpleInstance {
        public static final Codec<Instance> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                        EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(SentryBootsFallTrigger.Instance::player),
                        ItemPredicate.CODEC.optionalFieldOf("item").forGetter(SentryBootsFallTrigger.Instance::item))
                .apply(instance, SentryBootsFallTrigger.Instance::new));

        public static Criterion<Instance> forItem(ItemPredicate item) {
            return AetherIIAdvancementTriggers.SENTRY_BOOTS_FALL_TRIGGER.get().createCriterion(new SentryBootsFallTrigger.Instance(Optional.empty(), Optional.of(item)));
        }

        public boolean test(ServerPlayer player, ItemStack stack) {
                return player.fallDistance > 22 && player.getHealth() > 0 && player.getEquipmentSlotForItem(stack) == EquipmentSlot.FEET;
        }
    }
}