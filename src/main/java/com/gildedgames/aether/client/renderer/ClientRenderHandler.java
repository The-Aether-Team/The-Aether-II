package com.gildedgames.aether.client.renderer;

import com.gildedgames.aether.client.models.entities.player.LayerPlayerGloves;
import com.gildedgames.aether.client.renderer.entities.living.RenderPlayerHelper;
import com.gildedgames.aether.common.capabilities.player.PlayerAether;
import com.gildedgames.aether.common.items.ItemsAether;
import com.gildedgames.aether.common.util.helpers.PlayerUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderSpecificHandEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

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
			EntityPlayer player = Minecraft.getMinecraft().player;

			if (player.getAir() == 300 && player.isPotionActive(MobEffects.WATER_BREATHING))
			{
				event.setCanceled(true);
			}
		}
	}

	@SubscribeEvent
	public void onRenderBlockOverlay(RenderBlockOverlayEvent event)
	{
		EntityPlayer entity = event.getPlayer();

		PlayerAether aePlayer = PlayerAether.getPlayer(event.getPlayer());

		if (event.getOverlayType() == RenderBlockOverlayEvent.OverlayType.WATER)
		{
			if (entity.getAir() >= 295 && PlayerUtil.isWearingEquipment(aePlayer, ItemsAether.iron_bubble))
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
	public void onRenderSpecificHandEvent(RenderSpecificHandEvent event)
	{
		if (event.getHand() == EnumHand.MAIN_HAND && event.getItemStack().isEmpty())
		{
			GlStateManager.pushMatrix();

			//GlStateManager.rotate(event.getInterpolatedPitch(), 1f, 0f, 0f);

			RenderPlayerHelper.renderFirstPersonHand(event, PlayerAether.getPlayer(Minecraft.getMinecraft().player));

			GlStateManager.popMatrix();
		}
	}

}
