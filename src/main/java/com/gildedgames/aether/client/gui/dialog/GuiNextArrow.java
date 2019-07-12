package com.gildedgames.aether.client.gui.dialog;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.orbis.lib.client.gui.util.gui_library.GuiElement;
import com.gildedgames.orbis.lib.client.rect.Dim2D;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.Sys;

public class GuiNextArrow extends GuiElement
{
	private static final ResourceLocation NEXT_ARROW = AetherCore.getResource("textures/gui/conversation/next_arrow.png");

	private double nextArrowAnim, prevTime;

	public GuiNextArrow()
	{
		super(Dim2D.flush(), true);
	}

	@Override
	public void onDraw(GuiElement element)
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
			GlStateManager.translatef(0, anim / 500.0, 0);
		}
		else if (this.nextArrowAnim >= 500.0)
		{
			GlStateManager.translatef(0, -((anim - 500.0) / 500.0), 0);
		}

		GlStateManager.translatef(0, 0, 302F);
		GlStateManager.color(1.0F, 1.0F, 1.0F);

		Minecraft.getInstance().getTextureManager().bindTexture(NEXT_ARROW);

		AbstractGui.drawModalRectWithCustomSizedTexture((int) this.dim().x(), (int) this.dim().y(), 0, 0, 13, 12, 13, 12);

		GlStateManager.popMatrix();
	}
}
