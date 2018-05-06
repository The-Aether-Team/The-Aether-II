package com.gildedgames.aether.client.renderer.entities.living;

import com.gildedgames.aether.api.player.IPlayerAether;
import com.gildedgames.aether.common.items.armor.ItemAetherGloves;
import com.gildedgames.aether.common.util.helpers.EntityUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.event.RenderSpecificHandEvent;

public class RenderPlayerHelper
{

	private static final ModelBiped modelBiped = new ModelBiped();

	public static void renderFirstPersonHand(final RenderSpecificHandEvent event, final IPlayerAether player)
	{
		final ItemStack gloveStack = player.getEquipmentModule().getInventory().getStackInSlot(2);

		if (gloveStack.getItem() instanceof ItemAetherGloves)
		{
			RenderPlayerHelper.renderGloves(player.getEntity(), (ItemAetherGloves) gloveStack.getItem(), gloveStack, event.getPartialTicks(),
					event.getInterpolatedPitch(), event.getSwingProgress(), event.getEquipProgress());
		}
	}

	private static void renderRings(final EntityPlayer player, final ItemStack ring1, final ItemStack ring2, final RenderSpecificHandEvent event)
	{
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

		final EnumHandSide hand = Minecraft.getMinecraft().gameSettings.mainHand;

		if (hand == EnumHandSide.LEFT)
		{
			GlStateManager.scale(-1.0f, 1.0f, 1.0f);
		}

		Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
		String skinType = EntityUtil.getSkin(player);

		if (ring1 != null)
		{
			RenderPlayerHelper.renderItem(ring1, event.getPartialTicks(), event.getInterpolatedPitch(), event.getSwingProgress(), event.getEquipProgress(),
					skinType.equals("slim") ? 0.075F : 0.09F, skinType.equals("slim") ? 0.0752F : 0.112F, skinType.equals("slim") ? -0.25F : -0.20F);
		}

		if (ring2 != null)
		{
			RenderPlayerHelper.renderItem(ring2, event.getPartialTicks(), event.getInterpolatedPitch(), event.getSwingProgress(), event.getEquipProgress(),
					skinType.equals("slim") ? 0.18F : 0.195F, skinType.equals("slim") ? 0.017F : 0.0552F, skinType.equals("slim") ? -0.24F : -0.19F);
		}
	}

	private static void renderGloves(final EntityPlayer player, final ItemAetherGloves glove, final ItemStack stack, final float partialTicks,
			final float pitch,
			final float swingProgress, final float equipProgress)
	{
		final RenderManager manager = Minecraft.getMinecraft().getRenderManager();

		GlStateManager.pushMatrix();

		Minecraft.getMinecraft().getTextureManager().bindTexture(glove.getGloveTexture(player));

		final EnumHandSide hand = Minecraft.getMinecraft().gameSettings.mainHand;

		if (hand == EnumHandSide.LEFT)
		{
			GlStateManager.scale(-1.0f, 1.0f, 1.0f);
		}

		RenderPlayerHelper.renderArmFirstPerson(stack, equipProgress, swingProgress, EnumHandSide.RIGHT);

		GlStateManager.popMatrix();
	}

	private static void renderItem(final ItemStack stack, final float partialTicks, final float pitch, final float swingProgress, final float equipProgress,
			final float x,
			final float y, final float z)
	{
		GlStateManager.pushMatrix();

		final ItemRenderer itemRenderer = Minecraft.getMinecraft().getItemRenderer();

		//swingProgress = 0.1F;

		if (stack != null)
		{
			final float f = -0.4F * MathHelper.sin(MathHelper.sqrt(swingProgress) * (float) Math.PI);
			final float f1 = 0.2F * MathHelper.sin(MathHelper.sqrt(swingProgress) * ((float) Math.PI * 2F));
			final float f2 = -0.2F * MathHelper.sin(swingProgress * (float) Math.PI);
			final int i = 2;

			GlStateManager.translate(x, y, z);

			RenderPlayerHelper.transformSideFirstPerson(EnumHandSide.RIGHT, equipProgress);
			RenderPlayerHelper.transformFirstPerson(EnumHandSide.RIGHT, swingProgress);

			GlStateManager.translate((float) i * f, f1, f2);

			GlStateManager.scale(0.2F, 0.2F, 0.2F);
			//GlStateManager.rotate(15F, 1F, 0F, 0F);
			GlStateManager.rotate(17F, 1F, 0F, -1F);
			GlStateManager.rotate(80F, 0F, 1F, 0F);

			GlStateManager.rotate(40F, 1F, 0F, 0F);
			//GlStateManager.rotate(pitch, 1F, 0F, 0F);

			itemRenderer.renderItemSide(Minecraft.getMinecraft().player, stack, ItemCameraTransforms.TransformType.FIRST_PERSON_RIGHT_HAND, false);
		}

		GlStateManager.popMatrix();
	}

	private static void renderArmFirstPerson(final ItemStack glove, final float p_187456_1_, final float p_187456_2_, final EnumHandSide p_187456_3_)
	{
		final boolean flag = p_187456_3_ != EnumHandSide.LEFT;
		final float f = flag ? 1.0F : -1.0F;
		final float f1 = MathHelper.sqrt(p_187456_2_);
		final float f2 = -0.3F * MathHelper.sin(f1 * (float) Math.PI);
		final float f3 = 0.4F * MathHelper.sin(f1 * ((float) Math.PI * 2F));
		final float f4 = -0.4F * MathHelper.sin(p_187456_2_ * (float) Math.PI);

		GlStateManager.translate(f * (f2 + 0.64000005F), f3 + -0.6F + p_187456_1_ * -0.6F, f4 + -0.71999997F);

		GlStateManager.rotate(f * 45.0F, 0.0F, 1.0F, 0.0F);
		final float f5 = MathHelper.sin(p_187456_2_ * p_187456_2_ * (float) Math.PI);
		final float f6 = MathHelper.sin(f1 * (float) Math.PI);
		GlStateManager.rotate(f * f6 * 70.0F, 0.0F, 1.0F, 0.0F);
		GlStateManager.rotate(f * f5 * -20.0F, 0.0F, 0.0F, 1.0F);
		final AbstractClientPlayer abstractclientplayer = Minecraft.getMinecraft().player;

		GlStateManager.scale(0.78F, 0.78F, 0.78F);

		GlStateManager.translate(f * -1.0F, 3.6F, 3.5F);

		GlStateManager.translate(0.07F, -0.018F, -0.035F);

		String skinType = EntityUtil.getSkin(Minecraft.getMinecraft().player);

		GlStateManager.rotate(f * 120.0F, 0.0F, 0.0F, 1.0F);
		GlStateManager.rotate(200.0F, 1.0F, 0.0F, 0.0F);
		GlStateManager.rotate(f * -135.0F, 0.0F, 1.0F, 0.0F);

		GlStateManager.translate(f * 5.6F, 0.0F, 0.0F);

		GlStateManager.translate(-0.03F, 0.04F, -0.04F);

		if (!skinType.equals("slim"))
		{
			GlStateManager.translate(-0.03F, 0.0F, -0.02F);
		}
		else
		{
			GlStateManager.translate(0.0F, -0.07F, 0.0F);
		}

		GlStateManager.disableCull();

		if (flag)
		{
			final RenderLivingBase<?> playerRender = Minecraft.getMinecraft().getRenderManager().getSkinMap().get(skinType);

			final ModelBiped t = !skinType.equals("slim") ? new ModelBiped(0.5F) : new ModelPlayer(1F, true);
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

		GlStateManager.enableCull();
	}

	private static void transformFirstPerson(final EnumHandSide p_187453_1_, final float p_187453_2_)
	{
		final int i = p_187453_1_ == EnumHandSide.RIGHT ? 1 : -1;
		final float f = MathHelper.sin(p_187453_2_ * p_187453_2_ * (float) Math.PI);
		GlStateManager.rotate((float) i * (45.0F + f * -20.0F), 0.0F, 1.0F, 0.0F);
		final float f1 = MathHelper.sin(MathHelper.sqrt(p_187453_2_) * (float) Math.PI);
		GlStateManager.rotate((float) i * f1 * -20.0F, 0.0F, 0.0F, 1.0F);
		GlStateManager.rotate(f1 * -80.0F, 1.0F, 0.0F, 0.0F);
		GlStateManager.rotate((float) i * -45.0F, 0.0F, 1.0F, 0.0F);
	}

	private static void transformSideFirstPerson(final EnumHandSide p_187459_1_, final float p_187459_2_)
	{
		final int i = p_187459_1_ == EnumHandSide.RIGHT ? 1 : -1;
		GlStateManager.translate((float) i * 0.56F, -0.52F + p_187459_2_ * -0.6F, -0.72F);
	}

}
