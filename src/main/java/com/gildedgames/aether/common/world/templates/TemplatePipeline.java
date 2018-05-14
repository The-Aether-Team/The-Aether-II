package com.gildedgames.aether.common.world.templates;

import com.google.common.collect.Lists;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;

import java.util.Collections;
import java.util.List;

public class TemplatePipeline
{

	private final List<TemplateProgress> scheduledTemplates = Collections.synchronizedList(Lists.newArrayList());

	public TemplatePipeline()
	{

	}

	public void constructChunk(World world, int chunkX, int chunkZ)
	{
		List<TemplateProgress> toRemove = Lists.newArrayList();

		synchronized (this.scheduledTemplates)
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
				PlacementSettings settings = (new PlacementSettings()).setMirror(Mirror.NONE).setRotation(Rotation.NONE).setIgnoreEntities(false).setChunk(null)
						.setReplacedBlock(null).setIgnoreStructureBlock(false);

				this.template.addBlocksToWorld(world, this.pos, settings);

				this.chunksDone.add(pos);

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

}
