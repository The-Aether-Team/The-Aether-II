package com.aetherteam.aetherii.world.structure.piece.sentry;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;

public record SentryRuinsProcessorSettings(Holder<StructureProcessorList> roomSettings, Holder<StructureProcessorList> staircaseSettings, Holder<StructureProcessorList> bossSettings) {
    public static final MapCodec<SentryRuinsProcessorSettings> CODEC = RecordCodecBuilder.mapCodec(builder -> builder.group(
        StructureProcessorType.LIST_CODEC.fieldOf("generic_room_processors").forGetter(SentryRuinsProcessorSettings::roomSettings),
        StructureProcessorType.LIST_CODEC.fieldOf("staircase_processors").forGetter(SentryRuinsProcessorSettings::staircaseSettings),
        StructureProcessorType.LIST_CODEC.fieldOf("boss_room_processors").forGetter(SentryRuinsProcessorSettings::bossSettings)
    ).apply(builder, SentryRuinsProcessorSettings::new));
}
