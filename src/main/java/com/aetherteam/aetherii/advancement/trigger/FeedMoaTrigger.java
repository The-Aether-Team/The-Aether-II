package com.aetherteam.aetherii.advancement.trigger;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.criterion.ContextAwarePredicate;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.advancements.criterion.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.Validatable;
import net.minecraft.world.level.storage.loot.ValidationContextSource;

import java.util.Optional;

public class FeedMoaTrigger extends SimpleCriterionTrigger<FeedMoaTrigger.Instance> {
    @Override
    public Codec<Instance> codec() {
        return Instance.CODEC;
    }

    public void trigger(ServerPlayer player, ItemStack item, Entity entity) {
        LootContext lootcontext = EntityPredicate.createContext(player, entity);
        this.trigger(player, (instance) -> instance.matches(item, lootcontext));
    }

    public record Instance(Optional<ContextAwarePredicate> player, Optional<ItemPredicate> item, Optional<ContextAwarePredicate> entity) implements SimpleCriterionTrigger.SimpleInstance {
        public static final Codec<Instance> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
                EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(Instance::player),
                ItemPredicate.CODEC.optionalFieldOf("item").forGetter(Instance::item),
                EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("entity").forGetter(Instance::entity)
        ).apply(instance, Instance::new));

        public static Criterion<Instance> itemUsedOnEntity(Optional<ContextAwarePredicate> player, ItemPredicate.Builder item, Optional<ContextAwarePredicate> entity) {
            return AetherIIAdvancementTriggers.FEED_MOA.get().createCriterion(new Instance(player, Optional.of(item.build()), entity));
        }

        public static Criterion<Instance> itemUsedOnEntity(ItemPredicate.Builder item, Optional<ContextAwarePredicate> entity) {
            return itemUsedOnEntity(Optional.empty(), item, entity);
        }

        public static Criterion<Instance> itemUsedOnEntity(ItemPredicate.Builder item) {
            return itemUsedOnEntity(Optional.empty(), item, Optional.empty());
        }

        public boolean matches(ItemStack item, LootContext lootContext) {
            return (this.item.isEmpty() || this.item.get().test(item)) && (this.entity.isEmpty() || this.entity.get().matches(lootContext));
        }

        @Override
        public void validate(ValidationContextSource validator) {
            SimpleCriterionTrigger.SimpleInstance.super.validate(validator);
            Validatable.validate(validator.entityContext(), "entity", this.entity);
        }
    }
}

