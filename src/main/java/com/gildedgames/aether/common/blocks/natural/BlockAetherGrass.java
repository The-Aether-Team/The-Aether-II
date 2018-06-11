package com.gildedgames.aether.common.blocks.natural;

import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.blocks.IBlockMultiName;
import com.gildedgames.aether.common.blocks.natural.plants.BlockAetherFlower;
import com.gildedgames.aether.common.blocks.natural.plants.BlockTallAetherGrass;
import com.gildedgames.aether.common.blocks.properties.BlockVariant;
import com.gildedgames.aether.common.blocks.properties.PropertyVariant;
import com.gildedgames.aether.common.items.ItemsAether;
import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class BlockAetherGrass extends Block implements IBlockMultiName, IGrowable
{
	public static final BlockVariant AETHER = new BlockVariant(0, "normal"),
			ENCHANTED = new BlockVariant(1, "enchanted"),
			ARCTIC = new BlockVariant(2, "arctic"),
			MAGNETIC = new BlockVariant(3, "magnetic"),
			IRRADIATED = new BlockVariant(4, "irradiated");

	public static final PropertyVariant PROPERTY_VARIANT = PropertyVariant.create("variant", AETHER, ENCHANTED, ARCTIC, MAGNETIC, IRRADIATED);

	public static final PropertyBool SNOWY = PropertyBool.create("snowy");

	public BlockAetherGrass()
	{
		super(Material.GRASS);

		this.setSoundType(SoundType.PLANT);

		this.setHardness(0.5F);
		this.setTickRandomly(true);

		this.setDefaultState(this.getBlockState().getBaseState().withProperty(PROPERTY_VARIANT, AETHER).withProperty(SNOWY, Boolean.FALSE));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(final CreativeTabs tab, final NonNullList<ItemStack> list)
	{
		for (final BlockVariant variant : PROPERTY_VARIANT.getAllowedValues())
		{
			list.add(new ItemStack(this, 1, variant.getMeta()));
		}
	}

	@Override
	public IBlockState getActualState(final IBlockState state, final IBlockAccess worldIn, final BlockPos pos)
	{
		final IBlockState up = worldIn.getBlockState(pos.up());
		final Block block = up.getBlock();

		final Comparable<?> snowGrass = up.getProperties().get(BlockTallAetherGrass.TYPE);
		final Comparable<?> snowFlower = up.getProperties().get(BlockAetherFlower.PROPERTY_SNOWY);

		return state.withProperty(SNOWY,
				block == BlocksAether.highlands_snow || block == BlocksAether.highlands_snow_layer || (snowGrass != null && snowGrass
						.equals(BlockTallAetherGrass.Type.SNOWY)) || (
						snowFlower != null && snowFlower
								.equals(true)));
	}

	@Override
	public void updateTick(final World world, final BlockPos pos, final IBlockState state, final Random rand)
	{
		if (!world.isRemote && state.getValue(PROPERTY_VARIANT) != ENCHANTED)
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

						final IBlockState aboveState = world.getBlockState(randomNeighbor.up());
						final IBlockState neighborState = world.getBlockState(randomNeighbor);

						if (neighborState.getBlock() == BlocksAether.aether_dirt
								&& neighborState.getValue(BlockAetherDirt.PROPERTY_VARIANT) == BlockAetherDirt.DIRT &&
								world.getLightFromNeighbors(randomNeighbor.up()) >= 4
								&& aboveState.getLightOpacity(world, randomNeighbor.up()) <= 2)
						{
							final IBlockState grassState = this.getDefaultState().withProperty(PROPERTY_VARIANT, state.getValue(PROPERTY_VARIANT));

							world.setBlockState(randomNeighbor, grassState);
						}
					}
				}
			}
		}
	}

	@Override
	public Item getItemDropped(final IBlockState state, final Random rand, final int fortune)
	{
		return Item.getItemFromBlock(BlocksAether.aether_dirt);
	}

	@Override
	public IBlockState getStateFromMeta(final int meta)
	{
		final BlockVariant variant = PROPERTY_VARIANT.fromMeta(meta);

		return this.getDefaultState().withProperty(PROPERTY_VARIANT, variant);
	}

	@Override
	public ItemStack getPickBlock(final IBlockState state, final RayTraceResult target, final World world, final BlockPos pos, final EntityPlayer player)
	{
		return new ItemStack(this, 1, state.getValue(PROPERTY_VARIANT).getMeta());
	}

	@Override
	public int getMetaFromState(final IBlockState state)
	{
		return state.getValue(PROPERTY_VARIANT).getMeta();
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, SNOWY, PROPERTY_VARIANT);
	}

	@Override
	public String getUnlocalizedName(final ItemStack stack)
	{
		return PROPERTY_VARIANT.fromMeta(stack.getMetadata()).getName();
	}

	@Override
	public boolean canGrow(final World worldIn, final BlockPos pos, final IBlockState state, final boolean isClient)
	{
		return true;
	}

	@Override
	public boolean canUseBonemeal(final World worldIn, final Random rand, final BlockPos pos, final IBlockState state)
	{
		return true;
	}

	@Override
	public void grow(final World worldIn, final Random rand, final BlockPos pos, final IBlockState state)
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
									.withProperty(BlockAetherFlower.PROPERTY_VARIANT, BlockAetherFlower.WHITE_ROSE));
						}
						else
						{
							worldIn.setBlockState(nextPos, BlocksAether.aether_flower.getDefaultState()
									.withProperty(BlockAetherFlower.PROPERTY_VARIANT, BlockAetherFlower.PURPLE_FLOWER));
						}
					}
					else
					{
						final IBlockState nextState = BlocksAether.tall_aether_grass.getDefaultState();

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
	@SideOnly(Side.CLIENT)
	public boolean onBlockActivated(
			final World worldIn, final BlockPos pos, final IBlockState state, final EntityPlayer playerIn, final EnumHand hand, final EnumFacing facing,
			final float hitX, final float hitY, final float hitZ)
	{
		if (playerIn.getHeldItemMainhand().getItem() == ItemsAether.swet_jelly)
		{
			if (!worldIn.isRemote && hand == EnumHand.MAIN_HAND)
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
			if (!worldIn.isRemote && hand == EnumHand.OFF_HAND)
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
