package com.gildedgames.aether.client.models.entities.living;

import com.gildedgames.aether.client.renderer.ModelBaseAether;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelTaegoreBase extends ModelBaseAether
{
	public ModelRenderer Tail;

	public ModelRenderer HeadMain;

	public ModelRenderer HeadThroat;

	public ModelRenderer HeadSnoutRight;

	public ModelRenderer HeadSnoutLeft;

	public ModelRenderer HeadSnout;

	public ModelRenderer HeadJawBack;

	public ModelRenderer HeadSnoutRidge;

	public ModelRenderer HeadBrowLeft;

	public ModelRenderer HeadBrowRight;

	public ModelRenderer HeadEyeLeft;

	public ModelRenderer HeadEyeRight;

	public ModelRenderer HeadCrestMiddle;

	public ModelRenderer HeadCrestLeft;

	public ModelRenderer HeadCrestRight;

	public ModelRenderer HeadCrestBase;

	public ModelRenderer HeadTuskLeft;

	public ModelRenderer HeadTuskRight;

	public ModelRenderer HeadBeardMiddle;

	public ModelRenderer HeadBeardRight;

	public ModelRenderer HeadBeardLeft;

	public ModelRenderer HeadEarLeft;

	public ModelRenderer HeadEarRight;

	public ModelRenderer HeadRuffTop;

	public ModelRenderer HeadRuffLeft;

	public ModelRenderer HeadRuffRight;

	public ModelRenderer HeadJawFront;

	public ModelRenderer TorsoNeck;

	public ModelRenderer TorsoBack;

	public ModelRenderer TorsoSpine;

	public ModelRenderer TorsoBelly;

	public ModelRenderer TorsoFront;

	public ModelRenderer TorsoShoulderPlateLeft1;

	public ModelRenderer TorsoShoulderPlateRight1;

	public ModelRenderer TorsoShoulderPlateLeft2;

	public ModelRenderer TorsoShoulderPlateRight2;

	public ModelRenderer TorsoBackPlateRight;

	public ModelRenderer TorsoBackPlateLeft;

	public ModelRenderer FrontLegLeftUpper;

	public ModelRenderer FrontLegLeftLower;

	public ModelRenderer FrontLegLeftToeMiddle;

	public ModelRenderer FrontLegLeftToeOut;

	public ModelRenderer FrontLegLeftToeIn;

	public ModelRenderer FrontLegRightUpper;

	public ModelRenderer FrontLegRightLower;

	public ModelRenderer FrontLegRightToeMiddle;

	public ModelRenderer FrontLegRightToeOut;

	public ModelRenderer FrontLegRightToeIn;

	public ModelRenderer HindLegLeftCalf;

	public ModelRenderer HindLegLeftKnee;

	public ModelRenderer HindLegLeftShin;

	public ModelRenderer HindLegLeftToeMiddle;

	public ModelRenderer HindLegLeftToeOut;

	public ModelRenderer HindLegLeftToeIn;

	public ModelRenderer HindLegRightCalf;

	public ModelRenderer HindLegRightKnee;

	public ModelRenderer HindLegRightShin;

	public ModelRenderer HindLegRightToeMiddle;

	public ModelRenderer HindLegRightToeOut;

	public ModelRenderer HindLegRightToeIn;

	@Override
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch,
			float scaleFactor, Entity entity)
	{
		super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entity);

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
