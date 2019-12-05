package com.gildedgames.aether.common.dungeons;

import com.gildedgames.orbis.lib.core.world_objects.BlueprintRegion;
import com.gildedgames.orbis.lib.data.blueprint.BlueprintData;
import com.gildedgames.orbis.lib.util.RegionHelp;
import com.google.common.collect.Lists;
import net.minecraft.util.math.BlockPos;
import java.util.Iterator;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class DungeonGenerator implements IDungeonGenerator {

    public static final int PAD_SIZE = 3;

    @Override
    public IDungeon generate(IDungeonDefinition definition, Random rand) {
        List<DungeonNode> rooms = Lists.newArrayList();
        int extraRooms =rand.nextInt(definition.getMaxRooms() - definition.getMinRooms());
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
                boolean anyIntersect = false;
                List<AABB> intersecting = Lists.newArrayList();
                List<AABB> allAABB = soFar.rooms().stream().map(DungeonNode::getAABB).collect(Collectors.toList());

                for (AABB room : allAABB) {
                    AABB.fetchIntersecting(room, allAABB, intersecting, this.getCollisionPadding());
                    Iterator<AABB> it = intersecting.iterator();

                    while (it.hasNext()) {
                        AABB r = it.next();

                        if (r == room) {
                            continue;
                        }

                        int diffX = room.minX < r.minX ? 1 : -1;
                        int diffY = room.minY < r.minY ? 1 : -1;

                        r.add(diffX, diffY);
                        room.add(-diffX, -diffY);

                        it.remove();

                        anyIntersect = true;
                    }

                    intersecting.clear();
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
