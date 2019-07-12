package com.gildedgames.aether.client.gui.container.simple_crafting;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;

public class GuiCounterButton extends Button
{

	private final ResourceLocation texture;

	public GuiCounterButton(int buttonId, int x, int y, ResourceLocation texture)
	{
		super(buttonId, x, y, 10, 7, "");

		this.texture = texture;
	}

	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks)
	{
		if (this.visible)
		{
			this.hovered =
					mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;

			Minecraft.getInstance().getTextureManager().bindTexture(this.texture);
			AbstractGui.drawModalRectWithCustomSizedTexture(this.x, this.y, 0, 0, 10, 7, 10, 7);
		}
	}

}
