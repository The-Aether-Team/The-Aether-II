package com.gildedgames.aether.api.player.conditions.events;

import com.google.common.collect.Sets;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;
import java.util.Set;

public class SeeEntityEvents
{
	private static final int SEE_ENTITY_RADIUS = 10;

	private final Set<ISeeEntityEventsListener> listeners = Sets.newHashSet();

	public SeeEntityEvents()
	{

	}

	public void listen(final ISeeEntityEventsListener listener)
	{
		this.listeners.add(listener);
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

			final List<Entity> entitiesInSight = entity.world.getEntitiesWithinAABB(Entity.class, checkingArea);

			for (final Entity e : entitiesInSight)
			{
				if (player.canEntityBeSeen(e))
				{
					this.listeners.forEach(l -> l.onSeeEntity(e, player));
				}
			}
		}
	}

}
