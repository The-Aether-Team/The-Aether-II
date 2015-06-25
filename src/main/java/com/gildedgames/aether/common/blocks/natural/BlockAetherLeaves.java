package com.gildedgames.aether.common.blocks.natural;

import com.gildedgames.aether.common.AetherCreativeTabs;
import com.gildedgames.aether.common.blocks.util.variants.IAetherBlockWithSubtypes;
import com.gildedgames.aether.common.blocks.util.variants.blockstates.BlockVariant;
import com.gildedgames.aether.common.blocks.util.variants.blockstates.PropertyVariant;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BlockAetherLeaves extends Block implements IShearable, IAetherBlockWithSubtypes
{
	public static final BlockVariant
			BLUE_SKYROOT_LEAVES = new BlockVariant(0, "blue_skyroot"),
			GREEN_SKYROOT_LEAVES = new BlockVariant(1, "green_skyroot"),
			DARK_BLUE_SKYROOT_LEAVES = new BlockVariant(2, "dark_blue_skyroot"),
			GOLDEN_OAK_LEAVES = new BlockVariant(3, "golden_oak"),
			PURPLE_CRYSTAL_LEAVES = new BlockVariant(4, "purple_crystal"),
			PURPLE_FRUIT_LEAVES = new BlockVariant(5, "purple_fruit");

	public static PropertyVariant PROPERTY_VARIANT = PropertyVariant.create("variant", BLUE_SKYROOT_LEAVES, GREEN_SKYROOT_LEAVES, DARK_BLUE_SKYROOT_LEAVES,
			GOLDEN_OAK_LEAVES, PURPLE_CRYSTAL_LEAVES, PURPLE_FRUIT_LEAVES);

	public static PropertyBool PROPERTY_DECAYABLE = PropertyBool.create("decayable");

	public static PropertyBool PROPERTY_CHECK_DECAY = PropertyBool.create("check_decay");

	private int[] surroundings;

	public BlockAetherLeaves()
	{
		super(Material.leaves);

		this.setHardness(0.2f);
		this.setTickRandomly(true);

		this.setStepSound(Block.soundTypeGrass);

		this.setCreativeTab(AetherCreativeTabs.tabBlocks);

		this.setDefaultState(this.getBlockState().getBaseState().withProperty(PROPERTY_VARIANT, BLUE_SKYROOT_LEAVES)
				.withProperty(PROPERTY_DECAYABLE, Boolean.TRUE).withProperty(PROPERTY_CHECK_DECAY, Boolean.TRUE));
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs tab, List list)
	{
		for (BlockVariant variant : PROPERTY_VARIANT.getAllowedValues())
		{
			list.add(new ItemStack(item, 1, variant.getMeta()));
		}
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public boolean isShearable(ItemStack item, IBlockAccess world, BlockPos pos)
	{
		return true;
	}

	@Override
	public boolean isLeaves(IBlockAccess world, BlockPos pos)
	{
		return true;
	}

	@Override
	public boolean isVisuallyOpaque()
	{
		return false;
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
	{
		byte start = 1;

		int range = start + 1;
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();

		if (worldIn.isAreaLoaded(new BlockPos(x - range, y - range, z - range), new BlockPos(x + range, y + range, z + range)))
		{
			for (int x2 = -start; x2 <= start; ++x2)
			{
				for (int y2 = -start; y2 <= start; ++y2)
				{
					for (int z2 = -start; z2 <= start; ++z2)
					{
						BlockPos newPos = pos.add(x2, y2, z2);
						IBlockState newState = worldIn.getBlockState(newPos);

						if (newState.getBlock().isLeaves(worldIn, newPos))
						{
							newState.getBlock().beginLeavesDecay(worldIn, newPos);
						}
					}
				}
			}
		}
	}

	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
	{
		if (!worldIn.isRemote)
		{
			if ((Boolean) state.getValue(PROPERTY_CHECK_DECAY) && (Boolean) state.getValue(PROPERTY_DECAYABLE))
			{
				byte start = 4;
				int range = start + 1;
				int x = pos.getX();
				int y = pos.getY();
				int z = pos.getZ();

				byte b1 = 32;
				int i1 = b1 * b1;
				int j1 = b1 / 2;

				if (this.surroundings == null)
				{
					this.surroundings = new int[b1 * b1 * b1];
				}

				int x2;

				if (worldIn.isAreaLoaded(new BlockPos(x - range, y - range, z - range), new BlockPos(x + range, y + range, z + range)))
				{
					int y2;
					int z2;

					for (x2 = -start; x2 <= start; ++x2)
					{
						for (y2 = -start; y2 <= start; ++y2)
						{
							for (z2 = -start; z2 <= start; ++z2)
							{
								BlockPos newPos = new BlockPos(x + x2, y + y2, z + z2);
								Block block = worldIn.getBlockState(newPos).getBlock();

								if (!block.canSustainLeaves(worldIn, newPos))
								{
									if (block.isLeaves(worldIn, newPos))
									{
										this.surroundings[(x2 + j1) * i1 + (y2 + j1) * b1 + z2 + j1] = -2;
									}
									else
									{
										this.surroundings[(x2 + j1) * i1 + (y2 + j1) * b1 + z2 + j1] = -1;
									}
								}
								else
								{
									this.surroundings[(x2 + j1) * i1 + (y2 + j1) * b1 + z2 + j1] = 0;
								}
							}
						}
					}

					for (x2 = 1; x2 <= 4; ++x2)
					{
						for (y2 = -start; y2 <= start; ++y2)
						{
							for (z2 = -start; z2 <= start; ++z2)
							{
								for (int j2 = -start; j2 <= start; ++j2)
								{
									if (this.surroundings[(y2 + j1) * i1 + (z2 + j1) * b1 + j2 + j1] == x2 - 1)
									{
										if (this.surroundings[(y2 + j1 - 1) * i1 + (z2 + j1) * b1 + j2 + j1] == -2)
										{
											this.surroundings[(y2 + j1 - 1) * i1 + (z2 + j1) * b1 + j2 + j1] = x2;
										}

										if (this.surroundings[(y2 + j1 + 1) * i1 + (z2 + j1) * b1 + j2 + j1] == -2)
										{
											this.surroundings[(y2 + j1 + 1) * i1 + (z2 + j1) * b1 + j2 + j1] = x2;
										}

										if (this.surroundings[(y2 + j1) * i1 + (z2 + j1 - 1) * b1 + j2 + j1] == -2)
										{
											this.surroundings[(y2 + j1) * i1 + (z2 + j1 - 1) * b1 + j2 + j1] = x2;
										}

										if (this.surroundings[(y2 + j1) * i1 + (z2 + j1 + 1) * b1 + j2 + j1] == -2)
										{
											this.surroundings[(y2 + j1) * i1 + (z2 + j1 + 1) * b1 + j2 + j1] = x2;
										}

										if (this.surroundings[(y2 + j1) * i1 + (z2 + j1) * b1 + (j2 + j1 - 1)] == -2)
										{
											this.surroundings[(y2 + j1) * i1 + (z2 + j1) * b1 + (j2 + j1 - 1)] = x2;
										}

										if (this.surroundings[(y2 + j1) * i1 + (z2 + j1) * b1 + j2 + j1 + 1] == -2)
										{
											this.surroundings[(y2 + j1) * i1 + (z2 + j1) * b1 + j2 + j1 + 1] = x2;
										}
									}
								}
							}
						}
					}
				}

				x2 = this.surroundings[j1 * i1 + j1 * b1 + j1];

				if (x2 >= 0)
				{
					worldIn.setBlockState(pos, state.withProperty(PROPERTY_CHECK_DECAY, false), 4);
				}
				else
				{
					this.destroy(worldIn, pos);
				}
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public EnumWorldBlockLayer getBlockLayer()
	{
		return EnumWorldBlockLayer.CUTOUT_MIPPED;
	}

	@Override
	public int quantityDropped(Random random)
	{
		return random.nextInt(20) == 0 ? 1 : 0;
	}

	private void destroy(World worldIn, BlockPos pos)
	{
		this.dropBlockAsItem(worldIn, pos, worldIn.getBlockState(pos), 0);

		worldIn.setBlockToAir(pos);
	}

	@Override
	public List<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune)
	{
		ArrayList<ItemStack> stacks = new ArrayList<ItemStack>();
		stacks.add(new ItemStack(this));

		return stacks;
	}

	@Override
	public int damageDropped(IBlockState state)
	{
		return ((BlockVariant) state.getValue(PROPERTY_VARIANT)).getMeta();
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(PROPERTY_VARIANT, PROPERTY_VARIANT.fromMeta(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return ((BlockVariant) state.getValue(PROPERTY_VARIANT)).getMeta();
	}

	@Override
	protected BlockState createBlockState()
	{
		return new BlockState(this, PROPERTY_VARIANT, PROPERTY_DECAYABLE, PROPERTY_CHECK_DECAY);
	}

	@Override
	public String getSubtypeUnlocalizedName(ItemStack stack)
	{
		return PROPERTY_VARIANT.fromMeta(stack.getMetadata()).getName();
	}
}
