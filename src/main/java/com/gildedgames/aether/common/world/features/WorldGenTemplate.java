package com.gildedgames.aether.common.world.features;

import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.util.TemplatePrimer;
import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.structure.template.BlockRotationProcessor;
import net.minecraft.world.gen.structure.template.ITemplateProcessor;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;

import java.util.List;
import java.util.Random;

public class WorldGenTemplate extends WorldGenerator
{

	protected static final Rotation[] ROTATIONS = Rotation.values();

	private Template template;

	private List<PlacementCondition> placementConditions = Lists.newArrayList();

	public WorldGenTemplate(Template template, PlacementCondition condition, PlacementCondition... placementConditions)
	{
		this.template = template;

		this.placementConditions = Lists.newArrayList(placementConditions);

		this.placementConditions.add(condition);
	}

	public Template getTemplate()
	{
		return this.template;
	}

	protected boolean canGenerate(World world, BlockPos pos, PlacementSettings settings)
	{
		final BlockPos max = pos.add(this.template.transformedSize(settings.getRotation()).getX(), this.template.transformedSize(settings.getRotation()).getY(), this.template.transformedSize(settings.getRotation()).getZ());

		if (!world.isAreaLoaded(pos, max) || max.getY() > world.getActualHeight())
		{
			return false;
		}

		List<Template.BlockInfo> info = TemplatePrimer.getBlocks(this.template);

		List<Template.BlockInfo> infoTransformed = TemplatePrimer.getBlocks(info, pos, settings, this.template);

		for (PlacementCondition condition : this.placementConditions)
		{
			if (!condition.canPlace(this.template, world, pos, infoTransformed))
			{
				return false;
			}
		}

		return true;
	}

	@Override
	public final boolean generate(World world, Random rand, BlockPos pos)
	{
		Rotation rotation = ROTATIONS[rand.nextInt(ROTATIONS.length)];

		PlacementSettings settings = new PlacementSettings().setMirror(Mirror.NONE).setRotation(rotation).setIgnoreEntities(false).setChunk(null).setReplacedBlock(null).setIgnoreStructureBlock(false);

		boolean result = this.placeTemplateWithCheck(world, pos, settings);

		if (result)
		{
			this.postGenerate(world, rand, pos, rotation);
		}

		return result;
	}

	protected void postGenerate(World world, Random rand, BlockPos pos, Rotation rotation)
	{

	}

	public boolean canPlaceTemplate(World world, BlockPos pos, PlacementSettings settings)
	{
		if (this.canGenerate(world, pos, settings))
		{
			return true;
		}

		return false;
	}

	public boolean placeTemplateWithCheck(World world, BlockPos pos, PlacementSettings settings)
	{
		if (this.canPlaceTemplate(world, pos, settings))
		{
			this.placeTemplateWithoutCheck(world, pos, settings);

			return true;
		}

		return false;
	}

	public void placeTemplateWithoutCheck(World world, BlockPos pos, PlacementSettings settings)
	{
		ITemplateProcessor processor = new BlockRotationProcessor(pos, settings);
		TemplatePrimer.populateAll(this.template, world, pos, processor, settings);
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

		boolean canPlace(Template template, World world, BlockPos placedAt, List<Template.BlockInfo> blocks);

	}

}
