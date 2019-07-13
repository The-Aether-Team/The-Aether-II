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
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

import java.util.Random;

public class BlockBrettlPlant extends BlockAetherPlant implements IGrowable
{
	public static final BlockVariant
			BASE = new BlockVariant(0, "base"),
			MID = new BlockVariant(1, "mid"),
			TOP = new BlockVariant(2, "top"),
			BASE_G = new BlockVariant(3, "base_g"),
			MID_G = new BlockVariant(4, "mid_g"),
			TOP_G = new BlockVariant(5, "top_g");

	public static final PropertyVariant PROPERTY_VARIANT = PropertyVariant.create("variant", BASE, MID, TOP, BASE_G, MID_G, TOP_G);

	public static final BooleanProperty PROPERTY_HARVESTABLE = BooleanProperty.create("harvestable");

	public static final IntegerProperty PROPERTY_AGE = IntegerProperty.create("age", 0, 10);

	protected static final VoxelShape BRETTL_AABB = Block.makeCuboidShape(0.25D, 0.0D, 0.25D, 0.75D, 1.25D, 0.75D);

	protected static final VoxelShape BRETTL_TOP_AAB = Block.makeCuboidShape(0D, 0D, 0D, 0D, 0D, 0D);

	protected static final VoxelShape BRETTL_MID_AAB = Block.makeCuboidShape(0.25D, 0.0D, 0.25D, 0.75D, 1.5D, 0.75D);

	protected static final VoxelShape BRETTL_MID_G_AAB = Block.makeCuboidShape(0D, 0D, 0D, 1.0D, 1.5D, 1.0D);

	private static final int BRETTL_PLANT_BASE = 0, BRETTL_PLANT_MID = 1, BRETTL_PLANT_TOP = 2,
			BRETTL_PLANT_BASE_G = 3, BRETTL_PLANT_MID_G = 4, BRETTL_PLANT_TOP_G = 5;

	public BlockBrettlPlant(Block.Properties properties)
	{
		super(properties);
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
	{
		builder.add(PROPERTY_VARIANT, PROPERTY_HARVESTABLE, PROPERTY_AGE);
	}

	@Override
	public ItemStack getItem(IBlockReader world, BlockPos pos, BlockState state)
	{
		return new ItemStack(ItemsAether.brettl_cane);
	}

	@Override
	public void tick(BlockState state, World world, BlockPos pos, Random rand)
	{
		if (!state.get(PROPERTY_HARVESTABLE))
		{
			if (world.getLight(pos) >= 9)
			{
				if (state.get(PROPERTY_VARIANT).getMeta() == BRETTL_PLANT_BASE)
				{
					if (world.getBlockState(pos.up()).getBlock() == BlocksAether.brettl_plant)
					{
						if (rand.nextInt(5) == 0)
						{
							this.grow(world, rand, pos, state);
						}
					}
				}

				else if (state.get(PROPERTY_VARIANT).getMeta() == BRETTL_PLANT_MID)
				{
					if (world.getBlockState(pos.up()).getBlock() == BlocksAether.brettl_plant && world.getBlockState(pos.up()).get(PROPERTY_VARIANT).getMeta() == BRETTL_PLANT_MID)
					{
						if (rand.nextInt(5) == 0)
						{
							this.grow(world, rand, pos, state);
						}
					}
				}
			}
		}
		else
		{
			if (state.get(PROPERTY_AGE) >= 8)
			{
				if (state.get(PROPERTY_VARIANT).getMeta() == BRETTL_PLANT_TOP_G)
				{
					this.fullyPrunePlant(world, pos.down(), state);

				}
				else if (state.get(PROPERTY_VARIANT).getMeta() == BRETTL_PLANT_MID_G)
				{
					this.fullyPrunePlant(world, pos, state);
				}
				else
				{
					this.fullyPrunePlant(world, pos.up(), state);
				}
			}
			else
			{
				world.setBlockState(pos, state.with(PROPERTY_AGE, state.get(PROPERTY_AGE) + 1));
			}
		}
	}

	@Override
	public boolean canGrow(IBlockReader world, BlockPos pos, BlockState state, boolean isClient)
	{
		if (state.get(PROPERTY_VARIANT).getMeta() == BRETTL_PLANT_BASE)
		{
			if (world.getBlockState(pos.up()).getBlock() == BlocksAether.brettl_plant || world.getBlockState(pos.up()).isAir())
			{
				return world.getBlockState(pos.up(2)).getBlock() == BlocksAether.brettl_plant || world.getBlockState(pos.up(2)).isAir();
			}

			return false;
		}

		if (state.get(PROPERTY_VARIANT).getMeta() == BRETTL_PLANT_MID)
		{
			return world.getBlockState(pos.up()).getBlock() == BlocksAether.brettl_plant || world.getBlockState(pos.up()).isAir();

		}

		if (state.get(PROPERTY_VARIANT).getMeta() == BRETTL_PLANT_TOP)
		{
			if (world.getBlockState(pos.down()).get(PROPERTY_VARIANT).getMeta() == BRETTL_PLANT_BASE)
			{
				return world.getBlockState(pos.up()).isAir();
			}

			return world.getBlockState(pos.down()).get(PROPERTY_VARIANT).getMeta() == BRETTL_PLANT_MID;
		}

		return false;
	}

	@Override
	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state)
	{
		return !state.get(PROPERTY_HARVESTABLE);
	}

	@Override
	public void grow(World worldIn, Random rand, BlockPos pos, BlockState state)
	{
		if (state.get(PROPERTY_VARIANT).getMeta() == BRETTL_PLANT_BASE)
		{
			if (worldIn.getBlockState(pos.up()).getBlock() == BlocksAether.brettl_plant && (worldIn.isAirBlock(pos.up()) || worldIn.getBlockState(pos.up()).get(PROPERTY_VARIANT).getMeta() == BRETTL_PLANT_TOP))
			{
				worldIn.setBlockState(pos.up(), this.getDefaultState().with(PROPERTY_VARIANT, MID).with(PROPERTY_HARVESTABLE, false));
				worldIn.setBlockState(pos.up(2), this.getDefaultState().with(PROPERTY_VARIANT, TOP).with(PROPERTY_HARVESTABLE, false));
			}
			else if (worldIn.getBlockState(pos.up(2)).getBlock() == BlocksAether.brettl_plant && worldIn.getBlockState(pos.up(2)).get(PROPERTY_VARIANT).getMeta() == BRETTL_PLANT_TOP)
			{
				worldIn.setBlockState(pos.up(2), this.getDefaultState().with(PROPERTY_VARIANT, TOP_G).with(PROPERTY_HARVESTABLE, false));
			}
			else if (worldIn.getBlockState(pos.up(2)).getBlock() == BlocksAether.brettl_plant && (worldIn.getBlockState(pos.up(2)).get(PROPERTY_VARIANT).getMeta() == BRETTL_PLANT_TOP_G))
			{
				this.fullyGrowPlant(worldIn, pos.up(), state);
			}
		}
		else if (state.get(PROPERTY_VARIANT).getMeta() == BRETTL_PLANT_MID)
		{
			if (worldIn.getBlockState(pos.up()).getBlock() == BlocksAether.brettl_plant && worldIn.getBlockState(pos.up()).get(PROPERTY_VARIANT).getMeta() == BRETTL_PLANT_TOP)
			{
				worldIn.setBlockState(pos.up(), this.getDefaultState().with(PROPERTY_VARIANT, TOP_G).with(PROPERTY_HARVESTABLE, false));
			}
			else if (worldIn.getBlockState(pos.up()).getBlock() == BlocksAether.brettl_plant&& worldIn.getBlockState(pos.up()).get(PROPERTY_VARIANT).getMeta() == BRETTL_PLANT_TOP_G)
			{
				this.fullyGrowPlant(worldIn, pos, state);
			}
		}
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
				Block.spawnAsEntity(world, pos, new ItemStack(ItemsAether.brettl_grass));
				Block.spawnAsEntity(world, pos, new ItemStack(ItemsAether.brettl_grass));
			}

			if (state.get(PROPERTY_VARIANT).getMeta() == BRETTL_PLANT_BASE_G)
			{
				this.fullyPrunePlant(world, pos.up(), state);
			}
			else if (state.get(PROPERTY_VARIANT).getMeta() == BRETTL_PLANT_MID_G)
			{
				this.fullyPrunePlant(world, pos, state);
			}
			else if (state.get(PROPERTY_VARIANT).getMeta() == BRETTL_PLANT_TOP_G)
			{
				this.fullyPrunePlant(world, pos.down(), state);
			}

			return false;
		}

		return super.removedByPlayer(state, world, pos, player, willHarvest, fluid);
	}

	@Override
	public boolean isValidPosition(BlockState state, IWorldReader world, BlockPos pos)
	{
		BlockState downBlock = world.getBlockState(pos);

		return this.isSuitableSoilBlock(world, pos, downBlock);
	}

	@Override
	public boolean isSuitableSoilBlock(IWorldReader world, BlockPos pos, BlockState state)
	{
		return state.getBlock() == BlocksAether.quicksoil || state.getBlock() == BlocksAether.brettl_plant;
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context)
	{
		return this.getDefaultState().with(PROPERTY_VARIANT, BASE).with(PROPERTY_HARVESTABLE, false);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
	{
		if (state.get(PROPERTY_HARVESTABLE))
		{
			if (state.get(PROPERTY_VARIANT).getMeta() != BRETTL_PLANT_TOP_G)
			{
				if (state.get(PROPERTY_VARIANT).getMeta() == BRETTL_PLANT_MID_G)
				{
					return BRETTL_MID_G_AAB;
				}
				return VoxelShapes.fullCube();
			}

			return BRETTL_TOP_AAB;
		}
		else
		{
			if (state.get(PROPERTY_VARIANT).getMeta() == BRETTL_PLANT_MID)
			{
				return BRETTL_MID_AAB;
			}

			if (state.get(PROPERTY_VARIANT).getMeta() == BRETTL_PLANT_TOP || state.get(PROPERTY_VARIANT).getMeta() == BRETTL_PLANT_TOP_G)
			{
				return BRETTL_TOP_AAB;
			}
		}

		return BRETTL_AABB;
	}

	@Override
	public void neighborChanged(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean p_220069_6_)
	{
		if (world.isAirBlock(pos.down()))
		{
			if (state.get(PROPERTY_VARIANT).getMeta() != BRETTL_PLANT_TOP && state.get(PROPERTY_VARIANT).getMeta() != BRETTL_PLANT_TOP_G)
			{
				spawnAsEntity(world, pos, new ItemStack(ItemsAether.brettl_cane));
				if (state.get(PROPERTY_HARVESTABLE))
				{
					spawnAsEntity(world, pos, new ItemStack(ItemsAether.brettl_grass));
				}
			}
			world.removeBlock(pos, false);
		}

		// Causes a new top state to be placed ontop of an existing bot/mid section.
		if (state.get(PROPERTY_VARIANT).getMeta() == BRETTL_PLANT_MID || state.get(PROPERTY_VARIANT).getMeta() == BRETTL_PLANT_BASE)
		{
			if (world.isAirBlock(pos.up()))
			{
				if (!world.isAirBlock(pos))
				{
					world.setBlockState(pos.up(), state.with(PROPERTY_VARIANT, TOP).with(PROPERTY_HARVESTABLE, false));
				}
				if (world.getBlockState(pos.up(2)).getBlock() == BlocksAether.brettl_plant)
				{
					world.removeBlock(pos.up(2), false);
				}
			}
		}

	}

	// pos should be set to the middle of the 3 states.
	public void fullyGrowPlant(World worldIn, BlockPos pos, BlockState state)
	{
		worldIn.setBlockState(pos.down(),
				this.getDefaultState().with(PROPERTY_VARIANT, BASE_G).with(PROPERTY_HARVESTABLE, true).with(PROPERTY_AGE, 0));
		worldIn.setBlockState(pos,
				this.getDefaultState().with(PROPERTY_VARIANT, MID_G).with(PROPERTY_HARVESTABLE, true).with(PROPERTY_AGE, 0));
		worldIn.setBlockState(pos.up(),
				this.getDefaultState().with(PROPERTY_VARIANT, TOP_G).with(PROPERTY_HARVESTABLE, true).with(PROPERTY_AGE, 0));

	}

	public void fullyPrunePlant(World worldIn, BlockPos pos, BlockState state)
	{
		worldIn.setBlockState(pos.down(),
				this.getDefaultState().with(PROPERTY_VARIANT, BASE).with(PROPERTY_HARVESTABLE, false).with(PROPERTY_AGE, 0));
		worldIn.setBlockState(pos,
				this.getDefaultState().with(PROPERTY_VARIANT, MID).with(PROPERTY_HARVESTABLE, false).with(PROPERTY_AGE, 0));
		worldIn.setBlockState(pos.up(),
				this.getDefaultState().with(PROPERTY_VARIANT, TOP_G).with(PROPERTY_HARVESTABLE, false).with(PROPERTY_AGE, 0));

	}
}
