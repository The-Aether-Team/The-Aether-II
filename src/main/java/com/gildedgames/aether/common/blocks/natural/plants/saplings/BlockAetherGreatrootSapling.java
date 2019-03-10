package com.gildedgames.aether.common.blocks.natural.plants.saplings;

import com.gildedgames.aether.common.blocks.properties.BlockVariant;
import com.gildedgames.aether.common.blocks.properties.PropertyVariant;
import com.gildedgames.aether.common.registry.content.GenerationAether;
import com.gildedgames.orbis.lib.core.BlueprintDefinition;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;

public class BlockAetherGreatrootSapling extends BlockAetherSapling
{
	public static final BlockVariant
			GREEN_GREATROOT = new BlockVariant(0, "green_greatroot"),
			BLUE_GREATROOT = new BlockVariant(1, "blue_greatroot"),
			DARK_BLUE_GREATROOT = new BlockVariant(2, "dark_blue_greatroot");

	public static final PropertyVariant PROPERTY_VARIANT = PropertyVariant
			.create("variant", GREEN_GREATROOT, BLUE_GREATROOT, DARK_BLUE_GREATROOT);

	@Override
	public PropertyVariant getPropertyVariant()
	{
		return PROPERTY_VARIANT;
	}

	@Override
	public BlueprintDefinition getBlueprint(IBlockState state)
	{
		final BlockVariant variant = state.getValue(PROPERTY_VARIANT);

		if (variant == BlockAetherGreatrootSapling.GREEN_GREATROOT)
		{
			return GenerationAether.GREATROOT_TREE;
		}
		else if (variant == BlockAetherGreatrootSapling.BLUE_GREATROOT)
		{
			return GenerationAether.GREATROOT_TREE;
		}
		else if (variant == BlockAetherGreatrootSapling.DARK_BLUE_GREATROOT)
		{
			return GenerationAether.GREATROOT_TREE;
		}

		return null;
	}

	@Override
	public BlockPos getBlueprintOffset(IBlockState state)
	{
		return new BlockPos(-5, 0, -5);
	}

}
