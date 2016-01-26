package com.gildedgames.aether.client.gui.container;

import com.gildedgames.aether.common.containers.ContainerAccessories;
import com.gildedgames.aether.common.containers.slots.SlotAccessory;
import com.gildedgames.aether.common.items.accessories.AccessoryType;
import com.gildedgames.aether.common.player.PlayerAether;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemArmor;
import net.minecraft.util.ResourceLocation;

import java.util.Collections;

public class GuiAccessories extends GuiContainer
{
	// TODO: COINBAR AND COMPANIONS-- SEE TRELLO

	private static final ResourceLocation textureAccessories = new ResourceLocation("aether", "textures/gui/inventory/accessories/accessories.png");

	private static final ResourceLocation textureBackpack = new ResourceLocation("aether", "textures/gui/inventory/accessories/backpack.png");

	private static final ResourceLocation textureBackpackCreative = new ResourceLocation("aether", "textures/gui/inventory/accessories/backpack_creative.png");

//	private static final ResourceLocation TEXTURE_COINBAR = new ResourceLocation("aether", "textures/gui/coinbar.png");

	private final PlayerAether aePlayer;

	public GuiAccessories(PlayerAether aePlayer)
	{
		super(new ContainerAccessories(aePlayer));

		this.allowUserInput = true;
		this.aePlayer = aePlayer;
	}

	@Override
	public void initGui()
	{
		super.initGui();

		this.guiLeft = this.width / 2 - 90 - (176 / 2);
		this.guiTop = this.height / 2 - (146 / 2);

		this.xSize = 176 * 2;
		this.ySize = 150;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTick)
	{
		this.drawWorldBackground(0);

		this.mc.renderEngine.bindTexture(textureAccessories);

		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);

		this.drawTexturedModalRect(this.width / 2 - 90 - 176 / 2, this.height / 2 - 146 / 2, 0, 0, 176, 151);

		this.mc.renderEngine.bindTexture(aePlayer.getPlayer().capabilities.isCreativeMode ? textureBackpackCreative : textureBackpack);

		this.drawTexturedModalRect(this.width / 2 + 90 - 176 / 2, this.height / 2 - 166 / 2, 0, 0, 176, 166);

		this.fontRendererObj.drawString(I18n.format("container.crafting"), this.width / 2 + 88, this.height / 2 - 135 / 2, 4210752);

		super.drawScreen(mouseX, mouseY, partialTick);

		this.drawPlayer(mouseX, mouseY);
		// this.drawCoinCounter();

		this.drawSlotName(mouseX, mouseY);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) { }

	@Override
	public void drawDefaultBackground() { }

	private boolean isMouseOverSlot(Slot slot, int mouseX, int mouseY)
	{
		return this.isPointInRegion(slot.xDisplayPosition, slot.yDisplayPosition, 16, 16, mouseX, mouseY);
	}

	private void drawSlotName(int mouseX, int mouseY)
	{
		String unlocalizedTooltip = null;

		for (Slot slot : this.inventorySlots.inventorySlots)
		{
			if (slot.canBeHovered() && !slot.getHasStack())
			{
				if (this.isMouseOverSlot(slot, mouseX, mouseY))
				{
					if (slot instanceof SlotAccessory)
					{
						AccessoryType type = ((SlotAccessory) slot).getType();

						unlocalizedTooltip = type.getUnlocalizedName();
					}

					break;
				}
			}
		}

		if (unlocalizedTooltip != null)
		{
			this.drawHoveringText(Collections.singletonList(I18n.format(unlocalizedTooltip)), mouseX, mouseY, this.mc.fontRendererObj);
		}
	}

	private void drawPlayer(int mouseX, int mouseY)
	{
//		boolean hasCompanion = player.currentCompanion != null;
//		GuiInventory.drawEntityOnScreen(this.width / 2 - (hasCompanion ? 100 : 90), this.height / 2 + 40, 45, (this.guiLeft + 88) - mouseX, (this.guiTop + 42) - mouseY, this.mc.thePlayer);

		GuiInventory.drawEntityOnScreen(this.width / 2 - 90, this.height / 2 + 40, 45, (this.guiLeft + 88) - mouseX, (this.guiTop + 42) - mouseY, this.mc.thePlayer);

//		if (player.currentCompanion != null)
//		{
//			EntityCompanion.invRender = true;
//			GL11.glPushMatrix();
//			GL11.glScalef(0.5F, 0.5F, 0.5F);
//			GuiInventory.drawEntityOnScreen(this.width - 140, this.height + 90, 45, (this.guiLeft + 88) - mouseX, (this.guiTop + 42) - mouseY, player.currentCompanion);
//			GL11.glPopMatrix();
//			EntityCompanion.invRender = false;
//		}
	}

//	private void drawCoinCounter(/* ??? */)
//	{
//		int coinX = this.width / 2 - 89;
//		int coinY = this.height / 2 + 58;
//
//		int coinAmount = aePlayer.getAetherCoins();
//
//		this.mc.renderEngine.bindTexture(TEXTURE_COINBAR);
//
//		//drawTexturedModalRect(width / 2 - (71 / 2), dynamicY, 0, 0, 71, 15);
//		this.drawTexturedModalRect(coinX - ((this.mc.fontRendererObj.getStringWidth("x" + String.valueOf(coinAmount)) / 2) + 3) - (10 / 2), coinY + 1, 0, 15, 10, 10);
//
//		this.mc.fontRendererObj.drawStringWithShadow("x", coinX - ((this.mc.fontRendererObj.getStringWidth("x" + String.valueOf(coinAmount)) / 2) + 2) + 6, coinY + 1, 0xffffffff);
//		this.mc.fontRendererObj.drawStringWithShadow(String.valueOf(coinAmount), coinX - ((this.mc.fontRendererObj.getStringWidth("x" + String.valueOf(coinAmount)) / 2) + 2) + 13, coinY + 2, 0xffffffff);
//	}
}
