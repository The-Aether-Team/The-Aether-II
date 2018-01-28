package com.gildedgames.aether.common.blocks.builder;

import com.gildedgames.aether.common.blocks.IBlockWithItem;
import com.gildedgames.aether.common.blocks.properties.BlockVariant;
import com.gildedgames.aether.common.blocks.properties.PropertyVariant;
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
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class BlockStructureHint extends Block implements IBlockWithItem
{
	public static BlockVariant WILDCARD_BLOCK = new BlockVariant(0, "wildcard"),
			SPAWNER_ENTITY = new BlockVariant(1, "spawner_entity"),
			SPAWNER_PACK = new BlockVariant(2, "spawner_pack"),
			GENERATOR_LOOT = new BlockVariant(3, "generator_loot");

	public static PropertyVariant PROPERTY_VARIANT = PropertyVariant.create("type", WILDCARD_BLOCK, SPAWNER_ENTITY, SPAWNER_PACK, GENERATOR_LOOT);

	public BlockStructureHint()
	{
		super(Material.ROCK);

		this.setLightOpacity(0);

		this.setBlockUnbreakable();
		this.setResistance(100000.0F);

		this.disableStats();

		this.setDefaultState(this.getBlockState().getBaseState().withProperty(PROPERTY_VARIANT, WILDCARD_BLOCK));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(final CreativeTabs itemIn, final NonNullList<ItemStack> items)
	{
		for (final BlockVariant variant : PROPERTY_VARIANT.getAllowedValues())
		{
			items.add(new ItemStack(this, 1, variant.getMeta()));
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer()
	{
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	public boolean isOpaqueCube(final IBlockState state)
	{
		return false;
	}

	@Override
	public boolean isFullCube(final IBlockState state)
	{
		return true;
	}

	@Override
	public IBlockState getStateFromMeta(final int meta)
	{
		return this.getDefaultState().withProperty(PROPERTY_VARIANT, PROPERTY_VARIANT.fromMeta(meta));
	}

	@Override
	public int getMetaFromState(final IBlockState state)
	{
		return state.getValue(PROPERTY_VARIANT).getMeta();
	}

	@Override
	public BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, PROPERTY_VARIANT);
	}

	@Override
	public ItemBlock createItemBlock()
	{
		return new ItemBlockSubtypes(this);
	}

	@Override
	public int damageDropped(final IBlockState state)
	{
		return state.getValue(PROPERTY_VARIANT).getMeta();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(final ItemStack stack, @Nullable final World player, final List<String> tooltip, final ITooltipFlag advanced)
	{
		final IBlockState state = this.getStateFromMeta(stack.getMetadata());

		tooltip.add(I18n.format(this.getUnlocalizedName() + "." + state.getValue(PROPERTY_VARIANT).getName() + ".name"));
	}
}
