package com.gildedgames.aether.common.blocks.decorative;

import com.gildedgames.aether.common.blocks.IBlockWithItem;
import com.gildedgames.aether.common.blocks.util.BlockBuilder;
import com.gildedgames.aether.common.blocks.properties.BlockVariant;
import com.gildedgames.aether.common.blocks.properties.PropertyVariant;
import com.gildedgames.aether.common.items.blocks.ItemBlockDecorative;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.List;

public abstract class BlockDecorative extends BlockBuilder implements IBlockWithItem
{
	private final Block parentBlock;

	public BlockDecorative(Material material, @Nonnull Block block)
	{
		super(material);

		this.parentBlock = block;
	}

	@Nonnull
	protected abstract PropertyVariant getVariantProperty();
	
	@Override
	public int damageDropped(IBlockState state)
	{
		return state.getValue(this.getVariantProperty()).getMeta();
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		BlockVariant variant = this.getVariantProperty().fromMeta(meta);

		return this.getDefaultState().withProperty(this.getVariantProperty(), variant);
	}

	@Override
	public int getMetaFromState(IBlockState state)
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
	public void getSubBlocks(Item item, CreativeTabs tab, NonNullList<ItemStack> list)
	{
		for (BlockVariant variant : this.getVariantProperty().getAllowedValues())
		{
			list.add(new ItemStack(item, 1, variant.getMeta()));
		}
	}

	@Override
	public String getUnlocalizedName()
	{
		return this.parentBlock.getUnlocalizedName();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced)
	{
		String name = this.getStateFromMeta(stack.getItemDamage()).getValue(this.getVariantProperty()).getName();

		tooltip.add(TextFormatting.GRAY + I18n.format(this.getUnlocalizedName() + "." + name + ".name"));
	}

	@Override
	public ItemBlock createItemBlock()
	{
		return new ItemBlockDecorative(this);
	}
}
