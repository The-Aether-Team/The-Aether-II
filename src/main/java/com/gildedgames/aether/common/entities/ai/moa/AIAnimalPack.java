package com.gildedgames.aether.common.entities.ai.moa;

import com.gildedgames.aether.common.entities.util.groups.EntityGroupMember;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.pathfinding.Path;
import net.minecraft.world.World;

import java.util.List;

public class AIAnimalPack extends EntityAIBase
{

	public final World world;

	public EntityLiving animal;

	public EntityLiving packLeader;

	public final float moveSpeed;

	public AIAnimalPack(final EntityLiving animal, final float moveSpeed)
	{
		this.world = animal.world;
		this.moveSpeed = moveSpeed;

		if (animal instanceof EntityGroupMember)
		{
			this.animal = animal;
		}
	}

	@Override
	public boolean shouldExecute()
	{
		if (this.animal == null || this.world == null)
		{
			return false;
		}

		final List list = this.animal.world.getEntitiesWithinAABB(this.animal.getClass(), this.animal.getEntityBoundingBox().expand(12.0D, 4.0D, 12.0D));
		EntityAnimal potentialLeader = null;
		double d0 = Double.MAX_VALUE;

		for (final Object aList : list)
		{
			final EntityAnimal animal = (EntityAnimal) aList;

			if (animal instanceof EntityGroupMember)
			{
				final EntityGroupMember packAnimal = (EntityGroupMember) animal;
				final EntityGroupMember ourPackAnimal = (EntityGroupMember) this.animal;

				if (packAnimal.isGroupLeader() && !ourPackAnimal.isGroupLeader() && packAnimal.getGroup() == ourPackAnimal.getGroup())
				{
					final double d1 = this.animal.getDistance(animal);//Find the male closest to this animal from the same pack

					if (d1 <= d0)
					{
						d0 = d1;
						potentialLeader = animal;
					}
				}
			}
		}

		if (potentialLeader == null)
		{
			return false;
		}
		else if (d0 < 6.0D)
		{
			return false;
		}
		else
		{
			this.packLeader = potentialLeader;
			return true;
		}
	}

	@Override
	public void updateTask()
	{
		super.updateTask();

		final Path path = this.animal.getNavigator().getPathToXYZ(this.packLeader.posX, this.packLeader.posY, this.packLeader.posZ);

		if (this.animal.getDistance(this.packLeader) > 6)
		{
			this.animal.getNavigator().setPath(path, this.moveSpeed);
		}
		else if (this.animal.getNavigator().getPath() == path)
		{
			this.animal.getNavigator().clearPath();
		}
	}

}
