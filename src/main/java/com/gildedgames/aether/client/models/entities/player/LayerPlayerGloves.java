package com.gildedgames.aether.client.models.entities.player;

import com.gildedgames.aether.api.capabilites.entity.IPlayerAether;
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

	private final ModelPlayer slim, normal;

	public LayerPlayerGloves(RenderLivingBase<?> rendererIn)
	{
		super(rendererIn);

		this.renderer = rendererIn;
		this.modelArmor = new ModelBiped(1.0f);

		this.slim = new ModelPlayer(1.0F, true);
		this.normal = new ModelPlayer(1.0F, false);
	}

	@Override
	public void doRenderLayer(EntityLivingBase entitylivingbaseIn, float p_177141_2_, float p_177141_3_, float partialTicks, float p_177141_5_, float p_177141_6_, float p_177141_7_, float scale)
	{
		this.renderGloves((EntityPlayer) entitylivingbaseIn, p_177141_2_, p_177141_3_, partialTicks, p_177141_5_, p_177141_6_, p_177141_7_, scale);
	}

	private void renderGloves(EntityPlayer entity, float p_177182_2_, float p_177182_3_, float partialTicks, float p_177182_5_, float p_177182_6_, float p_177182_7_, float scale)
	{
		IPlayerAether aePlayer = PlayerAether.getPlayer(entity);

		ItemStack itemstack = aePlayer.getEquipmentInventory().getStackInSlot(2);

		if (itemstack != null && itemstack.getItem() instanceof ItemAetherGloves)
		{
			ItemAetherGloves glove = (ItemAetherGloves) itemstack.getItem();

			String skinType = DefaultPlayerSkin.getSkinType(entity.getUniqueID());

			ModelBiped t = this.modelArmor;

			t.bipedBody.showModel = true;
			t.bipedRightLeg.showModel = true;
			t.bipedLeftLeg.showModel = true;

			t.setModelAttributes(this.renderer.getMainModel());
			t.setLivingAnimations(entity, p_177182_2_, p_177182_3_, partialTicks);

			GlStateManager.pushMatrix();

			GlStateManager.scale(0.92F, 0.92F, 0.92F);
			GlStateManager.translate(0, 0.01F, 0);

			if (skinType == "slim")
			{
				GlStateManager.translate(0, 0.05F, 0);
			}

			if (glove instanceof ItemLeatherGloves)
			{
				ItemLeatherGloves leatherGloves = (ItemLeatherGloves) glove;

				int color = ItemLeatherGloves.getColor(itemstack);

				float r = (float) (color >> 16 & 255) / 255.0F;
				float g = (float) (color >> 8 & 255) / 255.0F;
				float b = (float) (color & 255) / 255.0F;

				GlStateManager.color(1.0f * r, 1.0f * g, 1.0f * b, 1.0f);

				this.renderer.bindTexture(glove.getGloveTexture(0));
				t.render(entity, p_177182_2_, p_177182_3_, p_177182_5_, p_177182_6_, p_177182_7_, scale);

				GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);

				this.renderer.bindTexture(glove.getGloveTexture(1));
				t.render(entity, p_177182_2_, p_177182_3_, p_177182_5_, p_177182_6_, p_177182_7_, scale);
			}
			else
			{
				this.renderer.bindTexture(glove.getGloveTexture(0));

				t.render(entity, p_177182_2_, p_177182_3_, p_177182_5_, p_177182_6_, p_177182_7_, scale);
			}

			GlStateManager.popMatrix();
		}
	}
}
