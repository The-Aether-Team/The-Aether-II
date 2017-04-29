package com.gildedgames.aether.client.models.entities.player;

import com.gildedgames.aether.api.player.IPlayerAether;
import com.gildedgames.aether.common.capabilities.player.PlayerAether;
import com.gildedgames.aether.common.items.armor.ItemAetherGloves;
import com.gildedgames.aether.common.items.armor.ItemLeatherGloves;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

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

			ModelBiped t = this.modelArmor;

			t.bipedBody.showModel = true;
			t.bipedRightLeg.showModel = true;
			t.bipedLeftLeg.showModel = true;

			t.setModelAttributes(this.renderer.getMainModel());
			t.setLivingAnimations(player.getEntity(), limbSwing, limbSwingAmount, partialTicks);

			GlStateManager.pushMatrix();

			GlStateManager.scale(0.92F, 0.92F, 0.92F);
			GlStateManager.translate(0, 0.01F, 0);

			if (skinType.equals("slim"))
			{
				GlStateManager.translate(0, 0.05F, 0);
			}

			if (glove instanceof ItemLeatherGloves)
			{
				int color = ItemLeatherGloves.getColor(stack);

				float r = (float) (color >> 16 & 255) / 255.0F;
				float g = (float) (color >> 8 & 255) / 255.0F;
				float b = (float) (color & 255) / 255.0F;

				GlStateManager.color(1.0f * r, 1.0f * g, 1.0f * b, 1.0f);

				this.renderer.bindTexture(glove.getGloveTexture(0));
				t.render(player.getEntity(), limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);

				GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);

				this.renderer.bindTexture(glove.getGloveTexture(1));
				t.render(player.getEntity(), limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
			}
			else
			{
				this.renderer.bindTexture(glove.getGloveTexture(0));

				t.render(player.getEntity(), limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
			}

			GlStateManager.popMatrix();
		}
	}
}
