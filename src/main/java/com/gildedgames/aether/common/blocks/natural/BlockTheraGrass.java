package com.gildedgames.aether.common.blocks.natural;

import com.gildedgames.aether.api.registrar.BlocksAether;
import com.gildedgames.aether.common.blocks.IBlockMultiName;
import com.gildedgames.aether.common.blocks.natural.plants.BlockAetherFlower;
import com.gildedgames.aether.common.blocks.properties.BlockVariant;
import com.gildedgames.aether.common.blocks.properties.PropertyVariant;
import net.minecraft.block.*;
import net.minecraft.block.GrassBlock;
import net.minecraft.state.BooleanProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemGroup;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateContainer;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

public class BlockTheraGrass extends GrassBlock implements IBlockMultiName
{
	public static final BlockVariant NORMAL = new BlockVariant(0, "normal");

	public static final PropertyVariant PROPERTY_VARIANT = PropertyVariant.create("variant", NORMAL);

	public static final BooleanProperty SNOWY = BooleanProperty.create("snowy");

	public BlockTheraGrass(Block.Properties properties)
	{
		super(properties.tickRandomly().doesNotBlockMovement());

		this.setDefaultState(this.stateContainer.getBaseState().with(PROPERTY_VARIANT, NORMAL).with(SNOWY, Boolean.FALSE));
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void getSubBlocks(final ItemGroup tab, final NonNullList<ItemStack> list)
	{
		for (final BlockVariant variant : PROPERTY_VARIANT.getAllowedValues())
		{
			list.add(new ItemStack(this, 1, variant.getMeta()));
		}
	}

	@Override
	public BlockState getActualState(final BlockState state, final IBlockReader worldIn, final BlockPos pos)
	{
		final Block block = worldIn.getBlockState(pos.up()).getBlock();

		return state.with(SNOWY, block == Blocks.SNOW || block == Blocks.SNOW_LAYER);
	}

	@Override
	public void tick(BlockState state, World world, BlockPos pos, Random rand)
	{
		if (!world.isRemote && state.get(PROPERTY_VARIANT) == NORMAL)
		{
			if (world.getLightFromNeighbors(pos.up()) < 4 && world.getBlockState(pos.up()).getLightOpacity(world, pos.up()) > 2)
			{
				world.setBlockState(pos, BlocksAether.aether_dirt.getDefaultState());
			}
			else
			{
				if (world.getLightFromNeighbors(pos.up()) >= 9)
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
								&& neighborState.getValue(BlockAetherDirt.PROPERTY_VARIANT) == BlockAetherDirt.DIRT &&
								world.getLightFromNeighbors(randomNeighbor.up()) >= 4
								&& aboveState.getLightOpacity(world, randomNeighbor.up()) <= 2)
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
	public Item getItemDropped(final BlockState state, final Random rand, final int fortune)
	{
		return Item.getItemFromBlock(BlocksAether.aether_dirt);
	}

	@Override
	public BlockState getStateFromMeta(final int meta)
	{
		final BlockVariant variant = PROPERTY_VARIANT.fromMeta(meta);

		return this.getDefaultState().with(PROPERTY_VARIANT, variant);
	}

	@Override
	public ItemStack getPickBlock(final BlockState state, final RayTraceResult target, final World world, final BlockPos pos, final PlayerEntity player)
	{
		return new ItemStack(this, 1, state.get(PROPERTY_VARIANT).getMeta());
	}

	@Override
	public int getMetaFromState(final BlockState state)
	{
		return state.get(PROPERTY_VARIANT).getMeta();
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
	{
		builder.add(PROPERTY_VARIANT, SNOWY);
	}

	@Override
	public String getTranslationKey(final ItemStack stack)
	{
		return PROPERTY_VARIANT.fromMeta(stack.getMetadata()).getName();
	}

	@Override
	public boolean canGrow(final World worldIn, final BlockPos pos, final BlockState state, final boolean isClient)
	{
		return true;
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
					nextPos = nextPos.add(rand.nextInt(3) - 1, (rand.nextInt(3) - 1) * rand.nextInt(3) / 2, rand.nextInt(3) - 1);

					if (worldIn.getBlockState(nextPos.down()).getBlock() == BlocksAether.thera_grass &&
							!worldIn.getBlockState(nextPos).isNormalCube())
					{
						grassCount++;

						continue;
					}
				}
				else if (worldIn.isAirBlock(nextPos))
				{
					if (rand.nextInt(8) == 0 && BlocksAether.aether_flower.canPlaceBlockAt(worldIn, nextPos))
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

						if (BlocksAether.tall_aether_grass.canPlaceBlockAt(worldIn, nextPos))
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
