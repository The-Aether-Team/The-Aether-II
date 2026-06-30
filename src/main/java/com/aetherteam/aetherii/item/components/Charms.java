package com.aetherteam.aetherii.item.components;

import com.aetherteam.aetherii.util.ItemStackCodecs;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import com.aetherteam.aetherii.network.codec.ByteBufCodecs;
import com.aetherteam.aetherii.network.codec.StreamCodec;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public record Charms(List<CharmHolder> charmHolders) {
    public static final Codec<Charms> CODEC = RecordCodecBuilder.create((builder) -> builder.group(
            CharmHolder.CODEC.listOf().fieldOf("holders").forGetter(Charms::charmHolders)
    ).apply(builder, list -> new Charms(new ArrayList<>(list))));
    public static final StreamCodec<FriendlyByteBuf, Charms> STREAM_CODEC = StreamCodec.composite(
            CharmHolder.STREAM_CODEC.apply(ByteBufCodecs.list(8)), Charms::charmHolders,
            list -> new Charms(new ArrayList<>(list)));

    public Charms(CharmHolder... charmHolders) {
        this(new ArrayList<>(List.of(charmHolders)));
    }

    public Charms() {
        this(new ArrayList<>());
    }

    public static List<Charms.CharmHolder> getCharmsForItem(ItemStack stack) {
        Charms charms = AetherIIDataComponents.get(stack, AetherIIDataComponents.CHARMS);
        if (charms != null) {
            return charms.charmHolders();
        }
        return null;
    }

    public static Charms.CharmHolder getCharmHolderForItem(ItemStack stack, int i) {
        List<Charms.CharmHolder> charmHolders = getCharmsForItem(stack);
        if (charmHolders != null) {
            if (charmHolders.size() > i) {
                return charmHolders.get(i);
            }
        }
        return null;
    }

    public static MutableComponent createCharmTypeComponent(Type type) {
        return Component.translatable("aether_ii.tooltip.item.charm.type." + type.name().toLowerCase(Locale.ROOT));
    }

    public static MutableComponent createCharmTierComponent(Tier tier) {
        return Component.translatable("aether_ii.tooltip.item.charm.tier", Component.translatable("enchantment.level." + tier.getValue()));
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        } else {
            if (other instanceof Charms charms) {
                return charms.charmHolders().equals(this.charmHolders());
            } else {
                return false;
            }
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.charmHolders());
    }

    @Override
    public String toString() {
        return "Charms{" + "charmHolders=" + this.charmHolders + '}';
    }

    public static class CharmHolder {
        public static final Codec<CharmHolder> CODEC = RecordCodecBuilder.create((builder) -> builder.group(
                Type.CODEC.fieldOf("type").forGetter(CharmHolder::getType),
                Tier.CODEC.fieldOf("tier").forGetter(CharmHolder::getTier),
                ItemStackCodecs.OPTIONAL_CODEC.fieldOf("stack").forGetter(CharmHolder::getStack)
        ).apply(builder, CharmHolder::new));
        public static final StreamCodec<FriendlyByteBuf, CharmHolder> STREAM_CODEC = StreamCodec.composite(
                ByteBufCodecs.fromCodecWithRegistries(Type.CODEC), CharmHolder::getType,
                ByteBufCodecs.fromCodecWithRegistries(Tier.CODEC), CharmHolder::getTier,
                ItemStackCodecs.OPTIONAL_STREAM_CODEC, CharmHolder::getStack,
                CharmHolder::new);

        private final Type type;
        private final Tier tier;
        private ItemStack stack;

        public CharmHolder(Type type, Tier tier, ItemStack stack) {
            this.type = type;
            this.tier = tier;
            this.stack = stack;
        }

        public CharmHolder(Type type, Tier tier) {
            this(type, tier, ItemStack.EMPTY);
        }

        public CharmHolder(Type type) {
            this(type, Tier.ONE);
        }

        public CharmHolder(CharmHolder other, ItemStack stack) {
            this(other.getType(), other.getTier(), stack);
        }

        public CharmHolder(CharmHolder other) {
            this(other.getType(), other.getTier(), other.getStack());
        }

        public Type getType() {
            return this.type;
        }

        public Tier getTier() {
            return this.tier;
        }

        public ItemStack getStack() {
            return this.stack;
        }

        public void setStack(ItemStack stack) {
            this.stack = stack;
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.getType(), this.getTier(), this.getStack());
        }

        @Override
        public boolean equals(Object other) {
            if (this == other) {
                return true;
            } else {
                if (other instanceof CharmHolder otherHolder) {
                    return otherHolder.getType() == this.getType() && otherHolder.getTier() == this.getTier() && ItemStack.matches(otherHolder.getStack(), this.getStack());
                } else {
                    return false;
                }
            }
        }

        @Override
        public String toString() {
            return "CharmHolder{" + "type=" + type + ", tier=" + tier + ", stack=" + stack + '}';
        }
    }

    public enum Type implements StringRepresentable {
        TOOL,
        WEAPON,
        ARMOR;

        public static final StringRepresentable.EnumCodec<Type> CODEC = StringRepresentable.fromEnum(Type::values);

        @Override
        public String getSerializedName() {
            return this.name().toLowerCase(Locale.ROOT);
        }
    }

    public enum Tier implements StringRepresentable {
        ONE(1),
        TWO(2),
        THREE(3);

        public static final StringRepresentable.EnumCodec<Tier> CODEC = StringRepresentable.fromEnum(Tier::values);
        public final int value;

        Tier(int value) {
            this.value = value;
        }

        @Override
        public String getSerializedName() {
            return this.name().toLowerCase(Locale.ROOT);
        }

        public int getValue() {
            return this.value;
        }
    }
}
