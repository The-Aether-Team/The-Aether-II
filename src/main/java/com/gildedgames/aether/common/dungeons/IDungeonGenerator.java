package com.gildedgames.aether.common.dungeons;

import java.util.Random;

public interface IDungeonGenerator {
    IDungeon generate(IDungeonDefinition definition, Random rand);

    boolean step(DungeonGenStep step, IDungeon soFar);

    int getCollisionPadding();
}
