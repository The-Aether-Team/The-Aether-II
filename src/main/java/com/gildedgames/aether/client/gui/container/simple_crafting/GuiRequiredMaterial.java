package com.gildedgames.aether.client.gui.container.simple_crafting;

import com.gildedgames.aether.client.gui.container.IExtendedContainer;
import com.gildedgames.aether.common.recipes.simple.OreDictionaryRequirement;
import com.gildedgames.aether.common.util.helpers.RecipeUtil;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.oredict.OreDictionary;

public class GuiRequiredMaterial extends Button
{

	public boolean resultStack;

	private Object required;

	private ItemStack displayStack;

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

			if (stack.getDamage() == OreDictionary.WILDCARD_VALUE)
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
	public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks)
	{
		//super.drawButton(mc, mouseX, mouseY);

		if (this.visible && this.displayStack != null)
		{
			this.hovered =
					mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;

			GlStateManager.enableRescaleNormal();
			RenderHelper.enableGUIStandardItemLighting();
			GlStateManager.enableDepth();

			Minecraft.getInstance().getRenderItem().renderItemIntoGUI(this.displayStack, this.x + 1, this.y + 1);

			RenderHelper.disableStandardItemLighting();
			GlStateManager.disableRescaleNormal();

			GlStateManager.pushMatrix();
			GlStateManager.translatef(0.0F, 0.0F, 300.0F);

			if (!this.resultStack || this.displayStack.getCount() > 1)
			{
				boolean hasEnough = RecipeUtil.hasEnoughOfMaterial(Minecraft.getInstance().player, this.required) || this.resultStack;

				int xOffset = (Math.max(String.valueOf(this.displayStack.getCount()).length() - 1, 0)) * -6;

				this.drawString(Minecraft.getInstance().fontRenderer, (!hasEnough ? TextFormatting.RED : "") + String.valueOf(this.displayStack.getCount()),
						this.x + 12 + xOffset, this.y + this.height - 8, 0xFFFFFF);
			}

			if (this.hovered)
			{
				Screen gui = Minecraft.getInstance().currentScreen;

				if (gui instanceof IExtendedContainer)
				{
					IExtendedContainer extendedGui = (IExtendedContainer) gui;
					extendedGui.setHoveredDescription(this.displayStack, this.displayStack.getTooltip(Minecraft.getInstance().player,
							Minecraft.getInstance().gameSettings.advancedItemTooltips ?
									ITooltipFlag.TooltipFlags.ADVANCED :
									ITooltipFlag.TooltipFlags.NORMAL));
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
