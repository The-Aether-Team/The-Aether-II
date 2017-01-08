package com.gildedgames.aether.client.gui.menu;

import com.gildedgames.aether.client.ui.common.GuiFrame;
import com.gildedgames.aether.client.ui.graphics.Graphics2D;
import com.gildedgames.aether.client.ui.input.InputProvider;
import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.capabilities.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.player.modules.TeleportingModule;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.init.MobEffects;
import net.minecraft.util.Timer;

public class PortalOverlay extends GuiFrame
{

	private Timer timer = new Timer(20.0F);

	private Minecraft mc = Minecraft.getMinecraft();

	public PortalOverlay()
	{

	}

	@Override
	public void initContent(InputProvider input)
	{
		super.initContent(input);
	}

	@Override
	public boolean isEnabled()
	{
		return Minecraft.getMinecraft().theWorld != null;
	}

	@Override
	public void draw(Graphics2D graphics, InputProvider input)
	{
		if (!this.isEnabled())
		{
			return;
		}

		if (!this.mc.thePlayer.isPotionActive(MobEffects.NAUSEA))
		{
			PlayerAether playerAether = PlayerAether.getPlayer(this.mc.thePlayer);
			TeleportingModule teleporter = playerAether.getTeleportingModule();

			float timeInPortal = teleporter.getPrevTimeInPortal()
					+ (teleporter.getTimeInPortal() - teleporter.getPrevTimeInPortal()) * this.timer.renderPartialTicks;

			if (timeInPortal > 0.0F)
			{
				if (timeInPortal < 1.0F)
				{
					timeInPortal = timeInPortal * timeInPortal;
					timeInPortal = timeInPortal * timeInPortal;
					timeInPortal = timeInPortal * 0.8F + 0.2F;
				}

				ScaledResolution scaledRes = new ScaledResolution(this.mc);

				GlStateManager.disableAlpha();
				GlStateManager.disableDepth();
				GlStateManager.depthMask(false);
				GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
				GlStateManager.color(1.0F, 1.0F, 1.0F, timeInPortal);
				this.mc.getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
				TextureAtlasSprite textureatlassprite = this.mc.getBlockRendererDispatcher().getBlockModelShapes().getTexture(BlocksAether.aether_portal.getDefaultState());
				float f = textureatlassprite.getMinU();
				float f1 = textureatlassprite.getMinV();
				float f2 = textureatlassprite.getMaxU();
				float f3 = textureatlassprite.getMaxV();
				Tessellator tessellator = Tessellator.getInstance();
				VertexBuffer vertexbuffer = tessellator.getBuffer();
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
