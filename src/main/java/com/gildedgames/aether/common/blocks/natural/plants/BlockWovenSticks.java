package com.gildedgames.aether.common.blocks.natural.plants;

import com.gildedgames.aether.common.blocks.IBlockMultiName;
import com.gildedgames.aether.common.blocks.properties.BlockVariant;
import com.gildedgames.aether.common.blocks.properties.PropertyVariant;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateContainer;
import net.minecraft.util.NonNullList;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

public class BlockWovenSticks extends Block implements IBlockMultiName
{

	public static final BlockVariant
			SKYROOT = new BlockVariant(0, "skyroot");

	public static final PropertyVariant PROPERTY_VARIANT = PropertyVariant.create("variant", SKYROOT);

	public BlockWovenSticks(Block.Properties properties)
	{
		super(properties);

		this.setDefaultState(this.stateContainer.getBaseState().with(PROPERTY_VARIANT, SKYROOT));
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void getSubBlocks(final ItemGroup tab, final NonNullList<ItemStack> list)
	{
		for (final BlockVariant variant : PROPERTY_VARIANT.getAllowedValues())
		{
			list.add(new ItemStack(this, 1, variant.getMeta()));
		}
	}

	@Override
	public int damageDropped(final BlockState state)
	{
		return state.get(PROPERTY_VARIANT).getMeta();
	}

	@Override
	public BlockState getStateFromMeta(final int meta)
	{
		return this.getDefaultState().with(PROPERTY_VARIANT, PROPERTY_VARIANT.fromMeta(meta));
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
	public String getTranslationKey(final ItemStack stack)
	{
		return PROPERTY_VARIANT.fromMeta(stack.getMetadata()).getName();
	}

}
