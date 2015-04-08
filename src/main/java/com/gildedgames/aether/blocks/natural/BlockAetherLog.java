package com.gildedgames.aether.blocks.natural;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.gildedgames.aether.AetherCreativeTabs;
import com.gildedgames.aether.blocks.util.IAetherBlockWithVariants;
import com.gildedgames.aether.blocks.util.blockstates.BlockVariant;
import com.gildedgames.aether.blocks.util.blockstates.PropertyVariant;

public class BlockAetherLog extends Block implements IAetherBlockWithVariants
{
	public static final BlockVariant
	SKYROOT_LOG = new BlockVariant(0, "skyroot_log"),
	GOLDEN_OAK_LOG = new BlockVariant(4, "golden_oak_log");

	public static final PropertyVariant LOG_TYPE = PropertyVariant.create("variant", SKYROOT_LOG, GOLDEN_OAK_LOG);

	public static final PropertyEnum LOG_AXIS = PropertyEnum.create("axis", BlockLog.EnumAxis.class);

	protected static final int[] AXIS_LOOKUP = new int[BlockLog.EnumAxis.values().length];

	static
	{
		AXIS_LOOKUP[BlockLog.EnumAxis.X.ordinal()] = 1;
		AXIS_LOOKUP[BlockLog.EnumAxis.Z.ordinal()] = 2;
		AXIS_LOOKUP[BlockLog.EnumAxis.NONE.ordinal()] = 3;
	}

	public BlockAetherLog()
	{
		super(Material.wood);
		this.setStepSound(Block.soundTypeWood);
		this.setHardness(2.0f);
		this.setCreativeTab(AetherCreativeTabs.tabBlocks);

		this.setDefaultState(this.getBlockState().getBaseState().withProperty(LOG_TYPE, SKYROOT_LOG).withProperty(LOG_AXIS, BlockLog.EnumAxis.Y));
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item itemIn, CreativeTabs tab, List list)
	{
		for (BlockVariant variant : LOG_TYPE.getAllowedValues())
		{
			list.add(new ItemStack(itemIn, 1, variant.getMeta()));
		}
	}

	@Override
	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
	{
		return super.onBlockPlaced(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer).withProperty(LOG_AXIS, BlockLog.EnumAxis.fromFacingAxis(facing.getAxis()));
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		int variantMeta = meta - (meta % 4);
		IBlockState state = this.getDefaultState().withProperty(LOG_TYPE, LOG_TYPE.getVariantFromMeta((variantMeta)));

		switch (meta - variantMeta)
		{
		case 0:
			state = state.withProperty(LOG_AXIS, BlockLog.EnumAxis.Y);
			break;
		case 4:
			state = state.withProperty(LOG_AXIS, BlockLog.EnumAxis.X);
			break;
		case 8:
			state = state.withProperty(LOG_AXIS, BlockLog.EnumAxis.Z);
			break;
		default:
			state = state.withProperty(LOG_AXIS, BlockLog.EnumAxis.NONE);
		}

		return state;
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		int stateMeta = ((BlockVariant) state.getValue(LOG_TYPE)).getMeta();
		int variantMeta = stateMeta - (stateMeta % 4);

		switch (AXIS_LOOKUP[((BlockLog.EnumAxis) state.getValue(LOG_AXIS)).ordinal()])
		{
		case 1:
			variantMeta += 1;
			break;
		case 2:
			variantMeta += 2;
			break;
		case 3:
			variantMeta += 3;
		}

		return variantMeta;
	}

	@Override
	public boolean canSustainLeaves(IBlockAccess world, BlockPos pos)
	{
		return true;
	}

	@Override
	public boolean isWood(IBlockAccess world, BlockPos pos)
	{
		return true;
	}

	@Override
	protected ItemStack createStackedBlock(IBlockState state)
	{
		return new ItemStack(Item.getItemFromBlock(this), 1, ((BlockVariant) state.getValue(LOG_TYPE)).getMeta());
	}

	@Override
	public int damageDropped(IBlockState state)
	{
		return ((BlockVariant) state.getValue(LOG_TYPE)).getMeta();
	}

	@Override
	protected BlockState createBlockState()
	{
		return new BlockState(this, new IProperty[] { LOG_TYPE, LOG_AXIS });
	}

	@Override
	public String getVariantNameFromStack(ItemStack stack)
	{
		int variantMeta = stack.getMetadata() - (stack.getMetadata() % 4);

		return LOG_TYPE.getVariantFromMeta(variantMeta).getName();
	}

}
