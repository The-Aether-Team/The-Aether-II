package com.gildedgames.aether.common.blocks.natural.plants;

import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.blocks.IBlockMultiName;
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
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class BlockBrettlPlant extends BlockAetherPlant implements IBlockMultiName, IGrowable
{
	protected static final AxisAlignedBB BRETTL_AABB = new AxisAlignedBB(0.25D, 0.0D, 0.25D, 0.75D, 1.25D, 0.75D);
	protected static final AxisAlignedBB BRETTL_TOP_AAB = new AxisAlignedBB(0D, 0D, 0D, 0D, 0D, 0D);
	protected static final AxisAlignedBB BRETTL_MID_AAB = new AxisAlignedBB(0.25D, 0.0D, 0.25D, 0.75D, 1.5D, 0.75D);
	protected static final AxisAlignedBB BRETTL_MID_G_AAB = new AxisAlignedBB(0D, 0D, 0D, 1.0D, 1.5D, 1.0D);

	private static final int BRETTL_PLANT_BASE = 0, BRETTL_PLANT_MID = 1, BRETTL_PLANT_TOP = 2,
							 BRETTL_PLANT_BASE_G = 3, BRETTL_PLANT_MID_G = 4, BRETTL_PLANT_TOP_G = 5;

	public static final BlockVariant
			BASE = new BlockVariant(0, "base"),
			MID = new BlockVariant(1, "mid"),
			TOP = new BlockVariant(2, "top"),
			BASE_G = new BlockVariant(3, "base_g"),
			MID_G = new BlockVariant(4, "mid_g"),
			TOP_G = new BlockVariant(5, "top_g");

	public static final PropertyVariant PROPERTY_VARIANT = PropertyVariant.create("variant", BASE, MID, TOP, BASE_G, MID_G, TOP_G);
	public static final PropertyBool PROPERTY_HARVESTABLE = PropertyBool.create("harvestable");


	public BlockBrettlPlant()
	{
		super(Material.LEAVES);
		this.setHardness(0.0f);
		this.setSoundType(SoundType.PLANT);
		this.setTickRandomly(true);
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, PROPERTY_VARIANT, PROPERTY_HARVESTABLE);
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(PROPERTY_VARIANT).getMeta();
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		if (meta <= 2)
		{
			return this.getDefaultState().withProperty(PROPERTY_VARIANT, PROPERTY_VARIANT.fromMeta(meta)).withProperty(PROPERTY_HARVESTABLE, false);
		}

		return this.getDefaultState().withProperty(PROPERTY_VARIANT, PROPERTY_VARIANT.fromMeta(meta)).withProperty(PROPERTY_HARVESTABLE, true);
	}

	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
	{
		return new ItemStack(ItemsAether.brettl_cane);
	}

	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand)
	{
		if (!state.getValue(PROPERTY_HARVESTABLE))
		{
			if (world.getLight(pos) >= 9)
			{
					if (state.getValue(PROPERTY_VARIANT).getMeta() == BRETTL_PLANT_BASE)
					{
						if (world.getBlockState(pos.up()).getBlock() == BlocksAether.brettl_plant)
						{
							if (rand.nextInt(5) == 0)
							{
								this.grow(world, rand, pos, state);
							}
						}
					}

					else if (state.getValue(PROPERTY_VARIANT).getMeta() == BRETTL_PLANT_MID)
					{
						if (world.getBlockState(pos.up()).getValue(PROPERTY_VARIANT).getMeta() == BRETTL_PLANT_MID)
						{
							if (rand.nextInt(5) == 0)
							{
								this.grow(world, rand, pos, state);
							}
						}
					}
			}
		}
	}

	@Override
	public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient)
	{
		if (state.getValue(PROPERTY_VARIANT).getMeta() == BRETTL_PLANT_BASE) {
			if (worldIn.getBlockState(pos.up()).getBlock() == BlocksAether.brettl_plant || worldIn.isAirBlock(pos.up())) {
				if (worldIn.getBlockState(pos.up(2)).getBlock() == BlocksAether.brettl_plant || worldIn.isAirBlock(pos.up(2)))
				{
					return true;
				}
			}

			return false;
		}

		if (state.getValue(PROPERTY_VARIANT).getMeta() == BRETTL_PLANT_MID)
		{
			return worldIn.getBlockState(pos.up()).getBlock() == BlocksAether.brettl_plant || worldIn.isAirBlock(pos.up());

		}

		if (state.getValue(PROPERTY_VARIANT).getMeta() == BRETTL_PLANT_TOP)
		{
			if (worldIn.getBlockState(pos.down()).getValue(PROPERTY_VARIANT).getMeta() == BRETTL_PLANT_BASE) {
				return worldIn.isAirBlock(pos.up());
			}

			if (worldIn.getBlockState(pos.down()).getValue(PROPERTY_VARIANT).getMeta() == BRETTL_PLANT_MID)
			{
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state)
	{
		return !state.getValue(PROPERTY_HARVESTABLE);
	}

	@Override
	public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state)
	{
		if (state.getValue(PROPERTY_VARIANT).getMeta() == BRETTL_PLANT_BASE) {
			if (worldIn.isAirBlock(pos.up()) || worldIn.getBlockState(pos.up()).getValue(PROPERTY_VARIANT).getMeta() == BRETTL_PLANT_TOP)
			{
				worldIn.setBlockState(pos.up(), this.getDefaultState().withProperty(PROPERTY_VARIANT, MID).withProperty(PROPERTY_HARVESTABLE, false));
				worldIn.setBlockState(pos.up(2), this.getDefaultState().withProperty(PROPERTY_VARIANT, TOP).withProperty(PROPERTY_HARVESTABLE, false));
			}
			else if (worldIn.getBlockState(pos.up(2)).getValue(PROPERTY_VARIANT).getMeta() == BRETTL_PLANT_TOP)
			{
				worldIn.setBlockState(pos.up(2), this.getDefaultState().withProperty(PROPERTY_VARIANT, TOP_G).withProperty(PROPERTY_HARVESTABLE, false));
			}
			else if (worldIn.getBlockState(pos.up(2)).getValue(PROPERTY_VARIANT).getMeta() == BRETTL_PLANT_TOP_G)
			{
				this.fullyGrowPlant(worldIn, pos.up(), state);
			}
		}
		else if (state.getValue(PROPERTY_VARIANT).getMeta() == BRETTL_PLANT_MID)
		{
			if (worldIn.getBlockState(pos.up()).getValue(PROPERTY_VARIANT).getMeta() == BRETTL_PLANT_TOP) {
				worldIn.setBlockState(pos.up(), this.getDefaultState().withProperty(PROPERTY_VARIANT, TOP_G).withProperty(PROPERTY_HARVESTABLE, false));
			}
			else if (worldIn.getBlockState(pos.up()).getValue(PROPERTY_VARIANT).getMeta() == BRETTL_PLANT_TOP_G)
			{
				this.fullyGrowPlant(worldIn, pos, state);
			}
		}
	}

	@Override
	public int damageDropped(IBlockState state)
	{
		return BRETTL_PLANT_BASE;
	}

	@Override
	public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest)
	{
		if (state.getValue(PROPERTY_HARVESTABLE))
		{
			if (player.capabilities.isCreativeMode)
			{
				world.setBlockToAir(pos);

				return false;
			}

			if (!world.isRemote)
			{
				Block.spawnAsEntity(world, pos, new ItemStack(ItemsAether.brettl_grass));
				Block.spawnAsEntity(world, pos, new ItemStack(ItemsAether.brettl_grass));
			}

			if (state.getValue(PROPERTY_VARIANT).getMeta() == BRETTL_PLANT_BASE_G)
			{
				this.fullyPrunePlant(world, pos.up(), state);
			}
			else if (state.getValue(PROPERTY_VARIANT).getMeta() == BRETTL_PLANT_MID_G)
			{
				this.fullyPrunePlant(world, pos, state);
			}
			else if (state.getValue(PROPERTY_VARIANT).getMeta() == BRETTL_PLANT_TOP_G)
			{
				this.fullyPrunePlant(world, pos.down(), state);
			}


			return false;
		}

		return super.removedByPlayer(state, world, pos, player, willHarvest);
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		if (state.getValue(PROPERTY_VARIANT).getMeta() != BRETTL_PLANT_TOP && state.getValue(PROPERTY_VARIANT).getMeta() != BRETTL_PLANT_TOP_G)
		{
			return ItemsAether.brettl_cane;
		}

		return null;
	}

	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		return PROPERTY_VARIANT.fromMeta(stack.getMetadata()).getName();
	}

	@Override
	public boolean canPlaceBlockAt(World world, BlockPos pos)
	{
		IBlockState downBlock = world.getBlockState(pos);

		return isSuitableSoilBlock(downBlock);
	}

	@Override
	public boolean isSuitableSoilBlock(IBlockState state)
	{
		return state.getBlock() == BlocksAether.quicksoil || state.getBlock() == BlocksAether.brettl_plant;
	}

	@Override
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
	{
		if (canPlaceBlockAt(worldIn,pos))
		{
			return this.getDefaultState().withProperty(PROPERTY_VARIANT, BASE).withProperty(PROPERTY_HARVESTABLE, false);
		}

		return Blocks.AIR.getDefaultState();
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		if (state.getValue(PROPERTY_HARVESTABLE)) {
			if (state.getValue(PROPERTY_VARIANT).getMeta() != BRETTL_PLANT_TOP_G)
			{
				if (state.getValue(PROPERTY_VARIANT).getMeta() == BRETTL_PLANT_MID_G)
				{
					return BRETTL_MID_G_AAB;
				}
				return FULL_BLOCK_AABB;
			}

			return BRETTL_TOP_AAB;
		}
		else
		{
			if (state.getValue(PROPERTY_VARIANT).getMeta() == BRETTL_PLANT_MID && source.getBlockState(pos.up()).getValue(PROPERTY_VARIANT).getMeta() == BRETTL_PLANT_TOP_G)
			{
				return BRETTL_MID_AAB;
			}

			if (state.getValue(PROPERTY_VARIANT).getMeta() == BRETTL_PLANT_TOP || state.getValue(PROPERTY_VARIANT).getMeta() == BRETTL_PLANT_TOP_G)
			{
				return BRETTL_TOP_AAB;
			}
		}

		return BRETTL_AABB;
	}

	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
	{
		if (worldIn.isAirBlock(pos.down()))
		{
			if (state.getValue(PROPERTY_VARIANT).getMeta() != BRETTL_PLANT_TOP && state.getValue(PROPERTY_VARIANT).getMeta() != BRETTL_PLANT_TOP_G)
			{
				spawnAsEntity(worldIn, pos, new ItemStack(ItemsAether.brettl_cane));
				if (state.getValue(PROPERTY_HARVESTABLE))
				{
					spawnAsEntity(worldIn, pos, new ItemStack(ItemsAether.brettl_grass));
				}
			}
			worldIn.setBlockToAir(pos);
		}

		// Causes a new top state to be placed ontop of an existing bot/mid section.
		if (state.getValue(PROPERTY_VARIANT).getMeta() == BRETTL_PLANT_MID || state.getValue(PROPERTY_VARIANT).getMeta() == BRETTL_PLANT_BASE)
		{
			if (worldIn.isAirBlock(pos.up()))
			{
				if (!worldIn.isAirBlock(pos))
				{
					worldIn.setBlockState(pos.up(), state.withProperty(PROPERTY_VARIANT, TOP).withProperty(PROPERTY_HARVESTABLE, false));
				}
				if (worldIn.getBlockState(pos.up(2)).getBlock() == BlocksAether.brettl_plant)
				{
					worldIn.setBlockToAir(pos.up(2));
				}
			}
		}

	}

	// pos should be set to the middle of the 3 states.
	private void fullyGrowPlant(World worldIn, BlockPos pos, IBlockState state)
	{
		worldIn.setBlockState(pos.down(), this.getDefaultState().withProperty(PROPERTY_VARIANT, BASE_G).withProperty(PROPERTY_HARVESTABLE, true));
		worldIn.setBlockState(pos, this.getDefaultState().withProperty(PROPERTY_VARIANT, MID_G).withProperty(PROPERTY_HARVESTABLE, true));
		worldIn.setBlockState(pos.up(), this.getDefaultState().withProperty(PROPERTY_VARIANT, TOP_G).withProperty(PROPERTY_HARVESTABLE, true));

	}

	private void fullyPrunePlant(World worldIn, BlockPos pos, IBlockState state)
	{
		worldIn.setBlockState(pos.down(), this.getDefaultState().withProperty(PROPERTY_VARIANT, BASE).withProperty(PROPERTY_HARVESTABLE, false));
		worldIn.setBlockState(pos, this.getDefaultState().withProperty(PROPERTY_VARIANT, MID).withProperty(PROPERTY_HARVESTABLE, false));
		worldIn.setBlockState(pos.up(), this.getDefaultState().withProperty(PROPERTY_VARIANT, TOP_G).withProperty(PROPERTY_HARVESTABLE, false));

	}
}
