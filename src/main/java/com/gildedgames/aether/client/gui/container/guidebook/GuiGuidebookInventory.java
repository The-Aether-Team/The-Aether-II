package com.gildedgames.aether.client.gui.container.guidebook;

import com.gildedgames.aether.api.items.equipment.ItemEquipmentSlot;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.modules.PlayerEquipmentModule;
import com.gildedgames.aether.common.containers.guidebook.ContainerGuidebookInventory;
import com.gildedgames.aether.common.containers.slots.SlotEquipment;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collections;

public class GuiGuidebookInventory extends AbstractGuidebookPage
{

	private static final ResourceLocation LEFT_PAGE = AetherCore.getResource("textures/gui/guidebook/inventory/guidebook_inventory_left.png");

	private static final ResourceLocation RIGHT_PAGE_CREATIVE = AetherCore.getResource("textures/gui/guidebook/inventory/guidebook_inventory_right_creative.png");

	private static final ResourceLocation RIGHT_PAGE_SURVIVAL = AetherCore.getResource("textures/gui/guidebook/inventory/guidebook_inventory_right_survival.png");

	public GuiGuidebookInventory(final PlayerAether aePlayer)
	{
		super(aePlayer, new ContainerGuidebookInventory(aePlayer));
	}

	@Override
	public void drawScreen(final int mouseX, final int mouseY, final float partialTick)
	{
		this.drawPlayer(mouseX, mouseY);

		this.drawEquipmentEffects();

		super.drawScreen(mouseX,mouseY, partialTick);

		this.drawSlotName(mouseX, mouseY);
	}

	@Override
	protected void drawLeftPage()
	{
		this.mc.renderEngine.bindTexture(LEFT_PAGE);

		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);

		Gui.drawModalRectWithCustomSizedTexture((this.width/2) - 176 - 11, this.height/2 - 185/2, 0, 0, this.PAGE_WIDTH, this.PAGE_HEIGHT, this.TEXTURE_WIDTH, this.TEXTURE_HEIGHT);
	}

	@Override
	protected void drawRightPage()
	{
		this.mc.renderEngine.bindTexture(this.aePlayer.getEntity().capabilities.isCreativeMode ? RIGHT_PAGE_CREATIVE : RIGHT_PAGE_SURVIVAL);

		int rightPageCoordX = (this.width/2) - 12;
		int rightPageCoordY = (this.height/2) - (185/2);

		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);

		Gui.drawModalRectWithCustomSizedTexture(rightPageCoordX, rightPageCoordY, this.PAGE_WIDTH - 13 ,0, this.PAGE_WIDTH, this.PAGE_HEIGHT,this.TEXTURE_WIDTH, this.TEXTURE_HEIGHT);
	}

	private void drawPlayer(final int mouseX, final int mouseY)
	{
		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);

		GuiInventory.drawEntityOnScreen(
				this.width / 2 - 48,
				this.height / 2, 32, (this.guiLeft + 88) - mouseX, (this.guiTop + 35) - mouseY, this.mc.player);
	}

	private boolean isMouseOverSlot(final Slot slot, final int mouseX, final int mouseY)
	{
		return this.isPointInRegion(slot.xPos, slot.yPos, 16, 16, mouseX, mouseY);
	}

	private void drawSlotName(final int mouseX, final int mouseY)
	{
		String unlocalizedTooltip = null;

		for (final Slot slot : this.inventorySlots.inventorySlots)
		{
			if (slot.isEnabled() && !slot.getHasStack())
			{
				if (this.isMouseOverSlot(slot, mouseX, mouseY))
				{
					if (slot instanceof SlotEquipment)
					{
						final ItemEquipmentSlot type = ((SlotEquipment) slot).getEquipmentType();

						unlocalizedTooltip = type.getUnlocalizedName();
					}

					final int dif = this.aePlayer.getEntity().inventory.getSizeInventory() - 2;

					if (slot.getSlotIndex() == dif + 1)
					{
						unlocalizedTooltip = "gui.aether.slot.offhand";
					}

					if (slot.getSlotIndex() == dif)
					{
						unlocalizedTooltip = "gui.aether.slot.helmet";
					}

					if (slot.getSlotIndex() == dif - 1)
					{
						unlocalizedTooltip = "gui.aether.slot.chestplate";
					}

					if (slot.getSlotIndex() == dif - 2)
					{
						unlocalizedTooltip = "gui.aether.slot.leggings";
					}

					if (slot.getSlotIndex() == dif - 3)
					{
						unlocalizedTooltip = "gui.aether.slot.boots";
					}

					break;
				}
			}
		}

		if (unlocalizedTooltip != null)
		{
			this.drawHoveringText(Collections.singletonList(I18n.format(unlocalizedTooltip)), mouseX, mouseY, this.mc.fontRenderer);
		}
	}

	private void drawEquipmentEffects()
	{
		final ArrayList<String> label = new ArrayList<>();

		final PlayerEquipmentModule equipment = this.aePlayer.getEquipmentModule();
		equipment.getActivePools().forEach((pool) -> pool.getInstance().ifPresent(instance -> instance.addInformation(label)));

		final String compiled = StringUtils.join(label, TextFormatting.RESET + ", ");

		this.mc.fontRenderer.drawString(compiled, this.guiLeft, this.guiTop + 160, 0xFFFFFF, true);
	}
}
