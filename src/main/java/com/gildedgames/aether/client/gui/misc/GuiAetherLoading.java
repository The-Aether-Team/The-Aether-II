package com.gildedgames.aether.client.gui.misc;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.orbis_api.client.PartialTicks;
import com.gildedgames.orbis_api.client.gui.util.GuiFrame;
import com.gildedgames.orbis_api.client.gui.util.GuiTexture;
import com.gildedgames.orbis_api.client.rect.Dim2D;
import com.gildedgames.orbis_api.client.rect.Pos2D;
import com.gildedgames.orbis_api.util.InputHelper;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GuiAetherLoading extends GuiFrame implements CustomLoadingRenderer.ICustomLoading
{
	private static final ResourceLocation HIGHLANDS = AetherCore.getResource("textures/gui/intro/highlands.png");

	private static final ResourceLocation HUE_BACKGROUND = AetherCore.getResource("textures/gui/intro/hue_background.png");

	private GuiTexture highlands;

	@Override
	public void onGuiClosed()
	{
		super.onGuiClosed();

		preventInnerTyping = true;
	}

	@Override
	public void init()
	{
		this.dim().mod().width(this.width).height(this.height).flush();

		preventInnerTyping = true;

		final Pos2D center = InputHelper.getCenter();

		this.highlands = new GuiTexture(Dim2D.build().scale(0.45F).width(512).height(235).center(true).pos(center).flush(), HIGHLANDS);

		this.addChildren(this.highlands);
	}

	@Override
	public void draw()
	{
		GlStateManager.pushMatrix();

		GlStateManager.disableDepth();

		GlStateManager.enableBlend();
		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GlStateManager
				.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
						GlStateManager.SourceFactor.ZERO,
						GlStateManager.DestFactor.ONE);

		GL11.glEnable(GL11.GL_ALPHA_TEST);

		GlStateManager.enableAlpha();

		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

		this.mc.getTextureManager().bindTexture(HUE_BACKGROUND);

		drawModalRectWithCustomSizedTexture(0, 0, 0, 0, this.width,
				this.height,
				this.width, this.height);

		GlStateManager.enableDepth();

		GlStateManager.enableBlend();

		GlStateManager.popMatrix();
	}

	@Override
	public void drawCustomLoading()
	{
		this.drawScreen((int) InputHelper.getMouseX(), (int) InputHelper.getMouseY(), PartialTicks.get());
	}
}
