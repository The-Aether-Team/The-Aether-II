package com.gildedgames.aether.common.blocks.natural;

import com.gildedgames.aether.api.registrar.BlocksAether;
import com.gildedgames.aether.api.registrar.ItemsAether;
import com.gildedgames.aether.common.blocks.IBlockMultiName;
import com.gildedgames.aether.common.blocks.natural.plants.BlockAetherFlower;
import com.gildedgames.aether.common.blocks.natural.plants.BlockTallAetherGrass;
import com.gildedgames.aether.common.blocks.properties.BlockVariant;
import com.gildedgames.aether.common.blocks.properties.PropertyVariant;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.state.BooleanProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.item.ItemGroup;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import java.util.Random;

public class BlockAetherGrass extends Block implements IBlockMultiName, IGrowable
{
	public static final BlockVariant AETHER = new BlockVariant(0, "normal"),
			ENCHANTED = new BlockVariant(1, "enchanted"),
			ARCTIC = new BlockVariant(2, "arctic"),
			MAGNETIC = new BlockVariant(3, "magnetic"),
			IRRADIATED = new BlockVariant(4, "irradiated");

	public static final PropertyVariant PROPERTY_VARIANT = PropertyVariant.create("variant", AETHER, ENCHANTED, ARCTIC, MAGNETIC, IRRADIATED);

	public static final BooleanProperty SNOWY = BooleanProperty.create("snowy");

	public BlockAetherGrass(Block.Properties properties)
	{
		super(properties);

		this.setDefaultState(this.stateContainer.getBaseState().with(PROPERTY_VARIANT, AETHER).with(SNOWY, Boolean.FALSE));
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
		final BlockState up = worldIn.getBlockState(pos.up());
		final Block block = up.getBlock();

		final Comparable<?> snowGrass = up.getProperties().get(BlockTallAetherGrass.TYPE);
		final Comparable<?> snowFlower = up.getProperties().get(BlockAetherFlower.PROPERTY_SNOWY);

		return state.with(SNOWY,
				block == BlocksAether.highlands_snow || block == BlocksAether.highlands_snow_layer || (snowGrass != null && snowGrass
						.equals(BlockTallAetherGrass.Type.SNOWY)) || (
						snowFlower != null && snowFlower
								.equals(true)));
	}

	@Override
	public void tick(BlockState state, World world, BlockPos pos, Random rand)
	{
		if (!world.isRemote && state.get(PROPERTY_VARIANT) != ENCHANTED)
		{
			BlockPos.PooledMutableBlockPos up = BlockPos.PooledMutableBlockPos.retain();
			up.setPos(pos.getX(), pos.getY() + 1, pos.getZ());

			if (world.getLightFromNeighbors(up) < 4 && world.getBlockState(up).getLightOpacity(world, up) > 2)
			{
				world.setBlockState(pos, BlocksAether.aether_dirt.getDefaultState());
			}
			else
			{
				if (world.getLightFromNeighbors(up) >= 9)
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
							final BlockState grassState = this.getDefaultState().with(PROPERTY_VARIANT, state.get(PROPERTY_VARIANT));

							world.setBlockState(randomNeighbor, grassState);
						}
					}
				}
			}

			up.release();
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
		builder.add(SNOWY, PROPERTY_VARIANT);
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

					if (worldIn.getBlockState(nextPos.down()).getBlock() == BlocksAether.aether_grass &&
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

	@Override
	@OnlyIn(Dist.CLIENT)
	public boolean onBlockActivated(
			final World worldIn, final BlockPos pos, final BlockState state, final PlayerEntity playerIn, final Hand hand, final Direction facing,
			final float hitX, final float hitY, final float hitZ)
	{
		if (playerIn.getHeldItemMainhand().getItem() == ItemsAether.swet_jelly)
		{
			if (!worldIn.isRemote && hand == Hand.MAIN_HAND)
			{
				if (!this.canGrow(worldIn, pos, state, true))
				{
					return false;
				}
				if (!playerIn.isCreative())
				{
					playerIn.getHeldItemMainhand().shrink(1);
				}
				this.grow(worldIn, new Random(), pos, state);
				return true;
			}
		}
		else if (playerIn.getHeldItemOffhand().getItem() == ItemsAether.swet_jelly)
		{
			if (!worldIn.isRemote && hand == Hand.OFF_HAND)
			{
				if (!this.canGrow(worldIn, pos, state, true))
				{
					return false;
				}
				if (!playerIn.isCreative())
				{
					playerIn.getHeldItemOffhand().shrink(1);
				}
				this.grow(worldIn, new Random(), pos, state);
				return true;
			}

		}
		return false;
	}

}
