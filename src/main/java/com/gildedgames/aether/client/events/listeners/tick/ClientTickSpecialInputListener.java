package com.gildedgames.aether.client.events.listeners.tick;

import com.gildedgames.aether.api.registrar.SoundsAether;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.modules.PlayerAbilitiesModule;
import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.aether.common.network.packets.PacketSpecialMovement;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientTickSpecialInputListener
{
	private static boolean PREV_JUMP_BIND_STATE;

	@SubscribeEvent
	public static void onClientTick(final TickEvent.ClientTickEvent event)
	{
		if (event.phase != TickEvent.Phase.END)
		{
			return;
		}

		final Minecraft mc = FMLClientHandler.instance().getClient();

		final World world = FMLClientHandler.instance().getWorldClient();

		final EntityPlayerSP player = FMLClientHandler.instance().getClientPlayerEntity();

		if (world != null && player != null)
		{
			final PlayerAether aePlayer = PlayerAether.getPlayer(player);

			PlayerAbilitiesModule abilitiesModule = aePlayer.getModule(PlayerAbilitiesModule.class);

			if (abilitiesModule.getMidAirJumpsAllowed() > 0)
			{
				if (mc.gameSettings.keyBindJump.isKeyDown() && !PREV_JUMP_BIND_STATE)
				{
					if (!player.isInWater() && abilitiesModule.getTicksAirborne() > 2 && !player.capabilities.isCreativeMode)
					{
						if (abilitiesModule.performMidAirJump())
						{
							NetworkingAether.sendPacketToServer(new PacketSpecialMovement(PacketSpecialMovement.Action.EXTRA_JUMP));

							world.playSound(player.posX, player.posY, player.posZ, SoundsAether.generic_wing_flap, SoundCategory.PLAYERS, 0.4f,
									0.8f + (world.rand.nextFloat() * 0.6f), false);
						}
					}
				}
			}

			PREV_JUMP_BIND_STATE = mc.gameSettings.keyBindJump.isKeyDown();
		}
	}
}
