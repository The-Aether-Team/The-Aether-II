package com.gildedgames.aether.client.gui.container.simple_crafting;

import com.gildedgames.aether.client.gui.IExtendedGui;
import com.gildedgames.aether.common.recipes.simple.OreDictionaryRequirement;
import com.gildedgames.aether.common.util.helpers.RecipeUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.oredict.OreDictionary;

public class GuiRequiredMaterial extends GuiButton
{

	private Object required;

	private ItemStack displayStack;

	public boolean resultStack;

	public GuiRequiredMaterial(int buttonId, int x, int y, ItemStack stack)
	{
		super(buttonId, x, y, 18, 18, "");

		this.setRequiredObject(stack);
	}

	public Object getRequiredObject()
	{
		return this.required;
	}

	public void setRequiredObject(Object obj)
	{
		if (obj instanceof ItemStack)
		{
			ItemStack stack = (ItemStack) obj;

			if (stack.getItemDamage() == OreDictionary.WILDCARD_VALUE)
			{
				this.required = stack;
				this.displayStack = new ItemStack(stack.getItem(), stack.getCount());
				return;
			}
		}

		this.required = obj;

		if (obj instanceof OreDictionaryRequirement)
		{
			OreDictionaryRequirement ore = (OreDictionaryRequirement) obj;
			this.displayStack = OreDictionary.getOres(((OreDictionaryRequirement) obj).getKey()).get(0).copy();
			this.displayStack.setCount(ore.getCount());
		}
		else if (obj instanceof ItemStack)
		{
			ItemStack stack = (ItemStack) obj;
			this.displayStack = stack.copy();
		}
		else
		{
			this.displayStack = null;
		}
	}

	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY)
	{
		//super.drawButton(mc, mouseX, mouseY);

		if (this.visible && this.displayStack != null)
		{
			this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width
					&& mouseY < this.yPosition + this.height;

			GlStateManager.enableRescaleNormal();
			RenderHelper.enableGUIStandardItemLighting();
			GlStateManager.enableDepth();

			Minecraft.getMinecraft().getRenderItem().renderItemIntoGUI(this.displayStack, this.xPosition + 1, this.yPosition + 1);

			RenderHelper.disableStandardItemLighting();
			GlStateManager.disableRescaleNormal();

			GlStateManager.pushMatrix();
			GlStateManager.translate(0.0F, 0.0F, 300.0F);

			if (!this.resultStack || this.displayStack.getCount() > 1)
			{
				boolean hasEnough = RecipeUtil.hasEnoughOfMaterial(Minecraft.getMinecraft().player, this.required) || this.resultStack;

				int xOffset = (Math.max(String.valueOf(this.displayStack.getCount()).length() - 1, 0)) * -6;

				this.drawString(Minecraft.getMinecraft().fontRenderer,
						(!hasEnough ? TextFormatting.RED : "") + String.valueOf(this.displayStack.getCount()),
						this.xPosition + 12 + xOffset, this.yPosition + this.height - 8, 0xFFFFFF);
			}

			if (this.hovered)
			{
				GuiScreen gui = Minecraft.getMinecraft().currentScreen;

				if (gui instanceof IExtendedGui)
				{
					IExtendedGui extendedGui = (IExtendedGui) gui;
					extendedGui.setHoveredDescription(this.displayStack.getTooltip(Minecraft.getMinecraft().player, false));
				}
			}

			GlStateManager.popMatrix();
		}
	}

	@Override
	public void playPressSound(SoundHandler soundHandlerIn)
	{

	}

}
