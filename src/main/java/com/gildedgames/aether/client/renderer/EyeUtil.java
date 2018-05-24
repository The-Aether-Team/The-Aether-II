package com.gildedgames.aether.client.renderer;

import com.gildedgames.aether.api.entity.IEntityInfo;
import com.gildedgames.aether.common.capabilities.entity.info.EntityInfo;
import com.google.common.collect.Lists;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;

import java.util.List;

public class EyeUtil
{
	private static List<ModelRenderer> boxesToUnhide = Lists.newArrayList();

	public static <T extends ModelBase> void renderEyes(RenderManager renderManager, T model, ModelRenderer eyeModel,
			EntityLivingBase entity, float limbSwing,
			float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale, ResourceLocation pupilLeft, ResourceLocation pupilRight,
			ResourceLocation eyesClosed, boolean eyeTracking)
	{
		renderEyes(renderManager, model, eyeModel, eyeModel, entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, pupilLeft,
				pupilRight,
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
			float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale, ResourceLocation pupilLeft, ResourceLocation pupilRight,
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

				double eyeTranslate = 0.0;
				double eyeCentering = eyeTracking ? 0.03 : 0.0;

				if (info.lookingAtEntity() != null && eyeTracking)
				{
					Vec3d vec = info.lookingAtEntity().getPositionVector().subtract(entity.getPositionVector()).normalize();
					double distance = Math.min(entity.getDistance(info.lookingAtEntity()), 5.0);

					eyeTranslate = Math.min((vec.x) * 0.09 + 0.065, 0.035);
					eyeCentering = ((distance / 5.0) * 0.03);
				}

				GlStateManager.pushMatrix();

				renderManager.renderEngine.bindTexture(pupilLeft);

				float oldOffsetX = leftEye.offsetX;
				float oldOffsetZ = leftEye.offsetZ;

				leftEye.offsetX = (float) Math.min(eyeTranslate - eyeCentering, 0.00);
				leftEye.offsetZ = -0.0001F;

				model.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);

				leftEye.offsetX = oldOffsetX;
				leftEye.offsetZ = oldOffsetZ;

				GlStateManager.popMatrix();

				GlStateManager.pushMatrix();
				renderManager.renderEngine.bindTexture(pupilRight);

				oldOffsetX = rightEye.offsetX;
				oldOffsetZ = rightEye.offsetZ;

				rightEye.offsetX = (float) Math.max(0.0, Math.min(eyeTranslate + eyeCentering, 0.075));
				rightEye.offsetZ = -0.0001F;

				model.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);

				rightEye.offsetX = oldOffsetX;
				rightEye.offsetZ = oldOffsetZ;

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

				rightEye.offsetZ = -0.00001F;
				leftEye.offsetZ = -0.00001F;

				//GlStateManager.scale(1.001F, 1.001F, 1.001F);

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
