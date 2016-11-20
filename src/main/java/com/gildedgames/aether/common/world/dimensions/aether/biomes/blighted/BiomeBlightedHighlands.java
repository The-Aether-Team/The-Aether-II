package com.gildedgames.aether.common.world.dimensions.aether.biomes.blighted;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.blocks.natural.BlockAetherGrass;
import com.gildedgames.aether.common.blocks.natural.BlockAetherLog;
import com.gildedgames.aether.common.world.dimensions.aether.biomes.BiomeAetherBase;
import com.gildedgames.aether.common.world.dimensions.aether.features.trees.WorldGenFruitTree;
import com.gildedgames.aether.common.world.dimensions.aether.features.trees.WorldGenMassiveSkyrootTree;
import com.gildedgames.aether.common.world.dimensions.aether.features.trees.WorldGenSkyrootTree;
import net.minecraft.block.BlockLog;
import net.minecraft.block.state.IBlockState;

public class BiomeBlightedHighlands extends BiomeAetherBase
{

	public static final WorldGenMassiveSkyrootTree dark_blue_tree = new WorldGenMassiveSkyrootTree(BlocksAether.dark_blue_skyroot_leaves.getDefaultState(), 35, true);

	public static final WorldGenFruitTree tree = new WorldGenFruitTree(BlocksAether.blighted_leaves.getDefaultState(), BlocksAether.blighted_leaves.getDefaultState(), 50, 5, true);

	public static final WorldGenSkyrootTree blightwillow_tree = new WorldGenSkyrootTree(BlocksAether.blightwillow_log.getDefaultState().withProperty(BlockAetherLog.PROPERTY_LOG_AXIS, BlockLog.EnumAxis.Y),
			BlocksAether.blightwillow_leaves.getDefaultState());

	public static final WorldGenSkyrootTree blighted_tree = new WorldGenSkyrootTree(BlocksAether.skyroot_log.getDefaultState().withProperty(BlockAetherLog.PROPERTY_LOG_AXIS, BlockLog.EnumAxis.Y),
			BlocksAether.blighted_leaves.getDefaultState());

	public BiomeBlightedHighlands()
	{
		super(new BiomeProperties("Blighted Highlands").setRainDisabled().setTemperature(0.5f), AetherCore.getResource("aether_blighted_highlands"));

		this.topBlock = BlocksAether.aether_grass.getDefaultState().withProperty(BlockAetherGrass.PROPERTY_VARIANT, BlockAetherGrass.BLIGHTED);
	}

	@Override
	public IBlockState getCoastalBlock()
	{
		return BlocksAether.quicksoil.getDefaultState();
	}

	/*@Override
	public WorldGenAbstractTree genBigTreeChance(Random random)
	{
		return blightwillow_tree;
	}*/

}
