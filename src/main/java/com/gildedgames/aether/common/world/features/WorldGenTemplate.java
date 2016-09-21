package com.gildedgames.aether.common.world.features;

import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.util.TemplatePrimer;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;

import java.util.List;
import java.util.Random;

import static java.awt.SystemColor.info;

public class WorldGenTemplate extends WorldGenerator
{

	private Template template;

	private TemplatePipeline pipeline;

	public WorldGenTemplate(TemplatePipeline pipeline, Template template)
	{
		this.pipeline = pipeline;
		this.template = template;
	}

	public boolean canGenerate(World world, Random rand, BlockPos pos, PlacementSettings settings)
	{
		final BlockPos max = pos.add(this.template.getSize().getX(), this.template.getSize().getY(), this.template.getSize().getZ());

		if (max.getY() > world.getActualHeight())
		{
			return false;
		}

		List<Template.BlockInfo> info = TemplatePrimer.getBlocks(this.template);

		List<Template.BlockInfo> infoTransformed = TemplatePrimer.getBlocks(info, pos, settings, this.template);

		for (Template.BlockInfo block : infoTransformed)
		{
			if (block.pos.getY() == pos.getY() && block.blockState != Blocks.AIR.getDefaultState() && block.blockState != Blocks.STRUCTURE_VOID.getDefaultState())
			{
				BlockPos down = block.pos.down();
				IBlockState state = world.getBlockState(down);

				if (state.getBlock() != BlocksAether.aether_grass)
				{
					return false;
				}
			}
		}

		for (Template.BlockInfo block : infoTransformed)
		{
			if (block.blockState != Blocks.AIR.getDefaultState())
			{
				if (!this.isReplaceable(world, block.pos))// || !state.isSideSolid(world, itPos, EnumFacing.UP))
				{
					return false;
				}
			}
		}

		return true;
	}

	public boolean generate(World world, Random rand, BlockPos pos, Runnable postConstruction)
	{
		PlacementSettings settings = (new PlacementSettings()).setMirror(Mirror.NONE).setRotation(Rotation.NONE).setIgnoreEntities(false).setChunk(null).setReplacedBlock(null).setIgnoreStructureBlock(false);

		if (this.canGenerate(world, rand, pos, settings))
		{
			this.template.addBlocksToWorld(world, pos, settings);

			postConstruction.run();

			return true;
		}

		return false;
	}

	public boolean generate(World world, Random rand, BlockPos pos, Rotation rotation)
	{
		PlacementSettings settings = (new PlacementSettings()).setMirror(Mirror.NONE).setRotation(rotation).setIgnoreEntities(false).setChunk(null).setReplacedBlock(null).setIgnoreStructureBlock(false);

		if (this.canGenerate(world, rand, pos, settings))
		{
			this.template.addBlocksToWorld(world, pos, settings);

			return true;
		}

		return false;
	}

	@Override
	public boolean generate(World world, Random rand, BlockPos pos)
	{
		return this.generate(world, rand, pos, Rotation.NONE);
	}

	protected boolean canGrowInto(Block blockType)
	{
		Material material = blockType.getDefaultState().getMaterial();
		return material == Material.AIR || material == Material.LEAVES || blockType == BlocksAether.aether_grass || blockType == BlocksAether.aether_dirt;
	}

	public boolean isReplaceable(World world, BlockPos pos)
	{
		net.minecraft.block.state.IBlockState state = world.getBlockState(pos);
		return state.getBlock().isAir(state, world, pos) || state.getBlock().isLeaves(state, world, pos) || this.canGrowInto(state.getBlock());
	}
	
}
