package com.gildedgames.aether.common.blocks.decorative;

import com.gildedgames.aether.common.blocks.IBlockWithItem;
import com.gildedgames.aether.common.blocks.properties.BlockVariant;
import com.gildedgames.aether.common.blocks.properties.PropertyVariant;
import com.gildedgames.aether.common.items.blocks.ItemBlockSubtypes;
import net.minecraft.block.BlockPane;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class BlockRockGlassPaneDecorative extends BlockPane implements IBlockWithItem
{
	public static final BlockVariant SKYROOT_FRAME = new BlockVariant(0, "skyroot_frame"),
			ARKENIUM_FRAME = new BlockVariant(1, "arkenium_frame");

	public static final PropertyVariant PROPERTY_VARIANT = PropertyVariant.create("variant", SKYROOT_FRAME, ARKENIUM_FRAME);

	public BlockRockGlassPaneDecorative(Material material)
	{
		super(material, true);

		this.setHardness(0.3f);
		this.setResistance(2000f);

		this.setLightOpacity(0);

		this.setSoundType(SoundType.GLASS);
	}

	@Nonnull
	protected PropertyVariant getVariantProperty()
	{
		return PROPERTY_VARIANT;
	}

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
		return new BlockStateContainer(this, BlockPane.NORTH, BlockPane.SOUTH, BlockPane.EAST, BlockPane.WEST, this.getVariantProperty());
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
	@SideOnly(Side.CLIENT)
	public void addInformation(final ItemStack stack, @Nullable final World player, final List<String> tooltip, final ITooltipFlag advanced)
	{
		final String name = this.getStateFromMeta(stack.getItemDamage()).getValue(this.getVariantProperty()).getName();

		tooltip.add(TextFormatting.GRAY + I18n.format(this.getTranslationKey() + "." + name + ".name"));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getRenderLayer()
	{
		return BlockRenderLayer.TRANSLUCENT;
	}

	@Override
	public ItemBlock createItemBlock()
	{
		return new ItemBlockSubtypes(this);
	}
}
