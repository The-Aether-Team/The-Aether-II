package com.gildedgames.aether.common.blocks.decorative;

import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.blocks.util.variants.blockstates.BlockVariant;
import com.gildedgames.aether.common.blocks.util.variants.blockstates.PropertyVariant;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockSkyrootDecorative extends BlockDecorative
{
	public static final BlockVariant BASE_PLANKS = new BlockVariant(1, "base_planks"),
			BASE_BEAM = new BlockVariant(2, "base_beam"),
			TOP_PLANKS = new BlockVariant(3, "top_planks"),
			TOP_BEAM = new BlockVariant(4, "top_beam"),
			FLOORBOARDS = new BlockVariant(5, "floorboards"),
			HIGHLIGHT = new BlockVariant(6, "highlight"),
			TILES = new BlockVariant(7, "tiles"),
			TILES_SMALL = new BlockVariant(8, "tiles_small");

	public static final PropertyVariant PROPERTY_VARIANT = PropertyVariant.create("variant", BASE_PLANKS, BASE_BEAM, TOP_PLANKS, TOP_BEAM, FLOORBOARDS, HIGHLIGHT, TILES, TILES_SMALL);

	public BlockSkyrootDecorative()
	{
		super(Material.WOOD, BlocksAether.skyroot_planks);

		this.setSoundType(SoundType.WOOD);

		this.setHardness(2f);
	}

	@Override
	protected PropertyVariant getVariantProperty()
	{
		return PROPERTY_VARIANT;
	}
}
