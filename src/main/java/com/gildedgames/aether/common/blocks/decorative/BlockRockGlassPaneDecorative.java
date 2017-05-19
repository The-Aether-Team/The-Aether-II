package com.gildedgames.aether.common.blocks.decorative;

import com.gildedgames.aether.common.blocks.IBlockWithItem;
import com.gildedgames.aether.common.blocks.properties.BlockVariant;
import com.gildedgames.aether.common.blocks.properties.PropertyVariant;
import com.gildedgames.aether.common.items.blocks.ItemBlockSubtypes;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPane;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.List;

public class BlockRockGlassPaneDecorative extends BlockPane implements IBlockWithItem
{
	public static final BlockVariant SKYROOT_FRAME = new BlockVariant(0, "skyroot_frame"),
			ARKENIUM_FRAME = new BlockVariant(1, "arkenium_frame");

	public static final PropertyVariant PROPERTY_VARIANT = PropertyVariant.create("variant", SKYROOT_FRAME, ARKENIUM_FRAME);

	private final Block parentBlock;

	public BlockRockGlassPaneDecorative(@Nonnull Block block)
	{
		super(Material.ROCK, true);

		this.parentBlock = block;

		this.setHardness(1f);
		this.setResistance(2000f);

		this.setLightOpacity(3);

		this.setSoundType(SoundType.GLASS);
	}

	@Nonnull
	protected PropertyVariant getVariantProperty()
	{
		return PROPERTY_VARIANT;
	}

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
		return new BlockStateContainer(this, BlockPane.NORTH, BlockPane.SOUTH, BlockPane.EAST, BlockPane.WEST, this.getVariantProperty());
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
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer()
	{
		return BlockRenderLayer.TRANSLUCENT;
	}

	@Override
	public ItemBlock createItemBlock()
	{
		return new ItemBlockSubtypes(this);
	}
}
