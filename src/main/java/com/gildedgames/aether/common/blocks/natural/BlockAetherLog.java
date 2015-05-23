package com.gildedgames.aether.common.blocks.natural;

import com.gildedgames.aether.common.AetherCreativeTabs;
import com.gildedgames.aether.common.blocks.util.variants.IAetherBlockWithVariants;
import com.gildedgames.aether.common.blocks.util.variants.blockstates.BlockVariant;
import com.gildedgames.aether.common.blocks.util.variants.blockstates.PropertyVariant;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.block.material.Material;
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

import java.util.List;

public class BlockAetherLog extends Block implements IAetherBlockWithVariants
{
	public static final BlockVariant
			SKYROOT_LOG = new BlockVariant(0, "skyroot_log"),
			GOLDEN_OAK_LOG = new BlockVariant(4, "golden_oak_log");

	public static final PropertyVariant LOG_VARIANT = PropertyVariant.create("variant", SKYROOT_LOG, GOLDEN_OAK_LOG);

	public static final PropertyEnum LOG_AXIS = PropertyEnum.create("axis", BlockLog.EnumAxis.class);

	public BlockAetherLog()
	{
		super(Material.wood);
		this.setStepSound(Block.soundTypeWood);
		this.setHardness(2.0f);
		this.setCreativeTab(AetherCreativeTabs.tabBlocks);

		this.setDefaultState(this.getBlockState().getBaseState().withProperty(LOG_VARIANT, SKYROOT_LOG).withProperty(LOG_AXIS, BlockLog.EnumAxis.Y));
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item itemIn, CreativeTabs tab, List list)
	{
		for (BlockVariant variant : LOG_VARIANT.getAllowedValues())
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
		int rotateMeta = (meta - variantMeta) % 4;

		return this.getDefaultState().withProperty(LOG_VARIANT, LOG_VARIANT.getVariantFromMeta((variantMeta)))
				.withProperty(LOG_AXIS, BlockLog.EnumAxis.values()[rotateMeta]);
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		int stateMeta = ((BlockVariant) state.getValue(LOG_VARIANT)).getMeta();
		int variantMeta = stateMeta - (stateMeta % 4);
		int rotateMeta = ((BlockLog.EnumAxis) state.getValue(LOG_AXIS)).ordinal();

		return variantMeta + rotateMeta;
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
		return new ItemStack(Item.getItemFromBlock(this), 1, ((BlockVariant) state.getValue(LOG_VARIANT)).getMeta());
	}

	@Override
	public int damageDropped(IBlockState state)
	{
		return ((BlockVariant) state.getValue(LOG_VARIANT)).getMeta();
	}

	@Override
	protected BlockState createBlockState()
	{
		return new BlockState(this, LOG_VARIANT, LOG_AXIS);
	}

	@Override
	public String getUnlocalizedNameFromStack(ItemStack stack)
	{
		int variantMeta = stack.getMetadata() - (stack.getMetadata() % 4);

		return LOG_VARIANT.getVariantFromMeta(variantMeta).getName();
	}

}
