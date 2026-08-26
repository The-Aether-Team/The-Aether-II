package com.aetherteam.aetherii.blockentity;

import com.aetherteam.aetherii.api.Mural;
import com.aetherteam.aetherii.data.resources.registries.AetherIIMurals;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;

import java.util.function.Consumer;

public record MuralSection(Holder<Mural> mural, int offsetX, int offsetY) implements TooltipProvider {
    public static final MuralSection DEFAULT = new MuralSection(AetherIIMurals.TEST, 0, 0);
    public static final Codec<MuralSection> CODEC = RecordCodecBuilder.create((builder) -> builder.group(
            Mural.CODEC.fieldOf("id").forGetter(MuralSection::mural),
            Codec.INT.fieldOf("x").forGetter(MuralSection::offsetX),
            Codec.INT.fieldOf("y").forGetter(MuralSection::offsetY)
        )
        .apply(builder, MuralSection::new)
    );
    public static final StreamCodec<RegistryFriendlyByteBuf, MuralSection> STREAM_CODEC = StreamCodec.composite(
        Mural.STREAM_CODEC,
        MuralSection::mural,
        ByteBufCodecs.INT,
        MuralSection::offsetX,
        ByteBufCodecs.INT,
        MuralSection::offsetY,
        MuralSection::new
    );

    @Override
    public void addToTooltip(TooltipContext context, Consumer<Component> tooltipAdder, TooltipFlag flag, DataComponentGetter componentGetter) {
        mural.value().title().ifPresent(tooltipAdder);
        tooltipAdder.accept(Component.translatable("mural.offset", this.offsetX(), this.offsetY()));
    }
}
