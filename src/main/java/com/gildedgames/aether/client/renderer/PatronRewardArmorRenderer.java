package com.gildedgames.aether.client.renderer;

import com.gildedgames.aether.api.items.equipment.ItemEquipmentSlot;
import com.gildedgames.aether.api.patron.IPatronRewardRenderer;
import com.gildedgames.aether.api.player.inventory.IInventoryEquipment;
import com.gildedgames.aether.client.models.entities.player.LayerAetherPatronArmor;
import com.gildedgames.aether.client.models.entities.player.LayerAetherPlayerGloves;
import com.gildedgames.aether.client.models.entities.player.LayerArmorProxy;
import com.gildedgames.aether.client.models.entities.player.LayerHeadShadow;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.patron.armor.PatronRewardArmor;
import com.gildedgames.aether.common.util.helpers.EntityUtil;
import com.gildedgames.orbis.lib.util.InputHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class PatronRewardArmorRenderer implements IPatronRewardRenderer
{
	private final PatronRewardArmor reward;

	private AbstractClientPlayer player;

	private PlayerAether playerAether;

	private RenderPlayer renderPlayer;

	private LayerAetherPatronArmor armorPreviewLayer;

	private LayerAetherPlayerGloves glovePreviewLayer;

	private LayerHeadShadow shadowPreviewLayer;

	private LayerArmorProxy vanillaArmorPreviewLayer;

	public PatronRewardArmorRenderer(PatronRewardArmor reward)
	{
		this.reward = reward;
	}

	private ItemStack getGloves()
	{
		IInventoryEquipment inventory = this.playerAether.getEquipmentModule().getInventory();

		final int slot = inventory.getNextEmptySlotForType(ItemEquipmentSlot.HANDWEAR);

		if (slot >= 0)
		{
			return inventory.getStackInSlot(slot);
		}

		return ItemStack.EMPTY;
	}

	private void setGloves(ItemStack stack)
	{
		IInventoryEquipment inventory = this.playerAether.getEquipmentModule().getInventory();

		inventory.setInventorySlotContents(2, stack);
	}

	@Override
	public void renderInit()
	{
		this.player = Minecraft.getMinecraft().player;
		this.playerAether = PlayerAether.getPlayer(this.player);

		this.renderPlayer = new RenderPlayer(Minecraft.getMinecraft().getRenderManager(), EntityUtil.getSkin(this.player).equals("slim"));

		List<LayerRenderer<?>> original = new ArrayList<>(this.renderPlayer.layerRenderers);
		List<LayerRenderer<?>> updated = new ArrayList<>();

		for (LayerRenderer<?> i : original)
		{
			if (i instanceof LayerBipedArmor)
			{
				updated.add(this.vanillaArmorPreviewLayer = new LayerArmorProxy(this.renderPlayer, (LayerBipedArmor) i));
			}
			else
			{
				updated.add(i);
			}
		}

		updated.add(this.armorPreviewLayer = new LayerAetherPatronArmor(this.renderPlayer));
		updated.add(this.glovePreviewLayer = new LayerAetherPlayerGloves(this.renderPlayer));
		updated.add(this.shadowPreviewLayer = new LayerHeadShadow(this.renderPlayer));

		original.clear();
		original.addAll(updated);
	}

	@Override
	public void renderPreview(int posX, int posY)
	{
		GlStateManager.pushMatrix();

		GlStateManager.enableColorMaterial();

		double mouseX = posX - InputHelper.getMouseX();
		double mouseY = posY - InputHelper.getMouseY() - 42;

		GlStateManager.translate((float) posX, (float) posY, 50.0F);

		GlStateManager.scale(4.0, 4.0, 4.0);

		GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);

		float f = this.player.renderYawOffset;
		float f1 = this.player.rotationYaw;
		float f2 = this.player.rotationPitch;
		float f3 = this.player.prevRotationYawHead;
		float f4 = this.player.rotationYawHead;

		GlStateManager.rotate(135.0F, 0.0F, 1.0F, 0.0F);

		RenderHelper.enableStandardItemLighting();

		GlStateManager.rotate(-135.0F, 0.0F, 1.0F, 0.0F);
		GlStateManager.rotate(-((float) Math.atan(mouseY / 40.0F)) * 20.0F, 1.0F, 0.0F, 0.0F);

		this.player.renderYawOffset = (float) Math.atan(mouseX / 40.0F) * 20.0F;
		this.player.rotationYaw = (float) Math.atan(mouseX / 40.0F) * 40.0F;
		this.player.rotationPitch = -((float) Math.atan(mouseY / 40.0F)) * 20.0F;
		this.player.rotationYawHead = this.player.rotationYaw;
		this.player.prevRotationYawHead = this.player.rotationYaw;

		GlStateManager.translate(0.0F, 0.0F, 0.0F);
		GlStateManager.scale(-10.0, 10.0, 10.0);

		this.armorPreviewLayer.setPreviewArmor(this.reward);
		this.glovePreviewLayer.setPreviewArmor(this.reward);
		this.shadowPreviewLayer.setPreviewArmor(this.reward);
		this.vanillaArmorPreviewLayer.setPreviewArmor(this.reward);

		this.renderPlayer.doRender(this.player, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F);

		this.player.renderYawOffset = f;
		this.player.rotationYaw = f1;
		this.player.rotationPitch = f2;
		this.player.prevRotationYawHead = f3;
		this.player.rotationYawHead = f4;

		RenderHelper.disableStandardItemLighting();

		GlStateManager.disableRescaleNormal();
		GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
		GlStateManager.disableTexture2D();
		GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);

		GlStateManager.popMatrix();
	}

	public void setItemStackToSlot(EntityEquipmentSlot slotIn, ItemStack stack)
	{
		if (slotIn == EntityEquipmentSlot.MAINHAND)
		{
			this.player.inventory.mainInventory.set(this.player.inventory.currentItem, stack);
		}
		else if (slotIn == EntityEquipmentSlot.OFFHAND)
		{
			this.player.inventory.offHandInventory.set(0, stack);
		}
		else if (slotIn.getSlotType() == EntityEquipmentSlot.Type.ARMOR)
		{
			this.player.inventory.armorInventory.set(slotIn.getIndex(), stack);
		}
	}
}
