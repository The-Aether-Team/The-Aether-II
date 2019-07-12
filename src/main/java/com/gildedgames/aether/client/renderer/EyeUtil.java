package com.gildedgames.aether.client.renderer;

import com.gildedgames.aether.api.entity.IEntityEyesComponent;
import com.gildedgames.aether.common.entities.util.eyes.IEntityEyesComponentProvider;
import com.gildedgames.aether.common.util.helpers.EntityUtil;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

import java.util.List;

public class EyeUtil
{
	private static final List<RendererModel> boxesToUnhide = Lists.newArrayList();

	public static <T extends EntityModel> void renderEyes(EntityRendererManager renderManager, T model, RendererModel eyeModel,
			LivingEntity entity, float limbSwing,
			float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale, ResourceLocation pupilLeft, ResourceLocation pupilRight,
			ResourceLocation eyesClosed, boolean eyeTracking)
	{
		renderEyes(renderManager, model, eyeModel, eyeModel, entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, pupilLeft,
				pupilRight,
				eyesClosed, eyeTracking);
	}

	public static boolean modelContains(RendererModel model, RendererModel part)
	{
		if (model.childModels != null)
		{
			for (RendererModel child : model.childModels)
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

	public static <T extends EntityModel> void renderEyes(EntityRendererManager renderManager, T model, RendererModel leftEye, RendererModel rightEye,
			LivingEntity entity, float limbSwing,
			float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale, ResourceLocation pupilLeft, ResourceLocation pupilRight,
			ResourceLocation eyesClosed, boolean eyeTracking)
	{
		for (RendererModel box : model.boxList)
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

		if (entity instanceof IEntityEyesComponentProvider)
		{
			IEntityEyesComponent info = ((IEntityEyesComponentProvider) entity).getEyes();

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

				renderManager.renderEngine.bindTexture(pupilLeft);

				float oldOffsetX = leftEye.offsetX;
				float oldOffsetZ = leftEye.offsetZ;

				leftEye.offsetX = -eyeCentering + eyeTranslate;
				leftEye.offsetZ = -0.0001F;

				model.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);

				leftEye.offsetX = oldOffsetX;
				leftEye.offsetZ = oldOffsetZ;

				GlStateManager.popMatrix();

				GlStateManager.pushMatrix();
				renderManager.renderEngine.bindTexture(pupilRight);

				oldOffsetX = rightEye.offsetX;
				oldOffsetZ = rightEye.offsetZ;

				rightEye.offsetX = eyeCentering + eyeTranslate;
				rightEye.offsetZ = -0.0001F;

				model.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entity);
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

				rightEye.offsetZ = -0.0001F;
				leftEye.offsetZ = -0.0001F;

				model.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);

				rightEye.offsetZ = oldOffsetZ;
				leftEye.offsetZ = oldOffsetZLeft;

				GlStateManager.popMatrix();
			}
		}
		else
		{
			throw new IllegalStateException("Entity " + entity + " does not implement IEntityEyesComponent");
		}

		for (RendererModel box : boxesToUnhide)
		{
			box.isHidden = false;
		}

		boxesToUnhide.clear();
	}

	public static <T extends ModelBaseAether> void renderEyesFast(T model, ModelRendererAether leftEye, ModelRendererAether rightEye,
			LivingEntity entity, float scale, ResourceLocation pupilLeft, ResourceLocation pupilRight, ResourceLocation eyesClosed,
			ResourceLocation beforeTexture, boolean eyeTracking)
	{
		EntityRendererManager renderManager = Minecraft.getInstance().getRenderManager();

		if (entity instanceof IEntityEyesComponentProvider)
		{
			IEntityEyesComponent info = ((IEntityEyesComponentProvider) entity).getEyes();

			if (info.getTicksEyesClosed() <= 0)
			{
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

				renderManager.renderEngine.bindTexture(pupilLeft);

				float oldOffsetX = leftEye.offsetX;
				float oldOffsetZ = leftEye.offsetZ;

				leftEye.offsetX = -eyeCentering + eyeTranslate;
				leftEye.offsetZ = -0.0001F;

				leftEye.render(scale, true, false);

				leftEye.offsetX = oldOffsetX;
				leftEye.offsetZ = oldOffsetZ;

				renderManager.renderEngine.bindTexture(pupilRight);

				oldOffsetX = rightEye.offsetX;
				oldOffsetZ = rightEye.offsetZ;

				rightEye.offsetX = eyeCentering + eyeTranslate;
				rightEye.offsetZ = -0.0001F;

				//				model.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entity);

				rightEye.render(scale, true, false);

				rightEye.offsetX = oldOffsetX;
				rightEye.offsetZ = oldOffsetZ;
			}
			else
			{
				renderManager.renderEngine.bindTexture(eyesClosed);

				float oldOffsetZ = rightEye.offsetZ;
				float oldOffsetZLeft = leftEye.offsetZ;

				rightEye.offsetZ = -0.0001F;
				leftEye.offsetZ = -0.0001F;

				leftEye.render(scale, true, false);
				rightEye.render(scale, true, false);
			}

			renderManager.renderEngine.bindTexture(beforeTexture);
		}
		else
		{
			throw new IllegalStateException("Entity " + entity + " does not implement IEntityEyesComponent");
		}
	}
}
