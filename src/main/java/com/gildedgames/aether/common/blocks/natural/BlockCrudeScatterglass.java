package com.gildedgames.aether.common.blocks.natural;

import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.blocks.util.variants.IBlockVariants;
import com.gildedgames.aether.common.blocks.util.variants.blockstates.BlockVariant;
import com.gildedgames.aether.common.blocks.util.variants.blockstates.PropertyVariant;
import com.gildedgames.aether.common.registry.content.CreativeTabsAether;
import net.minecraft.block.BlockBreakable;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.Random;

public class BlockCrudeScatterglass extends BlockBreakable implements IBlockVariants
{

	public static final BlockVariant NORMAL = new BlockVariant(0, "normal"),
			SKYROOT_FRAME = new BlockVariant(1, "skyroot_frame"),
			ARKENIUM_FRAME = new BlockVariant(2, "arkenium_frame");

	public static final PropertyVariant PROPERTY_VARIANT = PropertyVariant.create("variant", NORMAL, SKYROOT_FRAME, ARKENIUM_FRAME);

	public BlockCrudeScatterglass()
	{
		super(Material.ROCK, false);

		this.setHardness(1f);
		this.setResistance(2000f);

		this.setLightOpacity(3);

		this.setDefaultState(this.getBlockState().getBaseState().withProperty(PROPERTY_VARIANT, NORMAL));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer()
	{
		return BlockRenderLayer.TRANSLUCENT;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}

	@Override
	public int damageDropped(IBlockState state)
	{
		return state.getValue(PROPERTY_VARIANT).getMeta();
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
		return NORMAL.getName();
	}

	@Override
	public void addItemsToCreativeTab(Item item, CreativeTabs tab, List<ItemStack> stackList)
	{
		if (tab == CreativeTabsAether.VISUAL_VARIANTS)
		{
			for (BlockVariant variant : PROPERTY_VARIANT.getAllowedValues())
			{
				if (variant != NORMAL)
				{
					stackList.add(new ItemStack(item, 1, variant.getMeta()));
				}
			}
		}
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced)
	{
		if (stack.getItemDamage() != NORMAL.getMeta())
		{
			String s = this.getUnlocalizedName() + "." + PROPERTY_VARIANT.fromMeta(stack.getMetadata()).getName() + ".name";

			tooltip.add(TextFormatting.GRAY + "" + I18n.format(s));
		}
	}

}
