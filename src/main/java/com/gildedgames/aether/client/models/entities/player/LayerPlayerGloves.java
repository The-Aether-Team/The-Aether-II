package com.gildedgames.aether.client.models.entities.player;

import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.items.armor.ItemAetherGloves;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHandSide;

public class LayerPlayerGloves extends LayerBipedArmor
{
	private final RenderLivingBase<?> renderer;

	public LayerPlayerGloves(RenderLivingBase<?> rendererIn)
	{
		super(rendererIn);

		this.renderer = rendererIn;
		this.modelArmor = new ModelBiped(1.0f);
	}

	@Override
	public void doRenderLayer(EntityLivingBase entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks,
			float netHeadYaw, float headPitch, float scale)
	{
		this.renderGloves(PlayerAether.getPlayer(entity), limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale,
				EnumHandSide.RIGHT);
		this.renderGloves(PlayerAether.getPlayer(entity), limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale,
				EnumHandSide.LEFT);
	}

	private void renderGloves(PlayerAether player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks,
			float netHeadYaw, float headPitch, float scale, EnumHandSide side)
	{
		ItemStack stack = player.getEquipmentModule().getInventory().getStackInSlot(2);

		if (stack.getItem() instanceof ItemAetherGloves)
		{
			ItemAetherGloves glove = (ItemAetherGloves) stack.getItem();

			String skinType = DefaultPlayerSkin.getSkinType(player.getEntity().getUniqueID());

			ModelBiped t = this.modelArmor;

			boolean left = side == EnumHandSide.RIGHT;
			boolean right = side == EnumHandSide.LEFT;

			t.bipedRightArm.showModel = left;
			t.bipedLeftArm.showModel = right;

			t.setModelAttributes(this.renderer.getMainModel());
			t.setLivingAnimations(player.getEntity(), limbSwing, limbSwingAmount, partialTicks);

			GlStateManager.pushMatrix();

			if (skinType.equals("slim"))
			{
				GlStateManager.scale(0.7F, 1F, 0.7F);
				GlStateManager.translate(left ? -0.15 : 0.15, 0.06F, -0.03F);
			}
			else
			{
				GlStateManager.scale(0.725F, 1F, 0.725F);
				GlStateManager.translate(left ? -0.17 : 0.17, 0.01F, -0.03F);
			}

			this.renderer.bindTexture(glove.getGloveTexture(player.getEntity()));

			t.render(player.getEntity(), limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);

			GlStateManager.popMatrix();
		}
	}
}
