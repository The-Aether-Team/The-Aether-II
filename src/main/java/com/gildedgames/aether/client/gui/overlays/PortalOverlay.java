package com.gildedgames.aether.client.gui.overlays;

import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.modules.PlayerTeleportingModule;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.init.MobEffects;
import net.minecraft.util.Timer;

public class PortalOverlay implements IOverlay
{

	private final Timer timer = new Timer(20.0F);

	private final Minecraft mc = Minecraft.getMinecraft();

	public PortalOverlay()
	{

	}

	@Override
	public boolean isEnabled()
	{
		return Minecraft.getMinecraft().world != null;
	}

	@Override
	public void draw()
	{
		if (!this.mc.player.isPotionActive(MobEffects.NAUSEA))
		{
			final PlayerAether playerAether = PlayerAether.getPlayer(this.mc.player);
			final PlayerTeleportingModule teleportModule = playerAether.getModule(PlayerTeleportingModule.class);

			float timeInPortal = (teleportModule.getPrevTicksInTeleporter() + ((teleportModule.getTicksInTeleporter() - teleportModule.getPrevTicksInTeleporter())) * this.timer.renderPartialTicks)
					/ (float) PlayerTeleportingModule.TELEPORT_DELAY;
			timeInPortal *= 0.8f;

			if (timeInPortal > 0.0F)
			{
				final ScaledResolution scaledRes = new ScaledResolution(this.mc);

				GlStateManager.enableBlend();
				GlStateManager.disableAlpha();
				GlStateManager.disableDepth();
				GlStateManager.depthMask(false);
				GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
						GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
				GlStateManager.color(1.0F, 1.0F, 1.0F, timeInPortal);
				this.mc.getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
				final TextureAtlasSprite textureatlassprite = this.mc.getBlockRendererDispatcher().getBlockModelShapes()
						.getTexture(BlocksAether.aether_portal.getDefaultState());
				final float f = textureatlassprite.getMinU();
				final float f1 = textureatlassprite.getMinV();
				final float f2 = textureatlassprite.getMaxU();
				final float f3 = textureatlassprite.getMaxV();
				final Tessellator tessellator = Tessellator.getInstance();
				final BufferBuilder vertexbuffer = tessellator.getBuffer();
				vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
				vertexbuffer.pos(0.0D, (double) scaledRes.getScaledHeight(), -90.0D).tex((double) f, (double) f3).endVertex();
				vertexbuffer.pos((double) scaledRes.getScaledWidth(), (double) scaledRes.getScaledHeight(), -90.0D).tex((double) f2, (double) f3).endVertex();
				vertexbuffer.pos((double) scaledRes.getScaledWidth(), 0.0D, -90.0D).tex((double) f2, (double) f1).endVertex();
				vertexbuffer.pos(0.0D, 0.0D, -90.0D).tex((double) f, (double) f1).endVertex();
				tessellator.draw();
				GlStateManager.depthMask(true);
				GlStateManager.enableDepth();
				GlStateManager.enableAlpha();
				GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			}
		}
	}

}
