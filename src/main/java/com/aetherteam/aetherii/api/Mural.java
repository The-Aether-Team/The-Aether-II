package com.aetherteam.aetherii.api;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

import javax.annotation.Nullable;

import com.aetherteam.aetherii.api.registries.AetherIIRegistries;
import com.aetherteam.aetherii.blockentity.MuralSection;
import com.aetherteam.aetherii.data.resources.registries.AetherIIMurals;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.Holder;
import com.aetherteam.aetherii.item.components.DataComponentGetter;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import com.aetherteam.aetherii.util.ComponentSerialization;
import com.aetherteam.aetherii.network.codec.ByteBufCodecs;
import com.aetherteam.aetherii.network.codec.StreamCodec;
import net.minecraft.resources.RegistryFixedCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.TooltipFlag;
import com.aetherteam.aetherii.item.components.TooltipProvider;

public record Mural(int width, int height, ResourceLocation assetId, Optional<Component> title) implements TooltipProvider {
    public static final int MAX_SIZE = 4;
    
    public static final Codec<Mural> DIRECT_CODEC = RecordCodecBuilder.create(
        builder -> builder.group(
                ExtraCodecs.intRange(1, MAX_SIZE).fieldOf("width").forGetter(Mural::width),
                ExtraCodecs.intRange(1, MAX_SIZE).fieldOf("height").forGetter(Mural::height),
                ResourceLocation.CODEC.fieldOf("asset_id").forGetter(Mural::assetId),
                ComponentSerialization.CODEC.optionalFieldOf("title").forGetter(Mural::title)
            )
            .apply(builder, Mural::new)
    );
    public static final StreamCodec<FriendlyByteBuf, Mural> DIRECT_STREAM_CODEC = StreamCodec.composite(
        ByteBufCodecs.VAR_INT, Mural::width,
        ByteBufCodecs.VAR_INT, Mural::height,
        ByteBufCodecs.RESOURCE_LOCATION, Mural::assetId,
        ComponentSerialization.TRUSTED_OPTIONAL_STREAM_CODEC, Mural::title,
        Mural::new
    );
    public static final Codec<Holder<Mural>> CODEC = RegistryFixedCodec.create(AetherIIRegistries.MURAL);
    public static final StreamCodec<FriendlyByteBuf, Holder<Mural>> STREAM_CODEC = ByteBufCodecs.holder(AetherIIRegistries.MURAL, DIRECT_STREAM_CODEC);

    public Mural {
        checkSize(width, height);
    }

    public Mural(int width, int height, ResourceLocation assetId, @Nullable Component title) {
        this(width, height, assetId, Optional.ofNullable(title));
    }

    public Mural(int width, int height, ResourceLocation assetId) {
        this(width, height, assetId, Optional.empty());
    }

    public int area() {
        return this.width() * this.height();
    }

    public void checkOffset(int offsetX, int offsetY) {
        checkOffset(this.width(), this.height(), offsetX, offsetY);
    }

    @Override
    public void addToTooltip(Object context, Consumer<Component> tooltipAdder, TooltipFlag flag, DataComponentGetter componentGetter) {
        this.title().ifPresent(tooltipAdder);
        tooltipAdder.accept(Component.translatable("mural.dimensions", this.width(), this.height()));
    }

    public static void checkSize(int width, int height) {
        if (!((1 <= width && width <= MAX_SIZE) && (1 <= height && height <= MAX_SIZE))) {
            throw new IllegalArgumentException("Mural width/height must be between 1 and " + MAX_SIZE);
        }
    }

    public static void checkOffset(int width, int height, int offsetX, int offsetY) {
        if (!(0 <= offsetX && offsetX < width)) {
            throw new IllegalArgumentException("Mural offset X must be between 0 and " + (width - 1));
        }
        if (!(0 <= offsetY && offsetY < height)) {
            throw new IllegalArgumentException("Mural offset Y must be between 0 and " + (height - 1));
        }
    }

    public static Map<MuralSection, ResourceLocation> getPieces() {
        Map<MuralSection, ResourceLocation> pieces = new HashMap<>();
        AetherIIMurals.holders().forEach(muralReference -> {
            Mural mural = muralReference.value();
            for (int x = 0; x < mural.width(); x++) {
                for (int y = 0; y < mural.height(); y++) {
                    MuralSection section = new MuralSection(muralReference, x, y);
                    ResourceLocation location = mural.assetId().withSuffix("_" + x).withSuffix("_" + y);
                    pieces.put(section, location);
                }
            }
        });
        return pieces;
    }
}
