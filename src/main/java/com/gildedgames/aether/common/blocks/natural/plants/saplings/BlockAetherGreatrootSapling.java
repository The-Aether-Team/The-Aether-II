package com.gildedgames.aether.common.blocks.natural.plants.saplings;

import com.gildedgames.aether.common.blocks.properties.BlockVariant;
import com.gildedgames.aether.common.blocks.properties.PropertyVariant;
import com.gildedgames.aether.common.init.GenerationAether;
import com.gildedgames.orbis.lib.core.BlueprintDefinition;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class BlockAetherGreatrootSapling extends BlockAetherSapling
{
	public static final BlockVariant
			GREEN = new BlockVariant(0, "green_greatroot"),
			BLUE = new BlockVariant(1, "blue_greatroot"),
			DARK_BLUE = new BlockVariant(2, "dark_blue_greatroot");

	public static final PropertyVariant PROPERTY_VARIANT = PropertyVariant
			.create("variant", GREEN, BLUE, DARK_BLUE);

	@Override
	public PropertyVariant getPropertyVariant()
	{
		return PROPERTY_VARIANT;
	}

	@Override
	public BlueprintDefinition getBlueprint(BlockState state)
	{
		final BlockVariant variant = state.getValue(PROPERTY_VARIANT);

		if (variant == BlockAetherGreatrootSapling.GREEN)
		{
			return GenerationAether.GREATROOT_TREE;
		}
		else if (variant == BlockAetherGreatrootSapling.BLUE)
		{
			return GenerationAether.GREATROOT_TREE;
		}
		else if (variant == BlockAetherGreatrootSapling.DARK_BLUE)
		{
			return GenerationAether.GREATROOT_TREE;
		}

		return null;
	}

	@Override
	public BlockPos getBlueprintOffset(BlockState state)
	{
		return new BlockPos(-5, 0, -5);
	}

}
