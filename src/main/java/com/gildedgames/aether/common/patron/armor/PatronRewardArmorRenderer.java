package com.gildedgames.aether.common.patron.armor;

import com.gildedgames.aether.api.patron.IPatronRewardRenderer;
import com.gildedgames.aether.common.items.ItemsAether;
import com.gildedgames.aether.common.items.armor.ItemAetherArmor;
import com.gildedgames.orbis_api.util.InputHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

public class PatronRewardArmorRenderer implements IPatronRewardRenderer
{
	private PatronRewardArmor reward;

	private AbstractClientPlayer player;

	public PatronRewardArmorRenderer(PatronRewardArmor reward)
	{
		this.reward = reward;
	}

	@Override
	public void renderInit()
	{
		this.player = Minecraft.getMinecraft().player;
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

		RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
		rendermanager.setPlayerViewY(180.0F);

		GlStateManager.scale(-10.0, 10.0, 10.0);

		rendermanager.setRenderShadow(false);

		String texture = this.reward.getArmorTextureName();

		ItemStack head = this.player.getItemStackFromSlot(EntityEquipmentSlot.HEAD);
		ItemStack chest = this.player.getItemStackFromSlot(EntityEquipmentSlot.CHEST);
		ItemStack legs = this.player.getItemStackFromSlot(EntityEquipmentSlot.LEGS);
		ItemStack feet = this.player.getItemStackFromSlot(EntityEquipmentSlot.FEET);

		if (texture != null)
		{
			this.setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(ItemsAether.gravitite_helmet));
			this.setItemStackToSlot(EntityEquipmentSlot.CHEST, new ItemStack(ItemsAether.gravitite_chestplate));
			this.setItemStackToSlot(EntityEquipmentSlot.LEGS, new ItemStack(ItemsAether.gravitite_leggings));
			this.setItemStackToSlot(EntityEquipmentSlot.FEET, new ItemStack(ItemsAether.gravitite_boots));

			ItemAetherArmor.PATRON_TEXTURE_TEMP_OVERRIDE = this.reward.getArmorTextureName();
		}
		else
		{
			ItemAetherArmor.RENDER_NORMAL_TEMP = true;
		}

		rendermanager.renderEntity(this.player, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, false);

		if (texture != null)
		{
			ItemAetherArmor.PATRON_TEXTURE_TEMP_OVERRIDE = null;

			this.setItemStackToSlot(EntityEquipmentSlot.HEAD, head);
			this.setItemStackToSlot(EntityEquipmentSlot.CHEST, chest);
			this.setItemStackToSlot(EntityEquipmentSlot.LEGS, legs);
			this.setItemStackToSlot(EntityEquipmentSlot.FEET, feet);
		}
		else
		{
			ItemAetherArmor.RENDER_NORMAL_TEMP = false;
		}

		rendermanager.setRenderShadow(true);

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
