package com.gildedgames.aether.common.events.listeners.player;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.modules.PlayerTeleportingModule;
import com.gildedgames.aether.common.events.PostAetherTravelEvent;
import com.gildedgames.aether.common.network.AetherGuiHandler;
import com.gildedgames.aether.common.util.helpers.AetherHelper;
import com.gildedgames.orbis.lib.util.mc.BlockPosDimension;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.entity.EntityTravelToDimensionEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

@Mod.EventBusSubscriber
public class PlayerTeleportListener
{

	@SubscribeEvent
	public static void onEvent(EntityTravelToDimensionEvent event)
	{
		if (AetherHelper.isEnabled(event.getDimension()) && AetherHelper.isEnabled(event.getDimension()))
		{
			PlayerAether playerAether = PlayerAether.getPlayer((PlayerEntity) event.getEntity());

			if (!AetherHelper.isNecromancerTower(event.getEntity().dimension))
			{
				playerAether.getModule(PlayerTeleportingModule.class)
						.setNonAetherPos(new BlockPosDimension(event.getEntity().getPosition(), event.getEntity().dimension));
			}
		}
	}

	@SubscribeEvent
	public static void onEvent(PostAetherTravelEvent event)
	{
		if (!event.getEntity().getEntityWorld().isRemote)
		{
			if (event.getEntity() instanceof PlayerEntity)
			{
				PlayerEntity player = (PlayerEntity) event.getEntity();

				player.openGui(AetherCore.INSTANCE, AetherGuiHandler.AETHER_LOADING_ID, player.getEntityWorld(), player.getPosition().getX(),
						player.getPosition().getY(), player.getPosition().getZ());
			}
		}
	}
}
