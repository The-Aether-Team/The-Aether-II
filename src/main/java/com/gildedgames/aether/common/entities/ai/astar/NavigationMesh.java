package com.gildedgames.aether.common.entities.ai.astar;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class NavigationMesh
{

	private World world;

	private Node[] nodes;

	private BlockPos pos;

	private int volume;

	public NavigationMesh(World world, BlockPos pos, int width, int height, int length)
	{
		this.world = world;
		this.pos = pos;

		this.volume = width * height * length;

		this.nodes = new Node[this.volume];

		BlockPos.MutableBlockPos iterPos = new BlockPos.MutableBlockPos();

		for (int i = 0; i < this.volume; i++)
		{
			int x = 0;
			int y = 0;
			int z = 0;

			iterPos.setPos(x, y, z);

			IBlockState state = world.getBlockState(iterPos);
			Block block = state.getBlock();

			this.nodes[i] = new Node(iterPos.toImmutable());

			if (block != null && !block.isAir(state, world, iterPos))
			{
				this.nodes[i].setMovementCost(-1);
				this.nodes[i].setDistanceToTarget(-1);
			}
		}
	}

}
