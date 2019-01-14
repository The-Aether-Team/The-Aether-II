package com.gildedgames.aether.common.blocks.natural.plants.saplings;

import com.gildedgames.aether.common.blocks.properties.BlockVariant;
import com.gildedgames.aether.common.blocks.properties.PropertyVariant;
import com.gildedgames.aether.common.registry.content.GenerationAether;
import com.gildedgames.orbis_api.core.BlueprintDefinition;
import com.gildedgames.orbis_api.core.CreationData;
import com.gildedgames.orbis_api.core.baking.BakedBlueprint;
import com.gildedgames.orbis_api.core.util.BlueprintPlacer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.terraingen.TerrainGen;

import java.util.Random;

public class BlockAetherWisprootSapling extends BlockAetherSapling
{
	public static final BlockVariant
		GREEN_WISPROOT = new BlockVariant(0,"wisproot_green"),
		BLUE_WISPROOT = new BlockVariant(1, "wisproot_blue"),
		DARK_BLUE_WISPROOT = new BlockVariant(2, "wisproot_dark_blue");

	public static final PropertyVariant PROPERTY_VARIANT = PropertyVariant
			.create("variant", GREEN_WISPROOT, BLUE_WISPROOT, DARK_BLUE_WISPROOT);

	@Override
	public void generateTree(World world, BlockPos pos, IBlockState state, Random random)
	{
		if (TerrainGen.saplingGrowTree(world,random,pos))
		{
			final int meta = state.getValue(PROPERTY_VARIANT).getMeta();

			BlockPos adjustedPos = pos.add(-8, 0, -8);

			BlueprintDefinition tree;

			if (meta == GREEN_WISPROOT.getMeta())
			{
				tree = GenerationAether.WISPROOT_GREEN;
			}
			else if (meta == BLUE_WISPROOT.getMeta())
			{
				tree = GenerationAether.WISPROOT_BLUE;
			}
			else if (meta == DARK_BLUE_WISPROOT.getMeta())
			{
				tree = GenerationAether.WISPROOT_DARK_BLUE;
			}
			else
			{
				tree = null;
				adjustedPos = pos;
			}

			if (tree != null)
			{
				world.setBlockState(pos, Blocks.AIR.getDefaultState(), 4);

				BakedBlueprint baked = new BakedBlueprint(tree.getData(), new CreationData(world).pos(BlockPos.ORIGIN).placesAir(false).placesVoid(false));

				if (!BlueprintPlacer.place(world, baked, tree.getConditions(), adjustedPos, true))
				{
					world.setBlockState(pos, state, 4);
				}
			}
		}
	}

	@Override
	public PropertyVariant getPropertyVariant()
	{
		return PROPERTY_VARIANT;
	}
}
