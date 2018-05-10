package com.gildedgames.aether.client;

import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.aether.common.network.packets.PacketSpecialMovement;
import com.gildedgames.aether.common.util.helpers.AetherHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(Side.CLIENT)
public class SpecialMovesEventsClient
{

	private static long sneakKeyDownTimeStamp;

	private static int sneakTimeRequired = 300;

	private static KeyBinding lastKeybind;

	@SubscribeEvent
	public static void onEvent(EntityViewRenderEvent.FOVModifier event)
	{
		PlayerAether playerAether = PlayerAether.getPlayer(event.getEntity());

		if (playerAether != null)
		{
			if (playerAether.getRollMovementModule().isRolling())
			{
				int ticks = playerAether.getRollMovementModule().getTicksRolling();
				float rollingPercent = Math.min(1.0F, ((float)ticks + (float)event.getRenderPartialTicks())/ 10.0F);

				float fovMod = 12.5F;

				if (rollingPercent <= 0.5F)
				{
					float percent = rollingPercent * 2.0F;

					event.setFOV(event.getFOV() - (percent * fovMod));
				}
				else
				{
					float percent = (rollingPercent - 0.5F) * 2.0F;

					event.setFOV(event.getFOV() - fovMod + (percent * fovMod));
				}
			}
		}
	}

	@SubscribeEvent
	public static void onEvent(EntityViewRenderEvent.CameraSetup event)
	{
		PlayerAether playerAether = PlayerAether.getPlayer(event.getEntity());

		if (playerAether != null)
		{
			if (playerAether.getRollMovementModule().isRolling())
			{
				int ticks = playerAether.getRollMovementModule().getTicksRolling();
				float rollingPercent = Math.min(1.0F, ((float)ticks + (float)event.getRenderPartialTicks())/ 10.0F);

				float heightLower = 2.0F;

				if (rollingPercent <= 0.5F)
				{
					GlStateManager.translate(0.0F, rollingPercent * heightLower, 0.0F);
				}
				else
				{
					GlStateManager.translate(0.0F, (0.5 - (rollingPercent - 0.5F)) * heightLower, 0.0F);
				}
			}
		}
	}

	@SubscribeEvent
	public static void onClientTick(final TickEvent.ClientTickEvent event)
	{
		if (event.phase != TickEvent.Phase.END)
		{
			return;
		}

		final Minecraft mc = FMLClientHandler.instance().getClient();
		long time = System.currentTimeMillis();

		if (mc.player == null)
		{
			return;
		}

		if (!AetherHelper.isEnabled(mc.world))
		{
			return;
		}

		PlayerAether playerAether = PlayerAether.getPlayer(mc.player);

		boolean forward = mc.gameSettings.keyBindForward.isPressed();
		boolean back = mc.gameSettings.keyBindBack.isPressed();
		boolean left = mc.gameSettings.keyBindLeft.isPressed();
		boolean right = mc.gameSettings.keyBindRight.isPressed();

		if (forward || left || back || right)
		{
			if (time - sneakKeyDownTimeStamp < sneakTimeRequired)
			{
				if (!playerAether.getRollMovementModule().isRolling())
				{
					PacketSpecialMovement.Action action = null;

					if (forward && lastKeybind == mc.gameSettings.keyBindForward)
					{
						action = PacketSpecialMovement.Action.ROLL_FORWARD;
					}
					else if (back && lastKeybind == mc.gameSettings.keyBindBack)
					{
						action = PacketSpecialMovement.Action.ROLL_BACK;
					}
					else if (left && lastKeybind == mc.gameSettings.keyBindLeft)
					{
						action = PacketSpecialMovement.Action.ROLL_LEFT;
					}
					else if (right && lastKeybind == mc.gameSettings.keyBindRight)
					{
						action = PacketSpecialMovement.Action.ROLL_RIGHT;
					}

					if (action != null)
					{
						playerAether.getRollMovementModule().startRolling(action);
						NetworkingAether.sendPacketToServer(new PacketSpecialMovement(action));

						sneakKeyDownTimeStamp = 0;
					}
				}
			}
			else
			{
				sneakKeyDownTimeStamp = System.currentTimeMillis();
			}

			if (forward)
			{
				lastKeybind = mc.gameSettings.keyBindForward;
			}
			else if (back)
			{
				lastKeybind = mc.gameSettings.keyBindBack;
			}
			else if (left)
			{
				lastKeybind = mc.gameSettings.keyBindLeft;
			}
			else if (right)
			{
				lastKeybind = mc.gameSettings.keyBindRight;
			}
		}
	}

}
