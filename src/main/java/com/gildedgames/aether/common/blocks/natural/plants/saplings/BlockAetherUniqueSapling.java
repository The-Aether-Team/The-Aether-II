package com.gildedgames.aether.common.blocks.natural.plants.saplings;

import com.gildedgames.aether.common.blocks.properties.BlockVariant;
import com.gildedgames.aether.common.blocks.properties.PropertyVariant;
import com.gildedgames.aether.common.registry.content.GenerationAether;
import com.gildedgames.orbis_api.core.BlueprintDefinition;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;

public class BlockAetherUniqueSapling extends BlockAetherSapling
{
	public static final BlockVariant
			AMBEROOT = new BlockVariant(0, "amberoot"),
			MUTANT_TREE = new BlockVariant(1, "mutant_tree");

	public static final PropertyVariant PROPERTY_VARIANT = PropertyVariant
			.create("variant", AMBEROOT, MUTANT_TREE);

	@Override
	public BlueprintDefinition getBlueprint(IBlockState state)
	{
		final BlockVariant variant = state.getValue(PROPERTY_VARIANT);

		if (variant == BlockAetherUniqueSapling.AMBEROOT)
		{
			return GenerationAether.AMBEROOT_TREE;
		}
		else if (variant == BlockAetherUniqueSapling.MUTANT_TREE)
		{
			return GenerationAether.CRAZY_MUTANT_TREE;
		}

		return null;
	}

	@Override
	public BlockPos getBlueprintOffset(IBlockState state)
	{
		final BlockVariant variant = state.getValue(PROPERTY_VARIANT);

		if (variant == BlockAetherUniqueSapling.AMBEROOT)
		{
			return new BlockPos(-8, 0, -6);
		}
		else if (variant == BlockAetherUniqueSapling.MUTANT_TREE)
		{
			return new BlockPos(-5, 0, -5);
		}

		return BlockPos.ORIGIN;
	}

	@Override
	public PropertyVariant getPropertyVariant()
	{
		return PROPERTY_VARIANT;
	}
}
