package com.gildedgames.aether.client.models.entities.player;

import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.items.armor.ItemAetherGloves;
import com.gildedgames.aether.common.patron.armor.PatronRewardArmor;
import com.gildedgames.aether.common.util.helpers.EntityUtil;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class LayerAetherPlayerGloves extends LayerBipedArmor
{
	private final RenderLivingBase<?> renderer;

	private PatronRewardArmor previewArmor;

	private ModelPlayer modelArmorSlim;

	public LayerAetherPlayerGloves(RenderLivingBase<?> rendererIn)
	{
		super(rendererIn);

		this.renderer = rendererIn;
		this.modelArmor = new ModelBiped(0.5f);
		this.modelArmorSlim = new ModelPlayer(0.5f, true);
	}

	public void setPreviewArmor(PatronRewardArmor armor)
	{
		this.previewArmor = armor;
	}

	@Override
	public void doRenderLayer(EntityLivingBase entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks,
			float netHeadYaw, float headPitch, float scale)
	{
		PlayerAether aePlayer = PlayerAether.getPlayer(entity);

		this.renderGloves(aePlayer, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale);
	}

	private void renderGloves(PlayerAether player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks,
			float netHeadYaw, float headPitch, float scale)
	{
		PatronRewardArmor armor = this.previewArmor;

		if (armor == null)
		{
			armor = player.getPatronRewardsModule().getChoices().getArmorChoice();
		}

		ResourceLocation texture = null;

		if (armor == null)
		{
			ItemStack stack = player.getEquipmentModule().getInventory().getStackInSlot(2);

			if (stack.getItem() instanceof ItemAetherGloves)
			{
				ItemAetherGloves glove = (ItemAetherGloves) stack.getItem();

				texture = glove.getGloveTexture(player.getEntity());
			}
		}
		else
		{
			texture = armor.getArmorGloveTexture(EntityUtil.getSkin(player.getEntity()).equals("slim"));
		}

		if (texture == null)
		{
			return;
		}

		String skinType = EntityUtil.getSkin(player.getEntity());
		boolean slim = skinType.equals("slim");

		ModelBiped t = slim ? this.modelArmorSlim : this.modelArmor;

		t.bipedRightArm.showModel = true;
		t.bipedLeftArm.showModel = true;

		GlStateManager.pushMatrix();

		t.setModelAttributes(this.renderer.getMainModel());
		t.setLivingAnimations(player.getEntity(), limbSwing, limbSwingAmount, partialTicks);

		this.renderer.bindTexture(texture);

		t.render(player.getEntity(), limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);

		GlStateManager.popMatrix();
	}
}
