package com.gildedgames.aether.common.events.listeners.world;

import com.gildedgames.aether.common.init.DimensionsAether;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@Mod.EventBusSubscriber
public class WorldFallListener
{
	@SubscribeEvent
	public static void onLivingEntityUpdate(final LivingEvent.LivingUpdateEvent event)
	{
		final Entity entity = event.getEntity();

		if (!entity.world.isRemote)
		{
			if (entity.world.provider.getDimensionType() == DimensionsAether.AETHER && entity.posY < -10)
			{
				if (entity.isBeingRidden())
				{
					entity.removePassengers();
				}

				if (entity.isRiding())
				{
					entity.dismountRidingEntity();
				}

				entity.attackEntityFrom(DamageSource.OUT_OF_WORLD, 200.0F);

				entity.setDead();
			}
		}
	}
	@SubscribeEvent
	public static void onPlayerEntityTick(final TickEvent.PlayerTickEvent event)
	{
		final EntityPlayer player = event.player;

		if (!player.world.isRemote)
		{
			if (player.world.provider.getDimensionType() == DimensionsAether.AETHER && player.posY < -10)
			{
				if (player.isBeingRidden())
				{
					player.removePassengers();
				}

				if (player.isRiding())
				{
					player.dismountRidingEntity();
				}

				player.attackEntityFrom(DamageSource.OUT_OF_WORLD, 200.0F);

				if (player.getHealth() <= 0 && !player.isDead)
				{
					player.isDead = true;
				}

				player.world.playerEntities.remove(player);
				player.world.onEntityRemoved(player);
			}
		}
	}
}