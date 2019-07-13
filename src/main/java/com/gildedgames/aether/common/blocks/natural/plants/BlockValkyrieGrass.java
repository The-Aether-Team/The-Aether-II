package com.gildedgames.aether.common.blocks.natural.plants;

import com.gildedgames.aether.api.registrar.BlocksAether;
import com.gildedgames.aether.api.registrar.ItemsAether;
import com.gildedgames.aether.common.blocks.properties.BlockVariant;
import com.gildedgames.aether.common.blocks.properties.PropertyVariant;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IGrowable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

/*
 * Current state only allows growth through bonemeal.
 */

public class BlockValkyrieGrass extends BlockAetherPlant implements IGrowable
{
	public static final BlockVariant SPROUT = new BlockVariant(0, "sprout"),
			MID = new BlockVariant(1, "mid"),
			FULL = new BlockVariant(2, "full");

	public static final PropertyVariant PROPERTY_VARIANT = PropertyVariant.create("variant", SPROUT, MID, FULL);

	public static final BooleanProperty PROPERTY_HARVESTABLE = BooleanProperty.create("harvestable");

	private static final VoxelShape GRASS_SHORT_AABB = Block.makeCuboidShape(0.1D, 0.0D, 0.1D, 0.9D, 0.3D, 0.9D);

	private static final VoxelShape GRASS_NORMAL_AABB = Block.makeCuboidShape(0.1D, 0.0D, 0.1D, 0.9D, 0.6D, 0.9D);

	private static final VoxelShape GRASS_LONG_AABB = Block.makeCuboidShape(0.1D, 0.0D, 0.1D, 0.9D, 0.9D, 0.9D);

	private static final int VALKYRIE_GRASS_SPROUT = 0, VALKYRIE_GRASS_MID = 1, VALKYRIE_GRASS_FULL = 2;

	public BlockValkyrieGrass(Block.Properties properties)
	{
		super(properties);

		// default state is set to fully grown and harvest-able because of odd activity when a world is loaded
		this.setDefaultState(this.getStateContainer().getBaseState().with(PROPERTY_VARIANT, FULL).with(PROPERTY_HARVESTABLE, true));
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
	{
		builder.add(PROPERTY_VARIANT, PROPERTY_HARVESTABLE);
	}

	@Override
	public boolean canGrow(final IBlockReader worldIn, final BlockPos pos, final BlockState state, final boolean isClient)
	{
		return !state.get(PROPERTY_HARVESTABLE);
	}

	@Override
	public boolean canUseBonemeal(final World worldIn, final Random rand, final BlockPos pos, final BlockState state)
	{
		return !state.get(PROPERTY_HARVESTABLE);
	}

	@Override
	public void tick(BlockState state, World world, BlockPos pos, Random rand)
	{
		if (!state.get(PROPERTY_HARVESTABLE))
		{
			if (world.getLight(pos) >= 9)
			{
				if (rand.nextInt(60) == 0)
				{
					this.grow(world, rand, pos, state);
				}
			}
		}
	}

	// called when the plant grows.
	@Override
	public void grow(final World worldIn, final Random rand, final BlockPos pos, final BlockState state)
	{
		// if kirrid grass is anything but fully grown.
		if (!state.get(PROPERTY_HARVESTABLE))
		{
			// if sprout, grow to mid.
			if (worldIn.getBlockState(pos).get(PROPERTY_VARIANT).getMeta() == VALKYRIE_GRASS_SPROUT)
			{
				worldIn.setBlockState(pos, state.with(PROPERTY_VARIANT, BlockValkyrieGrass.MID));
			}
			// if mid, grow to full and set harvestable.
			else if (worldIn.getBlockState(pos).get(PROPERTY_VARIANT).getMeta() == VALKYRIE_GRASS_MID)
			{
				worldIn.setBlockState(pos, state.with(PROPERTY_HARVESTABLE, true).with(PROPERTY_VARIANT, BlockValkyrieGrass.FULL));
			}
		}
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
	{
		if (state.get(PROPERTY_VARIANT) == SPROUT)
		{
			return GRASS_SHORT_AABB;
		}
		else if (state.get(PROPERTY_VARIANT) == MID)
		{
			return GRASS_NORMAL_AABB;
		}
		else if (state.get(PROPERTY_VARIANT) == FULL)
		{
			return GRASS_LONG_AABB;
		}

		return super.getShape(state, worldIn, pos, context);
	}

	@Override
	public boolean removedByPlayer(BlockState state, World world, BlockPos pos, PlayerEntity player, boolean willHarvest, IFluidState fluid)
	{
		if (state.get(PROPERTY_HARVESTABLE))
		{
			if (player.isCreative())
			{
				world.removeBlock(pos, false);

				return false;
			}

			if (!world.isRemote())
			{
				final Random random = new Random();
				Block.spawnAsEntity(world, pos, new ItemStack(ItemsAether.valkyrie_wings));

				// randomly spawn a kirrid grass sprout
				if (random.nextInt(3) == 1)
				{
					Block.spawnAsEntity(world, pos, new ItemStack(BlocksAether.valkyrie_grass));
				}
			}

			world.setBlockState(pos, state.with(PROPERTY_HARVESTABLE, false).with(PROPERTY_VARIANT, SPROUT));

			return false;

		}

		return super.removedByPlayer(state, world, pos, player, willHarvest, fluid);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public OffsetType getOffsetType()
	{
		return OffsetType.XZ;
	}
}
