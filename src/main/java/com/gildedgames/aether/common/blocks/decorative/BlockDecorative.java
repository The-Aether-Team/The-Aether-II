package com.gildedgames.aether.common.blocks.decorative;

import com.gildedgames.aether.common.blocks.IBlockWithItem;
import com.gildedgames.aether.common.blocks.properties.BlockVariant;
import com.gildedgames.aether.common.blocks.properties.PropertyVariant;
import com.gildedgames.aether.common.blocks.util.BlockBuilder;
import com.gildedgames.aether.common.items.blocks.ItemBlockSubtypes;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public abstract class BlockDecorative extends BlockBuilder implements IBlockWithItem
{
	private final Block parentBlock;

	public BlockDecorative(final Material material, @Nonnull final Block block)
	{
		super(material);

		this.parentBlock = block;
	}

	@Nonnull
	protected abstract PropertyVariant getVariantProperty();

	@Override
	public int damageDropped(final IBlockState state)
	{
		return state.getValue(this.getVariantProperty()).getMeta();
	}

	@Override
	public IBlockState getStateFromMeta(final int meta)
	{
		final BlockVariant variant = this.getVariantProperty().fromMeta(meta);

		return this.getDefaultState().withProperty(this.getVariantProperty(), variant);
	}

	@Override
	public int getMetaFromState(final IBlockState state)
	{
		return state.getValue(this.getVariantProperty()).getMeta();
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, this.getVariantProperty());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(final CreativeTabs tab, final NonNullList<ItemStack> list)
	{
		for (final BlockVariant variant : this.getVariantProperty().getAllowedValues())
		{
			list.add(new ItemStack(this, 1, variant.getMeta()));
		}
	}

	@Override
	public String getUnlocalizedName()
	{
		return this.parentBlock.getUnlocalizedName();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(final ItemStack stack, @Nullable final World player, final List<String> tooltip, final ITooltipFlag advanced)
	{
		final String name = this.getStateFromMeta(stack.getItemDamage()).getValue(this.getVariantProperty()).getName();

		tooltip.add(TextFormatting.GRAY + I18n.format(this.getUnlocalizedName() + "." + name + ".name"));
	}

	@Override
	public ItemBlock createItemBlock()
	{
		return new ItemBlockSubtypes(this);
	}
}
