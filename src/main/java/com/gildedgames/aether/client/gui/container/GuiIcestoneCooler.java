package com.gildedgames.aether.client.gui.container;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.containers.tiles.ContainerIcestoneCooler;
import com.gildedgames.aether.common.entities.tiles.TileEntityIcestoneCooler;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GuiIcestoneCooler extends ContainerScreen<ContainerIcestoneCooler>
{
	private static final ResourceLocation TEXTURE = AetherCore.getResource("textures/gui/inventory/icestone_cooler.png");

	public GuiIcestoneCooler(ContainerIcestoneCooler cooler, PlayerInventory inv, ITextComponent name)
	{
		super(cooler, inv, name);
	}

	/**
	 * Draws the screen and all the components in it.
	 */
	@Override
	public void render(int mouseX, int mouseY, float partialTicks)
	{
		this.renderBackground();

		super.render(mouseX, mouseY, partialTicks);

		this.renderHoveredToolTip(mouseX, mouseY);
	}

	/**
	 * Draw the foreground layer for the GuiContainer (everything in front of the items)
	 */
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		String title = this.title.getFormattedText();

		this.font.drawString(title, this.xSize / 2 - this.font.getStringWidth(title) / 2, 6, 0x404040);
		this.font.drawString(this.playerInventory.getDisplayName().getFormattedText(), 8, this.ySize - 96 + 2, 0x404040);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.minecraft.getTextureManager().bindTexture(TEXTURE);
		int i = (this.width - this.xSize) / 2;
		int j = (this.height - this.ySize) / 2;
		this.blit(i, j, 0, 0, this.xSize, this.ySize);

		if (this.container.isCooling())
		{
			int k = this.getCoolLeftScaled(13);
			this.blit(i + 58, j + 36 + 14 - k, 176, 12 - k, 14, k + 1);
		}

		int l = this.getCoolProgressScaled(24);
		this.blit(i + 79, j + 35, 176, 16, l + 1, 16);
	}

	private int getCoolProgressScaled(int pixels)
	{
		int i = this.container.getCoolTime();
		int j = this.container.getTotalCoolTime();
		return j != 0 && i != 0 ? i * pixels / j : 0;
	}

	private int getCoolLeftScaled(int pixels)
	{
		int i = this.container.getCurrentItemCoolTime();

		if (i == 0)
		{
			i = 1;
		}

		return this.container.getCoolerCoolTime() * pixels / i;
	}
}
