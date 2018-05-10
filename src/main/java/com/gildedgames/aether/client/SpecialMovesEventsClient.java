package com.gildedgames.aether.client;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.modules.PlayerRollMovementModule;
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
		PlayerRollMovementModule module = playerAether.getRollMovementModule();

		if (playerAether != null)
		{
			if (module.isRolling())
			{
				int ticks = module.getTicksRolling();
				int ticksRequired = module.getTicksRollingMax();

				float rollingPercent = Math.min(1.0F, ((float)ticks + (float)event.getRenderPartialTicks()) / ticksRequired);

				float fovMod = (float) AetherCore.CONFIG.getRollFOV();

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

		if (playerAether == null)
		{
			return;
		}

		PlayerRollMovementModule module = playerAether.getRollMovementModule();

		if (module.isRolling())
		{
			int ticks = module.getTicksRolling();
			int ticksRequired = module.getTicksRollingMax();

			float rollingPercent = Math.min(1.0F, ((float)ticks + (float)event.getRenderPartialTicks()) / ticksRequired);

			float heightLower = (float) AetherCore.CONFIG.getRollCameraHeightLower();
			float cameraTilt = (float) AetherCore.CONFIG.getRollCameraTilt();

			float percent = rollingPercent * 2.0F;

			if (rollingPercent <= 0.5F)
			{
				event.setPitch(event.getPitch() + (percent * cameraTilt));
				GlStateManager.translate(0.0F, percent * heightLower, 0.0F);
			}
			else
			{
				percent = (rollingPercent - 0.5F) * 2.0F;

				event.setPitch(event.getPitch() + cameraTilt - (percent * cameraTilt));
				GlStateManager.translate(0.0F, heightLower - (percent * heightLower), 0.0F);
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
			if (time - sneakKeyDownTimeStamp < sneakTimeRequired && playerAether.getEntity().onGround)
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

				lastKeybind = null;
			}
			else
			{
				sneakKeyDownTimeStamp = System.currentTimeMillis();

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

}
