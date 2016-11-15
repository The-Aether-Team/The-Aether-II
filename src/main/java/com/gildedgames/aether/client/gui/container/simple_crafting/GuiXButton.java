package com.gildedgames.aether.client.gui.container.simple_crafting;

import com.gildedgames.aether.common.AetherCore;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;

public class GuiXButton extends GuiButton
{

	private static final ResourceLocation X_BUTTON = AetherCore.getResource("textures/gui/inventory/x_button.png");

	public GuiXButton(int buttonId, int x, int y)
	{
		super(buttonId, x, y, 9, 9, "");
	}

	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY)
	{
		if (this.visible)
		{
			this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;

			Minecraft.getMinecraft().getTextureManager().bindTexture(X_BUTTON);
			Gui.drawModalRectWithCustomSizedTexture(this.xPosition, this.yPosition, 0, 0, 9, 9, 9, 9);
		}
	}

}
