package com.aetherteam.aetherii.data.providers;

import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.core.Holder;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;

import java.util.stream.Stream;

public class AetherIIItemModelProvider  extends ModelProvider {
    public AetherIIItemModelProvider(PackOutput output, String modId) {
        super(output, modId);
    }

    @Override
    public Stream<? extends Holder<Block>> getKnownBlocks() {
        return Stream.of();
    }

    @Override
    public final String getName() {
        return this.modId + " Item Models";
    }
}
