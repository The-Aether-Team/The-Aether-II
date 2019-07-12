package com.gildedgames.aether.client.models.entities.living;

import com.gildedgames.aether.api.util.NoiseUtil;
import com.gildedgames.aether.client.renderer.ModelBaseAether;
import com.gildedgames.aether.client.renderer.ModelRendererAether;
import com.gildedgames.aether.common.entities.animals.EntityMoa;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelMoaBase extends ModelBaseAether<EntityMoa>
{
	public ModelRendererAether BodyMain;

	public ModelRendererAether BodyFront;

	public ModelRendererAether BodyBack;

	public ModelRendererAether TailBase;

	public ModelRendererAether LegL1;

	public ModelRendererAether LegR1;

	public ModelRendererAether ShoulderL;

	public ModelRendererAether ShoulderR;

	public ModelRendererAether Neck;

	public ModelRendererAether WingL1;

	public ModelRendererAether WingL2;

	public ModelRendererAether WingLFeatherInt2;

	public ModelRendererAether WingL3;

	public ModelRendererAether WingLFeatherInt1;

	public ModelRendererAether WingLFeatherExt1;

	public ModelRendererAether WingLFeatherExt2;

	public ModelRendererAether WingLFeatherExt3;

	public ModelRendererAether WingR1;

	public ModelRendererAether WingR2;

	public ModelRendererAether WingRFeatherInt2;

	public ModelRendererAether WingR3;

	public ModelRendererAether WingRFeatherInt1;

	public ModelRendererAether WingRFeatherExt1;

	public ModelRendererAether WingRFeatherExt2;

	public ModelRendererAether WingRFeatherExt3;

	public ModelRendererAether HeadMain;

	public ModelRendererAether HeadBack;

	public ModelRendererAether HeadBeakMain;

	public ModelRendererAether JawMain;

	public ModelRendererAether HeadFront;

	public ModelRendererAether HeadBrow;

	public ModelRendererAether HeadFeatherL1;

	public ModelRendererAether HeadFeatherR1;

	public ModelRendererAether HeadFeatherL2;

	public ModelRendererAether HeadFeatherR2;

	public ModelRendererAether HeadBeakIntL;

	public ModelRendererAether HeadBeakIntR;

	public ModelRendererAether HeadBeakFrontL;

	public ModelRendererAether HeadBeakFrontR;

	public ModelRendererAether JawBack;

	public ModelRendererAether JawFrontL;

	public ModelRendererAether JawToothL2;

	public ModelRendererAether JawToothR2;

	public ModelRendererAether JawFrontR;

	public ModelRendererAether JawToothL3;

	public ModelRendererAether JawToothR3;

	public ModelRendererAether JawToothL1;

	public ModelRendererAether JawToothL1_1;

	public ModelRendererAether TailFeatherR;

	public ModelRendererAether TailFeatherM;

	public ModelRendererAether TailFeatherL;

	public ModelRendererAether LegL2;

	public ModelRendererAether LegL3;

	public ModelRendererAether LegLAnkle;

	public ModelRendererAether LegLFoot;

	public ModelRendererAether LegLToeM;

	public ModelRendererAether LegLToeR;

	public ModelRendererAether LegLToeL;

	public ModelRendererAether LegLTalonM;

	public ModelRendererAether LegLTalonR;

	public ModelRendererAether LegLTalonL;

	public ModelRendererAether LegR2;

	public ModelRendererAether LegR3;

	public ModelRendererAether LegRAnkle;

	public ModelRendererAether LegRFoot;

	public ModelRendererAether LegRToeM;

	public ModelRendererAether LegRToeR;

	public ModelRendererAether LegRToeL;

	public ModelRendererAether LegRTalonM;

	public ModelRendererAether LegRTalonR;

	public ModelRendererAether LegRTalonL;

	@Override
	public void setRotationAngles(EntityMoa entity, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch, float scaleFactor)
	{
		boolean flying = !entity.onGround;

		super.setRotationAngles(entity, limbSwing, limbSwingAmount, ageInTicks, headYaw, headPitch, scaleFactor);

		float pitch = headPitch * 0.017453292F;
		float yaw = headYaw * 0.017453292F;

		this.HeadMain.rotateAngleX = 0.17453292519943295F + pitch;
		this.HeadMain.rotateAngleY = yaw;

		float leftSwingX = (MathHelper.cos(limbSwing * 0.6662F) * 0.55F * limbSwingAmount);
		float rightSwingX = (MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 0.55F * limbSwingAmount);

		float leftSwingXLower = (Math.max(-0.5009094953223726F, -rightSwingX * 2f)) - 0.1009094953223726F;
		float rightSwingXLower = (Math.max(-0.5009094953223726F, -leftSwingX * 2f)) - 0.1009094953223726F;

		float leftToeCurlAngle = -(rightSwingXLower * 2.35F);
		float rightToeCurlAngle = -(leftSwingXLower * 2.35F);

		if (!flying)
		{
			this.LegL1.offsetY = leftSwingX / 4f + .01f;
			this.LegR1.offsetY = rightSwingX / 4f + .01f;

			this.LegL1.rotateAngleX = (rightSwingX * 1.2f) - 0.6981317007977318F;
			this.LegR1.rotateAngleX = (leftSwingX * 1.2f) - 0.6981317007977318F;

			this.LegL3.rotateAngleX = (rightSwingXLower * 1.55F) - 1.0471975511965976F;
			this.LegR3.rotateAngleX = (leftSwingXLower * 1.55F) - 1.0471975511965976F;

			this.LegLAnkle.rotateAngleX = -(rightSwingXLower * 0.75F) + 0.4363323129985824F;
			this.LegRAnkle.rotateAngleX = -(leftSwingXLower * 0.75F) + 0.4363323129985824F;

			this.LegLToeL.rotateAngleX = leftToeCurlAngle - 0.17453292519943295F;
			this.LegLToeM.rotateAngleX = leftToeCurlAngle - 0.5235987755982988F;
			this.LegLToeR.rotateAngleX = leftToeCurlAngle - 0.17453292519943295F;

			this.LegRToeL.rotateAngleX = rightToeCurlAngle - 0.17453292519943295F;
			this.LegRToeM.rotateAngleX = rightToeCurlAngle - 0.5235987755982988F;
			this.LegRToeR.rotateAngleX = rightToeCurlAngle - 0.17453292519943295F;
		}
		else
		{
			this.setRotateAngle(this.LegL1, -0.6981317007977318F, 0.0F, -0.3490658503988659F);
			this.setRotateAngle(this.LegR1, -0.6981317007977318F, 0.0F, 0.3490658503988659F);

			this.setRotateAngle(this.LegL3, -1.0471975511965976F, 0.0F, 0.12217304763960307F);
			this.setRotateAngle(this.LegR3, -1.0471975511965976F, 0.0F, -0.12217304763960307F);

			this.setRotateAngle(this.LegLAnkle, 0.4363323129985824F, 0.0F, 0.24434609527920614F);
			this.setRotateAngle(this.LegRAnkle, 0.4363323129985824F, 0.0F, -0.24434609527920614F);

			this.setRotateAngle(this.LegLToeL, -0.17453292519943295F, -0.5235987755982988F, 0.0F);
			this.setRotateAngle(this.LegLToeM, -0.5235987755982988F, 0.0F, 0.0F);
			this.setRotateAngle(this.LegLToeR, -0.17453292519943295F, 0.5235987755982988F, 0.0F);

			this.setRotateAngle(this.LegRToeL, -0.17453292519943295F, -0.5235987755982988F, 0.0F);
			this.setRotateAngle(this.LegRToeM, -0.5235987755982988F, 0.0F, 0.0F);
			this.setRotateAngle(this.LegRToeR, -0.17453292519943295F, 0.5235987755982988F, 0.0F);
		}

		float tailSwayRange = 0.05F;

		this.TailFeatherL.rotateAngleZ = (MathHelper.cos((24F + ageInTicks) * 0.15662F) * tailSwayRange);
		this.TailFeatherM.rotateAngleZ = (MathHelper.cos((0.0F + ageInTicks) * 0.15662F) * tailSwayRange);
		this.TailFeatherR.rotateAngleZ = (MathHelper.cos((48F + ageInTicks) * 0.15662F) * tailSwayRange);

		this.TailFeatherL.rotateAngleX = -0.5235987755982988F + (MathHelper.cos((0F + ageInTicks) * 0.15662F) * tailSwayRange);
		this.TailFeatherM.rotateAngleX = -0.5235987755982988F + (MathHelper.cos((0.0F + ageInTicks) * 0.15662F) * tailSwayRange);
		this.TailFeatherR.rotateAngleX = -0.5235987755982988F + (MathHelper.cos((0F + ageInTicks) * 0.15662F) * tailSwayRange);

		this.HeadFeatherL1.rotateAngleZ = (MathHelper.cos((24F + ageInTicks) * 0.15662F) * 0.1F);
		this.HeadFeatherL2.rotateAngleZ = (MathHelper.cos((0.0F + ageInTicks) * 0.15662F) * 0.1F);
		this.HeadFeatherR1.rotateAngleZ = (MathHelper.cos((48F + ageInTicks) * 0.15662F) * 0.1F);
		this.HeadFeatherR2.rotateAngleZ = (MathHelper.cos((48F + ageInTicks) * 0.15662F) * 0.1F);

		this.HeadFeatherL1.rotateAngleX = 0.17453292519943295F + (MathHelper.cos((0.0F + ageInTicks) * 0.075662F) * 0.05F);
		this.HeadFeatherL2.rotateAngleX = -0.17453292519943295F + (MathHelper.cos((10.0F + ageInTicks) * 0.0755662F) * 0.05F);
		this.HeadFeatherR1.rotateAngleX = 0.17453292519943295F + (MathHelper.cos((20.0F + ageInTicks) * 0.0755662F) * 0.05F);
		this.HeadFeatherR2.rotateAngleX = -0.17453292519943295F + (MathHelper.cos((30.0F + ageInTicks) * 0.0755662F) * 0.05F);

		float wingSwayRange = 0.05F;

		float ageDif = ageInTicks - entity.getAgeSinceOffGround();

		float unfoldTimeInSeconds = 0.3F;
		float foldTimeInSeconds = 0.6F;

		if (flying)
		{
			float foldTime = (unfoldTimeInSeconds * 20.0F);

			float wingTime = ageDif / foldTime;
			float wingAlpha = Math.min(1.0F, wingTime);

			this.setRotateAngle(this.ShoulderR, 0.0F, 0.0F, NoiseUtil.lerp(0.9599310885968813F, 0.17453292519943295F, wingAlpha));
			this.setRotateAngle(this.ShoulderL, 0.0F, 0.0F, NoiseUtil.lerp(-0.9599310885968813F, -0.17453292519943295F, wingAlpha));

			this.setRotateAngle(this.WingR1, 0.0F, NoiseUtil.lerp(-0.17453292519943295F, 0.08726646259971647F, wingAlpha),
					NoiseUtil.lerp(0.0F, -0.17453292519943295F, wingAlpha));
			this.setRotateAngle(this.WingR2, 0.0F, NoiseUtil.lerp(1.8325957145940461F, -0.3490658503988659F, wingAlpha),
					NoiseUtil.lerp(-0.5235987755982988F, 0.17453292519943295F, wingAlpha));
			this.setRotateAngle(this.WingR3, 0.0F, NoiseUtil.lerp(0.2617993877991494F, 0.2617993877991494F, wingAlpha),
					NoiseUtil.lerp(-0.17453292519943295F, -0.17453292519943295F, wingAlpha));

			this.setRotateAngle(this.WingL1, 0.0F, NoiseUtil.lerp(0.17453292519943295F, -0.08726646259971647F, wingAlpha),
					NoiseUtil.lerp(0.0F, 0.17453292519943295F, wingAlpha));
			this.setRotateAngle(this.WingL2, 0.0F, NoiseUtil.lerp(-1.8325957145940461F, 0.3490658503988659F, wingAlpha),
					NoiseUtil.lerp(0.5235987755982988F, -0.17453292519943295F, wingAlpha));
			this.setRotateAngle(this.WingL3, 0.0F, NoiseUtil.lerp(-0.2617993877991494F, -0.2617993877991494F, wingAlpha),
					NoiseUtil.lerp(0.17453292519943295F, 0.17453292519943295F, wingAlpha));

			if (ageDif >= foldTime)
			{
				if (ageDif <= foldTime + 5.0F)
				{
					wingAlpha = (ageDif - foldTime) / 5.0F;

					this.ShoulderR.rotateAngleZ = NoiseUtil
							.lerp(this.ShoulderR.rotateAngleZ, this.ShoulderR.rotateAngleZ + (MathHelper.cos((0.0F + ageDif - 15.0F) * 0.175662F) * 0.6F),
									wingAlpha);
					this.ShoulderL.rotateAngleZ = NoiseUtil
							.lerp(this.ShoulderL.rotateAngleZ, this.ShoulderL.rotateAngleZ + (MathHelper.cos((0.0F + ageDif - 15.0F) * 0.175662F) * -0.6F),
									wingAlpha);
				}
				else
				{
					this.ShoulderR.rotateAngleZ = this.ShoulderR.rotateAngleZ + (MathHelper.cos((0.0F + ageDif - foldTime) * 0.175662F) * 0.6F);
					this.ShoulderL.rotateAngleZ = this.ShoulderL.rotateAngleZ + (MathHelper.cos((0.0F + ageDif - foldTime) * 0.175662F) * -0.6F);
				}
			}
		}
		else
		{
			float foldTime = (foldTimeInSeconds * 20.0F);

			ageDif = Math.abs(entity.getAgeSinceOffGround() - ageInTicks);

			float wingAlpha = Math.min(1.0F, ageDif / foldTime);

			this.setRotateAngle(this.ShoulderR, 0.0F, 0.0F, NoiseUtil.lerpReverse(-0.9599310885968813F, 0.17453292519943295F, wingAlpha));
			this.setRotateAngle(this.ShoulderL, 0.0F, 0.0F, NoiseUtil.lerpReverse(0.9599310885968813F, -0.17453292519943295F, wingAlpha));

			this.setRotateAngle(this.WingR1, 0.0F, NoiseUtil.lerpReverse(-0.17453292519943295F, 0.08726646259971647F, wingAlpha),
					NoiseUtil.lerpReverse(0.0F, -0.17453292519943295F, wingAlpha));
			this.setRotateAngle(this.WingR2, 0.0F, NoiseUtil.lerpReverse(1.8325957145940461F, -0.3490658503988659F, wingAlpha),
					NoiseUtil.lerpReverse(-0.5235987755982988F, 0.17453292519943295F, wingAlpha));
			this.setRotateAngle(this.WingR3, 0.0F, NoiseUtil.lerpReverse(0.2617993877991494F, 0.2617993877991494F, wingAlpha),
					NoiseUtil.lerpReverse(-0.17453292519943295F, -0.17453292519943295F, wingAlpha));

			this.setRotateAngle(this.WingL1, 0.0F, NoiseUtil.lerpReverse(0.17453292519943295F, -0.08726646259971647F, wingAlpha),
					NoiseUtil.lerpReverse(0.0F, 0.17453292519943295F, wingAlpha));
			this.setRotateAngle(this.WingL2, 0.0F, NoiseUtil.lerpReverse(-1.8325957145940461F, 0.3490658503988659F, wingAlpha),
					NoiseUtil.lerpReverse(0.5235987755982988F, -0.17453292519943295F, wingAlpha));
			this.setRotateAngle(this.WingL3, 0.0F, NoiseUtil.lerpReverse(-0.2617993877991494F, -0.2617993877991494F, wingAlpha),
					NoiseUtil.lerpReverse(0.17453292519943295F, 0.17453292519943295F, wingAlpha));
		}

		this.WingLFeatherExt1.rotateAngleZ = (MathHelper.cos((0.0F + ageInTicks) * 0.15662F) * wingSwayRange);
		this.WingLFeatherExt2.rotateAngleZ = (MathHelper.cos((10.0F + ageInTicks) * 0.15662F) * wingSwayRange);
		this.WingLFeatherExt3.rotateAngleZ = (MathHelper.cos((20.0F + ageInTicks) * 0.15662F) * wingSwayRange);
		this.WingLFeatherInt1.rotateAngleZ = (MathHelper.cos((30.0F + ageInTicks) * 0.15662F) * wingSwayRange);
		this.WingLFeatherInt2.rotateAngleZ = (MathHelper.cos((40.0F + ageInTicks) * 0.15662F) * wingSwayRange);

		this.WingRFeatherExt1.rotateAngleZ = (MathHelper.cos((0.0F + ageInTicks) * 0.15662F) * wingSwayRange);
		this.WingRFeatherExt2.rotateAngleZ = (MathHelper.cos((10.0F + ageInTicks) * 0.15662F) * wingSwayRange);
		this.WingRFeatherExt3.rotateAngleZ = (MathHelper.cos((20.0F + ageInTicks) * 0.15662F) * wingSwayRange);
		this.WingRFeatherInt1.rotateAngleZ = (MathHelper.cos((30.0F + ageInTicks) * 0.15662F) * wingSwayRange);
		this.WingRFeatherInt2.rotateAngleZ = (MathHelper.cos((40.0F + ageInTicks) * 0.15662F) * wingSwayRange);
	}

	/**
	 * This is a helper function from Tabula to set the rotation of model parts
	 */
	@Override
	public void setRotateAngle(RendererModel modelRenderer, float x, float y, float z)
	{
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}
