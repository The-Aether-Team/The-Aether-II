package com.aetherteam.aetherii.block;

import com.mojang.serialization.Codec;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

import java.util.Locale;

public class AetherIIBlockStateProperties {
    public static final EnumProperty<Mossy> MOSSY = EnumProperty.create("mossy_overlay", Mossy.class);
    public static final EnumProperty<TrapState> TRAP_STATE = EnumProperty.create("trap_state", TrapState.class);
    public static final EnumProperty<SentrySpawnerState> SENTRY_SPAWNER_STATE = EnumProperty.create("sentry_spawner_state", SentrySpawnerState.class);
    public static final EnumProperty<SideChain> SIDE_CHAIN = EnumProperty.create("side_chain", SideChain.class);
    public static final IntegerProperty TWIG_AMOUNT = IntegerProperty.create("twig_amount", 1, 2);
    public static final IntegerProperty ROCK_AMOUNT = IntegerProperty.create("rock_amount", 1, 3);
    public static final IntegerProperty PILES = IntegerProperty.create("piles", 1, 16);
    public static final IntegerProperty PURIFIER_LEVEL = IntegerProperty.create("purifier_level", 0, 4);
    public static final BooleanProperty BRETTL_GROWN = BooleanProperty.create("grown");
    public static final BooleanProperty EMPTY = BooleanProperty.create("empty");
    public static final BooleanProperty IGNITED = BooleanProperty.create("ignited");

    public enum Mossy implements StringRepresentable {
        BRYALINN,
        SHAYELINN,
        AMBRELINN,
        NONE;

        public static final Codec<Mossy> CODEC = StringRepresentable.fromEnum(Mossy::values);

        @Override
        public String getSerializedName() {
            return this.name().toLowerCase(Locale.ROOT);
        }
    }

    public enum TrapState implements StringRepresentable {
        LOADED,
        TRIGGERED,
        SPAWNED;

        @Override
        public String getSerializedName() {
            return this.name().toLowerCase(Locale.ROOT);
        }
    }

    public enum SentrySpawnerState implements StringRepresentable {
        INACTIVE,
        TRIGGERED,
        OPENING,
        CLOSING;

        @Override
        public String getSerializedName() {
            return this.name().toLowerCase(Locale.ROOT);
        }
    }

    public enum SideChain implements StringRepresentable {
        UNCONNECTED,
        LEFT,
        RIGHT,
        CENTER;

        @Override
        public String getSerializedName() {
            return this.name().toLowerCase(Locale.ROOT);
        }
    }
}
