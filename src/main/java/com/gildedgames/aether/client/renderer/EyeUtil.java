package com.gildedgames.aether.client.renderer;

import com.gildedgames.aether.api.entity.IEntityEyesComponent;
import com.gildedgames.aether.common.entities.util.IEntityEyesComponentProvider;
import com.gildedgames.aether.common.util.helpers.EntityUtil;
import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
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

		for (ModelRenderer box : boxesToUnhide)
		{
			box.isHidden = false;
		}

		boxesToUnhide.clear();
	}

	public static <T extends ModelBaseAether> void renderEyesFast(T model, ModelRendererAether leftEye, ModelRendererAether rightEye,
			EntityLivingBase entity, float scale, ResourceLocation pupilLeft, ResourceLocation pupilRight, ResourceLocation eyesClosed,
			ResourceLocation beforeTexture, boolean eyeTracking)
	{
		RenderManager renderManager = Minecraft.getMinecraft().getRenderManager();

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
