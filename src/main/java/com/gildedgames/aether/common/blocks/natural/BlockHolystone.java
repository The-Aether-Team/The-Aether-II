package com.gildedgames.aether.common.blocks.natural;

import com.gildedgames.aether.common.blocks.IBlockMultiName;
import com.gildedgames.aether.common.blocks.properties.BlockVariant;
import com.gildedgames.aether.common.blocks.properties.PropertyVariant;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateContainer;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ToolType;

public class BlockHolystone extends Block implements IBlockMultiName
{
	public static final BlockVariant
			NORMAL_HOLYSTONE = new BlockVariant(0, "normal"),
			MOSSY_HOLYSTONE = new BlockVariant(1, "mossy"),
			BLOOD_MOSS_HOLYSTONE = new BlockVariant(2, "blood_moss");

	public static final PropertyVariant PROPERTY_VARIANT = PropertyVariant.create("variant", NORMAL_HOLYSTONE, MOSSY_HOLYSTONE, BLOOD_MOSS_HOLYSTONE);

	public BlockHolystone(Block.Properties properties)
	{
		super(properties);

		this.setDefaultState(this.stateContainer.getBaseState().with(PROPERTY_VARIANT, NORMAL_HOLYSTONE));
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void getSubBlocks(final ItemGroup tab, final NonNullList<ItemStack> list)
	{
		for (final BlockVariant variant : PROPERTY_VARIANT.getAllowedValues())
		{
			if (variant != BLOOD_MOSS_HOLYSTONE)
			{
				list.add(new ItemStack(this, 1, variant.getMeta()));
			}
		}
	}

	@Override
	public float getBlockHardness(BlockState state, IBlockReader worldIn, BlockPos pos)
	{
		return state.getBlock() == this && state.get(PROPERTY_VARIANT) == BLOOD_MOSS_HOLYSTONE ? -1.0f : this.blockHardness;
	}

	@Override
	public BlockState getStateFromMeta(final int meta)
	{
		return this.getDefaultState().with(PROPERTY_VARIANT, PROPERTY_VARIANT.fromMeta(meta & 7));
	}

	@Override
	public int getMetaFromState(final BlockState state)
	{
		return state.get(PROPERTY_VARIANT).getMeta();
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
	{
		builder.add(PROPERTY_VARIANT);
	}

	@Override
	public int damageDropped(final BlockState state)
	{
		return state.get(PROPERTY_VARIANT).getMeta();
	}

	@Override
	public String getTranslationKey(final ItemStack stack)
	{
		return PROPERTY_VARIANT.fromMeta(stack.getMetadata()).getName();
	}

}
