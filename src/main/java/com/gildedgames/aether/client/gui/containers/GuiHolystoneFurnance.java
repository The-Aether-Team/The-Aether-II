package com.gildedgames.aether.client.gui.containers;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerFurnace;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.ResourceLocation;

public class GuiHolystoneFurnance extends GuiContainer
{
	private static final ResourceLocation furnaceGuiTextures = new ResourceLocation("textures/gui/container/furnace.png");

    private final InventoryPlayer playerInventory;

	private IInventory tileFurnace;

	public GuiHolystoneFurnance(InventoryPlayer playerInv, IInventory furnaceInv)
    {
		super(new ContainerFurnace(playerInv, furnaceInv));

        this.playerInventory = playerInv;
        this.tileFurnace = furnaceInv;
	}

	@Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        String name = this.tileFurnace.getDisplayName().getUnformattedText();

		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
        this.fontRendererObj.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
    }

	@Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(furnaceGuiTextures);

        int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize) / 2;

        this.drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);

        int size;

        if (TileEntityFurnace.isBurning(this.tileFurnace))
        {
            size = this.getFlameIconHeight(13);

            this.drawTexturedModalRect(x + 56, y + 36 + 12 - size, 176, 12 - size, 14, size + 1);
        }

        size = this.getProgressBarLength(24);

		this.drawTexturedModalRect(x + 79, y + 34, 176, 14, size + 1, 16);
    }

    private int getProgressBarLength(int width)
    {
        int cookTime = this.tileFurnace.getField(2);
        int totalCookTime = this.tileFurnace.getField(3);

		return totalCookTime != 0 && cookTime != 0 ? cookTime * width / totalCookTime : 0;
    }

    private int getFlameIconHeight(int height)
    {
        int currentBurnTime = this.tileFurnace.getField(1);

        if (currentBurnTime == 0)
        {
            currentBurnTime = 200;
        }

        return this.tileFurnace.getField(0) * height / currentBurnTime;
    }
}
