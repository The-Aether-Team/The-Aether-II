package com.gildedgames.aether.client.models.entities.living;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * Edison Sitting 1.tcn - TechneToTabulaImporter
 * Created using Tabula 5.1.0
 */
public class ModelEdisonSitting extends ModelBase {
	public ModelRenderer waist;
	public ModelRenderer backpack;
	public ModelRenderer book;
	public ModelRenderer abdomen;
	public ModelRenderer legleft1;
	public ModelRenderer legright1;
	public ModelRenderer chest;
	public ModelRenderer head;
	public ModelRenderer armleft1;
	public ModelRenderer armright1;
	public ModelRenderer earleft;
	public ModelRenderer earright;
	public ModelRenderer hatlayer;
	public ModelRenderer armleft1_1;
	public ModelRenderer elbowleft;
	public ModelRenderer armright1_1;
	public ModelRenderer elbowright;
	public ModelRenderer legleft2;
	public ModelRenderer legright2;
	public ModelRenderer bedroll;

	public ModelEdisonSitting() {
		this.textureWidth = 64;
		this.textureHeight = 64;
		this.waist = new ModelRenderer(this, 0, 37);
		this.waist.setRotationPoint(0.0F, 5.0F, 2.0F);
		this.waist.addBox(-4.5F, 10.0F, -2.5F, 9, 4, 5, 0.0F);
		this.hatlayer = new ModelRenderer(this, 32, 0);
		this.hatlayer.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.hatlayer.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.5F);
		this.armright1_1 = new ModelRenderer(this, 28, 26);
		this.armright1_1.mirror = true;
		this.armright1_1.setRotationPoint(1.0F, 4.0F, -1.0F);
		this.armright1_1.addBox(-4.01F, 0.0F, -1.0F, 4, 6, 4, 0.0F);
		this.setRotateAngle(armright1_1, -0.8196066167365371F, 0.0F, 0.0F);
		this.elbowright = new ModelRenderer(this, 44, 24);
		this.elbowright.mirror = true;
		this.elbowright.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.elbowright.addBox(-2.5F, 3.8F, -1.2F, 3, 3, 3, 0.0F);
		this.book = new ModelRenderer(this, 52, 26);
		this.book.setRotationPoint(-6.5F, 19.0F, 3.5F);
		this.book.addBox(-2.0F, -0.5F, -2.0F, 2, 6, 4, 0.0F);
		this.setRotateAngle(book, -1.2747884856566583F, -0.045553093477052F, 1.4114477660878142F);
		this.backpack = new ModelRenderer(this, 33, 46);
		this.backpack.setRotationPoint(-2.5F, 24.0F, 2.0F);
		this.backpack.addBox(-5.0F, -1.0F, 0.0F, 10, 12, 5, 0.0F);
		this.setRotateAngle(backpack, 1.5707963267948966F, 1.5707963267948966F, 0.0F);
		this.abdomen = new ModelRenderer(this, 2, 28);
		this.abdomen.setRotationPoint(0.0F, 11.0F, 0.0F);
		this.abdomen.addBox(-4.0F, -5.0F, -2.0F, 8, 5, 4, 0.0F);
		this.setRotateAngle(abdomen, 0.08726646259971647F, 0.0F, 0.0F);
		this.earleft = new ModelRenderer(this, 24, 2);
		this.earleft.setRotationPoint(4.0F, -3.0F, -2.0F);
		this.earleft.addBox(-1.0F, -2.0F, 0.0F, 1, 3, 3, 0.0F);
		this.setRotateAngle(earleft, 0.0F, 0.2617993877991494F, 0.08726646259971647F);
		this.armleft1_1 = new ModelRenderer(this, 28, 26);
		this.armleft1_1.setRotationPoint(1.0F, 4.0F, -1.0F);
		this.armleft1_1.addBox(-1.99F, 0.0F, -1.0F, 4, 6, 4, 0.0F);
		this.setRotateAngle(armleft1_1, -1.3089969389957472F, 0.0F, 0.0F);
		this.elbowleft = new ModelRenderer(this, 44, 24);
		this.elbowleft.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.elbowleft.addBox(-0.5F, 3.8F, -1.2F, 3, 3, 3, 0.0F);
		this.legright2 = new ModelRenderer(this, 16, 46);
		this.legright2.mirror = true;
		this.legright2.setRotationPoint(0.0F, 6.0F, -2.0F);
		this.legright2.addBox(-1.99F, 0.0F, 0.0F, 4, 6, 4, 0.0F);
		this.setRotateAngle(legright2, 0.17453292519943295F, 0.0F, 0.0F);
		this.bedroll = new ModelRenderer(this, 32, 36);
		this.bedroll.setRotationPoint(0.0F, -1.3F, -1.8F);
		this.bedroll.addBox(-5.5F, -4.5F, 1.6F, 11, 5, 5, 0.0F);
		this.setRotateAngle(bedroll, 0.08726646259971647F, 0.0F, 0.0F);
		this.legleft2 = new ModelRenderer(this, 16, 46);
		this.legleft2.setRotationPoint(0.0F, 6.0F, -2.0F);
		this.legleft2.addBox(-1.99F, 0.0F, 0.0F, 4, 6, 4, 0.0F);
		this.setRotateAngle(legleft2, 0.3490658503988659F, 0.0F, 0.0F);
		this.legright1 = new ModelRenderer(this, 0, 46);
		this.legright1.mirror = true;
		this.legright1.setRotationPoint(-2.4F, 12.0F, 0.0F);
		this.legright1.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, 0.0F);
		this.setRotateAngle(legright1, -1.1838568316277536F, 0.27314402793711257F, 0.0F);
		this.chest = new ModelRenderer(this, 0, 16);
		this.chest.setRotationPoint(0.0F, -4.0F, 0.0F);
		this.chest.addBox(-4.5F, -7.0F, -2.5F, 9, 7, 5, 0.0F);
		this.setRotateAngle(chest, 0.2617993877991494F, 0.0F, 0.0F);
		this.legleft1 = new ModelRenderer(this, 0, 46);
		this.legleft1.setRotationPoint(2.4F, 12.0F, 0.0F);
		this.legleft1.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, 0.0F);
		this.setRotateAngle(legleft1, -1.2217304763960306F, -0.3141592653589793F, 0.0F);
		this.armleft1 = new ModelRenderer(this, 28, 16);
		this.armleft1.setRotationPoint(5.0F, -5.0F, 0.0F);
		this.armleft1.addBox(-1.0F, -2.0F, -2.0F, 4, 6, 4, 0.0F);
		this.setRotateAngle(armleft1, 0.0F, 0.5009094953223726F, -0.27314402793711257F);
		this.armright1 = new ModelRenderer(this, 28, 16);
		this.armright1.mirror = true;
		this.armright1.setRotationPoint(-5.0F, -5.0F, 0.0F);
		this.armright1.addBox(-3.0F, -2.0F, -2.0F, 4, 6, 4, 0.0F);
		this.setRotateAngle(armright1, 0.36425021489121656F, 0.31869712141416456F, 0.045553093477052F);
		this.earright = new ModelRenderer(this, 24, 2);
		this.earright.mirror = true;
		this.earright.setRotationPoint(-4.0F, -3.0F, -2.0F);
		this.earright.addBox(0.0F, -2.0F, 0.0F, 1, 3, 3, 0.0F);
		this.setRotateAngle(earright, 0.0F, -0.2617993877991494F, -0.08726646259971647F);
		this.head = new ModelRenderer(this, 0, 0);
		this.head.setRotationPoint(0.0F, -7.0F, 0.0F);
		this.head.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0F);
		this.setRotateAngle(head, -0.3490658503988659F, -0.0F, 0.0F);
		this.head.addChild(this.hatlayer);
		this.armright1.addChild(this.armright1_1);
		this.armright1.addChild(this.elbowright);
		this.waist.addChild(this.abdomen);
		this.head.addChild(this.earleft);
		this.armleft1.addChild(this.armleft1_1);
		this.armleft1.addChild(this.elbowleft);
		this.legright1.addChild(this.legright2);
		this.backpack.addChild(this.bedroll);
		this.legleft1.addChild(this.legleft2);
		this.waist.addChild(this.legright1);
		this.abdomen.addChild(this.chest);
		this.waist.addChild(this.legleft1);
		this.chest.addChild(this.armleft1);
		this.chest.addChild(this.armright1);
		this.head.addChild(this.earright);
		this.chest.addChild(this.head);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		this.waist.render(f5);
		this.book.render(f5);
		this.backpack.render(f5);
	}

	/**
	 * This is a helper function from Tabula to set the rotation of model parts
	 */
	public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z)
	{
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}
