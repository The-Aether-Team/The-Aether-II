package com.gildedgames.aether.common.blocks.natural;

import com.gildedgames.aether.common.blocks.properties.BlockVariant;
import com.gildedgames.aether.common.blocks.properties.PropertyVariant;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateContainer;

public class BlockAetherDirt extends Block
{

	public static final BlockVariant DIRT = new BlockVariant(0, "dirt"),
			COARSE_DIRT = new BlockVariant(1, "coarse_dirt");

	public static final PropertyVariant PROPERTY_VARIANT = PropertyVariant.create("variant", DIRT, COARSE_DIRT);

	public BlockAetherDirt(Block.Properties properties)
	{
		super(properties);

		this.setDefaultState(this.getStateContainer().getBaseState().with(PROPERTY_VARIANT, DIRT));
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
	{
		builder.add(PROPERTY_VARIANT);
	}


}
