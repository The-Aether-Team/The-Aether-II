package com.gildedgames.aether.common.dungeons;

import com.gildedgames.orbis.lib.core.world_objects.BlueprintRegion;

import java.util.List;

public class Dungeon implements IDungeon {
    private List<BlueprintRegion> rooms;

    public Dungeon(List<BlueprintRegion> rooms) {
        this.rooms = rooms;
    }

    @Override
    public List<BlueprintRegion> rooms() {
        return this.rooms;
    }
}
