package com.gildedgames.aether.client.gui.container;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.containers.tiles.ContainerIcestoneCooler;
import com.gildedgames.aether.common.entities.tiles.TileEntityIcestoneCooler;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiIcestoneCooler extends GuiContainer
{
	private static final ResourceLocation TEXTURE = AetherCore.getResource("textures/gui/inventory/icestone_cooler.png");

	/** The player inventory bound to this GUI. */
	private final InventoryPlayer playerInventory;

	private final IInventory tile;

	public GuiIcestoneCooler(InventoryPlayer playerInv, IInventory coolerInv)
	{
		super(new ContainerIcestoneCooler(playerInv, coolerInv));
		this.playerInventory = playerInv;
		this.tile = coolerInv;
	}

	/**
	 * Draw the foreground layer for the GuiContainer (everything in front of the items)
	 */
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		String s = this.tile.getDisplayName().getUnformattedText();
		this.fontRenderer.drawString(s, this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2, 6, 4210752);
		this.fontRenderer.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);

		if (this.tile instanceof TileEntityIcestoneCooler)
		{
			TileEntityIcestoneCooler te = (TileEntityIcestoneCooler) this.tile;

			if (!te.isCooling())
			{
				return;
			}

			float percent = 0.0F;

			if (te.getCurrentCoolingProgress() < 0)
			{
				float thing = ((float) te.getCurrentCoolingProgress() / (float) te.getRequiredTemperatureThreshold());

				percent = thing * 100.0F;
			}

			String valueString = percent == (int) Math.floor(percent) ? String.valueOf((int) Math.floor(percent)) : String.valueOf(percent);

			if (percent != (int) Math.floor(percent))
			{
				double floor = Math.floor(percent);
				double dif = percent - floor;

				if (dif < 0.1F)
				{
					valueString = String.valueOf((int) Math.floor(percent));
				}
				else
				{
					valueString = String.format("%.1f", Float.valueOf(valueString));
				}
			}

			valueString += "%";

			this.fontRenderer.drawString(valueString,
					136 - (this.fontRenderer.getStringWidth(valueString) / 2), this.ySize - 105 + 2, 4210752);
		}
	}

	/**
	 * Draws the background layer of this container (behind the items).
	 */
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(TEXTURE);
		int i = (this.width - this.xSize) / 2;
		int j = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
	}

}
