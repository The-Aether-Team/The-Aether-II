package com.gildedgames.aether.client.gui.container;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.containers.tiles.ContainerIcestoneCooler;
import com.gildedgames.aether.common.entities.tiles.TileEntityIcestoneCooler;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GuiIcestoneCooler extends ContainerScreen
{
	private static final ResourceLocation TEXTURE = AetherCore.getResource("textures/gui/inventory/icestone_cooler.png");

	private final IInventory tileCooler;

	private final PlayerInventory playerInventory;

	public GuiIcestoneCooler(PlayerInventory playerInv, IInventory coolerInv)
	{
		super(new ContainerIcestoneCooler(playerInv, coolerInv));
		this.tileCooler = coolerInv;
		this.playerInventory = playerInv;
	}

	/**
	 * Draws the screen and all the components in it.
	 */
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		this.drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		this.renderHoveredToolTip(mouseX, mouseY);
	}

	/**
	 * Draw the foreground layer for the GuiContainer (everything in front of the items)
	 */
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		String s = this.tileCooler.getDisplayName().getUnformattedText();
		this.fontRenderer.drawString(s, this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2, 6, 4210752);
		this.fontRenderer.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(TEXTURE);
		int i = (this.width - this.xSize) / 2;
		int j = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);

		if (TileEntityIcestoneCooler.isCooling(this.tileCooler))
		{
			int k = this.getCoolLeftScaled(13);
			this.drawTexturedModalRect(i + 58, j + 36 + 14 - k, 176, 12 - k, 14, k + 1);
		}

		int l = this.getCoolProgressScaled(24);
		this.drawTexturedModalRect(i + 79, j + 35, 176, 16, l + 1, 16);
	}

	private int getCoolProgressScaled(int pixels)
	{
		int i = this.tileCooler.getField(2);
		int j = this.tileCooler.getField(3);
		return j != 0 && i != 0 ? i * pixels / j : 0;
	}

	private int getCoolLeftScaled(int pixels)
	{
		int i = this.tileCooler.getField(1);

		if (i == 0)
		{
			i = 1;
		}

		return this.tileCooler.getField(0) * pixels / i;
	}
}
