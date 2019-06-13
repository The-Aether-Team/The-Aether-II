package com.gildedgames.aether.common.blocks.natural.plants.saplings;

import com.gildedgames.aether.common.blocks.properties.BlockVariant;
import com.gildedgames.aether.common.blocks.properties.PropertyVariant;
import com.gildedgames.aether.common.registry.content.GenerationAether;
import com.gildedgames.orbis.lib.core.BlueprintDefinition;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;

public class BlockAetherSkyrootSapling extends BlockAetherSapling
{
	public static final BlockVariant
			GREEN = new BlockVariant(0, "green_skyroot"),
			BLUE = new BlockVariant(1, "blue_skyroot"),
			DARK_BLUE = new BlockVariant(2, "dark_blue_skyroot");

	public static final PropertyVariant PROPERTY_VARIANT = PropertyVariant
			.create("variant", GREEN, BLUE, DARK_BLUE);

	@Override
	public BlueprintDefinition getBlueprint(IBlockState state)
	{
		BlockVariant variant = state.getValue(PROPERTY_VARIANT);

		if (variant == BlockAetherSkyrootSapling.GREEN)
		{
			return GenerationAether.SKYROOT_OAK_GREEN;
		}
		else if (variant == BlockAetherSkyrootSapling.BLUE)
		{
			return GenerationAether.SKYROOT_OAK_BLUE;
		}
		else if (variant == BlockAetherSkyrootSapling.DARK_BLUE)
		{
			return GenerationAether.SKYROOT_OAK_DARK_BLUE;
		}

		return null;
	}

	@Override
	public BlockPos getBlueprintOffset(IBlockState state)
	{
		return new BlockPos(-4, 0, -4);
	}

	@Override
	public PropertyVariant getPropertyVariant()
	{
		return PROPERTY_VARIANT;
	}
}
