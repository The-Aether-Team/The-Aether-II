package com.gildedgames.aether.common.world.features;

import com.gildedgames.aether.common.entities.util.MoaNest;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.template.Template;

import java.util.Random;

public class WorldGenMoaNest extends WorldGenTemplate
{
	public WorldGenMoaNest(TemplatePipeline pipeline, Template template)
	{
		super(pipeline, template);
	}

	@Override
	public boolean generate(World world, Random random, BlockPos pos)
	{
		boolean result = super.generate(world, random, pos);

		if (result)
		{
			MoaNest nest = new MoaNest(world, pos);

			nest.spawnMoaFamily(world, 2 + world.rand.nextInt(4));
		}

		return result;
	}
}
