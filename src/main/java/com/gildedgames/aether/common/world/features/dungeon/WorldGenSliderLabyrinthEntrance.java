package com.gildedgames.aether.common.world.features.dungeon;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.blocks.dungeon.BlockDivine;
import com.gildedgames.aether.common.world.GenUtil;

public class WorldGenSliderLabyrinthEntrance extends WorldGenerator
{

	int randHeight;

	public WorldGenSliderLabyrinthEntrance(int randHeight)
	{
		this.randHeight = randHeight;
	}

	@Override
	public boolean generate(World world, Random rand, BlockPos pos)
	{
		int height = 8 + rand.nextInt(this.randHeight);

		if (pos.getY() + height + 5 > world.getActualHeight())
		{
			return false;
		}
		
		BlockPos.MutableBlockPos iterPos = new BlockPos.MutableBlockPos(pos.getX(), pos.getY(), pos.getZ());
		
		for (int x1 = pos.getX() - 3; x1 < pos.getX() + 3; x1++)
		{
			for (int y1 = pos.getY() + 1; y1 < pos.getY() + (height - 2); y1++)
			{
				for (int z1 = pos.getZ() - 3; z1 < pos.getZ() + 3; z1++)
				{
					iterPos.setPos(x1, y1, z1);
					
					if (world.getBlockState(iterPos).getBlock() != Blocks.AIR && world.getBlockState(iterPos).getBlock() != BlocksAether.aether_grass && world.getBlockState(iterPos).getBlock() != BlocksAether.aether_dirt && world.getBlockState(iterPos).getBlock() != BlocksAether.aercloud && world.getBlockState(iterPos).getBlock() != BlocksAether.tall_aether_grass)
					{
						return false;
					}
				}
			}
		}

		for (int x1 = pos.getX() - 3; x1 < pos.getX() + 3; x1++)
		{
			for (int y1 = pos.getY(); y1 < pos.getY() + 1; y1++)
			{
				for (int z1 = pos.getZ() - 3; z1 < pos.getZ() + 3; z1++)
				{
					iterPos.setPos(x1, y1, z1);
					
					if (world.getBlockState(iterPos).getBlock() == Blocks.AIR)
					{
						return false;
					}
				}
			}
		}

		iterPos.setPos(pos.getX() + 3, pos.getY(), pos.getZ() + 3);
		
		if (pos.getY() + (height + 2) <= world.getHeight() && (world.getBlockState(iterPos).getBlock() == BlocksAether.aether_grass || world.getBlockState(iterPos).getBlock() == BlocksAether.aether_dirt))
		{
			iterPos.setPos(pos.getX() + 6, pos.getY() + height, pos.getZ() + 6);
			
			GenUtil.cuboidVaried(world, pos, iterPos, BlocksAether.carved_stone.getStateFromMeta(BlockDivine.DIVINE), BlocksAether.sentry_stone.getStateFromMeta(BlockDivine.DIVINE), 8, rand, true);
		}
		else	
		{
			return false;
		}

		int gateMaxX = pos.getX() + 5;
		int gateMaxY = pos.getY() + 7;
		int gateMaxZ = pos.getZ() + 5;

		int gateMinX = pos.getX() + 1;
		int gateMinY = pos.getY() + 4;
		int gateMinZ = pos.getZ() + 1;
		
		BlockPos.MutableBlockPos iterPos2 = new BlockPos.MutableBlockPos(pos.getX(), pos.getY(), pos.getZ());

		GenUtil.cuboid(world, iterPos.setPos(gateMinX, gateMinY, gateMinZ - 1), iterPos2.setPos(gateMaxX, gateMaxY - 1, gateMaxZ + 1), Blocks.AIR.getDefaultState());
		GenUtil.cuboid(world, iterPos.setPos(gateMinX - 1, gateMinY, gateMinZ), iterPos2.setPos(gateMaxX + 1, gateMaxY - 1, gateMaxZ), Blocks.AIR.getDefaultState());

		for (int j = 0; j < 1; j++)
		{
			int i = 0;
			int j2 = pos.getY() + 4 + j;

			for (i = j; i <= ((pos.getX() + 2) - pos.getX()) - j; i++)
			{
				//world.setBlockState(pos.getX() + i + 2, j2, (pos.getZ() + 2) - j + 2, BlocksAether.DivineCarvedStairs, 2, 3);
				//world.setBlock(pos.getX() + i + 2, j2, pos.getZ() + j + 2, BlocksAether.DivineCarvedStairs, 2, 3);
			}

			for (i = j; i <= ((pos.getZ() + 2) - pos.getZ()) - j; i++)
			{
				//world.setBlock(pos.getX() + j + 2, j2, pos.getZ() + i + 2, BlocksAether.DivineCarvedStairs, 0, 3);
				//world.setBlock((pos.getX() + 2) - j + 2, j2, pos.getZ() + i + 2, BlocksAether.DivineCarvedStairs, 1, 3);
			}
		}
		
		iterPos.setPos(pos.getX() + 3, pos.getY() + 4, pos.getZ() + 3);

		world.setBlockState(iterPos, BlocksAether.carved_stone.getStateFromMeta(BlockDivine.DIVINE));
		
		iterPos.setPos(pos.getX() + 3, pos.getY() + 4, pos.getZ() + 3);
		
		world.setBlockState(iterPos, BlocksAether.labyrinth_totem.getDefaultState());

		for (int j = 0; j < 3; j++)
		{
			int i = 0;
			int j2 = height + pos.getY() + j;

			for (i = j; i <= (gateMaxX + 1 - pos.getX()) - j; i++)
			{
				//world.setBlock(pos.getX() + i, j2, gateMaxZ + 1 - j, BlocksAether.DivineCarvedStairs, 3, 3);
				//world.setBlock(pos.getX() + i, j2, pos.getZ() + j, BlocksAether.DivineCarvedStairs, 2, 3);
			}

			for (i = j; i <= (gateMaxZ + 1 - pos.getZ()) - j; i++)
			{
				//world.setBlock(pos.getX() + j, j2, pos.getZ() + i, BlocksAether.DivineCarvedStairs, 0, 3);
				//world.setBlock(gateMaxX + 1 - j, j2, pos.getZ() + i, BlocksAether.DivineCarvedStairs, 1, 3);
			}
		}

		iterPos.setPos(pos.getX() + 3, height + pos.getY() + 2, pos.getZ() + 3);
		
		//world.setBlockState(iterPos, BlocksAether.carved_stone.getStateFromMeta(BlockDungeon.DIVINE));

		return true;
	}
	
}