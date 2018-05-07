package com.gildedgames.aether.client.renderer;

import com.gildedgames.aether.client.gui.overlays.IOverlay;
import com.gildedgames.aether.client.gui.overlays.PortalOverlay;
import com.gildedgames.aether.client.gui.overlays.SwetOverlay;
import com.gildedgames.aether.client.models.entities.player.LayerHeadShadow;
import com.gildedgames.aether.client.models.entities.player.LayerAetherPlayerGloves;
import com.gildedgames.aether.client.models.entities.player.LayerSwetLatch;
import com.gildedgames.aether.client.renderer.entities.living.RenderPlayerHelper;
import com.gildedgames.aether.client.models.entities.player.LayerArmorProxy;
import com.gildedgames.aether.client.models.entities.player.LayerAetherPatronArmor;
import com.gildedgames.aether.common.ReflectionAether;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.util.EnumHand;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderSpecificHandEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ClientRenderHandler
{
	private static final List<IOverlay> overlays = Lists.newArrayList();

	public ClientRenderHandler()
	{
		for (RenderLivingBase<?> playerRender: new HashSet<>(Minecraft.getMinecraft().getRenderManager().getSkinMap().values()))
		{
			Field field = ReflectionAether.getField(RenderLivingBase.class, ReflectionAether.ENTITY_RENDER_LAYERS.getMappings());

			List<LayerRenderer<?>> original = new ArrayList<>(ReflectionAether.getValue(field, playerRender));
			List<LayerRenderer<?>> updated = new ArrayList<>();

			for (LayerRenderer<?> i : original)
			{
				if (i instanceof LayerBipedArmor)
				{
					updated.add(new LayerArmorProxy(playerRender, (LayerBipedArmor) i));
				}
				else
				{
					updated.add(i);
				}
			}

			updated.add(new LayerAetherPlayerGloves(playerRender));
			updated.add(new LayerHeadShadow(playerRender));
			updated.add(new LayerSwetLatch(playerRender));
			updated.add(new LayerAetherPatronArmor(playerRender));

			ReflectionAether.setField(field, playerRender, updated);
		}

		ClientRenderHandler.overlays.add(new PortalOverlay());
		ClientRenderHandler.overlays.add(new SwetOverlay());
	}

	@SubscribeEvent
	public void onRenderIngameOverlay(final RenderGameOverlayEvent.Pre event)
	{
		if (event.getType() == RenderGameOverlayEvent.ElementType.AIR)
		{
			final EntityPlayer player = Minecraft.getMinecraft().player;

			if (player.getAir() == 300 && player.isPotionActive(MobEffects.WATER_BREATHING))
			{
				event.setCanceled(true);
			}
		}

		if (event.getType() == RenderGameOverlayEvent.ElementType.ALL)
		{
			for (final IOverlay overlay : ClientRenderHandler.overlays)
			{
				if (overlay.isEnabled())
				{
					overlay.draw();
				}
			}
		}
	}

	@SubscribeEvent
	public void onRenderSpecificHandEvent(final RenderSpecificHandEvent event)
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
