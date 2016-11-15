package com.gildedgames.aether.client.gui.container.simple_crafting;

import com.gildedgames.aether.client.util.gui.GuiUtil;
import com.gildedgames.aether.common.util.helpers.RecipeUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiCreateFlatWorld;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.oredict.OreDictionary;

import java.util.Collections;

public class GuiRequiredMaterial extends GuiButton
{

	private ItemStack stack;

	private ItemStack displayStack;

	public boolean resultStack;

	public GuiRequiredMaterial(int buttonId, int x, int y, ItemStack stack)
	{
		super(buttonId, x, y, 18, 18, "");

		this.setItemStack(stack);
	}

	public ItemStack getItemStack()
	{
		return this.stack;
	}

	public void setItemStack(ItemStack stack)
	{
		if (stack != null && stack.getItemDamage() == OreDictionary.WILDCARD_VALUE)
		{
			this.stack = stack;
			this.displayStack = new ItemStack(stack.getItem(), stack.stackSize);
			return;
		}

		this.stack = stack;
		this.displayStack = stack;
	}

	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY)
	{
		//super.drawButton(mc, mouseX, mouseY);

		if (this.visible && this.stack != null)
		{
			this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;

			GlStateManager.enableRescaleNormal();
			RenderHelper.enableGUIStandardItemLighting();
			GlStateManager.enableDepth();

			Minecraft.getMinecraft().getRenderItem().renderItemIntoGUI(this.displayStack, this.xPosition + 1, this.yPosition + 1);

			RenderHelper.disableStandardItemLighting();
			GlStateManager.disableRescaleNormal();


			GlStateManager.pushMatrix();
			GlStateManager.translate(0.0F, 0.0F, 300.0F);

			if (!this.resultStack || this.stack.stackSize > 1)
			{
				boolean hasEnough = RecipeUtil.hasEnoughOfMaterial(Minecraft.getMinecraft().thePlayer, this.stack) || this.resultStack;

				int xOffset = (Math.max(String.valueOf(this.stack.stackSize).length() - 1, 0)) * -6;

				this.drawString(Minecraft.getMinecraft().fontRendererObj, (!hasEnough ? TextFormatting.RED : "") + String.valueOf(this.stack.stackSize), this.xPosition + 12 + xOffset, this.yPosition + this.height - 8, 0xFFFFFF);
			}

			if (this.hovered)
			{
				GuiScreen gui = Minecraft.getMinecraft().currentScreen;

				if (gui instanceof GuiSimpleCrafting)
				{
					GuiSimpleCrafting crafting = (GuiSimpleCrafting)gui;

					crafting.hoverDescription = I18n.format(this.stack.getDisplayName());
				}
			}

			GlStateManager.popMatrix();
		}
	}

}
