package com.gildedgames.aether.client.gui.container.simple_crafting;

import com.gildedgames.aether.common.AetherCore;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;

public class GuiXButton extends Button
{

	private static final ResourceLocation X_BUTTON = AetherCore.getResource("textures/gui/inventory/x_button.png");

	public GuiXButton(int buttonId, int x, int y)
	{
		super(buttonId, x, y, 9, 9, "");
	}

	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks)
	{
		if (this.visible)
		{
			this.hovered =
					mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;

			Minecraft.getInstance().getTextureManager().bindTexture(X_BUTTON);
			AbstractGui.drawModalRectWithCustomSizedTexture(this.x, this.y, 0, 0, 9, 9, 9, 9);
		}
	}

}
