package com.gildedgames.aether.client.gui.dialog;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.orbis_api.client.gui.util.GuiFrame;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.Sys;

public class GuiNextArrow extends GuiFrame
{
	private static final ResourceLocation NEXT_ARROW = AetherCore.getResource("textures/gui/conversation/next_arrow.png");

	private double nextArrowAnim, prevTime;

	@Override
	public void init()
	{

	}

	@Override
	public void draw()
	{
		GlStateManager.pushMatrix();

		final double time = (Sys.getTime() * 1000) / Sys.getTimerResolution();
		final double timePassed = time - this.prevTime;

		this.prevTime = time;

		if (this.nextArrowAnim < 1000)
		{
			this.nextArrowAnim += timePassed;
		}
		else
		{
			this.nextArrowAnim = 0.0;
		}

		final double anim = this.nextArrowAnim;

		if (this.nextArrowAnim < 500.0)
		{
			GlStateManager.translate(0, anim / 500.0, 0);
		}
		else if (this.nextArrowAnim >= 500.0)
		{
			GlStateManager.translate(0, -((anim - 500.0) / 500.0), 0);
		}

		GlStateManager.translate(0, 0, 302F);
		GlStateManager.color(1.0F, 1.0F, 1.0F);

		Minecraft.getMinecraft().renderEngine.bindTexture(NEXT_ARROW);

		Gui.drawModalRectWithCustomSizedTexture((int) this.dim().x(), (int) this.dim().y(), 0, 0, 13, 12, 13, 12);

		GlStateManager.popMatrix();
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(final float partialTicks, final int mouseX, final int mouseY)
	{

	}
}
