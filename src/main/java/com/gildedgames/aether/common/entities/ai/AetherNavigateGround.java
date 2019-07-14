package com.gildedgames.aether.common.entities.ai;

import com.gildedgames.aether.api.registrar.BlocksAether;
import com.gildedgames.aether.common.blocks.natural.BlockAercloud;
import net.minecraft.entity.MobEntity;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class AetherNavigateGround extends GroundPathNavigator
{
	public AetherNavigateGround(MobEntity entitylivingIn, World worldIn)
	{
		super(entitylivingIn, worldIn);
	}

	@Override
	protected void trimPath()
	{
		super.trimPath();

		for (int i = 0; i < this.currentPath.getCurrentPathLength(); ++i)
		{
			PathPoint pathpoint = this.currentPath.getPathPointFromIndex(i);

			if (this.world.getBlockState(new BlockPos(pathpoint.x, pathpoint.y, pathpoint.z)) ==
					BlocksAether.aercloud.getDefaultState().with(BlockAercloud.PROPERTY_VARIANT, BlockAercloud.BLUE_AERCLOUD))
			{
				this.currentPath.setCurrentPathIndex(i - 1);

				return;
			}
		}
	}
}