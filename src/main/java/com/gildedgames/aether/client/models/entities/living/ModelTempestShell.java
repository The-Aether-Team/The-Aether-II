package com.gildedgames.aether.client.models.entities.living;

import com.gildedgames.aether.client.renderer.entities.living.layers.ILayeredModel;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelTempestShell extends ModelBase implements ILayeredModel
{
	public ModelRenderer body;
	public ModelRenderer jaw;
	public ModelRenderer head;
	public ModelRenderer fin_left;
	public ModelRenderer fin_right;
	public ModelRenderer crest_centre;
	public ModelRenderer crest_left;
	public ModelRenderer crest_right;

	public ModelTempestShell() {
		this.textureWidth = 64;
		this.textureHeight = 32;
		this.crest_centre = new ModelRenderer(this, 0, 21);
		this.crest_centre.setRotationPoint(0.0F, -0.5F, 0.5F);
		this.crest_centre.addBox(-2.0F, 0.0F, 0.0F, 4, 2, 9, 0.0F);
		this.fin_right = new ModelRenderer(this, 44, 18);
		this.fin_right.mirror = true;
		this.fin_right.setRotationPoint(-3.5F, 0.0F, -3.0F);
		this.fin_right.addBox(0.0F, 0.0F, 0.0F, 2, 3, 8, 0.0F);
		this.setRotateAngle(fin_right, -0.08726646259971647F, -0.7853981633974483F, 0.0F);
		this.crest_left = new ModelRenderer(this, 26, 19);
		this.crest_left.setRotationPoint(2.0F, -0.5F, -1.5F);
		this.crest_left.addBox(-2.0F, 0.0F, 0.0F, 4, 3, 10, 0.0F);
		this.setRotateAngle(crest_left, -0.08726646259971647F, 0.5235987755982988F, 0.0F);
		this.head = new ModelRenderer(this, 0, 7);
		this.head.setRotationPoint(0.0F, -2.0F, -0.0F);
		this.head.addBox(-4.0F, -0.5F, -9.5F, 8, 4, 10, 0.0F);
		this.setRotateAngle(head, 0.20943951023931953F, 0.0F, 0.0F);
		this.fin_left = new ModelRenderer(this, 44, 18);
		this.fin_left.setRotationPoint(3.5F, 0.0F, -3.0F);
		this.fin_left.addBox(-2.0F, 0.0F, 0.0F, 2, 3, 8, 0.0F);
		this.setRotateAngle(fin_left, -0.08726646259971647F, 0.7853981633974483F, 0.0F);
		this.jaw = new ModelRenderer(this, 26, 9);
		this.jaw.setRotationPoint(0.0F, 2.0F, -3.0F);
		this.jaw.addBox(-3.5F, 0.2F, -5.5F, 7, 2, 6, 0.0F);
		this.crest_right = new ModelRenderer(this, 26, 19);
		this.crest_right.mirror = true;
		this.crest_right.setRotationPoint(-2.0F, -0.5F, -1.5F);
		this.crest_right.addBox(-2.0F, 0.0F, 0.0F, 4, 3, 10, 0.0F);
		this.setRotateAngle(crest_right, -0.08726646259971647F, -0.5235987755982988F, 0.0F);
		this.body = new ModelRenderer(this, 0, 0);
		this.body.setRotationPoint(0.0F, 14.0F, -3.0F);
		this.body.addBox(-3.0F, 2.9F, -2.8F, 0, 0, 0, 0.0F);
		this.head.addChild(this.crest_centre);
		this.body.addChild(this.fin_right);
		this.head.addChild(this.crest_left);
		this.body.addChild(this.head);
		this.body.addChild(this.fin_left);
		this.body.addChild(this.jaw);
		this.head.addChild(this.crest_right);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		this.body.render(f5);
	}

	/**
	 * This is a helper function from Tabula to set the rotation of model parts
	 */
	public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}

	@Override
	public void preLayerRender() {
		this.crest_right.isHidden = true;
		this.crest_left.isHidden = true;
		this.crest_centre.isHidden = true;
		this.jaw.isHidden = true;
		this.fin_left.isHidden = true;
		this.fin_right.isHidden = true;
	}

	@Override
	public void postLayerRender() {
		this.crest_right.isHidden = false;
		this.crest_left.isHidden = false;
		this.crest_centre.isHidden = false;
		this.jaw.isHidden = false;
		this.fin_left.isHidden = false;
		this.fin_right.isHidden = false;
	}
}
