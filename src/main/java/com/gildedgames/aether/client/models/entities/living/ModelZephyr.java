package com.gildedgames.aether.client.models.entities.living;

import com.gildedgames.aether.client.renderer.entities.living.layers.ILayeredModel;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

/**
 * Zephyr.tcn - TechneToTabulaImporter
 * Created using Tabula 5.1.0
 */
public class ModelZephyr extends ModelBase
{
	public ModelRenderer body;
	public ModelRenderer top;
	public ModelRenderer bottom;
	public ModelRenderer left;
	public ModelRenderer right;
	public ModelRenderer jaw;
	public ModelRenderer tail_base;
	public ModelRenderer wing_front_left;
	public ModelRenderer wing_front_right;
	public ModelRenderer tail_end;
	public ModelRenderer wing_back_left;
	public ModelRenderer wing_back_right;

	public ModelZephyr() {
		this.textureWidth = 64;
		this.textureHeight = 64;
		this.body = new ModelRenderer(this, 0, 12);
		this.body.setRotationPoint(0.0F, 16.0F, 0.0F);
		this.body.addBox(-4.0F, -4.0F, -7.0F, 8, 4, 16, 0.0F);
		this.tail_base = new ModelRenderer(this, 0, 49);
		this.tail_base.setRotationPoint(0.0F, -2.0F, 8.0F);
		this.tail_base.addBox(-2.0F, -1.5F, -0.5F, 4, 3, 6, 0.0F);
		this.wing_front_left = new ModelRenderer(this, 42, 0);
		this.wing_front_left.mirror = true;
		this.wing_front_left.setRotationPoint(4.0F, -4.0F, 0.0F);
		this.wing_front_left.addBox(-1.0F, 0.0F, 0.0F, 1, 4, 10, 0.0F);
		this.setRotateAngle(wing_front_left, 0.4363323129985824F, 0.3490658503988659F, 0.0F);
		this.jaw = new ModelRenderer(this, 0, 44);
		this.jaw.setRotationPoint(0.0F, 15.5F, -6.5F);
		this.jaw.addBox(-3.0F, 0.0F, 0.0F, 6, 2, 3, 0.0F);
		this.top = new ModelRenderer(this, 0, 0);
		this.top.setRotationPoint(0.0F, 12.0F, 0.0F);
		this.top.addBox(-4.0F, -2.0F, -5.0F, 8, 2, 10, 0.0F);
		this.left = new ModelRenderer(this, 32, 14);
		this.left.mirror = true;
		this.left.setRotationPoint(4.0F, 14.0F, 0.0F);
		this.left.addBox(0.0F, -2.0F, -5.0F, 2, 4, 10, 0.0F);
		this.tail_end = new ModelRenderer(this, 20, 49);
		this.tail_end.setRotationPoint(0.0F, 0.0F, 4.0F);
		this.tail_end.addBox(-1.5F, -1.0F, -0.5F, 3, 2, 6, 0.0F);
		this.bottom = new ModelRenderer(this, 0, 32);
		this.bottom.setRotationPoint(0.0F, 16.0F, 0.0F);
		this.bottom.addBox(-4.0F, 0.0F, -5.0F, 8, 2, 10, 0.0F);
		this.right = new ModelRenderer(this, 32, 14);
		this.right.setRotationPoint(-4.0F, 14.0F, 0.0F);
		this.right.addBox(-2.0F, -2.0F, -5.0F, 2, 4, 10, 0.0F);
		this.wing_front_right = new ModelRenderer(this, 42, 0);
		this.wing_front_right.setRotationPoint(-4.0F, -4.0F, 0.0F);
		this.wing_front_right.addBox(0.0F, 0.0F, 0.0F, 1, 4, 10, 0.0F);
		this.setRotateAngle(wing_front_right, 0.4363323129985824F, -0.3490658503988659F, 0.0F);
		this.wing_back_left = new ModelRenderer(this, 38, 47);
		this.wing_back_left.mirror = true;
		this.wing_back_left.setRotationPoint(2.2F, -2.5F, 0.5F);
		this.wing_back_left.addBox(-1.0F, 0.0F, 0.0F, 1, 3, 8, 0.0F);
		this.setRotateAngle(wing_back_left, 0.4363323129985824F, 0.17453292519943295F, 0.3490658503988659F);
		this.wing_back_right = new ModelRenderer(this, 38, 47);
		this.wing_back_right.setRotationPoint(-2.2F, -2.5F, 0.5F);
		this.wing_back_right.addBox(0.0F, 0.0F, 0.0F, 1, 3, 8, 0.0F);
		this.setRotateAngle(wing_back_right, 0.4363323129985824F, -0.17453292519943295F, -0.3490658503988659F);
		this.body.addChild(this.tail_base);
		this.body.addChild(this.wing_front_left);
		this.tail_base.addChild(this.tail_end);
		this.body.addChild(this.wing_front_right);
		this.tail_base.addChild(this.wing_back_left);
		this.tail_base.addChild(this.wing_back_right);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		this.body.render(f5);

		this.jaw.render(f5);
		this.top.render(f5);
		this.left.render(f5);
		this.bottom.render(f5);
		this.right.render(f5);
	}

	/**
	 * This is a helper function from Tabula to set the rotation of model parts
	 */
	public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}

}
