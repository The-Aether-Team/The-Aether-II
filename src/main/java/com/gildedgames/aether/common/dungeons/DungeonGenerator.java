package com.gildedgames.aether.common.dungeons;

import com.gildedgames.orbis.lib.core.world_objects.BlueprintRegion;
import com.gildedgames.orbis.lib.data.blueprint.BlueprintData;
import com.gildedgames.orbis.lib.util.RegionHelp;
import com.google.common.collect.Lists;
import net.minecraft.util.math.BlockPos;
import java.util.Iterator;

import java.util.List;
import java.util.Random;

public class DungeonGenerator implements IDungeonGenerator {

    public static final int PAD_SIZE = 3;

    @Override
    public IDungeon generate(IDungeonDefinition definition, Random rand) {
        List<BlueprintRegion> rooms = Lists.newArrayList();
        int extraRooms =rand.nextInt(definition.getMaxRooms() - definition.getMinRooms());
        int targetRooms = definition.getMinRooms() + extraRooms;

        for (int i = 0; i < targetRooms; i++) {
            BlueprintData data = definition.possibleBlueprints().get(rand.nextInt(definition.possibleBlueprints().size()));
            BlueprintRegion room = new BlueprintRegion(getRandomPos(rand), data);

            rooms.add(room);
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
                List<BlueprintRegion> intersecting = Lists.newArrayList();
                for (BlueprintRegion room : soFar.rooms()) {
                    RegionHelp.fetchIntersecting2D(room, soFar.rooms(), intersecting, this.getCollisionPadding());
                    Iterator<BlueprintRegion> it = intersecting.iterator();

                    while (it.hasNext()) {
                        BlueprintRegion r = it.next();

                        if (r == room) {
                            continue;
                        }

                        int diffX = room.getMin().getX() < r.getMin().getX() ? 1 : -1;
                        int diffZ = room.getMin().getZ() < r.getMin().getZ() ? 1 : -1;

                        BlockPos min = r.getMin().add(diffX, 0, diffZ);
                        BlockPos max = r.getMax().add(diffX, 0, diffZ);

                        r.setBounds(min, max);
                        room.setBounds(room.getMin().add(-diffX, 0, -diffZ), room.getMax().add(-diffX, 0, -diffZ));

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
