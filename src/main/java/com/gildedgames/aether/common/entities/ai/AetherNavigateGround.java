package com.gildedgames.aether.common.entities.ai;

import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.blocks.natural.BlockAercloud;
import net.minecraft.entity.EntityLiving;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class AetherNavigateGround extends PathNavigateGround
{
	public AetherNavigateGround(EntityLiving entitylivingIn, World worldIn)
	{
		super(entitylivingIn, worldIn);
	}

	@Override
	protected void removeSunnyPath()
	{
		super.removeSunnyPath();

		for (int i = 0; i < this.currentPath.getCurrentPathLength(); ++i)
		{
			PathPoint pathpoint = this.currentPath.getPathPointFromIndex(i);

			if (this.world.getBlockState(new BlockPos(pathpoint.x, pathpoint.y, pathpoint.z)) == BlocksAether.aercloud
					.getStateFromMeta(BlockAercloud.BLUE_AERCLOUD.getMeta()))
			{
				this.currentPath.setCurrentPathLength(i - 1);
				return;
			}
		}
	}
}