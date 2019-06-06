package com.gildedgames.aether.client.gui.container.guidebook;

import com.gildedgames.aether.api.items.equipment.ItemEquipmentSlot;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.modules.PlayerEquipmentModule;
import com.gildedgames.aether.common.containers.guidebook.ContainerGuidebookInventory;
import com.gildedgames.aether.common.containers.slots.SlotEquipment;
import com.gildedgames.orbis.lib.client.gui.util.GuiTexture;
import com.gildedgames.orbis.lib.client.gui.util.gui_library.IGuiElement;
import com.gildedgames.orbis.lib.client.gui.util.gui_library.IGuiViewer;
import com.gildedgames.orbis.lib.client.rect.Dim2D;
import com.google.common.collect.Lists;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class GuiGuidebookInventory extends AbstractGuidebookPage
{

	private static final ResourceLocation LEFT_PAGE = AetherCore.getResource("textures/gui/guidebook/inventory/guidebook_inventory_left.png");

	private static final ResourceLocation RIGHT_PAGE_CREATIVE = AetherCore
			.getResource("textures/gui/guidebook/inventory/guidebook_inventory_right_creative.png");

	private static final ResourceLocation RIGHT_PAGE_SURVIVAL = AetherCore
			.getResource("textures/gui/guidebook/inventory/guidebook_inventory_right_survival.png");

	public GuiGuidebookInventory(final IGuiViewer prevViewer, final PlayerAether aePlayer)
	{
		super(prevViewer, aePlayer, new ContainerGuidebookInventory(aePlayer));
	}

	@Override
	public void drawScreen(final int mouseX, final int mouseY, final float partialTick)
	{
		super.drawScreen(mouseX, mouseY, partialTick);

		// TODO: Move out into gui element so has proper render order
		this.drawPlayer(mouseX, mouseY);

		this.drawEquipmentEffects();

		final String slotName = this.getSlotName(mouseX, mouseY);

		if (slotName != null)
		{
			this.setHoveredDescription(Lists.newArrayList(slotName));
		}

		this.renderHoveredToolTip(mouseX, mouseY);
	}

	@Override
	protected List<IGuiElement> createLeftPage(final int screenX, final int screenY, final float u, final float v)
	{
		final GuiTexture leftPage = new GuiTexture(Dim2D.build().width(this.PAGE_WIDTH).height(this.PAGE_HEIGHT).x(screenX).y(screenY).flush(),
				LEFT_PAGE);

		return Lists.newArrayList(leftPage);
	}

	@Override
	protected List<IGuiElement> createRightPage(final int screenX, final int screenY, final float u, final float v)
	{
		final GuiTexture rightPage = new GuiTexture(Dim2D.build().width(this.PAGE_WIDTH).height(this.PAGE_HEIGHT).x(screenX).y(screenY).flush(),
				this.aePlayer.getEntity().capabilities.isCreativeMode ? RIGHT_PAGE_CREATIVE : RIGHT_PAGE_SURVIVAL);

		return Lists.newArrayList(rightPage);
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

	private String getSlotName(final int mouseX, final int mouseY)
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
			return I18n.format(unlocalizedTooltip);
		}

		return null;
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
