package com.aetherteam.aetherii.world.structure.piece.sentry;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;

public record SentryWorkshopProcessorSettings(Holder<StructureProcessorList> roomSettings, Holder<StructureProcessorList> tunnelSettings, Holder<StructureProcessorList> bossSettings) {
    public static final MapCodec<SentryWorkshopProcessorSettings> CODEC = RecordCodecBuilder.mapCodec(builder -> builder.group(
        StructureProcessorType.LIST_CODEC.fieldOf("generic_room_processors").forGetter(SentryWorkshopProcessorSettings::roomSettings),
        StructureProcessorType.LIST_CODEC.fieldOf("tunnel_processors").forGetter(SentryWorkshopProcessorSettings::tunnelSettings),
        StructureProcessorType.LIST_CODEC.fieldOf("boss_room_processors").forGetter(SentryWorkshopProcessorSettings::bossSettings)
    ).apply(builder, SentryWorkshopProcessorSettings::new));
}
