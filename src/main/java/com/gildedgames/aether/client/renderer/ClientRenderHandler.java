package com.gildedgames.aether.client.renderer;

import com.gildedgames.aether.api.capabilites.entity.effects.IEntityEffectsCapability;
import com.gildedgames.aether.client.models.entities.player.LayerPlayerGloves;
import com.gildedgames.aether.client.renderer.entities.living.RenderPlayerHelper;
import com.gildedgames.aether.common.capabilities.entity.effects.EntityEffects;
import com.gildedgames.aether.common.capabilities.player.PlayerAetherImpl;
import com.gildedgames.aether.common.items.ItemsAether;
import com.gildedgames.aether.common.util.PlayerUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.util.EnumHand;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.event.RenderSpecificHandEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static sun.audio.AudioPlayer.player;

public class ClientRenderHandler
{
	public ClientRenderHandler()
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

			PlayerAetherImpl aePlayer = PlayerAetherImpl.getPlayer(player);
			IEntityEffectsCapability effects = EntityEffects.get(player);

			if (player.getAir() == 300 && player.isPotionActive(MobEffects.WATER_BREATHING))
			{
				event.setCanceled(true);
			}
		}
	}

	@SubscribeEvent
	public void onRenderBlockOverlay(RenderBlockOverlayEvent event)
	{
		EntityPlayer player = event.getPlayer();
		PlayerAetherImpl aePlayer = PlayerAetherImpl.getPlayer(event.getPlayer());

		if (event.getOverlayType() == RenderBlockOverlayEvent.OverlayType.WATER)
		{
			if (player.getAir() >= 295 && PlayerUtil.isWearingEquipment(aePlayer, ItemsAether.iron_bubble))
			{
				event.setCanceled(true);
			}
		}
		else if (event.getOverlayType() == RenderBlockOverlayEvent.OverlayType.FIRE)
		{
			if (PlayerUtil.isWearingEquipment(aePlayer, ItemsAether.phoenix_rune))
			{
				event.setCanceled(true);
			}
		}
	}

	@SubscribeEvent
	public void onRenderPlayerEvent(RenderPlayerEvent.Post event)
	{
		//RenderPlayerHelper.renderFirstPersonHand(PlayerAether.getPlayer(Minecraft.getMinecraft().thePlayer), event.getPartialRenderTick());
	}

	@SubscribeEvent
	public void onRenderSpecificHandEvent(RenderSpecificHandEvent event)
	{
		if (event.getHand() == EnumHand.MAIN_HAND && event.getItemStack() == null)
		{
			GlStateManager.pushMatrix();

			//GlStateManager.rotate(event.getInterpolatedPitch(), 1f, 0f, 0f);

			RenderPlayerHelper.renderFirstPersonHand(event, PlayerAetherImpl.getPlayer(Minecraft.getMinecraft().thePlayer));

			GlStateManager.popMatrix();
		}
	}

}
