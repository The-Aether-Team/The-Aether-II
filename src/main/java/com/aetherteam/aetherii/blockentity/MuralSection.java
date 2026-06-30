package com.aetherteam.aetherii.blockentity;

import java.util.function.Consumer;

import com.aetherteam.aetherii.api.Mural;
import com.aetherteam.aetherii.data.resources.registries.AetherIIMurals;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.Holder;
import com.aetherteam.aetherii.item.components.DataComponentGetter;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import com.aetherteam.aetherii.network.codec.ByteBufCodecs;
import com.aetherteam.aetherii.network.codec.StreamCodec;
import net.minecraft.world.item.TooltipFlag;
import com.aetherteam.aetherii.item.components.TooltipProvider;

public record MuralSection(Holder<Mural> mural, int offsetX, int offsetY) implements TooltipProvider {
    public static final Codec<MuralSection> CODEC = RecordCodecBuilder.create((builder) -> builder.group(
            Mural.CODEC.fieldOf("id").forGetter(MuralSection::mural),
            Codec.INT.fieldOf("x").forGetter(MuralSection::offsetX),
            Codec.INT.fieldOf("y").forGetter(MuralSection::offsetY)
        )
        .apply(builder, MuralSection::new)
    );
    public static final StreamCodec<FriendlyByteBuf, MuralSection> STREAM_CODEC = StreamCodec.composite(
        Mural.STREAM_CODEC,
        MuralSection::mural,
        ByteBufCodecs.INT,
        MuralSection::offsetX,
        ByteBufCodecs.INT,
        MuralSection::offsetY,
        MuralSection::new
    );

    public static MuralSection defaultSection() {
        return new MuralSection(AetherIIMurals.TEST.getHolder().orElseGet(() -> Holder.direct(AetherIIMurals.TEST.get())), 0, 0);
    }

    @Override
    public void addToTooltip(Object context, Consumer<Component> tooltipAdder, TooltipFlag flag, DataComponentGetter componentGetter) {
        mural.value().title().ifPresent(tooltipAdder);
        tooltipAdder.accept(Component.translatable("mural.offset", this.offsetX(), this.offsetY()));
    }
}
