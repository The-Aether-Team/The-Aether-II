package com.gildedgames.aether.common.world.dimensions.aether.features;

import com.gildedgames.aether.common.entities.util.MoaNest;
import com.gildedgames.aether.common.world.GenUtil;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.template.Template;

import java.util.Random;

public class WorldGenMoaNest extends WorldGenTemplate
{

	private BlockPos familySpawnOffset;

	public WorldGenMoaNest(Template template, BlockPos familySpawnOffset, PlacementCondition condition,
			PlacementCondition... placementConditions)
	{
		super(template, condition, placementConditions);

		this.familySpawnOffset = familySpawnOffset;
	}

	@Override
	public void postGenerate(World world, Random random, BlockPos pos, Rotation rotation)
	{
		MoaNest nest = new MoaNest(world, GenUtil.rotate(pos, pos.add(this.familySpawnOffset), rotation));

		nest.spawnMoaFamily(world, 2 + random.nextInt(2), 3);
	}
}
