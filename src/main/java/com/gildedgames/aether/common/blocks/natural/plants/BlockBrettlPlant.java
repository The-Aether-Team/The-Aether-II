package com.gildedgames.aether.common.blocks.natural.plants;

import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.blocks.IBlockMultiName;
import com.gildedgames.aether.common.blocks.properties.BlockVariant;
import com.gildedgames.aether.common.blocks.properties.PropertyVariant;
import com.gildedgames.aether.common.items.ItemsAether;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class BlockBrettlPlant extends BlockAetherPlant implements IBlockMultiName, IGrowable
{
	//private static final AxisAlignedBB PLANT_AAB = new AxisAlignedBB(0.1D, 0.0D, 0.1D, 0.9D, 0.9D, 0.9D);

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

		this.setDefaultState(this.getBlockState().getBaseState().withProperty(PROPERTY_VARIANT, BASE).withProperty(PROPERTY_HARVESTABLE, false));
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
		return this.getDefaultState().withProperty(PROPERTY_VARIANT, PROPERTY_VARIANT.fromMeta(meta));
	}

	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand)
	{
		//if (world.isAirBlock(pos.up()))
		//{
		//	world.setBlockState(pos.up(), state.withProperty(PROPERTY_VARIANT, TOP).withProperty(PROPERTY_HARVESTABLE, false));
		//}
	}

	@Override
	public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient)
	{
		if (worldIn.isAirBlock(pos.up()) || worldIn.getBlockState(pos.up()).getValue(PROPERTY_VARIANT).getMeta() == BRETTL_PLANT_TOP)
		{
			return true;
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
			if (worldIn.isAirBlock(pos.up()))
			{
				worldIn.setBlockState(pos.up(), this.getDefaultState().withProperty(PROPERTY_VARIANT, MID));
			}
		}
		if (state.getValue(PROPERTY_VARIANT).getMeta() == BRETTL_PLANT_MID)
		{
			worldIn.setBlockState(pos.down(), this.getDefaultState().withProperty(PROPERTY_VARIANT, BASE_G).withProperty(PROPERTY_HARVESTABLE, true));
			worldIn.setBlockState(pos, this.getDefaultState().withProperty(PROPERTY_VARIANT, MID_G).withProperty(PROPERTY_HARVESTABLE, true));

		}
		/*
		if (state.getValue(PROPERTY_VARIANT).getMeta() == BRETTL_PLANT_BASE) {
			if (worldIn.isAirBlock(pos.up()) || worldIn.getBlockState(pos.up()).getValue(PROPERTY_VARIANT).getMeta() == BRETTL_PLANT_TOP)
			{
				//worldIn.setBlockState(pos.up(), this.getDefaultState().withProperty(PROPERTY_VARIANT, MID));
				worldIn.setBlockState(pos.up(), this.getDefaultState().withProperty(PROPERTY_VARIANT, TOP));
			}
			else if (worldIn.getBlockState(pos.up()).getValue(PROPERTY_VARIANT).getMeta() == BRETTL_PLANT_MID)
			{
				if (worldIn.getBlockState(pos.up(2)).getValue(PROPERTY_VARIANT).getMeta() == BRETTL_PLANT_TOP || worldIn.isAirBlock(pos.up(2))) {
					worldIn.setBlockState(pos, state.withProperty(PROPERTY_VARIANT, BASE_G).withProperty(PROPERTY_HARVESTABLE, true));
					worldIn.setBlockState(pos.up(), state.withProperty(PROPERTY_VARIANT, MID_G).withProperty(PROPERTY_HARVESTABLE, true));
					worldIn.setBlockState(pos.up(2), state.withProperty(PROPERTY_VARIANT, TOP_G).withProperty(PROPERTY_HARVESTABLE, true));
			}
			}
		}
		*/
	}

	@Override
	public int damageDropped(IBlockState state)
	{
		return BRETTL_PLANT_BASE;
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		if (state.getValue(PROPERTY_VARIANT).getMeta() != BRETTL_PLANT_TOP || state.getValue(PROPERTY_VARIANT).getMeta() != BRETTL_PLANT_TOP_G)
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
}
