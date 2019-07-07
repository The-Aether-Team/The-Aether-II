package com.gildedgames.aether.common.world.aether.biomes;

import com.gildedgames.aether.api.registrar.BlocksAether;
import com.gildedgames.aether.api.world.generation.WorldDecoration;
import com.gildedgames.aether.api.world.islands.IIslandData;
import com.gildedgames.aether.api.world.islands.IIslandGenerator;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.orbis.lib.util.mc.NBT;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class BiomeVoid extends BiomeAetherBase
{

	public BiomeVoid()
	{
		super(new Biome.BiomeProperties("Aether Void").setRainDisabled().setTemperature(0.5f).setWaterColor(0x70DB70), AetherCore.getResource("aether_void"));
	}

	@Override
	public IBlockState getCoastalBlock()
	{
		return BlocksAether.quicksoil.getDefaultState();
	}

	@Override
	public IIslandGenerator createIslandGenerator(Random rand, IIslandData islandData)
	{
		return null;
	}

	@Override
	public Collection<NBT> createIslandComponents(final IIslandData islandData)
	{
		return Collections.emptyList();
	}

	@Override
	public float getRarityWeight()
	{
		return 0.0F;
	}

	@Override
	public void postDecorate(final World world, final Random rand, final BlockPos pos)
	{

	}

	@Override
	public List<WorldDecoration> createBasicDecorations(Random rand)
	{
		return Collections.emptyList();
	}

	@Override
	public List<WorldDecoration> createTreeDecorations(Random rand)
	{
		return Collections.emptyList();
	}

	@Override
	public float createForestTreeCountModifier(Random rand)
	{
		return 0;
	}

	@Override
	public float createOpenAreaDecorationGenChance(Random rand)
	{
		return 0;
	}

}
