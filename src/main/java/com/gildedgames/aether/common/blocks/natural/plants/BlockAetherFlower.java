package com.gildedgames.aether.common.blocks.natural.plants;

import com.gildedgames.aether.common.blocks.util.variants.IAetherBlockWithVariants;
import com.gildedgames.aether.common.blocks.util.variants.blockstates.BlockVariant;
import com.gildedgames.aether.common.blocks.util.variants.blockstates.PropertyVariant;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class BlockAetherFlower extends BlockAetherPlant implements IAetherBlockWithVariants
{
	public static final BlockVariant
			WHITE_ROSE = new BlockVariant(0, "white_rose"),
			PURPLE_FLOWER = new BlockVariant(1, "purple_flower");

	public static final PropertyVariant PROPERTY_VARIANT = PropertyVariant.create("variant", WHITE_ROSE, PURPLE_FLOWER);

	public BlockAetherFlower()
	{
		super(Material.leaves);

		this.setStepSound(Block.soundTypeGrass);

		this.setBlockBounds(0.3f, 0.0F, 0.3f, 0.7f, 0.6f, 0.7f);

		this.setDefaultState(this.getBlockState().getBaseState().withProperty(PROPERTY_VARIANT, WHITE_ROSE));
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
	public int damageDropped(IBlockState state)
	{
		return state.getValue(PROPERTY_VARIANT).getMeta();
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(PROPERTY_VARIANT, PROPERTY_VARIANT.fromMeta(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(PROPERTY_VARIANT).getMeta();
	}

	@Override
	protected BlockState createBlockState()
	{
		return new BlockState(this, PROPERTY_VARIANT);
	}

	@Override
	public String getSubtypeUnlocalizedName(ItemStack stack)
	{
		return PROPERTY_VARIANT.fromMeta(stack.getMetadata()).getName();
	}
}
