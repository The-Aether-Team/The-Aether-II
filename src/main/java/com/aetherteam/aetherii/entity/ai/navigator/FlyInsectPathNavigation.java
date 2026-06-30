package com.aetherteam.aetherii.entity.ai.navigator;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.Node;

public class FlyInsectPathNavigation extends FlyingPathNavigation {
    public FlyInsectPathNavigation(Mob p_26424_, Level p_26425_) {
        super(p_26424_, p_26425_);
    }

    @Override
    public boolean isStableDestination(BlockPos pos) {
        return this.level.getBlockState(pos).isAir();
    }

    @Override
    protected void trimPath() {
        super.trimPath();
        if (this.level.isRaining()) {
            if (this.level.isRainingAt(BlockPos.containing(this.mob.getX(), this.mob.getY() + 0.5, this.mob.getZ()))) {
                return;
            }

            for (int i = 0; i < this.path.getNodeCount(); i++) {
                Node node = this.path.getNode(i);
                if (this.level.isRainingAt(new BlockPos(node.x, node.y, node.z))) {
                    this.path.truncateNodes(i);
                    return;
                }
            }
        }
    }
}
