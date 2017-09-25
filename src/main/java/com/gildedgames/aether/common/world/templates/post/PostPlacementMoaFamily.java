package com.gildedgames.aether.common.world.templates.post;

import com.gildedgames.aether.api.world.generation.PostPlacement;
import com.gildedgames.aether.api.world.generation.TemplateLoc;
import com.gildedgames.aether.common.entities.util.MoaNest;
import com.gildedgames.aether.common.world.util.GenUtil;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class PostPlacementMoaFamily implements PostPlacement
{
	private final BlockPos familySpawnOffset;

	public PostPlacementMoaFamily(final BlockPos familySpawnOffset)
	{
		this.familySpawnOffset = familySpawnOffset;
	}

	@Override
	public void postGenerate(final World world, final Random rand, final TemplateLoc loc)
	{
		final MoaNest nest = new MoaNest(world,
				GenUtil.rotate(loc.getPos(), loc.getPos().add(this.familySpawnOffset), loc.getSettings().getRotation()));

		nest.spawnMoaFamily(world, 2 + rand.nextInt(2), 3);
	}
}
