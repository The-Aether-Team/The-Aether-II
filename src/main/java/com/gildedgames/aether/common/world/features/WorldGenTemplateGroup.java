package com.gildedgames.aether.common.world.features;

import com.google.common.collect.Lists;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.List;
import java.util.Random;

public class WorldGenTemplateGroup extends WorldGenerator
{

	protected static final Rotation[] ROTATIONS = Rotation.values();

	private List<WorldGenTemplate> templates;

	public WorldGenTemplateGroup(WorldGenTemplate... templates)
	{
		this.templates = Lists.newArrayList(templates);
	}

	@Override
	public boolean generate(World world, Random rand, BlockPos position)
	{
		List<WorldGenTemplate> possibleTemplates = Lists.newArrayList();

		Rotation rotation = ROTATIONS[rand.nextInt(ROTATIONS.length)];

		for (WorldGenTemplate template : this.templates)
		{
			if (template.canPlaceTemplate(world, rand, position, rotation))
			{
				possibleTemplates.add(template);
			}
		}

		if (possibleTemplates.size() > 0)
		{
			WorldGenTemplate templateToGenerate = possibleTemplates.get(rand.nextInt(possibleTemplates.size()));

			templateToGenerate.placeTemplateWithoutCheck(world, rand, position, rotation);
			templateToGenerate.postGenerate(world, rand, position, rotation);

			return true;
		}

		return false;
	}

}
