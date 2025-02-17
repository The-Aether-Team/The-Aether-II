package com.aetherteam.aetherii.api.guidebook;

import net.minecraft.core.Holder;

import java.util.Map;

public interface MutableEntry {
    Holder<BestiaryEntry> getEntry();

    Map<String, GuidebookEntry.Info> getClientValues();
}
