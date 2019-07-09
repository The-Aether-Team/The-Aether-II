package com.gildedgames.aether.client.models.entities.living;

import net.minecraft.client.model.ModelRenderer;

public class ModelCarrionSproutLodHigh extends ModelCarrionSproutBase
{

	protected ModelRenderer bud_base_teeth_1;

	protected ModelRenderer bud_base_teeth_2;

	protected ModelRenderer bud_front_teeth_1;

	protected ModelRenderer bud_front_teeth_2;

	protected ModelRenderer bud_back_teeth_2;

	protected ModelRenderer bud_back_teeth_1;

	protected ModelRenderer bud_left_teeth_2;

	protected ModelRenderer bud_left_teeth_1;

	protected ModelRenderer bud_right_teeth_2;

	protected ModelRenderer bud_right_teeth_1;

	public ModelCarrionSproutLodHigh()
	{
		this.bud_left_teeth_2 = new ModelRenderer(this, 15, 6);
		this.bud_left_teeth_2.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.bud_left_teeth_2.addBox(-3.0F, -8.0F, 0.0F, 0, 8, 2, 0.0F);
		this.bud_right_teeth_2 = new ModelRenderer(this, 15, 6);
		this.bud_right_teeth_2.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.bud_right_teeth_2.addBox(-3.0F, -8.0F, 0.0F, 0, 8, 2, 0.0F);
		this.bud_base_teeth_2 = new ModelRenderer(this, 20, 6);
		this.bud_base_teeth_2.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.bud_base_teeth_2.addBox(0.0F, -3.0F, -5.0F, 0, 2, 10, 0.0F);
		this.setRotateAngle(this.bud_base_teeth_2, 0.0F, -0.7853981633974483F, 0.0F);
		this.bud_back_teeth_1 = new ModelRenderer(this, 15, 6);
		this.bud_back_teeth_1.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.bud_back_teeth_1.addBox(3.0F, -8.0F, 0.0F, 0, 8, 2, 0.0F);
		this.bud_front_teeth_1 = new ModelRenderer(this, 15, 6);
		this.bud_front_teeth_1.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.bud_front_teeth_1.addBox(3.0F, -8.0F, 0.0F, 0, 8, 2, 0.0F);
		this.bud_left_teeth_1 = new ModelRenderer(this, 15, 6);
		this.bud_left_teeth_1.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.bud_left_teeth_1.addBox(3.0F, -8.0F, 0.0F, 0, 8, 2, 0.0F);
		this.bud_right_teeth_1 = new ModelRenderer(this, 15, 6);
		this.bud_right_teeth_1.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.bud_right_teeth_1.addBox(3.0F, -8.0F, 0.0F, 0, 8, 2, 0.0F);
		this.bud_back_teeth_2 = new ModelRenderer(this, 15, 6);
		this.bud_back_teeth_2.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.bud_back_teeth_2.addBox(-3.0F, -8.0F, 0.0F, 0, 8, 2, 0.0F);
		this.bud_base_teeth_1 = new ModelRenderer(this, 20, 6);
		this.bud_base_teeth_1.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.bud_base_teeth_1.addBox(0.0F, -3.0F, -5.0F, 0, 2, 10, 0.0F);
		this.setRotateAngle(this.bud_base_teeth_1, 0.0F, 0.7853981633974483F, 0.0F);
		this.bud_front_teeth_2 = new ModelRenderer(this, 15, 6);
		this.bud_front_teeth_2.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.bud_front_teeth_2.addBox(-3.0F, -8.0F, 0.0F, 0, 8, 2, 0.0F);

		this.bud_left.addChild(this.bud_left_teeth_2);
		this.bud_right.addChild(this.bud_right_teeth_2);
		this.bud_base.addChild(this.bud_base_teeth_2);
		this.bud_right.addChild(this.bud_right_teeth_1);
		this.bud_back.addChild(this.bud_back_teeth_2);
		this.bud_base.addChild(this.bud_base_teeth_1);
		this.bud_front.addChild(this.bud_front_teeth_2);
		this.bud_left.addChild(this.bud_left_teeth_1);
		this.bud_back.addChild(this.bud_back_teeth_1);
		this.bud_front.addChild(this.bud_front_teeth_1);
	}

}
