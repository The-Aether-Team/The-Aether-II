package com.aetherteam.aetherii.item.components;

import com.mojang.serialization.Codec;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import com.aetherteam.aetherii.item.components.DataComponentGetter;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.Style;
import com.aetherteam.aetherii.network.codec.StreamCodec;
import net.minecraft.world.item.*;
import com.aetherteam.aetherii.item.components.TooltipProvider;

import java.util.Optional;
import java.util.function.Consumer;

public record EngravedDisc(Holder<JukeboxSong> song) implements TooltipProvider {
    public static final Codec<EngravedDisc> CODEC = JukeboxSong.CODEC.xmap(EngravedDisc::new, EngravedDisc::song);
    public static final StreamCodec<FriendlyByteBuf, EngravedDisc> STREAM_CODEC = StreamCodec.composite(JukeboxSong.STREAM_CODEC, EngravedDisc::song, EngravedDisc::new);

    @Override
    public void addToTooltip(Object context, Consumer<Component> consumer, TooltipFlag flag, DataComponentGetter components) {
        consumer.accept(ComponentUtils.mergeStyles(this.song.value().description().copy(), Style.EMPTY.withColor(ChatFormatting.GRAY)));
    }

    public static Optional<Holder<JukeboxSong>> getSong(ItemStack stack) {
        EngravedDisc music = AetherIIDataComponents.get(stack, AetherIIDataComponents.ENGRAVED_DISC);
        return music != null ? Optional.of(music.song()) : Optional.empty();
    }
}
