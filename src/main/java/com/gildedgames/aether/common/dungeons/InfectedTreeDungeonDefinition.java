package com.gildedgames.aether.common.dungeons;

import com.gildedgames.orbis.lib.data.blueprint.BlueprintData;

import java.util.List;

public class InfectedTreeDungeonDefinition implements IDungeonDefinition {
    private List<BlueprintData> possibleBlueprints;

    public InfectedTreeDungeonDefinition(List<BlueprintData> possibleBlueprints) {
        this.possibleBlueprints = possibleBlueprints;
    }

    @Override
    public List<BlueprintData> possibleBlueprints() {
        return this.possibleBlueprints;
    }

    @Override
    public int getMinRooms() {
        return 15;
    }

    @Override
    public int getMaxRooms() {
        return 30;
    }
}
