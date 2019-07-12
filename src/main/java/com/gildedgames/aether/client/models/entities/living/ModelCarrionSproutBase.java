package com.gildedgames.aether.client.models.entities.living;

import com.gildedgames.aether.client.renderer.ModelBaseAether;
import com.gildedgames.aether.common.entities.animals.EntityCarrionSprout;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

public class ModelCarrionSproutBase extends ModelBaseAether<EntityCarrionSprout>
{
	protected float pie = 3.141593F * 2F;

	public float sinage;

	public float sinage2;

	protected RendererModel stem;

	protected RendererModel bud_base;

	protected RendererModel petal_base;

	protected RendererModel bud_front;

	protected RendererModel bud_back;

	protected RendererModel bud_left;

	protected RendererModel bud_right;

	protected RendererModel bud_front_end;

	protected RendererModel bud_back_end;

	protected RendererModel bud_left_end;

	protected RendererModel bud_right_end;

	protected RendererModel petal_front;

	protected RendererModel petal_right;

	protected RendererModel petal_left;

	protected RendererModel petal_back;

	protected RendererModel petal_back_left;

	protected RendererModel petal_front_left;

	protected RendererModel petal_front_right;

	protected RendererModel petal_back_right;

	public ModelCarrionSproutBase()
	{
		this.textureWidth = 64;
		this.textureHeight = 64;
		this.petal_front_left = new RendererModel(this, 6, 37);
		this.petal_front_left.setRotationPoint(2.0F, 0.5F, -2.0F);
		this.petal_front_left.addBox(-3.0F, -8.0F, 0.0F, 6, 8, 0, 0.0F);
		this.setRotateAngle(this.petal_front_left, 1.3962634015954636F, -0.7853981633974483F, 0.0F);
		this.bud_right_end = new RendererModel(this, 20, 0);
		this.bud_right_end.setRotationPoint(0.0F, -9.0F, -0.5F);
		this.bud_right_end.addBox(-5.0F, -6.0F, 0.0F, 10, 6, 0, 0.0F);
		this.setRotateAngle(this.bud_right_end, -0.6981317007977318F, 0.0F, 0.0F);
		this.stem = new RendererModel(this, 24, 45);
		this.stem.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.stem.addBox(-1.5F, 2.0F, -1.5F, 3, 8, 3, 0.0F);
		this.bud_back = new RendererModel(this, 19, 6);
		this.bud_back.setRotationPoint(0.0F, -1.0F, 5.0F);
		this.bud_back.addBox(-5.0F, -9.0F, -1.0F, 10, 9, 1, 0.0F);
		this.setRotateAngle(this.bud_back, 0.0F, 3.141592653589793F, 0.0F);
		this.petal_front_right = new RendererModel(this, 6, 37);
		this.petal_front_right.setRotationPoint(-2.0F, 0.5F, -2.0F);
		this.petal_front_right.addBox(-3.0F, -8.0F, 0.0F, 6, 8, 0, 0.0F);
		this.setRotateAngle(this.petal_front_right, 1.3962634015954636F, 0.7853981633974483F, 0.0F);
		this.bud_left = new RendererModel(this, 19, 6);
		this.bud_left.setRotationPoint(5.0F, -1.0F, 0.0F);
		this.bud_left.addBox(-5.0F, -9.0F, -1.0F, 10, 9, 1, 0.0F);
		this.setRotateAngle(this.bud_left, 0.0F, -1.5707963267948966F, 0.0F);
		this.bud_right = new RendererModel(this, 19, 6);
		this.bud_right.setRotationPoint(-5.0F, -1.0F, 0.0F);
		this.bud_right.addBox(-5.0F, -9.0F, -1.0F, 10, 9, 1, 0.0F);
		this.setRotateAngle(this.bud_right, 0.0F, 1.5707963267948966F, 0.0F);
		this.petal_back_right = new RendererModel(this, 6, 37);
		this.petal_back_right.setRotationPoint(-2.0F, 0.5F, 2.0F);
		this.petal_back_right.addBox(-3.0F, -8.0F, 0.0F, 6, 8, 0, 0.0F);
		this.setRotateAngle(this.petal_back_right, 1.3962634015954636F, 2.356194490192345F, 0.0F);

		this.bud_left_end = new RendererModel(this, 20, 0);
		this.bud_left_end.setRotationPoint(0.0F, -9.0F, -0.5F);
		this.bud_left_end.addBox(-5.0F, -6.0F, 0.0F, 10, 6, 0, 0.0F);
		this.setRotateAngle(this.bud_left_end, -0.6981317007977318F, 0.0F, 0.0F);
		this.petal_base = new RendererModel(this, 18, 37);
		this.petal_base.setRotationPoint(0.0F, 4.0F, 0.0F);
		this.petal_base.addBox(-3.0F, 0.0F, -3.0F, 6, 2, 6, 0.0F);
		this.setRotateAngle(this.petal_base, 0.0F, 0.7853981633974483F, 0.0F);
		this.bud_front_end = new RendererModel(this, 20, 0);
		this.bud_front_end.setRotationPoint(0.0F, -9.0F, -0.5F);
		this.bud_front_end.addBox(-5.0F, -6.0F, 0.0F, 10, 6, 0, 0.0F);
		this.setRotateAngle(this.bud_front_end, -0.6981317007977318F, 0.0F, 0.0F);
		this.bud_front = new RendererModel(this, 19, 6);
		this.bud_front.setRotationPoint(0.0F, -1.0F, -5.0F);
		this.bud_front.addBox(-5.0F, -9.0F, -1.0F, 10, 9, 1, 0.0F);
		this.petal_back_left = new RendererModel(this, 6, 37);
		this.petal_back_left.setRotationPoint(2.0F, 0.5F, 2.0F);
		this.petal_back_left.addBox(-3.0F, -8.0F, 0.0F, 6, 8, 0, 0.0F);
		this.setRotateAngle(this.petal_back_left, 1.3962634015954636F, -2.356194490192345F, 0.0F);

		this.petal_left = new RendererModel(this, 6, 37);
		this.petal_left.setRotationPoint(2.0F, 1.5F, 0.0F);
		this.petal_left.addBox(-3.0F, -8.0F, 0.0F, 6, 8, 0, 0.0F);
		this.setRotateAngle(this.petal_left, 1.3962634015954636F, -1.5707963267948966F, 0.0F);
		this.bud_base = new RendererModel(this, 10, 25);
		this.bud_base.setRotationPoint(0.0F, 1.0F, 0.0F);
		this.bud_base.addBox(-5.0F, -1.0F, -5.0F, 10, 2, 10, 0.0F);

		this.bud_back_end = new RendererModel(this, 20, 0);
		this.bud_back_end.setRotationPoint(0.0F, -9.0F, -0.5F);
		this.bud_back_end.addBox(-5.0F, -6.0F, 0.0F, 10, 6, 0, 0.0F);
		this.setRotateAngle(this.bud_back_end, -0.6981317007977318F, 0.0F, 0.0F);

		this.petal_right = new RendererModel(this, 6, 37);
		this.petal_right.setRotationPoint(-2.0F, 1.5F, 0.0F);
		this.petal_right.addBox(-3.0F, -8.0F, 0.0F, 6, 8, 0, 0.0F);
		this.setRotateAngle(this.petal_right, 1.3962634015954636F, 1.5707963267948966F, 0.0F);
		this.petal_back = new RendererModel(this, 6, 37);
		this.petal_back.setRotationPoint(0.0F, 1.5F, 2.0F);
		this.petal_back.addBox(-3.0F, -8.0F, 0.0F, 6, 8, 0, 0.0F);
		this.setRotateAngle(this.petal_back, 1.3962634015954636F, 3.141592653589793F, 0.0F);

		this.petal_front = new RendererModel(this, 6, 37);
		this.petal_front.setRotationPoint(0.0F, 1.5F, -2.0F);
		this.petal_front.addBox(-3.0F, -8.0F, 0.0F, 6, 8, 0, 0.0F);
		this.setRotateAngle(this.petal_front, 1.3962634015954636F, 0.0F, 0.0F);
		this.petal_base.addChild(this.petal_front_left);
		this.bud_right.addChild(this.bud_right_end);
		this.bud_base.addChild(this.bud_back);
		this.petal_base.addChild(this.petal_front_right);
		this.bud_base.addChild(this.bud_left);
		this.bud_base.addChild(this.bud_right);
		this.petal_base.addChild(this.petal_back_right);
		this.bud_left.addChild(this.bud_left_end);
		this.stem.addChild(this.petal_base);
		this.bud_front.addChild(this.bud_front_end);
		this.bud_base.addChild(this.bud_front);
		this.petal_base.addChild(this.petal_back_left);
		this.petal_base.addChild(this.petal_left);
		this.stem.addChild(this.bud_base);
		this.bud_back.addChild(this.bud_back_end);
		this.petal_base.addChild(this.petal_right);
		this.petal_base.addChild(this.petal_back);
		this.petal_base.addChild(this.petal_front);
	}

	@Override
	public void setRotationAngles(final EntityCarrionSprout entity, final float f, final float f1, final float f2, final float f3, final float f4, final float f5)
	{
		super.setRotationAngles(entity, f, f1, f2, f3, f4, f5);

		final float petalAngle = this.sinage;
		final float budAngle = (this.sinage * 0.3F) + 0.1F;

		this.animatePetal(this.petal_left, 1.3962634015954636F, -1.5707963267948966F, petalAngle);
		this.animatePetal(this.petal_right, 1.3962634015954636F, 1.5707963267948966F, petalAngle);
		this.animatePetal(this.petal_back, 1.3962634015954636F, 3.141592653589793F, petalAngle);
		this.animatePetal(this.petal_front, 1.3962634015954636F, 0.0F, petalAngle);

		this.animatePetal(this.petal_back_left, 1.3962634015954636F, -2.356194490192345F, petalAngle);
		this.animatePetal(this.petal_back_right, 1.3962634015954636F, 2.356194490192345F, petalAngle);
		this.animatePetal(this.petal_front_left, 1.3962634015954636F, 0.7853981633974483F, petalAngle);
		this.animatePetal(this.petal_front_right, 1.3962634015954636F, 0.7853981633974483F, petalAngle);

		this.animatePetal(this.bud_front, 0.0F, 0.0F, budAngle);
		this.animatePetal(this.bud_back, 0.0F, 3.141592653589793F, budAngle);
		this.animatePetal(this.bud_left, 0.0F, -1.5707963267948966F, budAngle);
		this.animatePetal(this.bud_right, 0.0F, 1.5707963267948966F, budAngle);

		this.stem.rotationPointY = (this.sinage2 * 0.5F);
	}

	@Override
	public void render(final EntityCarrionSprout entity, final float f, final float f1, final float f2, final float f3, final float f4, final float f5)
	{
		this.setRotationAngles(entity, f, f1, f2, f3, f4, f5);

		GL11.glTranslatef(0.0F, 1.0F, 0.0F);

		this.stem.render(f5);
	}

	private void animatePetal(final RendererModel model, final float startAngleX, final float startAngleY, final float angleX)
	{
		model.rotateAngleX = startAngleX;
		model.rotateAngleX += angleX;
		model.rotateAngleY = startAngleY;
		model.rotateAngleY += this.pie;
	}

}
