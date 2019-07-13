package com.gildedgames.aether.common.blocks.decorative;

import com.gildedgames.aether.common.blocks.properties.PropertyVariant;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateContainer;

import javax.annotation.Nonnull;

public abstract class BlockDecorative extends Block
{
	public BlockDecorative(final Block.Properties properties)
	{
		super(properties);
	}

	@Nonnull
	protected abstract PropertyVariant getVariantProperty();

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
	{
		builder.add(this.getVariantProperty());
	}
}
