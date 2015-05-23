package com.gildedgames.aether.common.blocks.natural;

import com.gildedgames.aether.common.AetherCreativeTabs;
import com.gildedgames.aether.common.blocks.util.variants.IAetherBlockWithVariants;
import com.gildedgames.aether.common.blocks.util.variants.blockstates.BlockVariant;
import com.gildedgames.aether.common.blocks.util.variants.blockstates.PropertyVariant;
import com.google.common.base.Predicate;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockHelper;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class BlockHolystone extends Block implements IAetherBlockWithVariants
{
	public static final BlockVariant
			NORMAL_HOLYSTONE = new BlockVariant(0, "holystone"),
			MOSSY_HOLYSTONE = new BlockVariant(1, "mossy_holystone"),
			BLOOD_MOSS_HOLYSTONE = new BlockVariant(2, "blood_holystone");

	public static final PropertyVariant HOLYSTONE_VARIANT = PropertyVariant.create("variant", NORMAL_HOLYSTONE, MOSSY_HOLYSTONE, BLOOD_MOSS_HOLYSTONE);

	public BlockHolystone()
	{
		super(Material.rock);
		this.setHardness(2.0F);
		this.setStepSound(Block.soundTypeStone);

		this.setDefaultState(this.getBlockState().getBaseState().withProperty(HOLYSTONE_VARIANT, NORMAL_HOLYSTONE));
		this.setCreativeTab(AetherCreativeTabs.tabBlocks);
	}

	@Override
	public boolean isReplaceableOreGen(World world, BlockPos pos, Predicate<IBlockState> target)
	{
		// TODO: BAD BAD BAD
		return true;
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item itemIn, CreativeTabs tab, List list)
	{
		for (BlockVariant variant : HOLYSTONE_VARIANT.getAllowedValues())
		{
			list.add(new ItemStack(itemIn, 1, variant.getMeta()));
		}
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(HOLYSTONE_VARIANT, HOLYSTONE_VARIANT.getVariantFromMeta(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return ((BlockVariant) state.getValue(HOLYSTONE_VARIANT)).getMeta();
	}

	@Override
	protected BlockState createBlockState()
	{
		return new BlockState(this, HOLYSTONE_VARIANT);
	}

	@Override
	public int damageDropped(IBlockState state)
	{
		return ((BlockVariant) state.getValue(HOLYSTONE_VARIANT)).getMeta();
	}

	@Override
	public String getUnlocalizedNameFromStack(ItemStack stack)
	{
		return HOLYSTONE_VARIANT.getVariantFromMeta(stack.getMetadata()).getName();
	}
}
