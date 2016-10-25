package com.gildedgames.aether.common.world.features;

import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.util.TemplatePrimer;
import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;

import java.util.List;
import java.util.Random;

public class WorldGenTemplate extends WorldGenerator
{

	protected static final Rotation[] ROTATIONS = Rotation.values();

	private Template template;

	private TemplatePipeline pipeline;

	private List<PlacementCondition> placementConditions = Lists.newArrayList();

	public WorldGenTemplate(TemplatePipeline pipeline, Template template, PlacementCondition condition, PlacementCondition... placementConditions)
	{
		this.pipeline = pipeline;
		this.template = template;

		this.placementConditions = Lists.newArrayList(placementConditions);

		this.placementConditions.add(condition);
	}

	protected Template getTemplate()
	{
		return this.template;
	}

	protected boolean canGenerate(World world, Random rand, BlockPos pos, PlacementSettings settings)
	{
		final BlockPos max = pos.add(this.template.getSize().getX(), this.template.getSize().getY(), this.template.getSize().getZ());

		if (!world.isAreaLoaded(pos, max) || max.getY() > world.getActualHeight())
		{
			return false;
		}

		List<Template.BlockInfo> info = TemplatePrimer.getBlocks(this.template);

		List<Template.BlockInfo> infoTransformed = TemplatePrimer.getBlocks(info, pos, settings, this.template);

		for (PlacementCondition condition : this.placementConditions)
		{
			if (!condition.canPlace(world, pos, infoTransformed))
			{
				return false;
			}
		}

		return true;
	}

	@Override
	public boolean generate(World world, Random rand, BlockPos pos)
	{
		return this.placeTemplate(world, rand, pos);
	}

	protected boolean placeTemplate(World world, Random rand, BlockPos pos)
	{
		Rotation rotation = ROTATIONS[rand.nextInt(ROTATIONS.length)];

		return this.placeTemplate(world, rand, pos, rotation);
	}

	public boolean placeTemplate(World world, Random rand, BlockPos pos, Rotation rotation)
	{
		PlacementSettings settings = new PlacementSettings()
				.setMirror(Mirror.NONE)
				.setRotation(rotation)
				.setIgnoreEntities(false)
				.setChunk(null)
				.setReplacedBlock(null)
				.setIgnoreStructureBlock(false);

		if (this.canGenerate(world, rand, pos, settings))
		{
			this.template.addBlocksToWorld(world, pos, settings);

			return true;
		}

		return false;
	}

	public static boolean canGrowInto(Block block)
	{
		Material material = block.getDefaultState().getMaterial();

		return material == Material.AIR || material == Material.LEAVES || block == BlocksAether.aether_grass || block == BlocksAether.aether_dirt;
	}

	public static boolean isReplaceable(World world, BlockPos pos)
	{
		IBlockState state = world.getBlockState(pos);

		return state.getBlock().isAir(state, world, pos) || state.getBlock().isLeaves(state, world, pos) || WorldGenTemplate.canGrowInto(state.getBlock());
	}

	public interface PlacementCondition
	{

		boolean canPlace(World world, BlockPos placedAt, List<Template.BlockInfo> blocks);

	}

}
