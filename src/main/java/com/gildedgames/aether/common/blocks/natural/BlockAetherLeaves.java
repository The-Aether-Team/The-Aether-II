package com.gildedgames.aether.common.blocks.natural;

import com.gildedgames.aether.client.renderer.particles.ParticleGolden;
import com.gildedgames.aether.client.renderer.particles.ParticleLeaf;
import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.blocks.natural.plants.saplings.BlockAetherSapling;
import com.gildedgames.aether.common.blocks.natural.plants.saplings.BlockAetherUniqueSapling;
import com.gildedgames.aether.common.entities.living.passive.EntitySkyrootLizard;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
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

public class BlockAetherLeaves extends BlockLeaves implements IShearable
{

	public static final PropertyBool PROPERTY_DECAYABLE = PropertyBool.create("decayable");

	public static final PropertyBool PROPERTY_CHECK_DECAY = PropertyBool.create("check_decay");

	private final int saplingMeta;

	private int[] surroundings;

	/**
	 * Used for when a leaf block doesn't have saplings.
	 */
	public BlockAetherLeaves()
	{
		this(-1);
	}

	public BlockAetherLeaves(final int saplingMeta)
	{
		this.saplingMeta = saplingMeta;

		this.setHardness(0.2f);
		this.setTickRandomly(true);

		this.setSoundType(SoundType.PLANT);

		this.setDefaultState(
				this.getBlockState().getBaseState().withProperty(PROPERTY_DECAYABLE, Boolean.TRUE).withProperty(PROPERTY_CHECK_DECAY, Boolean.TRUE));
	}

	@Override
	public boolean isOpaqueCube(final IBlockState state)
	{
		return false;
	}

	@Override
	public boolean isShearable(final ItemStack item, final IBlockAccess world, final BlockPos pos)
	{
		return true;
	}

	@Override
	public boolean isLeaves(final IBlockState state, final IBlockAccess world, final BlockPos pos)
	{
		return true;
	}

	@Override
	public boolean causesSuffocation(final IBlockState state)
	{
		return false;
	}

	@Override
	public BlockPlanks.EnumType getWoodType(final int meta)
	{
		return null;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(final IBlockState state, final World world, final BlockPos pos, final Random rand)
	{
		if (Minecraft.getMinecraft().gameSettings.particleSetting != 0)
		{
			return;
		}

		super.randomDisplayTick(state, world, pos, rand);

		if (this == BlocksAether.amberoot_leaves)
		{
			if (rand.nextInt(100) > 90)
			{
				final double x = pos.getX() + (rand.nextFloat() * 6f) - 3f;
				final double y = pos.getY() + (rand.nextFloat() * 6f) - 3f;
				final double z = pos.getZ() + (rand.nextFloat() * 6f) - 3f;

				final ParticleGolden effect = new ParticleGolden(world, x, y, z, 0, 0, 0);

				FMLClientHandler.instance().getClient().effectRenderer.addEffect(effect);
			}
		}

		if (world.isAirBlock(pos.down()))
		{
			if (rand.nextInt(100) > 97)
			{
				final double x = pos.getX() + rand.nextFloat();
				final double y = pos.getY();
				final double z = pos.getZ() + rand.nextFloat();

				final ParticleLeaf effect = new ParticleLeaf(world, x, y, z,
						-0.04D + (rand.nextFloat() * 0.08f),
						-0.05D + (rand.nextFloat() * -0.02f),
						-0.04D + (rand.nextFloat() * 0.08f),
						this);

				FMLClientHandler.instance().getClient().effectRenderer.addEffect(effect);
			}
		}
	}

	@Override
	public void breakBlock(final World worldIn, final BlockPos pos, final IBlockState state)
	{
		final byte start = 1;

		final int size = start + 1;
		final int x = pos.getX();
		final int y = pos.getY();
		final int z = pos.getZ();

		if (worldIn.isAreaLoaded(new BlockPos(x - size, y - size, z - size), new BlockPos(x + size, y + size, z + size)))
		{
			for (int x2 = -start; x2 <= start; ++x2)
			{
				for (int y2 = -start; y2 <= start; ++y2)
				{
					for (int z2 = -start; z2 <= start; ++z2)
					{
						final BlockPos newPos = pos.add(x2, y2, z2);
						final IBlockState newState = worldIn.getBlockState(newPos);

						if (newState.getBlock().isLeaves(state, worldIn, newPos))
						{
							newState.getBlock().beginLeavesDecay(newState, worldIn, newPos);
						}
					}
				}
			}
		}

		if (!worldIn.isRemote && state.getValue(DECAYABLE) && worldIn.rand.nextInt(15) == 0)
		{
			EntitySkyrootLizard lizard = new EntitySkyrootLizard(worldIn);

			lizard.setPosition(pos.getX() + 0.5f, pos.getY() + 0.5f, pos.getZ() + 0.5f);
			lizard.setLizardType(this);

			worldIn.spawnEntity(lizard);
		}
	}

	@Override
	public void updateTick(final World world, final BlockPos pos, final IBlockState state, final Random rand)
	{
		if (!world.isRemote)
		{
			final boolean checkDecay = state.getValue(PROPERTY_CHECK_DECAY);
			final boolean isDecayable = state.getValue(PROPERTY_DECAYABLE);

			if (checkDecay && isDecayable)
			{
				final byte start = 4;
				final int range = start + 1;
				final int x = pos.getX();
				final int y = pos.getY();
				final int z = pos.getZ();

				final byte b1 = 32;
				final int i1 = b1 * b1;
				final int j1 = b1 / 2;

				if (this.surroundings == null)
				{
					this.surroundings = new int[b1 * b1 * b1];
				}

				int x2;

				if (world.isAreaLoaded(new BlockPos(x - range, y - range, z - range), new BlockPos(x + range, y + range, z + range)))
				{
					int y2;
					int z2;

					final BlockPos.MutableBlockPos newPos = new BlockPos.MutableBlockPos();

					for (x2 = -start; x2 <= start; ++x2)
					{
						for (y2 = -start; y2 <= start; ++y2)
						{
							for (z2 = -start; z2 <= start; ++z2)
							{
								newPos.setPos(x + x2, y + y2, z + z2);

								final Block block = world.getBlockState(newPos).getBlock();

								int i = (x2 + j1) * i1 + (y2 + j1) * b1 + z2 + j1;

								if (!block.canSustainLeaves(state, world, newPos))
								{
									if (block.isLeaves(state, world, newPos))
									{
										this.surroundings[i] = -2;
									}
									else
									{
										this.surroundings[i] = -1;
									}
								}
								else
								{
									this.surroundings[i] = 0;
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
										int i = (y2 + j1 - 1) * i1 + (z2 + j1) * b1 + j2 + j1;

										if (this.surroundings[i] == -2)
										{
											this.surroundings[i] = x2;
										}

										int i2 = (y2 + j1 + 1) * i1 + (z2 + j1) * b1 + j2 + j1;

										if (this.surroundings[i2] == -2)
										{
											this.surroundings[i2] = x2;
										}

										int i3 = (y2 + j1) * i1 + (z2 + j1 - 1) * b1 + j2 + j1;

										if (this.surroundings[i3] == -2)
										{
											this.surroundings[i3] = x2;
										}

										int i4 = (y2 + j1) * i1 + (z2 + j1 + 1) * b1 + j2 + j1;

										if (this.surroundings[i4] == -2)
										{
											this.surroundings[i4] = x2;
										}

										int i5 = (y2 + j1) * i1 + (z2 + j1) * b1 + (j2 + j1 - 1);

										if (this.surroundings[i5] == -2)
										{
											this.surroundings[i5] = x2;
										}

										int i6 = (y2 + j1) * i1 + (z2 + j1) * b1 + j2 + j1 + 1;

										if (this.surroundings[i6] == -2)
										{
											this.surroundings[i6] = x2;
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
					world.setBlockState(pos, world.getBlockState(pos).withProperty(PROPERTY_CHECK_DECAY, false), 4);
				}
				else
				{
					this.destroy(world, pos);
				}
			}
		}
	}

	@Override
	public void beginLeavesDecay(final IBlockState state, final World world, final BlockPos pos)
	{
		if (!state.getValue(PROPERTY_CHECK_DECAY))
		{
			world.setBlockState(pos, state.withProperty(PROPERTY_CHECK_DECAY, true), 4);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getRenderLayer()
	{
		return BlockRenderLayer.CUTOUT_MIPPED;
	}

	@Override
	public int quantityDropped(final Random random)
	{
		return random.nextInt(20) == 0 ? 1 : 0;
	}

	private void destroy(final World worldIn, final BlockPos pos)
	{
		this.dropBlockAsItem(worldIn, pos, worldIn.getBlockState(pos), 0);

		worldIn.setBlockToAir(pos);
	}

	@Override
	public List<ItemStack> onSheared(final ItemStack item, final IBlockAccess world, final BlockPos pos, final int fortune)
	{
		final ArrayList<ItemStack> stacks = new ArrayList<>();
		stacks.add(new ItemStack(this));

		return stacks;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(final IBlockState blockState, final IBlockAccess blockAccess, final BlockPos pos, final EnumFacing side)
	{
		return this.superShouldSideBeRendered(blockState, blockAccess, pos, side);
	}

	@SideOnly(Side.CLIENT)
	private boolean superShouldSideBeRendered(final IBlockState blockState, final IBlockAccess blockAccess, final BlockPos pos, final EnumFacing side)
	{
		final AxisAlignedBB axisalignedbb = blockState.getBoundingBox(blockAccess, pos);

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
	public int damageDropped(final IBlockState state)
	{
		return 0;
	}

	@Override
	public List<ItemStack> getDrops(final IBlockAccess world, final BlockPos pos, final IBlockState state, final int fortune)
	{
		final List<ItemStack> stacks = new ArrayList<>();

		if (this.saplingMeta >= 0)
		{
			final Random rand = world instanceof World ? ((World) world).rand : new Random();

			if (rand.nextInt(20) == 0)
			{
				if (this != BlocksAether.mutant_tree_leaves || this != BlocksAether.mutant_tree_leaves_decorated)
				{
					if (this == BlocksAether.blue_skyroot_leaves || this == BlocksAether.green_skyroot_leaves || this == BlocksAether.dark_blue_skyroot_leaves)
					{
						stacks.add(new ItemStack(BlocksAether.aether_skyroot_sapling, 1, this.saplingMeta));
					}

					if (this == BlocksAether.amberoot_leaves)
					{
						stacks.add(new ItemStack(BlocksAether.aether_unique_sapling, 1, this.saplingMeta));
					}

					if (this == BlocksAether.blue_light_skyroot_leaves || this == BlocksAether.green_light_skyroot_leaves || this == BlocksAether.dark_blue_light_skyroot_leaves)
					{
						stacks.add(new ItemStack(BlocksAether.aether_wisproot_sapling, 1, this.saplingMeta));
					}
					if (this == BlocksAether.green_dark_skyroot_leaves || this == BlocksAether.blue_dark_skyroot_leaves || this == BlocksAether.dark_blue_dark_skyroot_leaves)
					{
						stacks.add(new ItemStack(BlocksAether.aether_greatroot_sapling, 1, this.saplingMeta));
					}
				}
			}
		}

		return stacks;
	}

	@Override
	public IBlockState getStateFromMeta(final int meta)
	{
		return this.getDefaultState().withProperty(PROPERTY_DECAYABLE, (meta & 4) == 4)
				.withProperty(PROPERTY_CHECK_DECAY, (meta & 8) == 8);
	}

	@Override
	public int getMetaFromState(final IBlockState state)
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

/*	@Override
	public BlockPlanks.EnumType getWoodType(int meta)
	{
		return null;
	}*/

}
