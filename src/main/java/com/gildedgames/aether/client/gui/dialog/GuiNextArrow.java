package com.gildedgames.aether.client.gui.dialog;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.orbis.lib.client.gui.util.gui_library.GuiElement;
import com.gildedgames.orbis.lib.client.gui.util.gui_library.IGuiElement;
import com.gildedgames.orbis.lib.client.rect.Dim2D;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;

public class GuiNextArrow extends GuiElement
{
	private static final ResourceLocation NEXT_ARROW = AetherCore.getResource("textures/gui/conversation/next_arrow.png");

	private float nextArrowAnim, prevTime;

	public GuiNextArrow()
	{
		super(Dim2D.flush(), true);
	}

	@Override
	public void onDraw(IGuiElement element, int mouseX, int mouseY, float partialTicks)
	{
		GlStateManager.pushMatrix();

		final float time = Util.milliTime();
		final float timePassed = time - this.prevTime;

		this.prevTime = time;

		if (this.nextArrowAnim < 1000)
		{
			this.nextArrowAnim += timePassed;
		}
		else
		{
			this.nextArrowAnim = 0.0f;
		}

		final float anim = this.nextArrowAnim;

		if (this.nextArrowAnim < 500.0f)
		{
			GlStateManager.translatef(0, anim / 500.0f, 0);
		}
		else if (this.nextArrowAnim >= 500.0f)
		{
			GlStateManager.translatef(0, -((anim - 500.0f) / 500.0f), 0);
		}

		GlStateManager.translatef(0, 0, 302F);
		GlStateManager.color3f(1.0F, 1.0F, 1.0F);

		Minecraft.getInstance().getTextureManager().bindTexture(NEXT_ARROW);

		AbstractGui.blit((int) this.dim().x(), (int) this.dim().y(), 0, 0, 13, 12, 13, 12);

		GlStateManager.popMatrix();
	}
}
