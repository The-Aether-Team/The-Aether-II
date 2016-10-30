package com.gildedgames.aether.common.world.dimensions.aether.features;

import com.gildedgames.aether.common.world.gen.IWorldGen;
import com.google.common.collect.Lists;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.structure.template.PlacementSettings;

import java.util.List;
import java.util.Random;

public class WorldGenTemplateGroup extends WorldGenerator implements IWorldGen
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
		return this.generate(world, rand, position, false);
	}

	@Override
	public boolean generate(World world, Random rand, BlockPos position, boolean centered)
	{
		Rotation rotation = ROTATIONS[rand.nextInt(ROTATIONS.length)];

		PlacementSettings settings = new PlacementSettings().setMirror(Mirror.NONE).setRotation(rotation).setIgnoreEntities(false).setChunk(null).setReplacedBlock(null).setIgnoreStructureBlock(false);

		if (this.templates.size() > 0)
		{
			WorldGenTemplate templateToGenerate = this.templates.get(rand.nextInt(this.templates.size()));

			if (centered)
			{
				BlockPos size = templateToGenerate.getTemplate().transformedSize(rotation);

				switch (rotation)
				{
				case NONE:
				default:
					position = position.add(-(size.getX() / 2.0) + 1, 0, -(size.getZ() / 2.0) + 1);
					break;
				case CLOCKWISE_90:
					position = position.add(size.getX() / 2.0, 0, -(size.getZ() / 2.0) + 1);
					break;
				case COUNTERCLOCKWISE_90:
					position = position.add(-(size.getX() / 2.0) + 1, 0, (size.getZ() / 2.0));
					break;
				case CLOCKWISE_180:
					position = position.add((size.getX() / 2.0), 0, (size.getZ() / 2.0));
					break;
				}

				if (templateToGenerate.getCenterOffsetProcessor() != null)
				{
					position = position.add(templateToGenerate.getCenterOffsetProcessor().getOffset(rotation));
				}
			}

			boolean flag = templateToGenerate.placeTemplateWithCheck(world, position, settings);

			if (flag)
			{
				templateToGenerate.postGenerate(world, rand, position, rotation);
			}

			return flag;
		}

		return false;
	}

}
