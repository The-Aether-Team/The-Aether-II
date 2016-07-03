package com.gildedgames.aether.client.gui.container;

import com.gildedgames.aether.common.containers.ContainerEquipment;
import com.gildedgames.aether.common.containers.slots.SlotEquipment;
import com.gildedgames.aether.api.player.IPlayerAetherCapability;
import com.gildedgames.aether.api.items.properties.ItemEquipmentType;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;

import java.util.Collections;

public class GuiEquipment extends GuiContainer
{
	// TODO: COINBAR AND COMPANIONS-- SEE TRELLO

	private static final ResourceLocation textureAccessories = new ResourceLocation("aether", "textures/gui/inventory/accessories/equipment.png");

	//private static final ResourceLocation textureAccessoriesPattern = new ResourceLocation("aether", "textures/gui/inventory/accessories/equipment_pattern.png");

	private static final ResourceLocation textureBackpack = new ResourceLocation("aether", "textures/gui/inventory/accessories/backpack.png");

	private static final ResourceLocation textureBackpackCreative = new ResourceLocation("aether", "textures/gui/inventory/accessories/backpack_creative.png");

	//private static final ResourceLocation textureBackpackPattern = new ResourceLocation("aether", "textures/gui/inventory/accessories/backpack_pattern.png");

	//private static final ResourceLocation textureBackpackCreativePattern = new ResourceLocation("aether", "textures/gui/inventory/accessories/backpack_creative_pattern.png");

//	private static final ResourceLocation TEXTURE_COINBAR = new ResourceLocation("aether", "textures/gui/coinbar.png");

	private final IPlayerAetherCapability aePlayer;

	public GuiEquipment(IPlayerAetherCapability aePlayer)
	{
		super(new ContainerEquipment(aePlayer));

		this.allowUserInput = true;
		this.aePlayer = aePlayer;
	}

	@Override
	public void initGui()
	{
		super.initGui();

		this.guiLeft = this.width / 2 - 90 - (176 / 2);
		this.guiTop = this.height / 2 - (147 / 2);

		this.xSize = 176 * 2;
		this.ySize = 146;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTick)
	{
		this.drawWorldBackground(0);

		this.mc.renderEngine.bindTexture(textureAccessories);

		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);

		this.drawTexturedModalRect(this.width / 2 - 90 - 170 / 2, this.height / 2 - 123 / 2, 0, 0, 183, 171);

		//this.mc.renderEngine.bindTexture(textureAccessoriesPattern);

		//GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);

		//this.drawTexturedModalRect(this.width / 2 - 90 - 183 / 2, this.height / 2 - 166 / 2, 0, 0, 183, 171);

		this.mc.renderEngine.bindTexture(aePlayer.getPlayer().capabilities.isCreativeMode ? textureBackpackCreative : textureBackpack);

		this.drawTexturedModalRect(this.width / 2 + 90 - 176 / 2, this.height / 2 - 166 / 2, 0, 0, 176, 166);

		this.fontRendererObj.drawString(I18n.format("container.crafting"), this.width / 2 + (this.aePlayer.getPlayer().capabilities.isCreativeMode ? 70 : 51), this.height / 2 - 135 / 2, 4210752);

		//this.mc.renderEngine.bindTexture(aePlayer.getEntity().capabilities.isCreativeMode ? textureBackpackCreativePattern : textureBackpackPattern);

		//GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);

		//this.drawTexturedModalRect(this.width / 2 + 90 - 176 / 2, this.height / 2 - 166 / 2, 0, 0, 176, 166);

		this.drawPlayer(mouseX, mouseY);

		super.drawScreen(mouseX, mouseY, partialTick);

		// this.drawCoinCounter();

		this.drawSlotName(mouseX, mouseY);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
	}

	@Override
	public void drawDefaultBackground()
	{
	}

	private boolean isMouseOverSlot(Slot slot, int mouseX, int mouseY)
	{
		return this.isPointInRegion(slot.xDisplayPosition, slot.yDisplayPosition, 16, 16, mouseX, mouseY);
	}

	private void drawSlotName(int mouseX, int mouseY)
	{
		for (Slot slot : this.inventorySlots.inventorySlots)
		{
			if (slot.canBeHovered() && !slot.getHasStack())
			{
				if (this.isMouseOverSlot(slot, mouseX, mouseY))
				{
					String unlocalizedName = this.getSlotUnlocalizedName(slot);

					if (unlocalizedName != null)
					{
						this.drawHoveringText(Collections.singletonList(I18n.format(unlocalizedName)), mouseX, mouseY, this.mc.fontRendererObj);
					}
				}
			}
		}

	}

	private String getSlotUnlocalizedName(Slot slot)
	{
		if (slot instanceof SlotEquipment)
		{
			ItemEquipmentType type = ((SlotEquipment) slot).getEquipmentType();

			return type.getUnlocalizedName();
		}

		switch (slot.getSlotIndex())
		{
		case 40:
			return "gui.aether.slot.offhand";
		case 39:
			return "gui.aether.slot.helmet";
		case 38:
			return "gui.aether.slot.chestplate";
		case 37:
			return "gui.aether.slot.leggings";
		case 36:
			return "gui.aether.slot.boots";
		default:
			return null;
		}
	}

	private void drawPlayer(int mouseX, int mouseY)
	{
//		boolean hasCompanion = player.currentCompanion != null;
//		GuiInventory.drawEntityOnScreen(this.width / 2 - (hasCompanion ? 100 : 90), this.height / 2 + 40, 45, (this.guiLeft + 88) - mouseX, (this.guiTop + 42) - mouseY, this.mc.thePlayer);

		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);

		GuiInventory.drawEntityOnScreen(this.width / 2 - 48, this.height / 2 + 25, 35, (this.guiLeft + 88) - mouseX, (this.guiTop + 42) - mouseY, this.mc.thePlayer);

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
