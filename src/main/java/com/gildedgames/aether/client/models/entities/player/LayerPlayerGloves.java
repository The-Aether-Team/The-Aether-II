package com.gildedgames.aether.client.models.entities.player;

import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.items.armor.ItemAetherGloves;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public class LayerPlayerGloves extends LayerBipedArmor
{
	private final RenderLivingBase<?> renderer;

	private ModelPlayer modelArmorSlim;

	public LayerPlayerGloves(RenderLivingBase<?> rendererIn)
	{
		super(rendererIn);

		this.renderer = rendererIn;
		this.modelArmor = new ModelBiped(0.15f);
		this.modelArmorSlim = new ModelPlayer(0.15f, true);
	}

	@Override
	public void doRenderLayer(EntityLivingBase entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks,
			float netHeadYaw, float headPitch, float scale)
	{
		this.renderGloves(PlayerAether.getPlayer(entity), limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale);
	}

	private void renderGloves(PlayerAether player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks,
			float netHeadYaw, float headPitch, float scale)
	{
		ItemStack stack = player.getEquipmentModule().getInventory().getStackInSlot(2);

		if (stack.getItem() instanceof ItemAetherGloves)
		{
			ItemAetherGloves glove = (ItemAetherGloves) stack.getItem();

			String skinType = DefaultPlayerSkin.getSkinType(player.getEntity().getUniqueID());

			ModelBiped t = skinType.equals("slim") ? this.modelArmorSlim : this.modelArmor;

			t.bipedRightArm.showModel = true;
			t.bipedLeftArm.showModel = true;

			GlStateManager.pushMatrix();

			t.setModelAttributes(this.renderer.getMainModel());
			t.setLivingAnimations(player.getEntity(), limbSwing, limbSwingAmount, partialTicks);

			this.renderer.bindTexture(glove.getGloveTexture(player.getEntity()));

			t.render(player.getEntity(), limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);

			GlStateManager.popMatrix();
		}
	}
}
