package com.gildedgames.aether.common.blocks.decorative;

import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.blocks.properties.BlockVariant;
import com.gildedgames.aether.common.blocks.properties.PropertyVariant;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockLightSkyrootDecorative extends BlockDecorative
{
	public static final BlockVariant BASE_PLANKS = new BlockVariant(0, "base_planks"),
			BASE_BEAM = new BlockVariant(1, "base_beam"),
			TOP_PLANKS = new BlockVariant(2, "top_planks"),
			TOP_BEAM = new BlockVariant(3, "top_beam"),
			FLOORBOARDS = new BlockVariant(4, "floorboards"),
			HIGHLIGHT = new BlockVariant(5, "highlight"),
			TILES = new BlockVariant(6, "tiles"),
			TILES_SMALL = new BlockVariant(7, "tiles_small");

	public static final PropertyVariant PROPERTY_VARIANT = PropertyVariant.create("variant", BASE_PLANKS, BASE_BEAM, TOP_PLANKS, TOP_BEAM, FLOORBOARDS, HIGHLIGHT, TILES, TILES_SMALL);

	public BlockLightSkyrootDecorative()
	{
		super(Material.WOOD, BlocksAether.light_skyroot_planks);

		this.setSoundType(SoundType.WOOD);

		this.setHardness(2f);
	}

	@Override
	protected PropertyVariant getVariantProperty()
	{
		return PROPERTY_VARIANT;
	}
}
