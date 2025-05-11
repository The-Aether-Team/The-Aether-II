package com.aetherteam.aetherii.api.guidebook;

import net.minecraft.core.Holder;

import java.util.Map;

public interface MutableEntry {
    Holder<? extends GuidebookEntry> getEntry();

    Map<String, GuidebookEntry.Info> getClientValues();
}
