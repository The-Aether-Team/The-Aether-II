package com.gildedgames.aether.client.models.entities.companions;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;

public class ModelShadeOfArkenzus extends ModelBase
{
	private final ModelRenderer Shoulder_Right;

	private final ModelRenderer Shoulder_Left;

	private final ModelRenderer Arm_Right;

	private final ModelRenderer Arm_Left;

	private final ModelRenderer Chest_Left;

	private final ModelRenderer Chest_Right;

	private final ModelRenderer Wing_Base_Right;

	private final ModelRenderer Wing_Base_Left;

	private final ModelRenderer Wing_Top_Right;

	private final ModelRenderer Wing_Top_Left;

	private final ModelRenderer Wing_Mid_Left;

	private final ModelRenderer Wing_Mid_Right;

	private final ModelRenderer Wing_Bottom_Left;

	private final ModelRenderer Wing_Bottom_Right;

	private final ModelRenderer head;

	private final ModelRenderer body;

	private final ModelRenderer rightleg;

	private final ModelRenderer leftleg;

	public ModelShadeOfArkenzus()
	{
		this.textureWidth = 128;
		this.textureHeight = 64;

		this.Shoulder_Right = new ModelRenderer(this, 40, 16);
		this.Shoulder_Right.addBox(-4.2F, -2F, -2F, 5, 4, 4);
		this.Shoulder_Right.setRotationPoint(-4F, 2F, 0F);
		this.Shoulder_Right.setTextureSize(64, 32);
		this.Shoulder_Right.mirror = true;
		this.setRotation(this.Shoulder_Right, 0F, 0F, 0.3490659F);

		this.Shoulder_Left = new ModelRenderer(this, 40, 16);
		this.Shoulder_Left.addBox(-0.8F, -2F, -2F, 5, 4, 4);
		this.Shoulder_Left.setRotationPoint(4F, 2F, 0F);
		this.Shoulder_Left.setTextureSize(64, 32);
		this.Shoulder_Left.mirror = true;
		this.setRotation(this.Shoulder_Left, 0F, 0F, -0.3490659F);
		this.Shoulder_Left.mirror = false;

		this.Arm_Right = new ModelRenderer(this, 40, 24);
		this.Arm_Right.addBox(-6F, -2F, -2F, 6, 4, 4);
		this.Arm_Right.setRotationPoint(-4F, 2F, 0F);
		this.Arm_Right.setTextureSize(64, 32);
		this.setRotation(this.Arm_Right, 0F, 0F, -0.4363323F);

		this.Arm_Left = new ModelRenderer(this, 40, 24);
		this.Arm_Left.mirror = true;
		this.Arm_Left.addBox(0F, -2F, -2F, 6, 4, 4);
		this.Arm_Left.setRotationPoint(4F, 2F, 0F);
		this.Arm_Left.setTextureSize(64, 32);
		this.Arm_Left.mirror = true;
		this.setRotation(this.Arm_Left, 0F, 0F, 0.4363323F);

		this.Arm_Left.mirror = false;
		this.Chest_Left = new ModelRenderer(this, 11, 32);
		this.Chest_Left.addBox(0.5F, 0F, -2.5F, 4, 5, 5);
		this.Chest_Left.setRotationPoint(0F, 0F, 0F);
		this.Chest_Left.setTextureSize(64, 32);
		this.Chest_Left.mirror = true;
		this.setRotation(this.Chest_Left, 0F, 0F, -0.0872665F);

		this.Chest_Right = new ModelRenderer(this, 11, 32);
		this.Chest_Right.mirror = true;
		this.Chest_Right.addBox(-4.5F, 0F, -2.5F, 4, 5, 5);
		this.Chest_Right.setRotationPoint(0F, 0F, 0F);
		this.Chest_Right.setTextureSize(64, 32);
		this.Chest_Right.mirror = true;
		this.setRotation(this.Chest_Right, 0F, 0F, 0.0872665F);

		this.Chest_Right.mirror = false;
		this.Wing_Base_Right = new ModelRenderer(this, 60, 19);
		this.Wing_Base_Right.addBox(-2F, 0F, -1F, 2, 3, 10);
		this.Wing_Base_Right.setRotationPoint(-1F, 1F, 2F);
		this.Wing_Base_Right.setTextureSize(64, 32);
		this.Wing_Base_Right.mirror = true;
		this.setRotation(this.Wing_Base_Right, 0.3490659F, -0.8726646F, 0F);

		this.Wing_Base_Left = new ModelRenderer(this, 60, 19);
		this.Wing_Base_Left.mirror = true;
		this.Wing_Base_Left.addBox(0F, 0F, -1F, 2, 3, 10);
		this.Wing_Base_Left.setRotationPoint(1F, 1F, 2F);
		this.Wing_Base_Left.setTextureSize(64, 32);
		this.Wing_Base_Left.mirror = true;
		this.setRotation(this.Wing_Base_Left, 0.3490659F, 0.8726646F, 0F);

		this.Wing_Base_Left.mirror = false;
		this.Wing_Top_Right = new ModelRenderer(this, 84, 0);
		this.Wing_Top_Right.addBox(-2F, -3F, 6F, 2, 3, 16);
		this.Wing_Top_Right.setRotationPoint(-1F, 1F, 2F);
		this.Wing_Top_Right.setTextureSize(64, 32);
		this.Wing_Top_Right.mirror = true;
		this.setRotation(this.Wing_Top_Right, 0.3490659F, -0.8726646F, 0F);

		this.Wing_Top_Left = new ModelRenderer(this, 84, 0);
		this.Wing_Top_Left.mirror = true;
		this.Wing_Top_Left.addBox(0F, -3F, 6F, 2, 3, 16);
		this.Wing_Top_Left.setRotationPoint(1F, 1F, 2F);
		this.Wing_Top_Left.setTextureSize(64, 32);
		this.Wing_Top_Left.mirror = true;
		this.setRotation(this.Wing_Top_Left, 0.3490659F, 0.8726646F, 0F);

		this.Wing_Mid_Left = new ModelRenderer(this, 88, 19);
		this.Wing_Top_Left.mirror = false;
		this.Wing_Mid_Left.addBox(0F, -4F, 8F, 2, 3, 12);
		this.Wing_Mid_Left.setRotationPoint(1F, 1F, 2F);
		this.Wing_Mid_Left.setTextureSize(64, 32);
		this.Wing_Mid_Left.mirror = true;
		this.setRotation(this.Wing_Mid_Left, 0F, 0.8726646F, 0F);

		this.Wing_Mid_Right = new ModelRenderer(this, 88, 19);
		this.Wing_Mid_Right.addBox(-2F, -4F, 8F, 2, 3, 12);
		this.Wing_Mid_Right.setRotationPoint(-1F, 1F, 2F);
		this.Wing_Mid_Right.setTextureSize(64, 32);
		this.Wing_Mid_Right.mirror = true;
		this.setRotation(this.Wing_Mid_Right, 0F, -0.8726646F, 0F);

		this.Wing_Bottom_Left = new ModelRenderer(this, 92, 34);
		this.Wing_Bottom_Left.addBox(0F, -5F, 7F, 2, 3, 8);
		this.Wing_Bottom_Left.setRotationPoint(1F, 1F, 2F);
		this.Wing_Bottom_Left.setTextureSize(64, 32);
		this.Wing_Bottom_Left.mirror = true;
		this.setRotation(this.Wing_Bottom_Left, -0.3490659F, 0.8726646F, 0F);

		this.Wing_Bottom_Right = new ModelRenderer(this, 92, 34);
		this.Wing_Bottom_Right.addBox(-2F, -5F, 7F, 2, 3, 8);
		this.Wing_Bottom_Right.setRotationPoint(-1F, 1F, 2F);
		this.Wing_Bottom_Right.setTextureSize(64, 32);
		this.Wing_Bottom_Right.mirror = true;
		this.setRotation(this.Wing_Bottom_Right, -0.3490659F, -0.8726646F, 0F);

		this.head = new ModelRenderer(this, 0, 0);
		this.head.addBox(-4F, -8F, -4F, 8, 8, 8);
		this.head.setRotationPoint(0F, 0F, 0F);
		this.head.setTextureSize(64, 32);
		this.head.mirror = true;
		this.setRotation(this.head, 0F, 0F, 0F);

		this.body = new ModelRenderer(this, 16, 16);
		this.body.addBox(-4F, 0F, -2F, 8, 12, 4);
		this.body.setRotationPoint(0F, 0F, 0F);
		this.body.setTextureSize(64, 32);
		this.body.mirror = true;
		this.setRotation(this.body, 0F, 0F, 0F);

		this.rightleg = new ModelRenderer(this, 0, 16);
		this.rightleg.addBox(-2.5F, 0F, -2F, 4, 12, 4);
		this.rightleg.setRotationPoint(-2F, 12F, 0F);
		this.rightleg.setTextureSize(64, 32);
		this.rightleg.mirror = true;
		this.setRotation(this.rightleg, 0F, 0F, 0F);

		this.leftleg = new ModelRenderer(this, 0, 16);
		this.leftleg.mirror = true;
		this.leftleg.addBox(-1.5F, 0F, -2F, 4, 12, 4);
		this.leftleg.setRotationPoint(2F, 12F, 0F);
		this.leftleg.setTextureSize(64, 32);
		this.setRotation(this.leftleg, 0F, 0F, 0F);

		this.leftleg.mirror = false;
	}

	@Override
	public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch,
			float scale)
	{
		super.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);

		this.setRotationAngles(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);

		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);

		this.Shoulder_Right.render(scale);
		this.Shoulder_Left.render(scale);
		this.Arm_Right.render(scale);
		this.Arm_Left.render(scale);
		this.Chest_Left.render(scale);
		this.Chest_Right.render(scale);
		this.Wing_Base_Right.render(scale);
		this.Wing_Base_Left.render(scale);
		this.Wing_Top_Right.render(scale);
		this.Wing_Top_Left.render(scale);
		this.Wing_Mid_Left.render(scale);
		this.Wing_Mid_Right.render(scale);
		this.Wing_Bottom_Left.render(scale);
		this.Wing_Bottom_Right.render(scale);
		this.head.render(scale);
		this.body.render(scale);
		this.rightleg.render(scale);
		this.leftleg.render(scale);

		GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();
	}

	private void setRotation(ModelRenderer model, float x, float y, float z)
	{
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	public void setRotationAngles(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);

		this.leftleg.rotateAngleX = MathHelper.cos(f * 0.6662F + 3.141593F) * 1.4F * f1;
		this.rightleg.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1;

		/*this.Wing_Top_Right.rotateAngleX = 0.3490659F + (float) (Math.sin(f2 * 20 / 87.2957795) * 4.0F) / 20;
		this.Wing_Top_Left.rotateAngleX = 0.3490659F + (float) (Math.cos(f2 * 20 / 87.2957795) * 4.0F) / 20;*/
	}
}
