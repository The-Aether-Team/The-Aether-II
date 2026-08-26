package com.aetherteam.aetherii.data.generators.tags;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.data.resources.registries.AetherIITimelines;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.KeyTagProvider;
import net.minecraft.tags.TimelineTags;
import net.minecraft.world.timeline.Timeline;
import net.minecraft.world.timeline.Timelines;

import java.util.concurrent.CompletableFuture;

public class AetherIITimelineTagData extends KeyTagProvider<Timeline> {
    public AetherIITimelineTagData(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, Registries.TIMELINE, lookupProvider);
    }

    @Override
    protected void addTags(HolderLookup.Provider registries) {
        this.tag(AetherIITags.Timelines.IN_HOLY_ISLES).addTag(TimelineTags.UNIVERSAL).add(AetherIITimelines.HOLY_ISLES_DAY, Timelines.MOON);
    }
}
