package com.gildedgames.aether.client.gui.container;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.containers.tiles.ContainerIncubator;
import com.gildedgames.aether.common.entities.tiles.TileEntityIncubator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

//TODO: Adding/Removing ambrosium chunks freezes the GUI which requires to close and reopen GUI.

@SideOnly(Side.CLIENT)
public class GuiIncubator extends GuiContainer
{
	private static final ResourceLocation TEXTURE = AetherCore.getResource("textures/gui/inventory/incubator.png");

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

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		TileEntity tile = Minecraft.getMinecraft().world.getTileEntity(this.incubatorPos);

		String name = tile.getDisplayName().getUnformattedText();
		this.fontRenderer.drawString(name, this.xSize / 2 - this.fontRenderer.getStringWidth(name) / 2, 6, 4210752);
		this.fontRenderer.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);

		if (tile instanceof TileEntityIncubator)
		{
			TileEntityIncubator te = (TileEntityIncubator) tile;

			String incubateText = I18n.format("gui.aether.incubator.label.incubation");
			String heatingText = I18n.format("gui.aether.incubator.label.heating");
			String eggStatusString;
			String heatStatusString;
			int incubateColor = 0xff5a00;
			int heatingColor = 0xcc0000;
			int eggStatusColor = 0xffffff;
			int heatStatusColor = 0xffffff;

			float eggPercent = 0.0F;

			if (te.getField(0) < te.getRequiredTemperatureThreshold())
			{
				float heatValue = (float) te.getField(0) / ((float) te.getRequiredTemperatureThreshold()) * 100.f;
				if (heatValue > 100.f)
				{
					heatValue = 100.f;
				}
				heatStatusString = String.valueOf(heatValue);
				heatStatusString = String.format("%.0f", Float.valueOf(heatStatusString));
				heatStatusString += "%";
				if (te.getField(0) == 0)
				{
					heatStatusColor = 0x787878;
				}
			}
			else
			{
				heatStatusString = I18n.format("gui.aether.incubator.label.max");
				heatStatusColor = 0xffffff;
			}

			if (te.canEggIncubate())
			{
				eggStatusString = I18n.format("gui.aether.incubator.label.ready");

				if (!te.getMoaEgg().isEmpty())
				{
					if (te.getField(1) > 0)
					{
						float eggMath = ((float) te.getEggTimer() / (float) (te.getEggTimerMax()));

						eggPercent = eggMath * 100.0F;
					}

					eggStatusString = String.valueOf(eggPercent);
					eggStatusString = String.format("%.0f", Float.valueOf(eggStatusString));
					eggStatusString += "%";
				}
			}
			else
			{
				eggStatusString = I18n.format("gui.aether.incubator.label.more_heat");
				eggStatusColor = 0x787878;

				if (!te.getMoaEgg().isEmpty())
				{
					eggStatusString = I18n.format("gui.aether.incubator.label.fail");
					eggStatusColor = 0x2f2f2f;
					heatStatusColor = 0x2f2f2f;
				}
			}

			// Text rendering
			this.fontRenderer.drawString(incubateText, 135 - (this.fontRenderer.getStringWidth(incubateText) / 2), this.ySize - 145, incubateColor);
			this.fontRenderer.drawString(heatingText, 45 - (this.fontRenderer.getStringWidth(heatingText) / 2), this.ySize - 145, heatingColor);
			this.fontRenderer.drawString(eggStatusString, 135 - (this.fontRenderer.getStringWidth(eggStatusString) / 2), this.ySize - 110, eggStatusColor);
			this.fontRenderer.drawString(heatStatusString, 45 - (this.fontRenderer.getStringWidth(heatStatusString) / 2), this.ySize - 110, heatStatusColor);
		}
	}

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
		int j = TileEntityIncubator.REQ_TEMPERATURE_THRESHOLD - 500;

		return j != 0 && i != 0 ? i * pixels / j : 0;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		this.drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		this.renderHoveredToolTip(mouseX, mouseY);
	}
}
