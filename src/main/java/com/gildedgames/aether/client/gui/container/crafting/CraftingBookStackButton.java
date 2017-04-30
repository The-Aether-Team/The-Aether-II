package com.gildedgames.aether.client.gui.container.crafting;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.item.ItemStack;

import java.util.Collections;
import java.util.List;

public abstract class CraftingBookStackButton extends GuiButton
{
	protected final Minecraft mc;

	protected final RenderItem itemRenderer;

	protected boolean selected;

	public List<ItemStack> stacks;

	public CraftingBookStackButton(int id, List<ItemStack> stacks, int x, int y)
	{
		super(id, x, y, "missingno");

		this.mc = Minecraft.getMinecraft();
		this.itemRenderer = this.mc.getRenderItem();

		this.width = 16;
		this.height = 16;

		this.stacks = stacks;
	}

	public CraftingBookStackButton(int id, ItemStack stack, int x, int y)
	{
		this(id, Collections.singletonList(stack), x, y);
	}

	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY)
	{
		if (this.stacks.size() <= 0)
		{
			return;
		}

		this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition &&
				mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;

		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

		GlStateManager.enableBlend();
		GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
		GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);

		RenderHelper.enableGUIStandardItemLighting();
		GlStateManager.enableDepth();

		if (this.hovered || this.selected)
		{
			if (this.hovered)
			{
				drawRect(this.xPosition, this.yPosition, this.xPosition + 16, this.yPosition + 16,
						this.selected ? 0x9FFFFFBF : 0x7FFFFFFF);
			}
			else
			{
				drawRect(this.xPosition, this.yPosition, this.xPosition + 16, this.yPosition + 16, 0x7FFFFF7F);
			}
		}

		this.renderItem(mouseX, mouseY, this.hovered, this.selected);

		RenderHelper.disableStandardItemLighting();
		GlStateManager.disableDepth();
	}

	protected void renderItem(int mouseX, int mouseY, boolean hovered, boolean selected)
	{
		this.itemRenderer.zLevel = 0.0F;

		this.itemRenderer.renderItemAndEffectIntoGUI(this.mc.player, this.getItemStackForRender(), this.xPosition, this.yPosition);
	}

	public ItemStack getItemStackForRender()
	{
		int index = (int) (this.mc.world.getWorldTime() / 20) % this.stacks.size();

		return this.stacks.get(index);
	}
}
