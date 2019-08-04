package com.gildedgames.aether.api.player.conditions.events;

import com.google.common.collect.Lists;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

public class SeeEntityEvents
{
	private static final int SEE_ENTITY_RADIUS = 10;

	private final List<ISeeEntityEventsListener> listeners = Lists.newArrayList();

	public SeeEntityEvents()
	{

	}

	public void listen(final ISeeEntityEventsListener listener)
	{
		if (!this.listeners.contains(listener))
		{
			this.listeners.add(listener);
		}
	}

	public void unlisten(final ISeeEntityEventsListener listener)
	{
		this.listeners.remove(listener);
	}

	@SubscribeEvent
	public void onLivingEntityUpdate(final LivingEvent.LivingUpdateEvent event)
	{
		final Entity entity = event.getEntity();

		if (!entity.world.isRemote && entity instanceof EntityPlayer)
		{
			final EntityPlayer player = ((EntityPlayer) entity);

			final double minX = entity.posX - SEE_ENTITY_RADIUS;
			final double minY = entity.posY - SEE_ENTITY_RADIUS;
			final double minZ = entity.posZ - SEE_ENTITY_RADIUS;

			final double maxX = entity.posX + SEE_ENTITY_RADIUS;
			final double maxY = entity.posY + SEE_ENTITY_RADIUS;
			final double maxZ = entity.posZ + SEE_ENTITY_RADIUS;

			final AxisAlignedBB checkingArea = new AxisAlignedBB(minX, minY, minZ, maxX, maxY, maxZ);

			final List<EntityLivingBase> entitiesInSight = entity.world.getEntitiesWithinAABB(EntityLivingBase.class, checkingArea);

			for (final EntityLivingBase e : entitiesInSight)
			{
				if (player.canEntityBeSeen(e))
				{
					for (final ISeeEntityEventsListener l : this.listeners)
					{
						l.onSeeEntity(e, player);
					}
				}
			}
		}
	}

}
