package com.gildedgames.aether.client.gui.container.guidebook;

import com.gildedgames.aether.api.items.equipment.ItemEquipmentSlot;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.modules.PlayerEquipmentModule;
import com.gildedgames.aether.common.containers.guidebook.ContainerGuidebookInventory;
import com.gildedgames.aether.common.containers.slots.SlotEquipment;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.screen.inventory.InventoryScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.container.Slot;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collections;

public class GuiGuidebookInventory extends AbstractGuidebookPage<ContainerGuidebookInventory>
{

	private static final ResourceLocation LEFT_PAGE = AetherCore.getResource("textures/gui/guidebook/inventory/guidebook_inventory_left.png");

	private static final ResourceLocation RIGHT_PAGE_CREATIVE = AetherCore.getResource("textures/gui/guidebook/inventory/guidebook_inventory_right_creative.png");

	private static final ResourceLocation RIGHT_PAGE_SURVIVAL = AetherCore.getResource("textures/gui/guidebook/inventory/guidebook_inventory_right_survival.png");

	public GuiGuidebookInventory(final PlayerAether aePlayer)
	{
		super(new ContainerGuidebookInventory(aePlayer), aePlayer, new StringTextComponent("Guidebook Inventory Page"));
	}

	@Override
	public void render(final int mouseX, final int mouseY, final float partialTick)
	{
		this.drawPlayer(mouseX, mouseY);

		this.drawEquipmentEffects();

		super.render(mouseX,mouseY, partialTick);

		this.drawSlotName(mouseX, mouseY);
	}

	@Override
	protected void drawLeftPage(int screenX, int screenY, float u, float v)
	{
		this.minecraft.getTextureManager().bindTexture(LEFT_PAGE);

		GlStateManager.color4f(1.0f, 1.0f, 1.0f, 1.0f);

		AbstractGui.blit(screenX, screenY, u, v, this.PAGE_WIDTH, this.PAGE_HEIGHT, this.TEXTURE_WIDTH, this.TEXTURE_HEIGHT);
	}

	@Override
	protected void drawRightPage(int screenX, int screenY, float u, float v)
	{
		this.minecraft.getTextureManager().bindTexture(this.aePlayer.getEntity().isCreative() ? RIGHT_PAGE_CREATIVE : RIGHT_PAGE_SURVIVAL);

		GlStateManager.color4f(1.0f, 1.0f, 1.0f, 1.0f);

		AbstractGui.blit(screenX, screenY, u ,v, this.PAGE_WIDTH, this.PAGE_HEIGHT,this.TEXTURE_WIDTH, this.TEXTURE_HEIGHT);
	}

	private void drawPlayer(final int mouseX, final int mouseY)
	{
		GlStateManager.color4f(1.0f, 1.0f, 1.0f, 1.0f);

		InventoryScreen.drawEntityOnScreen(
				this.width / 2 - 48,
				this.height / 2, 32, (this.guiLeft + 88) - mouseX, (this.guiTop + 35) - mouseY, this.minecraft.player);
	}

	private boolean isMouseOverSlot(final Slot slot, final int mouseX, final int mouseY)
	{
		return this.isPointInRegion(slot.xPos, slot.yPos, 16, 16, mouseX, mouseY);
	}

	private void drawSlotName(final int mouseX, final int mouseY)
	{
		String unlocalizedTooltip = null;

		for (final Slot slot : this.container.inventorySlots)
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
			this.renderTooltip(Collections.singletonList(I18n.format(unlocalizedTooltip)), mouseX, mouseY, this.font);
		}
	}

	private void drawEquipmentEffects()
	{
		final ArrayList<String> label = new ArrayList<>();

		final PlayerEquipmentModule equipment = this.aePlayer.getModule(PlayerEquipmentModule.class);
		equipment.getActivePools().forEach((pool) -> pool.getInstance().ifPresent(instance -> instance.addInformation(label)));

		final String compiled = StringUtils.join(label, TextFormatting.RESET + ", ");

		this.font.drawStringWithShadow(compiled, this.guiLeft, this.guiTop + 160, 0xFFFFFF);
	}
}
