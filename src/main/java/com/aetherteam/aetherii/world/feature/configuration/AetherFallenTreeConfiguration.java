package com.aetherteam.aetherii.world.feature.configuration;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.IntProviders;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;

import java.util.ArrayList;
import java.util.List;

public class AetherFallenTreeConfiguration implements FeatureConfiguration {
    public static final Codec<AetherFallenTreeConfiguration> CODEC = RecordCodecBuilder.create((i) -> i.group(
            BlockStateProvider.CODEC.fieldOf("trunk_provider").forGetter((c) -> c.trunkProvider),
            IntProviders.codec(0, 4).fieldOf("stump_height").forGetter((t) -> t.stumpHeight),
            IntProviders.codec(0, 16).fieldOf("log_length").forGetter((t) -> t.logLength),
            TreeDecorator.CODEC.listOf().fieldOf("stump_decorators").forGetter((c) -> c.stumpDecorators),
            TreeDecorator.CODEC.listOf().fieldOf("log_decorators").forGetter((c) -> c.logDecorators)
    ).apply(i, AetherFallenTreeConfiguration::new));
    public final BlockStateProvider trunkProvider;
    public final IntProvider stumpHeight;
    public final IntProvider logLength;
    public final List<TreeDecorator> stumpDecorators;
    public final List<TreeDecorator> logDecorators;

    protected AetherFallenTreeConfiguration(BlockStateProvider trunkProvider, IntProvider stumpHeight, IntProvider logLength, List<TreeDecorator> stumpDecorators, List<TreeDecorator> logDecorators) {
        this.trunkProvider = trunkProvider;
        this.stumpHeight = stumpHeight;
        this.logLength = logLength;
        this.stumpDecorators = stumpDecorators;
        this.logDecorators = logDecorators;
    }

    public static class FallenTreeConfigurationBuilder {
        private final BlockStateProvider trunkProvider;
        private final IntProvider stumpHeight;
        private final IntProvider logLength;
        private List<TreeDecorator> stumpDecorators = new ArrayList<>();
        private List<TreeDecorator> logDecorators = new ArrayList<>();

        public FallenTreeConfigurationBuilder(BlockStateProvider trunkProvider, IntProvider stumpHeight, IntProvider logLength) {
            this.trunkProvider = trunkProvider;
            this.stumpHeight = stumpHeight;
            this.logLength = logLength;
        }

        public FallenTreeConfigurationBuilder stumpDecorators(List<TreeDecorator> stumpDecorators) {
            this.stumpDecorators = stumpDecorators;
            return this;
        }

        public FallenTreeConfigurationBuilder logDecorators(List<TreeDecorator> logDecorators) {
            this.logDecorators = logDecorators;
            return this;
        }

        public AetherFallenTreeConfiguration build() {
            return new AetherFallenTreeConfiguration(this.trunkProvider, this.stumpHeight, this.logLength, this.stumpDecorators, this.logDecorators);
        }
    }
}
