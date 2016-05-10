package com.gildedgames.aether.client;

import com.gildedgames.aether.client.sound.AetherMusicManager;
import com.gildedgames.aether.common.containers.slots.SlotEquipment;
import com.gildedgames.aether.common.entities.player.PlayerAether;
import com.gildedgames.aether.common.entities.player.PlayerAetherBase;
import com.gildedgames.aether.common.items.armor.ItemObsidianArmor;
import com.gildedgames.aether.common.util.PlayerUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;

public class ClientEventHandler
{
	@SubscribeEvent
	public void onClientTick(ClientTickEvent event)
	{
		EntityPlayerSP player = FMLClientHandler.instance().getClientPlayerEntity();

		if (player != null)
		{
			if (PlayerUtil.isWearingFullSet(player, ItemObsidianArmor.class))
			{
				KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindSprint.getKeyCode(), false);
			}

			PlayerAetherBase aePlayer = PlayerAether.getPlayer(player);

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
