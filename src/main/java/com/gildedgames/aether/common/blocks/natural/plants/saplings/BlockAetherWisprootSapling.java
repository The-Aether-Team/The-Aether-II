package com.gildedgames.aether.common.blocks.natural.plants.saplings;

import com.gildedgames.aether.common.blocks.properties.BlockVariant;
import com.gildedgames.aether.common.blocks.properties.PropertyVariant;
import com.gildedgames.aether.common.registry.content.GenerationAether;
import com.gildedgames.orbis.lib.core.BlueprintDefinition;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;

public class BlockAetherWisprootSapling extends BlockAetherSapling
{
	public static final BlockVariant
			GREEN = new BlockVariant(0, "wisproot_green"),
			BLUE = new BlockVariant(1, "wisproot_blue"),
			DARK_BLUE = new BlockVariant(2, "wisproot_dark_blue");

	public static final PropertyVariant PROPERTY_VARIANT = PropertyVariant
			.create("variant", GREEN, BLUE, DARK_BLUE);

	@Override
	public BlueprintDefinition getBlueprint(IBlockState state)
	{
		BlockVariant variant = state.getValue(PROPERTY_VARIANT);

		if (variant == BlockAetherWisprootSapling.GREEN)
		{
			return GenerationAether.WISPROOT_GREEN;
		}
		else if (variant == BlockAetherWisprootSapling.BLUE)
		{
			return GenerationAether.WISPROOT_BLUE;
		}
		else if (variant == BlockAetherWisprootSapling.DARK_BLUE)
		{
			return GenerationAether.WISPROOT_DARK_BLUE;
		}

		return null;
	}

	@Override
	public BlockPos getBlueprintOffset(IBlockState state)
	{
		return new BlockPos(-8, 0, -8);
	}

	@Override
	public PropertyVariant getPropertyVariant()
	{
		return PROPERTY_VARIANT;
	}
}
