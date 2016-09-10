package com.gildedgames.aether.common.blocks.natural;

import com.gildedgames.aether.client.renderer.particles.ParticleGolden;
import com.gildedgames.aether.common.blocks.BlocksAether;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BlockAetherLeaves extends BlockLeaves
{
	public static final PropertyBool PROPERTY_DECAYABLE = PropertyBool.create("decayable");

	public static final PropertyBool PROPERTY_CHECK_DECAY = PropertyBool.create("check_decay");

	public static final PropertyBool PROPERTY_OPAQUE = PropertyBool.create("opaque");

	private final int saplingMeta;

	private int[] surroundings;

	protected static boolean graphicsFancy;

	public BlockAetherLeaves(int saplingMeta)
	{
		super();

		this.saplingMeta = saplingMeta;

		this.setHardness(0.2f);
		this.setTickRandomly(true);

		this.setSoundType(SoundType.PLANT);

		this.setDefaultState(this.getBlockState().getBaseState().withProperty(PROPERTY_DECAYABLE, Boolean.TRUE).withProperty(PROPERTY_CHECK_DECAY, Boolean.TRUE).withProperty(PROPERTY_OPAQUE, Boolean.valueOf(!BlockAetherLeaves.graphicsFancy)));

		Blocks.FIRE.setFireInfo(this, 30, 60);
	}

	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
	{
		return state.withProperty(PROPERTY_OPAQUE, Boolean.valueOf(!BlockAetherLeaves.graphicsFancy));
	}

	@Override
	public boolean isShearable(ItemStack item, IBlockAccess world, BlockPos pos)
	{
		return true;
	}

	@Override
	public boolean isLeaves(IBlockState state, IBlockAccess world, BlockPos pos)
	{
		return true;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return !BlockAetherLeaves.graphicsFancy;
	}

	@SideOnly(Side.CLIENT)
	public static void setGraphics(boolean fancy)
	{
		BlockAetherLeaves.graphicsFancy = fancy;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer()
	{
		return BlockAetherLeaves.graphicsFancy ? BlockRenderLayer.CUTOUT_MIPPED : BlockRenderLayer.SOLID;
	}

	@Override
	public boolean isVisuallyOpaque()
	{
		return false;
	}

	@Override
	public BlockPlanks.EnumType getWoodType(int meta)
	{
		return null;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
	{
		return !BlockAetherLeaves.graphicsFancy && blockAccess.getBlockState(pos.offset(side)).getBlock() == this ? false : this.superShouldSideBeRendered(blockState, blockAccess, pos, side);
	}

	@SideOnly(Side.CLIENT)
	public boolean superShouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
	{
		AxisAlignedBB axisalignedbb = blockState.getBoundingBox(blockAccess, pos);

		switch (side)
		{
		case DOWN:

			if (axisalignedbb.minY > 0.0D)
			{
				return true;
			}

			break;
		case UP:

			if (axisalignedbb.maxY < 1.0D)
			{
				return true;
			}

			break;
		case NORTH:

			if (axisalignedbb.minZ > 0.0D)
			{
				return true;
			}

			break;
		case SOUTH:

			if (axisalignedbb.maxZ < 1.0D)
			{
				return true;
			}

			break;
		case WEST:

			if (axisalignedbb.minX > 0.0D)
			{
				return true;
			}

			break;
		case EAST:

			if (axisalignedbb.maxX < 1.0D)
			{
				return true;
			}
		}

		return !blockAccess.getBlockState(pos.offset(side)).doesSideBlockRendering(blockAccess, pos.offset(side), side.getOpposite());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand)
	{
		if (this == BlocksAether.golden_oak_leaves)
		{
			for (int count = 0; count < 3; count++)
			{
				double x = pos.getX() + (rand.nextFloat() * 6f) - 3f;
				double y = pos.getY() + (rand.nextFloat() * 6f) - 3f;
				double z = pos.getZ() + (rand.nextFloat() * 6f) - 3f;

				ParticleGolden effect = new ParticleGolden(world, x, y, z, 0, 0, 0);

				FMLClientHandler.instance().getClient().effectRenderer.addEffect(effect);
			}
		}
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
	{
		byte start = 1;

		int size = start + 1;
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();

		if (worldIn.isAreaLoaded(new BlockPos(x - size, y - size, z - size), new BlockPos(x + size, y + size, z + size)))
		{
			for (int x2 = -start; x2 <= start; ++x2)
			{
				for (int y2 = -start; y2 <= start; ++y2)
				{
					for (int z2 = -start; z2 <= start; ++z2)
					{
						BlockPos newPos = pos.add(x2, y2, z2);
						IBlockState newState = worldIn.getBlockState(newPos);

						if (newState.getBlock().isLeaves(state, worldIn, newPos))
						{
							newState.getBlock().beginLeavesDecay(state, worldIn, newPos);
						}
					}
				}
			}
		}
	}

	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand)
	{
		if (!world.isRemote)
		{
			boolean checkDecay = state.getValue(PROPERTY_CHECK_DECAY);
			boolean isDecayable = state.getValue(PROPERTY_DECAYABLE);

			if (checkDecay && isDecayable)
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

				if (world.isAreaLoaded(new BlockPos(x - range, y - range, z - range), new BlockPos(x + range, y + range, z + range)))
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
								Block block = world.getBlockState(newPos).getBlock();

								if (!block.canSustainLeaves(state, world, newPos))
								{
									if (block.isLeaves(state, world, newPos))
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
					world.setBlockState(pos, state.withProperty(PROPERTY_CHECK_DECAY, false), 4);
				}
				else
				{
					this.destroy(world, pos);
				}
			}
		}
	}

	@Override
	public void beginLeavesDecay(IBlockState state, World world, BlockPos pos)
	{
		if (!state.getValue(PROPERTY_CHECK_DECAY))
		{
			world.setBlockState(pos, state.withProperty(PROPERTY_CHECK_DECAY, true), 4);
		}
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
		ArrayList<ItemStack> stacks = new ArrayList<>();
		stacks.add(new ItemStack(this));

		return stacks;
	}

	@Override
	public int damageDropped(IBlockState state)
	{
		return 0;
	}

	@Override
	public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
	{
		List<ItemStack> stacks = new ArrayList<>();

		Random rand = world instanceof World ? ((World) world).rand : new Random();

		if (rand.nextInt(20) == 0)
		{
			stacks.add(new ItemStack(BlocksAether.aether_sapling, 1, this.saplingMeta));
		}

		return stacks;
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(PROPERTY_DECAYABLE, (meta & 4) == 4).withProperty(PROPERTY_CHECK_DECAY, (meta & 8) == 8);
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		int meta = 0;

		if (state.getValue(PROPERTY_DECAYABLE))
		{
			meta |= 4;
		}

		if (state.getValue(PROPERTY_CHECK_DECAY))
		{
			meta |= 8;
		}

		return meta;
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[] {PROPERTY_DECAYABLE, PROPERTY_CHECK_DECAY, PROPERTY_OPAQUE});
	}
}
