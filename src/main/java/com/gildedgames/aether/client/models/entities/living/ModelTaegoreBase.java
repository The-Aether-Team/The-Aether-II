package com.gildedgames.aether.client.models.entities.living;

import com.gildedgames.aether.client.renderer.ModelBaseAether;
import com.gildedgames.aether.common.entities.animals.EntityTaegore;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelTaegoreBase extends ModelBaseAether<EntityTaegore>
{
	public RendererModel Tail;

	public RendererModel HeadMain;

	public RendererModel HeadThroat;

	public RendererModel HeadSnoutRight;

	public RendererModel HeadSnoutLeft;

	public RendererModel HeadSnout;

	public RendererModel HeadJawBack;

	public RendererModel HeadSnoutRidge;

	public RendererModel HeadBrowLeft;

	public RendererModel HeadBrowRight;

	public RendererModel HeadEyeLeft;

	public RendererModel HeadEyeRight;

	public RendererModel HeadCrestMiddle;

	public RendererModel HeadCrestLeft;

	public RendererModel HeadCrestRight;

	public RendererModel HeadCrestBase;

	public RendererModel HeadTuskLeft;

	public RendererModel HeadTuskRight;

	public RendererModel HeadBeardMiddle;

	public RendererModel HeadBeardRight;

	public RendererModel HeadBeardLeft;

	public RendererModel HeadEarLeft;

	public RendererModel HeadEarRight;

	public RendererModel HeadRuffTop;

	public RendererModel HeadRuffLeft;

	public RendererModel HeadRuffRight;

	public RendererModel HeadJawFront;

	public RendererModel TorsoNeck;

	public RendererModel TorsoBack;

	public RendererModel TorsoSpine;

	public RendererModel TorsoBelly;

	public RendererModel TorsoFront;

	public RendererModel TorsoShoulderPlateLeft1;

	public RendererModel TorsoShoulderPlateRight1;

	public RendererModel TorsoShoulderPlateLeft2;

	public RendererModel TorsoShoulderPlateRight2;

	public RendererModel TorsoBackPlateRight;

	public RendererModel TorsoBackPlateLeft;

	public RendererModel FrontLegLeftUpper;

	public RendererModel FrontLegLeftLower;

	public RendererModel FrontLegLeftToeMiddle;

	public RendererModel FrontLegLeftToeOut;

	public RendererModel FrontLegLeftToeIn;

	public RendererModel FrontLegRightUpper;

	public RendererModel FrontLegRightLower;

	public RendererModel FrontLegRightToeMiddle;

	public RendererModel FrontLegRightToeOut;

	public RendererModel FrontLegRightToeIn;

	public RendererModel HindLegLeftCalf;

	public RendererModel HindLegLeftKnee;

	public RendererModel HindLegLeftShin;

	public RendererModel HindLegLeftToeMiddle;

	public RendererModel HindLegLeftToeOut;

	public RendererModel HindLegLeftToeIn;

	public RendererModel HindLegRightCalf;

	public RendererModel HindLegRightKnee;

	public RendererModel HindLegRightShin;

	public RendererModel HindLegRightToeMiddle;

	public RendererModel HindLegRightToeOut;

	public RendererModel HindLegRightToeIn;

	@Override
	public void setRotationAngles(EntityTaegore entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch,
			float scaleFactor)
	{
		super.setRotationAngles(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);

		float pitch = headPitch * 0.017453292F;
		float yaw = netHeadYaw * 0.017453292F;

		this.HeadMain.rotateAngleX = pitch;
		this.HeadMain.rotateAngleY = yaw;

		float leftSwingX = (MathHelper.cos(limbSwing * 0.6662F) * 0.75F * limbSwingAmount);
		float rightSwingX = (MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 0.75F * limbSwingAmount);

		this.FrontLegLeftUpper.rotateAngleX = 0.17453292012214658F + leftSwingX;
		this.FrontLegLeftLower.rotateAngleX = leftSwingX;

		this.FrontLegRightUpper.rotateAngleX = 0.17453292012214658F + rightSwingX;
		this.FrontLegRightLower.rotateAngleX = rightSwingX;

		this.HindLegRightShin.rotateAngleX = leftSwingX;
		this.HindLegRightKnee.rotateAngleX = -0.17453292012214658F + leftSwingX;
		this.HindLegRightCalf.rotateAngleX = -0.34906584024429316F + leftSwingX;

		this.HindLegLeftShin.rotateAngleX = rightSwingX;
		this.HindLegLeftKnee.rotateAngleX = -0.17453292012214658F + rightSwingX;
		this.HindLegLeftCalf.rotateAngleX = -0.34906584024429316F + rightSwingX;
	}
}
