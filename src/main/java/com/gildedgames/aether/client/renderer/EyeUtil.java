package com.gildedgames.aether.client.renderer;

import com.gildedgames.aether.api.entity.IEntityInfo;
import com.gildedgames.aether.common.capabilities.entity.info.EntityInfo;
import com.gildedgames.aether.common.util.helpers.EntityUtil;
import com.google.common.collect.Lists;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

import java.util.List;

public class EyeUtil
{
	private static final List<ModelRenderer> boxesToUnhide = Lists.newArrayList();

	public static <T extends ModelBase> void renderEyes(RenderManager renderManager, T model, ModelRenderer eyeModel,
			EntityLivingBase entity, float limbSwing,
			float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale, ResourceLocation pupils,
			ResourceLocation eyesClosed, boolean eyeTracking)
	{
		renderEyes(renderManager, model, eyeModel, eyeModel, entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, pupils,
				eyesClosed, eyeTracking);
	}

	public static boolean modelContains(ModelRenderer model, ModelRenderer part)
	{
		if (model.childModels != null)
		{
			for (ModelRenderer child : model.childModels)
			{
				if (child == part)
				{
					return true;
				}

				if (child.childModels != null && modelContains(child, part))
				{
					return true;
				}
			}
		}

		return false;
	}

	public static <T extends ModelBase> void renderEyes(RenderManager renderManager, T model, ModelRenderer leftEye, ModelRenderer rightEye,
			EntityLivingBase entity, float limbSwing,
			float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale, ResourceLocation pupils,
			ResourceLocation eyesClosed, boolean eyeTracking)
	{
		for (ModelRenderer box : model.boxList)
		{
			if (!box.isHidden)
			{
				boxesToUnhide.add(box);

				if (!modelContains(box, leftEye) && !modelContains(box, rightEye))
				{
					box.isHidden = true;
				}
			}
		}

		IEntityInfo info = EntityInfo.get(entity);

		if (info != null)
		{
			if (info.getTicksEyesClosed() <= 0)
			{
				leftEye.isHidden = false;
				rightEye.isHidden = false;

				float eyeTranslate = 0.0F;
				float eyeCentering = eyeTracking ? 0.03F : 0.0F;

				if (info.lookingAtEntity() != null && eyeTracking)
				{
					double yawBetweenLookingEntity = EntityUtil.getYawFacingPosition(entity, info.lookingAtEntity().posX, info.lookingAtEntity().posZ);
					double yawDif = MathHelper.wrapDegrees(yawBetweenLookingEntity - entity.rotationYawHead);

					double clampYawDif = MathHelper.clamp(yawDif, -45.0, 45.0);

					float percent = (float) ((clampYawDif) / 90.0F);

					eyeTranslate = -0.05F * percent;
				}

				GlStateManager.pushMatrix();

				renderManager.renderEngine.bindTexture(pupils);

				float oldLeftOffsetX = leftEye.offsetX;
				float oldLeftOffsetZ = leftEye.offsetZ;

				float oldRightOffsetX = rightEye.offsetX;
				float oldRightOffsetZ = rightEye.offsetZ;

				leftEye.offsetX = -eyeCentering + eyeTranslate;
				leftEye.offsetZ = -0.0001F;

				rightEye.offsetX = eyeCentering + eyeTranslate;
				rightEye.offsetZ = -0.0001F;

				model.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entity);
				model.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);

				leftEye.offsetX = oldLeftOffsetX;
				leftEye.offsetZ = oldLeftOffsetZ;

				rightEye.offsetX = oldRightOffsetX;
				rightEye.offsetZ = oldRightOffsetZ;

				GlStateManager.popMatrix();
			}
			else
			{
				leftEye.isHidden = false;
				rightEye.isHidden = false;

				renderManager.renderEngine.bindTexture(eyesClosed);

				GlStateManager.pushMatrix();

				float oldOffsetZ = rightEye.offsetZ;
				float oldOffsetZLeft = leftEye.offsetZ;

				rightEye.offsetZ = -0.0001F;
				leftEye.offsetZ = -0.0001F;

				model.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);

				rightEye.offsetZ = oldOffsetZ;
				leftEye.offsetZ = oldOffsetZLeft;

				GlStateManager.popMatrix();
			}
		}

		for (ModelRenderer box : boxesToUnhide)
		{
			box.isHidden = false;
		}

		boxesToUnhide.clear();
	}
}
