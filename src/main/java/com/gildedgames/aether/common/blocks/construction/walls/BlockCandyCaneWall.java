package com.gildedgames.aether.common.blocks.construction.walls;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class BlockCandyCaneWall extends BlockCustomWall
{
	public BlockCandyCaneWall(Block.Properties properties)
	{
		super(properties);
	}

	@Override
	public void onEntityWalk(World world, BlockPos pos, Entity entity)
	{
		super.onEntityWalk(world, pos, entity);

		if (world.isRaining())
		{
			Vec3d motion = entity.getMotion();
			entity.setMotion(motion.mul(0.1D, 1.0D, 0.1D));
		}
	}
}
