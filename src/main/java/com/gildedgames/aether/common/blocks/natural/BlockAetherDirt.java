package com.gildedgames.aether.common.blocks.natural;

import com.gildedgames.aether.common.blocks.util.BlockCustom;
import com.gildedgames.aether.common.blocks.util.variants.IBlockVariants;
import com.gildedgames.aether.common.blocks.util.variants.blockstates.BlockVariant;
import com.gildedgames.aether.common.blocks.util.variants.blockstates.PropertyVariant;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class BlockAetherDirt extends BlockCustom implements IBlockVariants
{

	public static final BlockVariant DIRT = new BlockVariant(0, "dirt"), COARSE_DIRT = new BlockVariant(1, "coarse_dirt"), PERMAFROST = new BlockVariant(2, "permafrost");

	public static final PropertyVariant PROPERTY_VARIANT = PropertyVariant.create("variant", DIRT, COARSE_DIRT);

	public BlockAetherDirt()
	{
		super(Material.GROUND);

		this.setSoundType(SoundType.GROUND);
		this.setHardness(0.5f);

		this.setDefaultState(this.getBlockState().getBaseState().withProperty(PROPERTY_VARIANT, DIRT));
	}

	@Override
	public int damageDropped(IBlockState state)
	{
		return state.getValue(PROPERTY_VARIANT).getMeta();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs tab, List<ItemStack> list)
	{
		for (BlockVariant variant : PROPERTY_VARIANT.getAllowedValues())
		{
			list.add(new ItemStack(item, 1, variant.getMeta()));
		}
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		BlockVariant variant = PROPERTY_VARIANT.fromMeta(meta);

		return this.getDefaultState().withProperty(PROPERTY_VARIANT, variant);
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(PROPERTY_VARIANT).getMeta();
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, PROPERTY_VARIANT);
	}

	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		return PROPERTY_VARIANT.fromMeta(stack.getMetadata()).getName();
	}

	@Override
	public void addItemsToCreativeTab(Item item, CreativeTabs tab, List<ItemStack> stackList)
	{

	}

}
