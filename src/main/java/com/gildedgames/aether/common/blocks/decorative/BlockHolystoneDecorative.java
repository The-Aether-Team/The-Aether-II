package com.gildedgames.aether.common.blocks.decorative;

import com.gildedgames.aether.common.blocks.properties.BlockVariant;
import com.gildedgames.aether.common.blocks.properties.PropertyVariant;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockHolystoneDecorative extends BlockDecorative
{
	public static final BlockVariant BASE_BRICKS = new BlockVariant(0, "base_bricks"),
			BASE_PILLAR = new BlockVariant(1, "base_pillar"),
			CAPSTONE_BRICKS = new BlockVariant(2, "capstone_bricks"),
			CAPSTONE_PILLAR = new BlockVariant(3, "capstone_pillar"),
			FLAGSTONES = new BlockVariant(4, "flagstones"),
			HEADSTONE = new BlockVariant(5, "headstone"),
			KEYSTONE = new BlockVariant(6, "keystone");

	public static final PropertyVariant PROPERTY_VARIANT = PropertyVariant
			.create("variant", BASE_BRICKS, BASE_PILLAR, CAPSTONE_BRICKS, CAPSTONE_PILLAR, FLAGSTONES, HEADSTONE, KEYSTONE);

	public BlockHolystoneDecorative(Block.Properties properties)
	{
		super(properties);
	}

	@Override
	protected PropertyVariant getVariantProperty()
	{
		return PROPERTY_VARIANT;
	}
}
