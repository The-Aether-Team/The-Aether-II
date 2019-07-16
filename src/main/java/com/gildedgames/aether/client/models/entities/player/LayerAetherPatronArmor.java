package com.gildedgames.aether.client.models.entities.player;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.modules.PlayerPatronRewardModule;
import com.gildedgames.aether.common.patron.armor.PatronRewardArmor;
import com.google.common.collect.Maps;
import net.minecraft.client.model.ModelBiped;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.ResourceLocation;

import java.util.Map;

public class LayerAetherPatronArmor implements LayerRenderer<LivingEntity>
{
	private static final Map<String, ResourceLocation> ARMOR_TEXTURE_RES_MAP = Maps.newHashMap();

	private final RenderLivingBase<?> renderer;

	private PatronRewardArmor previewArmor;

	private ModelBiped modelLeggings;

	private ModelBiped modelArmor;

	public LayerAetherPatronArmor(RenderLivingBase<?> rendererIn)
	{
		this.renderer = rendererIn;

		this.initArmor();
	}

	public void setPreviewArmor(PatronRewardArmor armor)
	{
		this.previewArmor = armor;
	}

	@Override
	public void doRenderLayer(LivingEntity entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks,
			float netHeadYaw, float headPitch, float scale)
	{
		if (!(entity instanceof PlayerEntity))
		{
			return;
		}

		PatronRewardArmor armor = this.previewArmor;

		if (armor == null)
		{
			PlayerAether aePlayer = PlayerAether.getPlayer((PlayerEntity) entity);

			armor = aePlayer.getModule(PlayerPatronRewardModule.class).getChoices().getArmorChoice();
		}

		if (armor != null && armor.getArmorTextureName() != null)
		{
			this.renderArmorLayer(entity, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale, EquipmentSlotType.CHEST,
					armor.getArmorTextureName());
			this.renderArmorLayer(entity, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale, EquipmentSlotType.LEGS,
					armor.getArmorTextureName());
			this.renderArmorLayer(entity, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale, EquipmentSlotType.FEET,
					armor.getArmorTextureName());
			this.renderArmorLayer(entity, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale, EquipmentSlotType.HEAD,
					armor.getArmorTextureName());
		}
	}

	@Override
	public boolean shouldCombineTextures()
	{
		return false;
	}

	protected void renderArmorLayer(LivingEntity entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks,
			float netHeadYaw, float headPitch, float scale, EquipmentSlotType slot, String res)
	{
		ResourceLocation texture = ARMOR_TEXTURE_RES_MAP
				.computeIfAbsent(AetherCore.getResourcePath("textures/armor/" + res + "_layer_" + (slot == EquipmentSlotType.LEGS ? 2 : 1) + ".png"),
						ResourceLocation::new);

		ModelBiped model = this.getModelFromSlot(slot);

		model.setModelAttributes(this.renderer.getMainModel());
		model.setLivingAnimations(entity, limbSwing, limbSwingAmount, partialTicks);

		this.setModelSlotVisible(model, slot);

		this.renderer.bindTexture(texture);

		float alpha = 1.0F;
		float colorR = 1.0F;
		float colorG = 1.0F;
		float colorB = 1.0F;
		GlStateManager.color4f(colorR, colorG, colorB, alpha);
		model.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
	}

	private ModelBiped getModelFromSlot(EquipmentSlotType slotIn)
	{
		return (this.isLegSlot(slotIn) ? this.modelLeggings : this.modelArmor);
	}

	private boolean isLegSlot(EquipmentSlotType slotIn)
	{
		return slotIn == EquipmentSlotType.LEGS;
	}

	private void initArmor()
	{
		this.modelLeggings = new ModelBiped(0.5F);
		this.modelArmor = new ModelBiped(1.0F);
	}

	private void setModelSlotVisible(ModelBiped model, EquipmentSlotType slot)
	{
		this.setModelVisible(model);

		switch (slot)
		{
			case HEAD:
				model.bipedHead.showModel = true;
				model.bipedHeadwear.showModel = true;
				break;
			case CHEST:
				model.bipedBody.showModel = true;
				model.bipedRightArm.showModel = true;
				model.bipedLeftArm.showModel = true;
				break;
			case LEGS:
				model.bipedBody.showModel = true;
				model.bipedRightLeg.showModel = true;
				model.bipedLeftLeg.showModel = true;
				break;
			case FEET:
				model.bipedRightLeg.showModel = true;
				model.bipedLeftLeg.showModel = true;
		}
	}

	private void setModelVisible(ModelBiped model)
	{
		model.setVisible(false);
	}
}
