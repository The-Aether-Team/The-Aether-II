package com.gildedgames.aether.client.gui.container;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.containers.tiles.ContainerIncubator;
import com.gildedgames.aether.common.entities.tiles.TileEntityIcestoneCooler;
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

	public GuiIncubator(InventoryPlayer playerInv, IInventory incubatorInv)
	{
		super(new ContainerIncubator(playerInv, incubatorInv));

		this.playerInventory = playerInv;
		this.tileIncubator = incubatorInv;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		this.drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		this.renderHoveredToolTip(mouseX, mouseY);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		String s = this.tileIncubator.getDisplayName().getUnformattedText();
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

		if (TileEntityIncubator.isHeating(this.tileIncubator))
		{
			int k = this.getHeatingProgressScaled(13);

			this.drawTexturedModalRect(i + 65, j + 36 + k, 176, k, 20, 13);
		}

		if (TileEntityIncubator.isIncubating(this.tileIncubator))
		{
			int l = this.getIncubationProgressScaled(55);

			this.drawTexturedModalRect(i + 101, j + 36 + 33 - l, 196, 54 - l, 10, l + 1);
		}
	}

	private int getHeatingProgressScaled(int pixels)
	{
		int i = this.tileIncubator.getField(3);
		int j = this.tileIncubator.getField(2);
		return j != 0 && i != 0 ? i * pixels / j : 0;
	}

	private int getIncubationProgressScaled(int pixels)
	{
		int i = this.tileIncubator.getField(1);
		int j = this.tileIncubator.getField(0);
		return j != 0 && i != 0 ? i * pixels / j : 0;
	}
}
