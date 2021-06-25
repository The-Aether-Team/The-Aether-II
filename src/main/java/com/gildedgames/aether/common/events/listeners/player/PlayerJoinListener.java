package com.gildedgames.aether.common.events.listeners.player;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.aether.common.network.packets.PacketRequestClientInfo;
import com.google.common.collect.Lists;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

@Mod.EventBusSubscriber
public class PlayerJoinListener
{
	private static final ResourceLocation TELEPORTER_RECIPE = AetherCore.getResource("misc/aether_teleporter");

	@SubscribeEvent
	public static void onPlayerJoined(final PlayerEvent.PlayerLoggedInEvent event)
	{
		NetworkingAether.sendPacketToPlayer(new PacketRequestClientInfo(), (EntityPlayerMP) event.player);

		final PlayerAether aePlayer = PlayerAether.getPlayer(event.player);
		aePlayer.onLoggedIn();
		aePlayer.sendFullUpdate();

		IRecipe recipe = ForgeRegistries.RECIPES.getValue(TELEPORTER_RECIPE);

		if (recipe != null)
		{
			event.player.unlockRecipes(Lists.newArrayList(recipe));
		}
	}
}
