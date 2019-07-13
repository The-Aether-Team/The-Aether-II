package com.gildedgames.aether.common.blocks.natural;

import com.gildedgames.aether.api.registrar.BlocksAether;
import com.gildedgames.aether.common.blocks.natural.plants.BlockAetherFlower;
import com.gildedgames.aether.common.blocks.properties.BlockVariant;
import com.gildedgames.aether.common.blocks.properties.PropertyVariant;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.GrassBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import java.util.Random;

public class BlockTheraGrass extends GrassBlock
{
	public static final BlockVariant NORMAL = new BlockVariant(0, "normal");

	public static final PropertyVariant PROPERTY_VARIANT = PropertyVariant.create("variant", NORMAL);

	public static final BooleanProperty SNOWY = BooleanProperty.create("snowy");

	public BlockTheraGrass(Block.Properties properties)
	{
		super(properties.tickRandomly().doesNotBlockMovement());

		this.setDefaultState(this.getStateContainer().getBaseState().with(PROPERTY_VARIANT, NORMAL).with(SNOWY, Boolean.FALSE));
	}

	@Override
	public void tick(BlockState state, World world, BlockPos pos, Random rand)
	{
		if (!world.isRemote() && state.get(PROPERTY_VARIANT) == NORMAL)
		{
			if (world.getBrightness(pos.up()) < 4 && world.getBlockState(pos.up()).getOpacity(world, pos.up()) > 2)
			{
				world.setBlockState(pos, BlocksAether.aether_dirt.getDefaultState());
			}
			else
			{
				if (world.getBrightness(pos.up()) >= 9)
				{
					for (int i = 0; i < 4; ++i)
					{
						final BlockPos randomNeighbor = pos.add(rand.nextInt(3) - 1, rand.nextInt(5) - 3, rand.nextInt(3) - 1);

						if (randomNeighbor.getY() >= 0 && randomNeighbor.getY() < 256 && !world.isBlockLoaded(randomNeighbor))
						{
							return;
						}

						final BlockState aboveState = world.getBlockState(randomNeighbor.up());
						final BlockState neighborState = world.getBlockState(randomNeighbor);

						if (neighborState.getBlock() == BlocksAether.aether_dirt
								&& neighborState.get(BlockAetherDirt.PROPERTY_VARIANT) == BlockAetherDirt.DIRT &&
								world.getBrightness(randomNeighbor.up()) >= 4
								&& aboveState.getOpacity(world, randomNeighbor.up()) <= 2)
						{
							final BlockState grassState = this.getDefaultState().with(PROPERTY_VARIANT, NORMAL);

							world.setBlockState(randomNeighbor, grassState);
						}
					}
				}
			}
		}
	}

	@Override
	public ItemStack getItem(IBlockReader world, BlockPos pos, BlockState state)
	{
		return new ItemStack(this, 1);
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
	{
		builder.add(PROPERTY_VARIANT, SNOWY);
	}

	@Override
	public boolean canUseBonemeal(final World worldIn, final Random rand, final BlockPos pos, final BlockState state)
	{
		return true;
	}

	@Override
	public void grow(final World worldIn, final Random rand, final BlockPos pos, final BlockState state)
	{
		int count = 0;

		while (count < 128)
		{
			BlockPos nextPos = pos.up();
			int grassCount = 0;

			while (true)
			{
				if (grassCount < count / 16)
				{
					nextPos = nextPos.add(
							rand.nextInt(3) - 1,
							(rand.nextInt(3) - 1) * rand.nextInt(3) / 2,
							rand.nextInt(3) - 1);

					if (worldIn.getBlockState(nextPos.down()).getBlock() == BlocksAether.thera_grass &&
							!worldIn.getBlockState(nextPos).isNormalCube(worldIn, nextPos))
					{
						grassCount++;

						continue;
					}
				}
				else if (worldIn.isAirBlock(nextPos))
				{
					if (rand.nextInt(8) == 0 && BlocksAether.aether_flower.getDefaultState().isValidPosition(worldIn, nextPos))
					{
						final int randFlower = rand.nextInt(3);

						if (randFlower >= 2)
						{
							worldIn.setBlockState(nextPos, BlocksAether.aether_flower.getDefaultState()
									.with(BlockAetherFlower.PROPERTY_VARIANT, BlockAetherFlower.WHITE_ROSE));
						}
						else
						{
							worldIn.setBlockState(nextPos, BlocksAether.aether_flower.getDefaultState()
									.with(BlockAetherFlower.PROPERTY_VARIANT, BlockAetherFlower.PURPLE_FLOWER));
						}
					}
					else
					{
						final BlockState nextState = BlocksAether.tall_aether_grass.getDefaultState();

						if (nextState.isValidPosition(worldIn, nextPos))
						{
							worldIn.setBlockState(nextPos, nextState, 3);
						}
					}
				}

				++count;
				break;
			}
		}
	}

}
