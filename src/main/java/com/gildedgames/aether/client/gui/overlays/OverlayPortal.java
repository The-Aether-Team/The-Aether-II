package com.gildedgames.aether.client.gui.overlays;

import com.gildedgames.aether.api.registrar.BlocksAether;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.modules.PlayerTeleportingModule;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.potion.Effects;
import net.minecraft.util.Timer;

public class OverlayPortal implements IOverlay
{

	private final Timer timer = new Timer(20.0F, 0L);

	private final Minecraft mc = Minecraft.getInstance();

	public OverlayPortal()
	{

	}

	@Override
	public boolean isEnabled()
	{
		return Minecraft.getInstance().world != null;
	}

	@Override
	public void draw()
	{
		if (!this.mc.player.isPotionActive(Effects.NAUSEA))
		{
			final PlayerAether playerAether = PlayerAether.getPlayer(this.mc.player);
			final PlayerTeleportingModule teleportModule = playerAether.getModule(PlayerTeleportingModule.class);

			float timeInPortal = (teleportModule.getPrevTicksInTeleporter()
					+ ((teleportModule.getTicksInTeleporter() - teleportModule.getPrevTicksInTeleporter())) * this.timer.renderPartialTicks)
					/ (float) PlayerTeleportingModule.TELEPORT_DELAY;
			timeInPortal *= 0.8f;

			if (timeInPortal > 0.0F)
			{
				GlStateManager.enableBlend();
				GlStateManager.disableAlphaTest();
				GlStateManager.disableDepthTest();
				GlStateManager.depthMask(false);
				GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
						GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
				GlStateManager.color4f(1.0F, 1.0F, 1.0F, timeInPortal);
				this.mc.getTextureManager().bindTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE);
				final TextureAtlasSprite textureatlassprite = this.mc.getBlockRendererDispatcher().getBlockModelShapes()
						.getTexture(BlocksAether.aether_portal.getDefaultState());
				final float f = textureatlassprite.getMinU();
				final float f1 = textureatlassprite.getMinV();
				final float f2 = textureatlassprite.getMaxU();
				final float f3 = textureatlassprite.getMaxV();
				final Tessellator tessellator = Tessellator.getInstance();
				final BufferBuilder vertexbuffer = tessellator.getBuffer();
				vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
				vertexbuffer.pos(0.0D, (double) this.mc.mainWindow.getScaledHeight(), -90.0D).tex((double) f, (double) f3).endVertex();
				vertexbuffer.pos((double) this.mc.mainWindow.getScaledWidth(), (double) this.mc.mainWindow.getScaledHeight(), -90.0D)
						.tex((double) f2, (double) f3).endVertex();
				vertexbuffer.pos((double) this.mc.mainWindow.getScaledWidth(), 0.0D, -90.0D).tex((double) f2, (double) f1).endVertex();
				vertexbuffer.pos(0.0D, 0.0D, -90.0D).tex((double) f, (double) f1).endVertex();
				tessellator.draw();
				GlStateManager.depthMask(true);
				GlStateManager.enableDepthTest();
				GlStateManager.enableAlphaTest();
				GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
			}
		}
	}

}
