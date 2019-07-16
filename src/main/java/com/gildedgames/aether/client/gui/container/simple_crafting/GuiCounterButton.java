package com.gildedgames.aether.client.gui.container.simple_crafting;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;

public class GuiCounterButton extends Button
{

	private final ResourceLocation texture;

	public GuiCounterButton(int x, int y, ResourceLocation texture, IPressable callback)
	{
		super(x, y, 10, 7, "", callback);

		this.texture = texture;
	}

	@Override
	public void render(int mouseX, int mouseY, float partialTicks)
	{
		if (this.visible)
		{
			this.isHovered =
					mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;

			Minecraft.getInstance().getTextureManager().bindTexture(this.texture);
			AbstractGui.blit(this.x, this.y, 0, 0, 10, 7, 10, 7);
		}
	}

}
