package com.gildedgames.aether.client;

import com.gildedgames.aether.client.sound.AetherMusicManager;
import com.gildedgames.aether.common.containers.slots.SlotEquipment;
import com.gildedgames.aether.common.items.armor.ItemGravititeArmor;
import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.aether.common.network.packets.AetherMovementPacket;
import com.gildedgames.aether.common.player.PlayerAether;
import com.gildedgames.aether.api.player.IPlayerAetherCapability;
import com.gildedgames.aether.common.items.armor.ItemObsidianArmor;
import com.gildedgames.aether.common.util.PlayerUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;

public class ClientEventHandler
{
	private boolean prevJumpBindState;

	@SubscribeEvent
	public void onClientTick(ClientTickEvent event)
	{
		if (event.phase != TickEvent.Phase.END)
		{
			return;
		}

		EntityPlayerSP player = FMLClientHandler.instance().getClientPlayerEntity();

		if (player != null)
		{
			Minecraft mc = Minecraft.getMinecraft();

			IPlayerAetherCapability aePlayer = PlayerAether.getPlayer(player);

			Class<? extends Item> armorSet = PlayerUtil.findArmorSet(player);

			if (armorSet == ItemObsidianArmor.class)
			{
				KeyBinding.setKeyBindState(mc.gameSettings.keyBindSprint.getKeyCode(), false);
			}
			else if (armorSet == ItemGravititeArmor.class)
			{
				if (aePlayer.getTicksAirborne() > 2 && mc.gameSettings.keyBindJump.isKeyDown() && !this.prevJumpBindState)
				{
					if (aePlayer.performDoubleJump())
					{
						NetworkingAether.sendPacketToServer(new AetherMovementPacket(AetherMovementPacket.Action.EXTRA_JUMP));
					}
				}
			}

			this.prevJumpBindState = mc.gameSettings.keyBindJump.isKeyDown();

			if (aePlayer != null)
			{
				AetherMusicManager.INSTANCE.update(aePlayer);
			}
		}
	}

	@SubscribeEvent
	public void onWorldLoad(WorldEvent.Load event)
	{
		if (event.world instanceof WorldClient)
		{
			Minecraft mc = Minecraft.getMinecraft();

			if (!(mc.playerController instanceof PlayerControllerAetherMP))
			{
				Minecraft.getMinecraft().playerController = PlayerControllerAetherMP.create(mc.playerController);
			}

			ClientProxy.clientPlayerController = (PlayerControllerAetherMP) mc.playerController;
		}
	}

	@SubscribeEvent
	public void onTextureStitchPre(TextureStitchEvent.Pre event)
	{
		SlotEquipment.registerIcons(event);
	}
}
