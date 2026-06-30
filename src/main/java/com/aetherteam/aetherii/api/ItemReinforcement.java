package com.aetherteam.aetherii.api;

import com.aetherteam.aetherii.api.registries.AetherIIRegistries;
import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import com.aetherteam.aetherii.item.components.DataComponents;
import com.aetherteam.aetherii.item.components.ReinforcementTier;
import com.aetherteam.aetherii.recipe.recipes.OutputEntry;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import com.aetherteam.aetherii.item.components.DataComponentType;
import com.aetherteam.aetherii.item.components.TypedDataComponent;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import com.aetherteam.aetherii.util.ComponentSerialization;
import com.aetherteam.aetherii.network.codec.ByteBufCodecs;
import com.aetherteam.aetherii.network.codec.StreamCodec;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.ItemStack;
import com.aetherteam.aetherii.item.components.ItemStackTemplate;
import com.aetherteam.aetherii.recipe.display.RecipeDisplay;
import com.aetherteam.aetherii.recipe.display.SlotDisplay;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

public record ItemReinforcement(Upgrade... upgrades) {
    public static final Codec<ItemReinforcement> DIRECT_CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            Upgrade.CODEC.listOf().fieldOf("operations").forGetter((a) -> List.of(a.upgrades()))
    ).apply(instance, ItemReinforcement::new));
    public static final StreamCodec<FriendlyByteBuf, ItemReinforcement> DIRECT_STREAM_CODEC = StreamCodec.composite(
            Upgrade.STREAM_CODEC.apply(ByteBufCodecs.list(4)), (u) -> List.of(u.upgrades()),
            ItemReinforcement::new);
    public static final Codec<Holder<ItemReinforcement>> CODEC = RegistryFileCodec.create(AetherIIRegistries.ITEM_REINFORCEMENT, DIRECT_CODEC);
    public static final StreamCodec<FriendlyByteBuf, Holder<ItemReinforcement>> STREAM_CODEC = ByteBufCodecs.holder(AetherIIRegistries.ITEM_REINFORCEMENT, DIRECT_STREAM_CODEC);

    public ItemReinforcement(List<Upgrade> upgrades) {
        this(upgrades.toArray(Upgrade[]::new));
    }

    public ItemStack modify(ItemStack stack, int tierGoal) {
        ReinforcementTier tier = AetherIIDataComponents.get(stack, AetherIIDataComponents.REINFORCEMENT_TIER);
        int startingTier = 0;
        if (tier != null) {
            startingTier = tier.getTierNumber();
        }
        for (int i = startingTier; i < tierGoal; i++) {
            this.upgrades()[i].modify(stack);
        }
        return stack;
    }

    public record Upgrade(Component description, Cost cost, ComponentOperation... operations) {
        public static final Codec<Upgrade> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
                ComponentSerialization.CODEC.fieldOf("description").forGetter(Upgrade::description),
                Cost.CODEC.fieldOf("cost").forGetter(Upgrade::cost),
                ComponentOperation.CODEC.listOf().fieldOf("operations").forGetter((u) -> List.of(u.operations()))
        ).apply(instance, Upgrade::new));
        public static final StreamCodec<FriendlyByteBuf, Upgrade> STREAM_CODEC = StreamCodec.composite(
                ComponentSerialization.STREAM_CODEC, Upgrade::description,
                Cost.STREAM_CODEC, Upgrade::cost,
                ComponentOperation.STREAM_CODEC.apply(ByteBufCodecs.list()), (u) -> List.of(u.operations()),
                Upgrade::new);

        private Upgrade(Component description, Cost cost, List<ComponentOperation> operations) {
            this(description, cost, operations.toArray(ComponentOperation[]::new));
        }

        public ItemStack modify(ItemStack stack) {
            for (ComponentOperation info : this.operations()) {
                info.modifyComponent(stack);
            }
            return stack;
        }
    }

    public record Cost(ItemStackTemplate primaryCost, Optional<ItemStackTemplate> secondaryCost) {
        public static final Codec<Cost> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
                ItemStackTemplate.CODEC.fieldOf("primary_cost").forGetter(Cost::primaryCost),
                ItemStackTemplate.CODEC.optionalFieldOf("secondary_cost").forGetter(Cost::secondaryCost)
        ).apply(instance, Cost::new));
        public static final StreamCodec<FriendlyByteBuf, Cost> STREAM_CODEC = StreamCodec.composite(
                ItemStackTemplate.STREAM_CODEC, Cost::primaryCost,
                ByteBufCodecs.optional(ItemStackTemplate.STREAM_CODEC), Cost::secondaryCost,
                Cost::new);

        public Cost(ItemStackTemplate primaryCost, ItemStackTemplate secondaryCost) {
            this(primaryCost, Optional.of(secondaryCost));
        }

        public Cost(ItemStackTemplate primaryCost) {
            this(primaryCost, Optional.empty());
        }
    }

    public static abstract class ComponentOperation<V> {
        public static final Codec<TypedDataComponent<?>> COMPONENT_CODEC = DataComponentType.PERSISTENT_CODEC.dispatch(TypedDataComponent::type, Add::makeCustomDataValueCodec);
        public static final Codec<ComponentOperation> CODEC = OperationType.CODEC.dispatch(ComponentOperation::type, type -> type.codec.fieldOf("value").codec());
        public static StreamCodec<FriendlyByteBuf, ComponentOperation> STREAM_CODEC = OperationType.STREAM_CODEC.dispatch(ComponentOperation::type, type -> type.streamCodec);

        protected final TypedDataComponent<V> componentInfo;

        public ComponentOperation(TypedDataComponent<V> componentInfo) {
            this.componentInfo = componentInfo;
        }

        public TypedDataComponent<V> componentInfo() {
            return this.componentInfo;
        }

        public abstract ItemStack modifyComponent(ItemStack stack);

        public abstract OperationType type();

        protected static <T> Codec<TypedDataComponent<T>> makeCustomDataValueCodec(DataComponentType<T> type) {
            return type.codecOrThrow().fieldOf("value").xmap(
                    val -> TypedDataComponent.createUnchecked(type, val),
                    TypedDataComponent::value
            ).codec();
        }

        public enum OperationType implements StringRepresentable {
            ADD(Add.CODEC, Add.STREAM_CODEC),
            SET(Set.CODEC, Set.STREAM_CODEC);

            public static final StringRepresentable.EnumCodec<OperationType> CODEC = StringRepresentable.fromEnum(OperationType::values);
            public static final StreamCodec<FriendlyByteBuf, OperationType> STREAM_CODEC = ByteBufCodecs.fromCodecWithRegistries(CODEC);
            final Codec<? extends ComponentOperation> codec;
            final StreamCodec<FriendlyByteBuf, ? extends ComponentOperation> streamCodec;

            OperationType(Codec<? extends ComponentOperation> codec, StreamCodec<FriendlyByteBuf, ? extends ComponentOperation> streamCodec) {
                this.codec = codec;
                this.streamCodec = streamCodec;
            }

            @Override
            public String getSerializedName() {
                return this.name().toLowerCase(Locale.ROOT);
            }
        }
    }

    public static class Add<N extends Number> extends ComponentOperation<N> {
        public static final Codec<Add> CODEC = ComponentOperation.COMPONENT_CODEC.xmap(c -> new Add(c.type(), (Number) c.value()), add -> add.componentInfo());
        public static final StreamCodec<FriendlyByteBuf, Add> STREAM_CODEC = StreamCodec.composite(
                TypedDataComponent.STREAM_CODEC, Add::componentInfo,
                (c) -> new Add(c.type(), (Number) c.value())
        );

        public Add(DataComponentType<N> componentType, N add) {
            super(new TypedDataComponent<>(componentType, add));
        }

        @Override
        public ItemStack modifyComponent(ItemStack stack) {
            N component = AetherIIDataComponents.get(stack, this.componentInfo.type());
            if (component == null) {
                component = this.getFallbackComponent(stack);
            }
            N added = this.add(component, this.componentInfo.value());
            if (added != null) {
                AetherIIDataComponents.set(stack, this.componentInfo.type(), added);
            } else {
                throw new IllegalArgumentException("Additive component value for reinforcement upgrading must be a number");
            }
            return stack;
        }

        @SuppressWarnings("unchecked")
        private N getFallbackComponent(ItemStack stack) {
            if (this.componentInfo.type() == DataComponents.MAX_DAMAGE && stack.isDamageableItem()) {
                return (N) Integer.valueOf(stack.getMaxDamage());
            }
            return null;
        }

        public N add(N a, N b) {
            if (a instanceof Integer n) {
                return (N) Integer.valueOf(n + b.intValue());
            } else if (a instanceof Long n) {
                return (N) Long.valueOf(n + b.longValue());
            } else if (a instanceof Short n) {
                return (N) Short.valueOf((short) (n + b.shortValue()));
            } else if (a instanceof Float n) {
                return (N) Float.valueOf(n + b.floatValue());
            } else if (a instanceof Double n) {
                return (N) Double.valueOf(n + b.doubleValue());
            }
            return null;
        }

        @Override
        public OperationType type() {
            return OperationType.ADD;
        }
    }

    public static class Set<V extends Object> extends ComponentOperation<V> {
        public static final Codec<Set> CODEC = ComponentOperation.COMPONENT_CODEC.xmap(c -> new Set(c.type(), c.value()), set -> set.componentInfo());
        public static final StreamCodec<FriendlyByteBuf, Set> STREAM_CODEC = StreamCodec.composite(
                TypedDataComponent.STREAM_CODEC, Set::componentInfo,
                (c) -> new Set(c.type(), c.value())
        );

        public Set(DataComponentType<V> componentType, V set) {
            super(new TypedDataComponent<>(componentType, set));
        }

        @Override
        public ItemStack modifyComponent(ItemStack stack) {
            AetherIIDataComponents.set(stack, this.componentInfo.type(), this.componentInfo.value());
            return stack;
        }

        @Override
        public OperationType type() {
            return OperationType.SET;
        }
    }
}
