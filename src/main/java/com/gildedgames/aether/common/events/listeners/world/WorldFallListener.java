package com.gildedgames.aether.common.events.listeners.world;

import com.gildedgames.aether.common.init.DimensionsAether;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class WorldFallListener
{
	@SubscribeEvent
	public static void onLivingEntityUpdate(final LivingEvent.LivingUpdateEvent event)
	{
		final Entity entity = event.getEntity();

		// Checks whether or not an entity is in the Aether's void
		if (entity.world.provider.getDimensionType() == DimensionsAether.AETHER && entity.posY < -10)
		{
			if (!entity.world.isRemote)
			{
				if (entity instanceof EntityPlayer)
				{
					final EntityPlayer player = (EntityPlayer) entity;

					player.attackEntityFrom(DamageSource.OUT_OF_WORLD, 200.0F);

					if (player.getHealth() <= 0 && !player.isDead)
					{
						player.isDead = true;
					}
				}
				else
				{
					if (!entity.getPassengers().isEmpty())
					{
						entity.removePassengers();
					}
					else
					{
						entity.setDead();
					}
				}
			}
		}
	}
}
