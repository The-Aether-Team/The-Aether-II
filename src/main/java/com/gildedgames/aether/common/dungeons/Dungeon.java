package com.gildedgames.aether.common.dungeons;

import java.util.List;

public class Dungeon implements IDungeon {
    private List<DungeonNode> rooms;

    public Dungeon(List<DungeonNode> rooms) {
        this.rooms = rooms;
    }

    @Override
    public List<DungeonNode> rooms() {
        return this.rooms;
    }
}
