package com.gildedgames.aether.client;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.modules.PlayerRollMovementModule;
import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.aether.common.network.packets.PacketSpecialMovement;
import com.gildedgames.aether.common.util.helpers.AetherHelper;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;
import org.lwjgl.input.Keyboard;

@Mod.EventBusSubscriber(Side.CLIENT)
public class SpecialMovesEventsClient
{

	private static long sneakKeyDownTimeStamp, rollKeyDownTimeStamp;

	private static int sneakTimeRequired = 300, maxRollHold = 300;

	private static int lastKey;

	@SubscribeEvent
	public static void onEvent(EntityViewRenderEvent.FOVModifier event)
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

			float rollingPercent = Math.min(1.0F, ((float) ticks + (float) event.getRenderPartialTicks()) / ticksRequired);

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

			float rollingPercent = Math.min(1.0F, ((float) ticks + (float) event.getRenderPartialTicks()) / ticksRequired);

			float cameraTilt = (float) AetherCore.CONFIG.getRollCameraTilt();

			float percent = rollingPercent * 2.0F;

			if (rollingPercent <= 0.5F)
			{
				event.setPitch(event.getPitch() + (percent * cameraTilt));
			}
			else
			{
				percent = (rollingPercent - 0.5F) * 2.0F;

				event.setPitch(event.getPitch() + cameraTilt - (percent * cameraTilt));
			}
		}
	}

	@SubscribeEvent
	public static void onClientTick(final InputEvent.KeyInputEvent event)
	{
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

		Keyboard.enableRepeatEvents(false);

		int key = Keyboard.getEventKey();

		if (!Keyboard.getEventKeyState())
		{
			if (rollKeyDownTimeStamp > 0 && time - rollKeyDownTimeStamp < maxRollHold && playerAether.getEntity().onGround)
			{
				PacketSpecialMovement.Action action = null;

				if (lastKey == mc.gameSettings.keyBindForward.getKeyCode())
				{
					action = PacketSpecialMovement.Action.ROLL_FORWARD;
				}
				else if (lastKey == mc.gameSettings.keyBindBack.getKeyCode())
				{
					action = PacketSpecialMovement.Action.ROLL_BACK;
				}
				else if (lastKey == mc.gameSettings.keyBindLeft.getKeyCode())
				{
					action = PacketSpecialMovement.Action.ROLL_LEFT;
				}
				else if (lastKey == mc.gameSettings.keyBindRight.getKeyCode())
				{
					action = PacketSpecialMovement.Action.ROLL_RIGHT;
				}

				if (action != null)
				{
					playerAether.getRollMovementModule().startRolling(action);
					NetworkingAether.sendPacketToServer(new PacketSpecialMovement(action));

					sneakKeyDownTimeStamp = 0;
				}

				lastKey = Keyboard.KEY_NONE;
			}
			else
			{
				rollKeyDownTimeStamp = 0;
			}

			return;
		}

		boolean forward = key == mc.gameSettings.keyBindForward.getKeyCode();
		boolean back = key == mc.gameSettings.keyBindBack.getKeyCode();
		boolean left = key == mc.gameSettings.keyBindLeft.getKeyCode();
		boolean right = key == mc.gameSettings.keyBindRight.getKeyCode();

		if (forward || left || back || right)
		{
			if (!playerAether.getRollMovementModule().isRolling() && key == lastKey && time - sneakKeyDownTimeStamp < sneakTimeRequired)
			{
				rollKeyDownTimeStamp = System.currentTimeMillis();
			}

			if (time - sneakKeyDownTimeStamp >= sneakTimeRequired)
			{
				sneakKeyDownTimeStamp = System.currentTimeMillis();

				if (forward)
				{
					lastKey = mc.gameSettings.keyBindForward.getKeyCode();
				}
				else if (back)
				{
					lastKey = mc.gameSettings.keyBindBack.getKeyCode();
				}
				else if (left)
				{
					lastKey = mc.gameSettings.keyBindLeft.getKeyCode();
				}
				else
				{
					lastKey = mc.gameSettings.keyBindRight.getKeyCode();
				}
			}
		}
	}
}
