package com.gildedgames.aether.client.gui.container;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.containers.tiles.ContainerIcestoneCooler;
import com.gildedgames.aether.common.containers.tiles.ContainerIncubator;
import com.gildedgames.aether.common.entities.tiles.TileEntityIncubator;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import static com.gildedgames.aether.common.entities.tiles.TileEntityIncubator.REQ_TEMPERATURE_THRESHOLD;

//TODO: Adding/Removing ambrosium chunks freezes the GUI which requires to close and reopen GUI.
//TODO: Check if we fixed this in 1.14 by properly using container datums -Angeline

@OnlyIn(Dist.CLIENT)
public class GuiIncubator extends ContainerScreen<ContainerIncubator>
{
	private static final ResourceLocation TEXTURE = AetherCore.getResource("textures/gui/inventory/incubator.png");

	public GuiIncubator(ContainerIncubator container, PlayerInventory playerInventory, ITextComponent name)
	{
		super(container, playerInventory, name);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		String name = this.title.getFormattedText();

		this.font.drawString(name, this.xSize / 2 - this.font.getStringWidth(name) / 2, 6, 0x404040);
		this.font.drawString(this.playerInventory.getDisplayName().getFormattedText(), 8, this.ySize - 96 + 2, 0x404040);

		String incubateText = I18n.format("gui.aether.incubator.label.incubation");
		String heatingText = I18n.format("gui.aether.incubator.label.heating");
		String eggStatusString;
		String heatStatusString;

		int incubateColor = 0xff5a00;
		int heatingColor = 0xcc0000;
		int eggStatusColor = 0xffffff;
		int heatStatusColor = 0xffffff;

		float eggPercent = 0.0F;

		if (this.container.getCurrentHeatingProgress() < TileEntityIncubator.REQ_TEMPERATURE_THRESHOLD)
		{
			float heatValue = (float) this.container.getCurrentHeatingProgress() / ((float) REQ_TEMPERATURE_THRESHOLD) * 100.f;

			if (heatValue > 100.f)
			{
				heatValue = 100.f;
			}

			heatStatusString = String.format("%.0f%%", heatValue);

			if (this.container.getCurrentHeatingProgress() == 0)
			{
				heatStatusColor = 0x787878;
			}
		}
		else
		{
			heatStatusString = I18n.format("gui.aether.incubator.label.max");
			heatStatusColor = 0xffffff;
		}

		if (this.container.getCurrentHeatingProgress() >= TileEntityIncubator.REQ_TEMPERATURE_THRESHOLD)
		{
			eggStatusString = I18n.format("gui.aether.incubator.label.ready");

			if (!this.container.getMoaEggStack().isEmpty())
			{
				if (this.container.getEggTimer() > 0)
				{
					float eggMath = ((float) this.container.getEggTimer() / (float) REQ_TEMPERATURE_THRESHOLD);

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

			if (!this.container.getMoaEggStack().isEmpty())
			{
				eggStatusString = I18n.format("gui.aether.incubator.label.fail");
				eggStatusColor = 0x2f2f2f;
				heatStatusColor = 0x2f2f2f;
			}
		}

		// Text rendering
		this.font.drawString(incubateText, 135 - (this.font.getStringWidth(incubateText) / 2), this.ySize - 145, incubateColor);
		this.font.drawString(heatingText, 45 - (this.font.getStringWidth(heatingText) / 2), this.ySize - 145, heatingColor);
		this.font.drawString(eggStatusString, 135 - (this.font.getStringWidth(eggStatusString) / 2), this.ySize - 110, eggStatusColor);
		this.font.drawString(heatStatusString, 45 - (this.font.getStringWidth(heatStatusString) / 2), this.ySize - 110, heatStatusColor);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.minecraft.getTextureManager().bindTexture(TEXTURE);
		int i = (this.width - this.xSize) / 2;
		int j = (this.height - this.ySize) / 2;
		this.blit(i, j, 0, 0, this.xSize, this.ySize);

		int k = this.getHeatingScaled(13);
		this.blit(i + 78, j + 36 + 12 - k, 176, 12 - k, 20, k);

	}

	private int getHeatingScaled(int pixels)
	{
		int i = this.container.getCurrentHeatingProgress();
		int j = REQ_TEMPERATURE_THRESHOLD;

		return j != 0 && i != 0 ? i * pixels / j : 0;
	}

	@Override
	public void render(int mouseX, int mouseY, float partialTicks)
	{
		this.renderBackground();
		super.render(mouseX, mouseY, partialTicks);
		this.renderHoveredToolTip(mouseX, mouseY);
	}
}
