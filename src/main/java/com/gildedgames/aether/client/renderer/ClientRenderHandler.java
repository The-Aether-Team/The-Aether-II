package com.gildedgames.aether.client.renderer;

import com.gildedgames.aether.client.models.entities.player.LayerPlayerGloves;
import com.gildedgames.aether.client.renderer.entities.living.RenderPlayerHelper;
import com.gildedgames.aether.common.items.ItemsAether;
import com.gildedgames.aether.common.items.armor.ItemNeptuneArmor;
import com.gildedgames.aether.common.items.armor.ItemPhoenixArmor;
import com.gildedgames.aether.common.player.PlayerAether;
import com.gildedgames.aether.common.util.PlayerUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ClientRenderHandler
{
	public static void init()
	{
		RenderLivingBase<?> playerRender = Minecraft.getMinecraft().getRenderManager().getSkinMap().get("default");
		playerRender.addLayer(new LayerPlayerGloves(playerRender));

		playerRender = Minecraft.getMinecraft().getRenderManager().getSkinMap().get("slim");
		playerRender.addLayer(new LayerPlayerGloves(playerRender));
	}

	@SubscribeEvent
	public void onRenderIngameOverlay(RenderGameOverlayEvent.Pre event)
	{
		if (event.getType() == RenderGameOverlayEvent.ElementType.AIR)
		{
			EntityPlayer player = Minecraft.getMinecraft().thePlayer;

			PlayerAether aePlayer = PlayerAether.getPlayer(player);

			if (player.getAir() >= 295 && PlayerUtil.isWearingEquipment(aePlayer, ItemsAether.neptune_armor_set))
			{
				event.setCanceled(true);
			}
		}
	}

	@SubscribeEvent
	public void onRenderBlockOverlay(RenderBlockOverlayEvent event)
	{
		PlayerAether aePlayer = PlayerAether.getPlayer(event.getPlayer());

		if (event.getOverlayType() == RenderBlockOverlayEvent.OverlayType.WATER)
		{
			if (PlayerUtil.isWearingEquipment(aePlayer, ItemsAether.neptune_armor_set))
			{
				event.setCanceled(true);
			}
		}
		else if (event.getOverlayType() == RenderBlockOverlayEvent.OverlayType.FIRE)
		{
			if (PlayerUtil.isWearingEquipment(aePlayer, ItemsAether.phoenix_armor_set))
			{
				event.setCanceled(true);
			}
		}
	}

	@SubscribeEvent
	public void onRenderPlayerEvent(RenderPlayerEvent.Post event)
	{
		RenderPlayerHelper.renderFirstPersonHand(PlayerAether.getPlayer(Minecraft.getMinecraft().thePlayer), event.getPartialRenderTick());
	}
}
