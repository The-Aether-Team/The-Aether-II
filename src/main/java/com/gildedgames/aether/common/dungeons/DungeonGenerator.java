package com.gildedgames.aether.common.dungeons;

import com.gildedgames.orbis.lib.core.world_objects.BlueprintRegion;
import com.gildedgames.orbis.lib.data.blueprint.BlueprintData;
import com.google.common.collect.Lists;
import net.minecraft.util.math.BlockPos;

import java.util.List;
import java.util.Random;
import java.util.Iterator;


import java.util.stream.Collectors;

public class DungeonGenerator implements IDungeonGenerator {

    public static final int PAD_SIZE = 3;

    @Override
    public IDungeon generate(IDungeonDefinition definition, Random rand) {
        List<DungeonNode> rooms = Lists.newArrayList();
        int extraRooms = rand.nextInt(definition.getMaxRooms() - definition.getMinRooms());
        int targetRooms = definition.getMinRooms() + extraRooms;

        for (int i = 0; i < targetRooms; i++) {
            BlueprintData data = definition.possibleBlueprints().get(rand.nextInt(definition.possibleBlueprints().size()));
            BlueprintRegion room = new BlueprintRegion(getRandomPos(rand), data);

            rooms.add(new DungeonNode(room));
        }

        return new Dungeon(rooms);
    }

    private BlockPos getRandomPos(Random rand) {
        int x = rand.nextInt(20) * (rand.nextBoolean() ? -1 : 1);
        int y = rand.nextInt(20);
        int z = rand.nextInt(20) * (rand.nextBoolean() ? -1 : 1);

        return new BlockPos(x, y, z);
    }

    @Override
    public boolean step(DungeonGenStep step, IDungeon soFar) {
        switch (step) {
            case PUSH_ROOMS_APART: {
                List<AABB> allAABB = soFar.rooms().stream().map(DungeonNode::getAABB).collect(Collectors.toList());
                Iterator<AABB> it = allAABB.iterator();
                boolean anyIntersect = false;

                while (it.hasNext()) {
                    AABB room = it.next();
                    boolean intersected = false;

                    for (AABB other : allAABB) {
                        if (other == room) {
                            continue;
                        }

                        if (room.intersects(other, getCollisionPadding())) {
                            int diffX = room.minX < other.minX ? 1 : -1;
                            int diffY = room.minY < other.minY ? 1 : -1;

                            other.add(diffX, diffY);
                            room.add(-diffX, -diffY);

                            intersected = true;
                            anyIntersect = true;
                        }
                    }

                    if (intersected) {
                        it.remove();
                    }
                }

                return !anyIntersect;
            }
        }

        return false;
    }

    public int getCollisionPadding()
    {
        return PAD_SIZE;
    }
}
