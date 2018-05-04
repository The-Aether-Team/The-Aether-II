package com.gildedgames.aether.common.patron.armor;

import com.gildedgames.aether.api.patron.IPatronRewardRenderer;
import com.gildedgames.aether.common.items.ItemsAether;
import com.gildedgames.aether.common.items.armor.ItemAetherArmor;
import com.gildedgames.orbis_api.util.InputHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

public class PatronRewardArmorRenderer implements IPatronRewardRenderer
{
	private PatronRewardArmor reward;

	private ModelBiped modelLeggings;

	private ModelBiped modelArmor;

	private AbstractClientPlayer fakePlayer;

	public PatronRewardArmorRenderer(PatronRewardArmor reward)
	{
		this.reward = reward;
	}

	@Override
	public void renderInit()
	{
		this.modelLeggings = new ModelBiped(0.5F);
		this.modelArmor = new ModelBiped(1.0F);

		this.fakePlayer = Minecraft.getMinecraft().player;
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

		float f = this.fakePlayer.renderYawOffset;
		float f1 = this.fakePlayer.rotationYaw;
		float f2 = this.fakePlayer.rotationPitch;
		float f3 = this.fakePlayer.prevRotationYawHead;
		float f4 = this.fakePlayer.rotationYawHead;
		GlStateManager.rotate(135.0F, 0.0F, 1.0F, 0.0F);
		RenderHelper.enableStandardItemLighting();
		GlStateManager.rotate(-135.0F, 0.0F, 1.0F, 0.0F);
		GlStateManager.rotate(-((float) Math.atan(mouseY / 40.0F)) * 20.0F, 1.0F, 0.0F, 0.0F);
		this.fakePlayer.renderYawOffset = (float) Math.atan(mouseX / 40.0F) * 20.0F;
		this.fakePlayer.rotationYaw = (float) Math.atan(mouseX / 40.0F) * 40.0F;
		this.fakePlayer.rotationPitch = -((float) Math.atan(mouseY / 40.0F)) * 20.0F;
		this.fakePlayer.rotationYawHead = this.fakePlayer.rotationYaw;
		this.fakePlayer.prevRotationYawHead = this.fakePlayer.rotationYaw;
		GlStateManager.translate(0.0F, 0.0F, 0.0F);

		RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
		rendermanager.setPlayerViewY(180.0F);

		GlStateManager.scale(-10.0, 10.0, 10.0);

		rendermanager.setRenderShadow(false);

		ItemStack head = this.fakePlayer.getItemStackFromSlot(EntityEquipmentSlot.HEAD);
		ItemStack chest = this.fakePlayer.getItemStackFromSlot(EntityEquipmentSlot.CHEST);
		ItemStack legs = this.fakePlayer.getItemStackFromSlot(EntityEquipmentSlot.LEGS);
		ItemStack feet = this.fakePlayer.getItemStackFromSlot(EntityEquipmentSlot.FEET);

		this.fakePlayer.setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(ItemsAether.gravitite_helmet));
		this.fakePlayer.setItemStackToSlot(EntityEquipmentSlot.CHEST, new ItemStack(ItemsAether.gravitite_chestplate));
		this.fakePlayer.setItemStackToSlot(EntityEquipmentSlot.LEGS, new ItemStack(ItemsAether.gravitite_leggings));
		this.fakePlayer.setItemStackToSlot(EntityEquipmentSlot.FEET, new ItemStack(ItemsAether.gravitite_boots));

		ItemAetherArmor.PATRON_TEXTURE_TEMP_OVERRIDE = this.reward.getArmorTextureName();

		rendermanager.renderEntity(this.fakePlayer, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, false);

		ItemAetherArmor.PATRON_TEXTURE_TEMP_OVERRIDE = null;

		this.fakePlayer.setItemStackToSlot(EntityEquipmentSlot.HEAD, head);
		this.fakePlayer.setItemStackToSlot(EntityEquipmentSlot.CHEST, chest);
		this.fakePlayer.setItemStackToSlot(EntityEquipmentSlot.LEGS, legs);
		this.fakePlayer.setItemStackToSlot(EntityEquipmentSlot.FEET, feet);

		rendermanager.setRenderShadow(true);

		this.fakePlayer.renderYawOffset = f;
		this.fakePlayer.rotationYaw = f1;
		this.fakePlayer.rotationPitch = f2;
		this.fakePlayer.prevRotationYawHead = f3;
		this.fakePlayer.rotationYawHead = f4;

		RenderHelper.disableStandardItemLighting();
		GlStateManager.disableRescaleNormal();
		GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
		GlStateManager.disableTexture2D();
		GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);

		GlStateManager.popMatrix();
	}
}
