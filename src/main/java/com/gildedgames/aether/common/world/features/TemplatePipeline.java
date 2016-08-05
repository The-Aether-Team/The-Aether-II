package com.gildedgames.aether.common.world.features;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.Runnables;
import net.minecraft.block.Block;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;

import java.util.Collections;
import java.util.List;

import static net.minecraft.realms.Tezzelator.t;

public class TemplatePipeline
{

	private static class TemplateProgress
	{

		private int chunksLeft;

		private Template template;

		private BlockPos pos;

		private List<ChunkPos> chunksDone = Lists.newArrayList();

		private Runnable postConstruction;

		protected TemplateProgress(Template template, BlockPos pos)
		{
			this.pos = pos;
			this.template = template;

			int chunkX = template.getSize().getX() >> 4;
			int chunkZ = template.getSize().getZ() >> 4;

			this.chunksLeft = (chunkX * chunkZ) + chunkX + chunkZ;
		}

		protected TemplateProgress(Template template, BlockPos pos, Runnable postConstruction)
		{
			this(template, pos);

			this.postConstruction = postConstruction;
		}

		public void construct(World world, ChunkPos pos)
		{
			if (!this.chunksDone.contains(pos))
			{
				PlacementSettings settings = (new PlacementSettings()).setMirror(Mirror.NONE).setRotation(Rotation.NONE).setIgnoreEntities(false).setChunk(null).setReplacedBlock((Block) null).setIgnoreStructureBlock(false);

				this.template.addBlocksToWorld(world, this.pos, settings);

				chunksDone.add(pos);

				this.chunksLeft--;

				if (this.isFinishedConstructing())
				{
					this.postConstruction.run();
				}
			}
		}

		public boolean isFinishedConstructing()
		{
			return this.chunksLeft <= 0;
		}

	}

	private List<TemplateProgress> scheduledTemplates = Collections.synchronizedList(Lists.<TemplateProgress>newArrayList());

	public TemplatePipeline()
	{

	}

	public void constructChunk(World world, int chunkX, int chunkZ)
	{
		List<TemplateProgress> toRemove = Lists.newArrayList();

		synchronized (scheduledTemplates)
		{
			ChunkPos pos = new ChunkPos(chunkX, chunkZ);

			for (TemplateProgress template : this.scheduledTemplates)
			{
				template.construct(world, pos);

				if (template.isFinishedConstructing())
				{
					toRemove.add(template);
				}
			}
		}

		this.scheduledTemplates.removeAll(toRemove);
	}

	public void scheduleTemplate(Template template, BlockPos pos)
	{
		this.scheduleTemplate(new TemplateProgress(template, pos));
	}

	public void scheduleTemplate(Template template, BlockPos pos, Runnable postConstuction)
	{
		this.scheduleTemplate(new TemplateProgress(template, pos, postConstuction));
	}

	private void scheduleTemplate(TemplateProgress progress)
	{
		this.scheduledTemplates.add(progress);
	}

}
