package com.gildedgames.aether.common.blocks.dungeon;

import com.gildedgames.aether.common.blocks.util.variants.IBlockVariants;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockDivine extends BlockLabyrinth implements IBlockVariants
{
	public static final int
			NORMAL = 0,
			DIVINE = 1;

	public static final PropertyBool PROPERTY_IS_DIVINE = PropertyBool.create("is_divine");

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs tab, NonNullList<ItemStack> list)
	{
		list.add(new ItemStack(item, 1, NORMAL));
		list.add(new ItemStack(item, 1, DIVINE));
	}

	@Override
	public float getBlockHardness(IBlockState state, World world, BlockPos pos)
	{
		return state.getBlock() == this && state.getValue(PROPERTY_IS_DIVINE) ? -1.0f : this.blockHardness;
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(PROPERTY_IS_DIVINE, meta == 1);
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(PROPERTY_IS_DIVINE) ? DIVINE : NORMAL;
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, PROPERTY_IS_DIVINE);
	}

	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		switch (stack.getMetadata())
		{
		case DIVINE:
			return "divine";
		case NORMAL:
			return "normal";
		default:
			return "missingno";
		}
	}

}
