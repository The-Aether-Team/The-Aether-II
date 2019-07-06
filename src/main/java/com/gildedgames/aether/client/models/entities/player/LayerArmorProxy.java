package com.gildedgames.aether.client.models.entities.player;

import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.modules.PlayerPatronRewardModule;
import com.gildedgames.aether.common.patron.armor.PatronRewardArmor;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

// Delegate Hack
public class LayerArmorProxy extends LayerBipedArmor
{
	private final LayerBipedArmor proxy;

	private PatronRewardArmor previewArmor;

	public LayerArmorProxy(RenderLivingBase<?> rendererIn, LayerBipedArmor proxy)
	{
		super(rendererIn);

		this.proxy = proxy;
	}

	public void setPreviewArmor(PatronRewardArmor armor)
	{
		this.previewArmor = armor;
	}

	@Override
	public void doRenderLayer(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks,
			float ageInTicks, float netHeadYaw, float headPitch, float scale)
	{
		if (this.previewArmor != null)
		{
			return;
		}

		PlayerAether aePlayer = PlayerAether.getPlayer(entitylivingbaseIn);
		PatronRewardArmor armor = aePlayer.getModule(PlayerPatronRewardModule.class).getChoices().getArmorChoice();

		if (armor == null || armor.getArmorTextureName() == null)
		{
			this.proxy.doRenderLayer(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale);
		}
	}

	@Override
	public boolean shouldCombineTextures()
	{
		return this.proxy.shouldCombineTextures();
	}

	@Override
	public ModelBiped getModelFromSlot(EntityEquipmentSlot slotIn)
	{
		return this.proxy.getModelFromSlot(slotIn);
	}

	@Override
	public ResourceLocation getArmorResource(Entity entity, ItemStack stack, EntityEquipmentSlot slot, String type)
	{
		return this.proxy.getArmorResource(entity, stack, slot, type);
	}
}
