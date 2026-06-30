package com.aetherteam.aetherii.data.resources.maps;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;

public record BlockInfection(ResourceKey<Block> block) {
    public static final Codec<BlockInfection> BLOCK_CODEC = ResourceKey.codec(Registries.BLOCK).xmap(BlockInfection::new, BlockInfection::block);
    public static final Codec<BlockInfection> DIRECT_CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResourceKey.codec(Registries.BLOCK).fieldOf("block").forGetter(BlockInfection::block)
    ).apply(instance, BlockInfection::new));

    public static final Codec<BlockInfection> CODEC = Codec.either(DIRECT_CODEC, BLOCK_CODEC).xmap(
            either -> either.map(infection -> infection, infection -> infection),
            infection -> com.mojang.datafixers.util.Either.left(infection)
    );
}
