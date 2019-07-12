package com.gildedgames.aether.common.blocks.decorative;

import com.gildedgames.aether.common.blocks.IBlockWithItem;
import com.gildedgames.aether.common.blocks.properties.BlockVariant;
import com.gildedgames.aether.common.blocks.properties.PropertyVariant;
import com.gildedgames.aether.common.blocks.util.BlockBuilder;
import com.gildedgames.aether.common.items.blocks.ItemBlockSubtypes;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.BlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public abstract class BlockDecorative extends BlockBuilder implements IBlockWithItem
{
	public BlockDecorative(final Material material)
	{
		super(material);
	}

	@Nonnull
	protected abstract PropertyVariant getVariantProperty();

	@Override
	public int damageDropped(final BlockState state)
	{
		return state.getValue(this.getVariantProperty()).getMeta();
	}

	@Override
	public BlockState getStateFromMeta(final int meta)
	{
		final BlockVariant variant = this.getVariantProperty().fromMeta(meta);

		return this.getDefaultState().withProperty(this.getVariantProperty(), variant);
	}

	@Override
	public int getMetaFromState(final BlockState state)
	{
		return state.getValue(this.getVariantProperty()).getMeta();
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, this.getVariantProperty());
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void getSubBlocks(final ItemGroup tab, final NonNullList<ItemStack> list)
	{
		for (final BlockVariant variant : this.getVariantProperty().getAllowedValues())
		{
			list.add(new ItemStack(this, 1, variant.getMeta()));
		}
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void addInformation(final ItemStack stack, @Nullable final World player, final List<String> tooltip, final ITooltipFlag advanced)
	{
		final String name = this.getStateFromMeta(stack.getDamage()).getValue(this.getVariantProperty()).getName();

		tooltip.add(TextFormatting.GRAY + I18n.format(this.getTranslationKey() + "." + name + ".name"));
	}

	@Override
	public BlockItem createItemBlock()
	{
		return new ItemBlockSubtypes(this);
	}
}
