package com.gildedgames.aether.common.dungeons;

import com.gildedgames.orbis.lib.data.blueprint.BlueprintData;

import java.util.List;

public interface IDungeonDefinition {
    List<BlueprintData> possibleBlueprints();

    int getMinRooms();

    int getMaxRooms();
}
