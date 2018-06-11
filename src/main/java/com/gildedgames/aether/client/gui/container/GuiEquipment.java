package com.gildedgames.aether.client.gui.container;

import com.gildedgames.aether.api.items.equipment.ItemEquipmentSlot;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.modules.PlayerEquipmentModule;
import com.gildedgames.aether.common.containers.ContainerEquipment;
import com.gildedgames.aether.common.containers.slots.SlotEquipment;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collections;

public class GuiEquipment extends GuiContainer
{
	// TODO: COINBAR AND COMPANIONS-- SEE TRELLO

	private static final ResourceLocation textureAccessories = AetherCore.getResource("textures/gui/inventory/accessories/equipment.png");

	private static final ResourceLocation textureAccessoriesPattern = AetherCore.getResource("textures/gui/inventory/accessories/equipment_pattern.png");

	private static final ResourceLocation textureBackpack = AetherCore.getResource("textures/gui/inventory/accessories/backpack.png");

	private static final ResourceLocation textureBackpackCreative = AetherCore.getResource("textures/gui/inventory/accessories/backpack_creative.png");

	private static final ResourceLocation textureBackpackPattern = AetherCore.getResource("textures/gui/inventory/accessories/backpack_pattern.png");

	private static final ResourceLocation textureBackpackCreativePattern = AetherCore.getResource("textures/gui/inventory/accessories/backpack_creative_pattern.png");

	//	private static final ResourceLocation TEXTURE_COINBAR = AetherCore.getResource("textures/gui/coinbar.png");

	private final PlayerAether aePlayer;

	public GuiEquipment(final PlayerAether aePlayer)
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

		this.xSize = 179 * 2;
		this.ySize = 169;
	}

	@Override
	public void drawScreen(final int mouseX, final int mouseY, final float partialTick)
	{
		this.drawWorldBackground(0);

		this.mc.renderEngine.bindTexture(textureAccessories);

		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);

		this.drawTexturedModalRect(this.width / 2 - 90 - 179 / 2, this.height / 2 - 169 / 2, 0, 0, 179, 169);

		if (AetherCore.CONFIG.getDisplayInventoryPattern())
		{
			this.mc.renderEngine.bindTexture(textureAccessoriesPattern);

			GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);

			this.drawTexturedModalRect(this.width / 2 - 90 - 179 / 2, this.height / 2 - 169 / 2, 0, 0, 179, 169);
		}

		this.mc.renderEngine.bindTexture(this.aePlayer.getEntity().capabilities.isCreativeMode ? textureBackpackCreative : textureBackpack);

		this.drawTexturedModalRect(this.width / 2 + 90 - 176 / 2, this.height / 2 - 166 / 2, 0, 0, 176, 166);

		this.fontRenderer.drawString(I18n.format("container.crafting"),
				this.width / 2 + (this.aePlayer.getEntity().capabilities.isCreativeMode ? 70 : 51), this.height / 2 - 135 / 2, 4210752);

		if (AetherCore.CONFIG.getDisplayInventoryPattern())
		{
			this.mc.renderEngine.bindTexture(
					this.aePlayer.getEntity().capabilities.isCreativeMode ? textureBackpackCreativePattern : textureBackpackPattern);

			GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);

			this.drawTexturedModalRect(this.width / 2 + 90 - 176 / 2, this.height / 2 - 166 / 2, 0, 0, 176, 166);
		}

		this.drawPlayer(mouseX, mouseY);

		this.drawEquipmentEffects();

		super.drawScreen(mouseX, mouseY, partialTick);

		// this.drawCoinCounter();

		this.drawSlotName(mouseX, mouseY);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(final float partialTicks, final int mouseX, final int mouseY)
	{
	}

	@Override
	public void drawDefaultBackground()
	{
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

	private void drawPlayer(final int mouseX, final int mouseY)
	{
		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);

		GuiInventory.drawEntityOnScreen(
				this.width / 2 - 48,
				this.height / 2 + 10, 37, (this.guiLeft + 88) - mouseX, (this.guiTop + 42) - mouseY, this.mc.player);
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
