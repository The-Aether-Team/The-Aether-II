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
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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
	public void getSubBlocks(Item item, CreativeTabs tab, NonNullList<ItemStack> list)
	{
		for (BlockVariant variant : PROPERTY_VARIANT.getAllowedValues())
		{
			list.add(new ItemStack(item, 1, variant.getMeta()));
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer()
	{
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state)
	{
		return true;
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
	public int damageDropped(IBlockState state)
	{
		return state.getValue(PROPERTY_VARIANT).getMeta();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced)
	{
		IBlockState state = this.getStateFromMeta(stack.getMetadata());

		tooltip.add(I18n.format(this.getUnlocalizedName() + "." + state.getValue(PROPERTY_VARIANT).getName() + ".name"));
	}
}
