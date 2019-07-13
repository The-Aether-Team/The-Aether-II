package com.gildedgames.aether.common.world.templates.post;

import com.gildedgames.aether.api.world.templates.PostPlacementTemplate;
import com.gildedgames.aether.api.world.templates.TemplateLoc;
import com.gildedgames.aether.common.world.util.GenUtil;
import net.minecraft.entity.MobEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;
import java.util.function.Function;

public class PostPlacementSpawnEntity implements PostPlacementTemplate
{
	private final Function<World, MobEntity> entity;

	private final BlockPos offset;

	public PostPlacementSpawnEntity(final Function<World, MobEntity> entity, final BlockPos offset)
	{
		this.entity = entity;
		this.offset = offset;
	}

	@Override
	public void postGenerate(final World world, final Random rand, final TemplateLoc loc)
	{
		if (world.isRemote())
		{
			return;
		}

		final BlockPos spawnAt = GenUtil.rotate(loc.getPos(), loc.getPos().add(this.offset), loc.getSettings().getRotation());

		final MobEntity entity = this.entity.apply(world);

		entity.setPositionAndUpdate(spawnAt.getX(), spawnAt.getY(), spawnAt.getZ());

		world.spawnEntity(entity);
	}
}
