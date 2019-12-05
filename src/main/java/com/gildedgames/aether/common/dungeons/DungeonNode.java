package com.gildedgames.aether.common.dungeons;

import com.gildedgames.orbis.lib.core.world_objects.BlueprintRegion;

public class DungeonNode
{
    private BlueprintRegion region;

    private AABB aabb;

    public DungeonNode(BlueprintRegion region) {
        this.region = region;
        this.aabb = new AABB(region.getMin().getX(), region.getMin().getZ(), region.getMax().getX(), region.getMax().getZ());
    }

    public AABB getAABB() {
        return this.aabb;
    }
}
