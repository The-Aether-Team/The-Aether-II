package com.gildedgames.aether.common.world.features;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import com.gildedgames.aether.common.blocks.BlocksAether;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;

public class WorldGenTemplate extends WorldGenerator
{

	private Template template;

	private TemplatePipeline pipeline;

	public WorldGenTemplate(TemplatePipeline pipeline, Template template)
	{
		this.pipeline = pipeline;
		this.template = template;
	}

	public boolean canGenerate(World world, Random rand, BlockPos pos)
	{
		final BlockPos max = pos.add(this.template.getSize().getX(), this.template.getSize().getY(), this.template.getSize().getZ());

		if (max.getY() > world.getActualHeight())
		{
			return false;
		}

		for (BlockPos itPos : BlockPos.getAllInBoxMutable(pos, new BlockPos(max.getX(), pos.getY(), max.getZ())))
		{
			IBlockState state = world.getBlockState(itPos);

			if (!this.isReplaceable(world, itPos) || !state.isSideSolid(world, itPos, EnumFacing.UP))
			{
				return false;
			}
		}

		/*for (BlockPos itPos : BlockPos.getAllInBoxMutable(min, max))
		{
			if (!this.isReplaceable(world, itPos))
			{
				return false;
			}
		}*/

		for (BlockPos itPos : BlockPos.getAllInBoxMutable(pos.add(0, Math.min(max.getY(), 2), 0), max))
		{
			if (!world.isAirBlock(itPos))
			{
				return false;
			}
		}

		return true;
	}

	public boolean generate(World world, Random rand, BlockPos pos, Runnable postConstruction)
	{
		if (this.canGenerate(world, rand, pos))
		{
			//this.pipeline.scheduleTemplate(this.template, pos, postConstruction);
			PlacementSettings settings = (new PlacementSettings()).setMirror(Mirror.NONE).setRotation(Rotation.NONE).setIgnoreEntities(false).setChunk(null).setReplacedBlock(null).setIgnoreStructureBlock(false);

			this.template.addBlocksToWorld(world, pos, settings);

			postConstruction.run();

			return true;
		}

		return false;
	}

	@Override
	public boolean generate(World world, Random rand, BlockPos pos)
	{
		if (this.canGenerate(world, rand, pos))
		{
			//this.pipeline.scheduleTemplate(this.template, pos);
			PlacementSettings settings = (new PlacementSettings()).setMirror(Mirror.NONE).setRotation(Rotation.NONE).setIgnoreEntities(false).setChunk(null).setReplacedBlock(null).setIgnoreStructureBlock(false);

			this.template.addBlocksToWorld(world, pos, settings);

			return true;
		}

		return false;
	}

	protected boolean canGrowInto(Block blockType)
	{
		Material material = blockType.getDefaultState().getMaterial();
		return material == Material.AIR || material == Material.LEAVES || blockType == BlocksAether.aether_grass || blockType == BlocksAether.aether_dirt;
	}

	public boolean isReplaceable(World world, BlockPos pos)
	{
		net.minecraft.block.state.IBlockState state = world.getBlockState(pos);
		return state.getBlock().isAir(state, world, pos) || state.getBlock().isLeaves(state, world, pos) || state.getBlock().isWood(world, pos) || this.canGrowInto(state.getBlock());
	}
	
}
