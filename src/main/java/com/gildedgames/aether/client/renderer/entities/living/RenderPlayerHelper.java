package com.gildedgames.aether.client.renderer.entities.living;

import com.gildedgames.aether.api.player.IPlayerAetherCapability;
import com.gildedgames.aether.common.items.armor.ItemAetherGloves;
import com.gildedgames.aether.common.items.armor.ItemLeatherGloves;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.event.RenderSpecificHandEvent;

public class RenderPlayerHelper
{

	public static void renderFirstPersonHand(RenderSpecificHandEvent event, IPlayerAetherCapability player)
	{
		ItemStack gloveStack = player.getEquipmentInventory().getStackInSlot(2);

		if (gloveStack != null && gloveStack.getItem() instanceof ItemAetherGloves)
		{
			RenderPlayerHelper.renderGloves(player.getPlayer(), (ItemAetherGloves) gloveStack.getItem(), gloveStack, event.getPartialTicks(), event.getInterpolatedPitch(), event.getSwingProgress(), event.getEquipProgress());
		}

		ItemStack ring1 = player.getEquipmentInventory().getStackInSlot(3);
		ItemStack ring2 = player.getEquipmentInventory().getStackInSlot(4);

		RenderPlayerHelper.renderRings(player.getPlayer(), ring1, ring2, event);
	}

	private static final ModelBiped modelBiped = new ModelBiped();

	private static void renderRings(EntityPlayer player, ItemStack ring1, ItemStack ring2, RenderSpecificHandEvent event)
	{
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

		Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
		String skinType = DefaultPlayerSkin.getSkinType(player.getUniqueID());

		EnumHandSide hand = Minecraft.getMinecraft().gameSettings.mainHand;

		if (ring1 != null)
		{
			RenderPlayerHelper.renderItem(ring1, event.getPartialTicks(), event.getInterpolatedPitch(), event.getSwingProgress(), event.getEquipProgress(), skinType == "slim" ? 0.075F : 0.11F, skinType == "slim" ? 0.0752F : 0.112F, -0.25F);
		}

		if (ring2 != null)
		{
			RenderPlayerHelper.renderItem(ring2, event.getPartialTicks(), event.getInterpolatedPitch(), event.getSwingProgress(), event.getEquipProgress(), skinType == "slim" ? 0.18F : 0.22F, skinType == "slim" ? 0.017F : 0.052F, -0.24F);
		}
	}

	private static void renderGloves(EntityPlayer player, ItemAetherGloves glove, ItemStack stack, float partialTicks, float pitch, float swingProgress, float equipProgress)
	{
		RenderManager manager = Minecraft.getMinecraft().getRenderManager();

		GlStateManager.pushMatrix();

		Minecraft.getMinecraft().getTextureManager().bindTexture(glove.getGloveTexture(0));

		RenderPlayerHelper.renderArmFirstPerson(stack, equipProgress, swingProgress, Minecraft.getMinecraft().gameSettings.mainHand);

		GlStateManager.popMatrix();
	}

	private static void renderItem(ItemStack stack, float partialTicks, float pitch, float swingProgress, float equipProgress, float x, float y, float z)
	{
		GlStateManager.pushMatrix();

		final ItemRenderer itemRenderer = Minecraft.getMinecraft().getItemRenderer();

		//swingProgress = 0.1F;

		if (stack != null)
		{
			GlStateManager.translate(x, y, z);

			RenderPlayerHelper.transformSideFirstPerson(Minecraft.getMinecraft().gameSettings.mainHand, equipProgress);
			RenderPlayerHelper.transformFirstPerson(Minecraft.getMinecraft().gameSettings.mainHand, swingProgress);

			GlStateManager.scale(0.2F, 0.2F, 0.2F);
			//GlStateManager.rotate(15F, 1F, 0F, 0F);
			GlStateManager.rotate(17F, 1F, 0F, -1F);
			GlStateManager.rotate(80F, 0F, 1F, 0F);

			GlStateManager.rotate(40F, 1F, 0F, 0F);
			//GlStateManager.rotate(pitch, 1F, 0F, 0F);

			itemRenderer.renderItemSide(Minecraft.getMinecraft().thePlayer, stack, Minecraft.getMinecraft().gameSettings.mainHand == EnumHandSide.LEFT ? ItemCameraTransforms.TransformType.FIRST_PERSON_LEFT_HAND : ItemCameraTransforms.TransformType.FIRST_PERSON_RIGHT_HAND, Minecraft.getMinecraft().gameSettings.mainHand == EnumHandSide.LEFT);
		}

		GlStateManager.popMatrix();
	}

	private static void renderArmFirstPerson(ItemStack glove, float p_187456_1_, float p_187456_2_, EnumHandSide p_187456_3_)
	{
		boolean flag = p_187456_3_ != EnumHandSide.LEFT;
		float f = flag ? 1.0F : -1.0F;
		float f1 = MathHelper.sqrt_float(p_187456_2_);
		float f2 = -0.3F * MathHelper.sin(f1 * (float)Math.PI);
		float f3 = 0.4F * MathHelper.sin(f1 * ((float)Math.PI * 2F));
		float f4 = -0.4F * MathHelper.sin(p_187456_2_ * (float)Math.PI);

		GlStateManager.translate(f * (f2 + 0.64000005F), f3 + -0.6F + p_187456_1_ * -0.6F, f4 + -0.71999997F);

		GlStateManager.rotate(f * 45.0F, 0.0F, 1.0F, 0.0F);
		float f5 = MathHelper.sin(p_187456_2_ * p_187456_2_ * (float)Math.PI);
		float f6 = MathHelper.sin(f1 * (float)Math.PI);
		GlStateManager.rotate(f * f6 * 70.0F, 0.0F, 1.0F, 0.0F);
		GlStateManager.rotate(f * f5 * -20.0F, 0.0F, 0.0F, 1.0F);
		AbstractClientPlayer abstractclientplayer = Minecraft.getMinecraft().thePlayer;

		GlStateManager.scale(0.78F, 0.78F, 0.78F);

		GlStateManager.translate(f * -1.0F, 3.6F, 3.5F);

		GlStateManager.translate(0.07F, -0.018F, -0.035F);

		String skinType = DefaultPlayerSkin.getSkinType(Minecraft.getMinecraft().thePlayer.getUniqueID());

		GlStateManager.rotate(f * 120.0F, 0.0F, 0.0F, 1.0F);
		GlStateManager.rotate(200.0F, 1.0F, 0.0F, 0.0F);
		GlStateManager.rotate(f * -135.0F, 0.0F, 1.0F, 0.0F);

		GlStateManager.translate(f * 5.6F, 0.0F, 0.0F);

		if (skinType == "slim")
		{
			GlStateManager.translate(0.08F, 0.06F, 0.0F);
		}

		RenderPlayer renderplayer = (RenderPlayer)Minecraft.getMinecraft().getRenderManager().getEntityRenderObject(abstractclientplayer);
		GlStateManager.disableCull();

		if (flag)
		{
			RenderLivingBase<?> playerRender = Minecraft.getMinecraft().getRenderManager().getSkinMap().get("default");

			if (glove.getItem() instanceof ItemLeatherGloves)
			{
				int color = ItemLeatherGloves.getColor(glove);

				float r = (float) (color >> 16 & 255) / 255.0F;
				float g = (float) (color >> 8 & 255) / 255.0F;
				float b = (float) (color & 255) / 255.0F;

				GlStateManager.color(1.0f * r, 1.0f * g, 1.0f * b, 1.0f);
			}
			else
			{
				GlStateManager.color(1.0F, 1.0F, 1.0F);
			}

			ModelBiped t = new ModelBiped(1.0F);
			t.bipedBody.showModel = true;
			t.bipedRightLeg.showModel = true;
			t.bipedLeftLeg.showModel = true;

			t.setModelAttributes(playerRender.getMainModel());
			//t.setLivingAnimations(entity, p_177182_2_, p_177182_3_, partialTicks);
			GlStateManager.enableBlend();
			t.swingProgress = 0.0F;
			t.isSneak = false;

			t.setRotationAngles(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, abstractclientplayer);
			t.bipedRightArm.rotateAngleX = 0.0F;

			//GlStateManager.scale(0.75F, 0.75F, 0.75F);
			t.bipedRightArm.render(0.0625F);
			//t.bipedRightArmwear.rotateAngleX = 0.0F;
			//t.bipedRightArmwear.render(0.0625F);
			GlStateManager.disableBlend();

			GlStateManager.color(1.0F, 1.0F, 1.0F);
		}
		else
		{
			renderplayer.renderLeftArm(abstractclientplayer);
		}

		GlStateManager.enableCull();
	}

	private static void transformFirstPerson(EnumHandSide p_187453_1_, float p_187453_2_)
	{
		int i = p_187453_1_ == EnumHandSide.RIGHT ? 1 : -1;
		float f = MathHelper.sin(p_187453_2_ * p_187453_2_ * (float)Math.PI);
		GlStateManager.rotate((float)i * (45.0F + f * -20.0F), 0.0F, 1.0F, 0.0F);
		float f1 = MathHelper.sin(MathHelper.sqrt_float(p_187453_2_) * (float)Math.PI);
		GlStateManager.rotate((float)i * f1 * -20.0F, 0.0F, 0.0F, 1.0F);
		GlStateManager.rotate(f1 * -80.0F, 1.0F, 0.0F, 0.0F);
		GlStateManager.rotate((float)i * -45.0F, 0.0F, 1.0F, 0.0F);
	}

	private static void transformSideFirstPerson(EnumHandSide p_187459_1_, float p_187459_2_)
	{
		int i = p_187459_1_ == EnumHandSide.RIGHT ? 1 : -1;
		GlStateManager.translate((float)i * 0.56F, -0.52F + p_187459_2_ * -0.6F, -0.72F);
	}

}
