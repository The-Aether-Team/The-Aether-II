package com.gildedgames.aether.common.blocks.construction;

import com.gildedgames.aether.common.blocks.util.BlockCustom;
import com.gildedgames.aether.common.blocks.util.variants.IBlockVariants;
import com.gildedgames.aether.common.blocks.util.variants.blockstates.BlockVariant;
import com.gildedgames.aether.common.blocks.util.variants.blockstates.PropertyVariant;
import com.gildedgames.aether.common.registry.content.CreativeTabsAether;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class BlockSkyrootPlanks extends BlockCustom implements IBlockVariants
{

	public static final BlockVariant NORMAL = new BlockVariant(0, "normal"),
			BASE_PLANKS = new BlockVariant(1, "base_planks"),
			BASE_BEAM = new BlockVariant(2, "base_beam"),
			TOP_PLANKS = new BlockVariant(3, "top_planks"),
			TOP_BEAM = new BlockVariant(4, "top_beam"),
			FLOORBOARDS = new BlockVariant(5, "floorboards"),
			HIGHLIGHT = new BlockVariant(6, "highlight"),
			TILES = new BlockVariant(7, "tiles"),
			TILES_SMALL = new BlockVariant(8, "tiles_small");

	public static final PropertyVariant PROPERTY_VARIANT = PropertyVariant.create("variant", NORMAL, BASE_PLANKS, BASE_BEAM, TOP_PLANKS, TOP_BEAM, FLOORBOARDS, HIGHLIGHT, TILES, TILES_SMALL);

	public BlockSkyrootPlanks()
	{
		super(Material.WOOD);

		this.setSoundType(SoundType.WOOD);
		this.setHardness(2f);

		this.setDefaultState(this.getBlockState().getBaseState().withProperty(PROPERTY_VARIANT, NORMAL));
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
