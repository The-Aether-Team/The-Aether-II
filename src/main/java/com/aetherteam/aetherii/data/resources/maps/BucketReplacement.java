package com.aetherteam.aetherii.data.resources.maps;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;

public record BucketReplacement(ResourceKey<Item> bucket) {
    public static final Codec<BucketReplacement> ITEM_CODEC = ResourceKey.codec(Registries.ITEM).xmap(BucketReplacement::new, BucketReplacement::bucket);

    public static final Codec<BucketReplacement> CODEC = Codec.withAlternative(
            RecordCodecBuilder.create(instance -> instance.group(ResourceKey.codec(Registries.ITEM).fieldOf("item").forGetter(BucketReplacement::bucket)).apply(instance, BucketReplacement::new)),
            ITEM_CODEC);
}
