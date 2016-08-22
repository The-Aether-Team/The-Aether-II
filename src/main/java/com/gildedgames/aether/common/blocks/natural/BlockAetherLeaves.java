package com.gildedgames.aether.common.blocks.natural;

import com.gildedgames.aether.client.renderer.particles.ParticleGolden;
import com.gildedgames.aether.common.blocks.BlocksAether;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
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

public class BlockAetherLeaves extends Block implements IShearable
{
	public static final PropertyBool PROPERTY_DECAYABLE = PropertyBool.create("decayable");

	public static final PropertyBool PROPERTY_CHECK_DECAY = PropertyBool.create("check_decay");

	private final int saplingMeta;

	private int[] surroundings;

	public BlockAetherLeaves(int saplingMeta)
	{
		super(Material.LEAVES);

		this.saplingMeta = saplingMeta;

		this.setHardness(0.2f);
		this.setTickRandomly(true);

		this.setSoundType(SoundType.PLANT);

		this.setDefaultState(this.getBlockState().getBaseState().withProperty(PROPERTY_DECAYABLE, Boolean.TRUE).withProperty(PROPERTY_CHECK_DECAY, Boolean.TRUE));
	}

	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
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
	public boolean isVisuallyOpaque()
	{
		return false;
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
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer()
	{
		return BlockRenderLayer.CUTOUT_MIPPED;
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
		return this.getDefaultState().withProperty(PROPERTY_DECAYABLE, (meta & 4) == 4)
				.withProperty(PROPERTY_CHECK_DECAY, (meta & 8) == 8);
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
		return new BlockStateContainer(this, PROPERTY_DECAYABLE, PROPERTY_CHECK_DECAY);
	}
}
