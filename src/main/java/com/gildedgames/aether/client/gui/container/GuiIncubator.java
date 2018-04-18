package com.gildedgames.aether.client.gui.container;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.containers.tiles.ContainerIncubator;
import com.gildedgames.aether.common.entities.tiles.TileEntityIncubator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiIncubator extends GuiContainer
{
	private static final ResourceLocation TEXTURE = AetherCore.getResource("textures/gui/inventory/incubator.png");

	/** The player inventory bound to this GUI. */
	private final InventoryPlayer playerInventory;

	private final IInventory tileIncubator;

	private final BlockPos incubatorPos;

	public GuiIncubator(InventoryPlayer playerInv, IInventory incubatorInv, BlockPos incubatorPos)
	{
		super(new ContainerIncubator(playerInv, incubatorInv));

		this.playerInventory = playerInv;
		this.incubatorPos = incubatorPos;
		this.tileIncubator = incubatorInv;
	}

	/**
	 * Draw the foreground layer for the GuiContainer (everything in front of the items)
	 */
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		TileEntity tile = Minecraft.getMinecraft().world.getTileEntity(this.incubatorPos);

		String s = tile.getDisplayName().getUnformattedText();
		this.fontRenderer.drawString(s, this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2, 6, 4210752);
		this.fontRenderer.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);

		if (tile instanceof TileEntityIncubator)
		{
			TileEntityIncubator te = (TileEntityIncubator) tile;

			if (te.canEggIncubate())
			{
				if (!te.getMoaEgg().isEmpty())
				{
					this.fontRenderer.drawString("Incubating", 126 - (this.fontRenderer.getStringWidth("Incubating") / 2), this.ySize - 126, 15435844);
				}
			}
			else if (te.areFuelSlotsFilled() && te.getField(0) < 2500)
			{
				this.fontRenderer.drawString("Heating", 60 - (this.fontRenderer.getStringWidth("Heating") / 2), this.ySize - 126, 11743532);
			}

			if (!te.hasStartedHeating())
			{
				return;
			}

			float percent = 0.0F;

			if (te.getField(1) > 0)
			{
				// the percentage will represent the how long till the egg is finished incubation.
				// currently the egg needs to incubate from 0 to half of the requiredTemperatureThreshold(5000/2 = 2500).
				float eggMath = ((float) te.getEggTimer() / (float) (te.getRequiredTemperatureThreshold() / 2));

				percent = eggMath * 100.0F;
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

			this.fontRenderer.drawString(valueString, 113 - (this.fontRenderer.getStringWidth(valueString) / 2), this.ySize - 145 + 2, 4210752);
		}
	}

	/**
	 * Draws the background layer of this container (behind the items).
	 */
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		TileEntity tile = Minecraft.getMinecraft().world.getTileEntity(this.incubatorPos);

		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(TEXTURE);
		int i = (this.width - this.xSize) / 2;
		int j = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);

		if (tile instanceof TileEntityIncubator)
		{
			TileEntityIncubator te = (TileEntityIncubator) tile;
			int k = this.getHeatingScaled(13);
			this.drawTexturedModalRect(i + 78, j + 36 + 12 - k, 176, 12 - k, 20, k);
		}
	}

	private int getHeatingScaled(int pixels)
	{
		int i = this.tileIncubator.getField(0);
		int j = 2500;

		return j != 0 && i != 0 ? i * pixels / j : 0;
	}

	@Override
	public void updateScreen()
	{
		super.updateScreen();

		this.drawGuiContainerForegroundLayer(0, 0);
	}
}
