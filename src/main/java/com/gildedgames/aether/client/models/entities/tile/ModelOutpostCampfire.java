package com.gildedgames.aether.client.models.entities.tile;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelOutpostCampfire extends ModelBase
{
    public ModelRenderer wallright;
    public ModelRenderer wallleft;
    public ModelRenderer wallback;
    public ModelRenderer wallfront;
    public ModelRenderer wallbackleft;
    public ModelRenderer wallfrontleft;
    public ModelRenderer wallfrontright;
    public ModelRenderer wallbackright;
    public ModelRenderer basefront;
    public ModelRenderer baseback;
    public ModelRenderer baseright;
    public ModelRenderer baseleft;
    public ModelRenderer basefrontright;
    public ModelRenderer basefrontleft;
    public ModelRenderer basebackleft;
    public ModelRenderer basebackright;
    public ModelRenderer basemiddle;
    public ModelRenderer log1;
    public ModelRenderer log2;
    public ModelRenderer log3;
    public ModelRenderer log4;
    public ModelRenderer log5;
    public ModelRenderer log6;
    public ModelRenderer stick1;
    public ModelRenderer stick2;
    public ModelRenderer stick3;
    public ModelRenderer stick4;
    public ModelRenderer stick5;
    public ModelRenderer stick6;
    public ModelRenderer flame1;
    public ModelRenderer flame2;

    public ModelOutpostCampfire()
    {
        this.textureWidth = 64;
        this.textureHeight = 256;
        this.basefrontright = new ModelRenderer(this, 0, 154);
        this.basefrontright.setRotationPoint(8.0F, 24.0F, 8.0F);
        this.basefrontright.addBox(-5.0F, -0.20000000298023224F, -15.0F, 10, 1, 5, 0.0F);
        this.setRotateAngle(basefrontright, 0.0F, 0.7853981852531433F, 0.0F);
        this.basemiddle = new ModelRenderer(this, 0, 117);
        this.basemiddle.setRotationPoint(8.0F, 24.0F, 8.0F);
        this.basemiddle.addBox(-7.0F, -0.800000011920929F, -7.0F, 14, 1, 14, 0.0F);
        this.wallbackleft = new ModelRenderer(this, 40, 246);
        this.wallbackleft.setRotationPoint(8.0F, 24.0F, 8.0F);
        this.wallbackleft.addBox(-5.0F, -3.0F, 15.0F, 10, 3, 2, 0.0F);
        this.setRotateAngle(wallbackleft, 0.0F, 0.7853981852531433F, 0.0F);
        this.stick4 = new ModelRenderer(this, 0, 37);
        this.stick4.setRotationPoint(11.0F, 19.0F, 11.0F);
        this.stick4.addBox(0.0F, 0.0F, 0.0F, 8, 1, 1, 0.0F);
        this.setRotateAngle(stick4, -0.1927023226432416F, -0.6118199524026158F, 0.7029732375442334F);
        this.stick5 = new ModelRenderer(this, 0, 35);
        this.stick5.setRotationPoint(0.0F, 23.0F, 16.0F);
        this.stick5.addBox(-1.0F, 0.0F, 0.0F, 10, 1, 1, 0.0F);
        this.setRotateAngle(stick5, -0.3399323192398366F, 0.3098565859018108F, -0.9146956490129345F);
        this.basefrontleft = new ModelRenderer(this, 0, 143);
        this.basefrontleft.setRotationPoint(8.0F, 24.0F, 8.0F);
        this.basefrontleft.addBox(10.0F, -0.20000000298023224F, -5.0F, 5, 1, 10, 0.0F);
        this.setRotateAngle(basefrontleft, 0.0F, 0.7853981852531433F, 0.0F);
        this.log3 = new ModelRenderer(this, 0, 80);
        this.log3.setRotationPoint(6.0F, 22.0F, -3.0F);
        this.log3.addBox(0.0F, 0.0F, -1.0F, 3, 3, 9, 0.0F);
        this.setRotateAngle(log3, 0.3717861175537108F, -0.07435721904039383F, 0.0F);
        this.log5 = new ModelRenderer(this, 0, 63);
        this.log5.setRotationPoint(8.0F, 21.0F, 14.0F);
        this.log5.addBox(0.0F, 0.0F, -1.0F, 3, 3, 8, 0.0F);
        this.setRotateAngle(log5, -0.22307166457176206F, -0.3346075117588043F, 0.0F);
        this.log2 = new ModelRenderer(this, 0, 92);
        this.log2.setRotationPoint(7.0F, 14.0F, 6.0F);
        this.log2.addBox(0.0F, 0.0F, 0.0F, 11, 4, 4, 0.0F);
        this.setRotateAngle(log2, 0.3995053943177482F, 0.40012552022054276F, 0.825630955210822F);
        this.basebackleft = new ModelRenderer(this, 0, 160);
        this.basebackleft.setRotationPoint(8.0F, 24.0F, 8.0F);
        this.basebackleft.addBox(-5.0F, -0.20000000298023224F, 10.0F, 10, 1, 5, 0.0F);
        this.setRotateAngle(basebackleft, 0.0F, 0.7853981852531433F, 0.0F);
        this.wallright = new ModelRenderer(this, 0, 212);
        this.wallright.setRotationPoint(8.0F, 24.0F, 8.0F);
        this.wallright.addBox(-16.0F, -3.0F, -7.0F, 2, 3, 14, 0.0F);
        this.wallleft = new ModelRenderer(this, 0, 229);
        this.wallleft.setRotationPoint(8.0F, 24.0F, 8.0F);
        this.wallleft.addBox(14.0F, -3.0F, -7.0F, 2, 3, 14, 0.0F);
        this.stick1 = new ModelRenderer(this, 0, 49);
        this.stick1.setRotationPoint(0.0F, 23.0F, 0.0F);
        this.stick1.addBox(0.0F, 0.0F, 0.0F, 1, 1, 7, 0.0F);
        this.setRotateAngle(stick1, 0.919958600895923F, 0.8059776102653321F, 0.4370326840040265F);
        this.baseleft = new ModelRenderer(this, 0, 166);
        this.baseleft.setRotationPoint(22.0F, 24.0F, 7.0F);
        this.baseleft.addBox(-7.0F, -0.20000000298023224F, -6.0F, 7, 1, 14, 0.0F);
        this.setRotateAngle(baseleft, 0.0F, -0.0F, 0.08726646006107329F);
        this.wallfront = new ModelRenderer(this, 0, 251);
        this.wallfront.setRotationPoint(8.0F, 24.0F, 8.0F);
        this.wallfront.addBox(-7.0F, -3.0F, -16.0F, 14, 3, 2, 0.0F);
        this.baseback = new ModelRenderer(this, 0, 196);
        this.baseback.setRotationPoint(8.0F, 24.0F, 22.0F);
        this.baseback.addBox(-7.0F, -0.20000000298023224F, -7.0F, 14, 1, 7, 0.0F);
        this.setRotateAngle(baseback, -0.08726646006107329F, -0.0F, 0.0F);
        this.wallback = new ModelRenderer(this, 0, 246);
        this.wallback.setRotationPoint(8.0F, 24.0F, 8.0F);
        this.wallback.addBox(-7.0F, -3.0F, 14.0F, 14, 3, 2, 0.0F);
        this.wallfrontleft = new ModelRenderer(this, 40, 220);
        this.wallfrontleft.setRotationPoint(8.0F, 24.0F, 8.0F);
        this.wallfrontleft.addBox(15.0F, -3.0F, -5.0F, 2, 3, 10, 0.0F);
        this.setRotateAngle(wallfrontleft, 0.0F, 0.7853981852531433F, 0.0F);
        this.stick3 = new ModelRenderer(this, 0, 39);
        this.stick3.setRotationPoint(12.0F, 20.0F, 8.0F);
        this.stick3.addBox(0.0F, 0.0F, 0.0F, 8, 1, 1, 0.0F);
        this.setRotateAngle(stick3, 0.7433089541584427F, 0.5828747280373996F, 0.45017882994495967F);
        this.flame1 = new ModelRenderer(this, 0, -11);
        this.flame1.setRotationPoint(8.0F, 24.0F, 8.0F);
        this.flame1.addBox(0.0F, -16.0F, -14.0F, 0, 16, 28, 0.0F);
        this.wallfrontright = new ModelRenderer(this, 40, 251);
        this.wallfrontright.setRotationPoint(8.0F, 24.0F, 8.0F);
        this.wallfrontright.addBox(-5.0F, -3.0F, -17.0F, 10, 3, 2, 0.0F);
        this.setRotateAngle(wallfrontright, 0.0F, 0.7853981852531433F, 0.0F);
        this.log1 = new ModelRenderer(this, 0, 100);
        this.log1.setRotationPoint(0.0F, 18.0F, 6.0F);
        this.log1.addBox(0.0F, 0.0F, 0.0F, 5, 5, 12, 0.0F);
        this.setRotateAngle(log1, 0.0F, 0.5948577523231506F, 0.0F);
        this.basebackright = new ModelRenderer(this, 0, 132);
        this.basebackright.setRotationPoint(8.0F, 24.0F, 8.0F);
        this.basebackright.addBox(-15.0F, -0.20000000298023224F, -5.0F, 5, 1, 10, 0.0F);
        this.setRotateAngle(basebackright, 0.0F, 0.7853981852531433F, 0.0F);
        this.log6 = new ModelRenderer(this, 0, 57);
        this.log6.setRotationPoint(-5.0F, 22.0F, 6.0F);
        this.log6.addBox(0.0F, 0.0F, 0.0F, 7, 3, 3, 0.0F);
        this.setRotateAngle(log6, 0.03474486630428221F, -0.1826539890642162F, -0.1890753719838405F);
        this.flame2 = new ModelRenderer(this, 0, 17);
        this.flame2.setRotationPoint(8.0F, 24.0F, 8.0F);
        this.flame2.addBox(-14.0F, -16.0F, 0.0F, 28, 16, 0, 0.0F);
        this.stick6 = new ModelRenderer(this, 0, 33);
        this.stick6.setRotationPoint(-5.0F, 23.0F, 14.0F);
        this.stick6.addBox(0.0F, 0.0F, 0.0F, 9, 1, 1, 0.0F);
        this.setRotateAngle(stick6, -0.5202518955778146F, 0.5156060740289738F, -0.43076516568685086F);
        this.basefront = new ModelRenderer(this, 0, 204);
        this.basefront.setRotationPoint(8.0F, 24.0F, -6.0F);
        this.basefront.addBox(-7.0F, -0.20000000298023224F, 0.0F, 14, 1, 7, 0.0F);
        this.setRotateAngle(basefront, 0.08726646006107329F, -0.0F, 0.0F);
        this.baseright = new ModelRenderer(this, 0, 181);
        this.baseright.setRotationPoint(-6.0F, 24.0F, 8.0F);
        this.baseright.addBox(0.0F, -0.20000000298023224F, -7.0F, 7, 1, 14, 0.0F);
        this.setRotateAngle(baseright, 0.0F, -0.0F, -0.08726646006107329F);
        this.stick2 = new ModelRenderer(this, 0, 41);
        this.stick2.setRotationPoint(13.0F, 23.0F, -2.0F);
        this.stick2.addBox(0.0F, 0.0F, 0.0F, 1, 1, 7, 0.0F);
        this.setRotateAngle(stick2, -0.1064627175234901F, -0.642731696348357F, 0.6194275173554241F);
        this.wallbackright = new ModelRenderer(this, 40, 233);
        this.wallbackright.setRotationPoint(8.0F, 24.0F, 8.0F);
        this.wallbackright.addBox(-17.0F, -3.0F, -5.0F, 2, 3, 10, 0.0F);
        this.setRotateAngle(wallbackright, 0.0F, 0.7853981852531433F, 0.0F);
        this.log4 = new ModelRenderer(this, 0, 74);
        this.log4.setRotationPoint(10.0F, 20.0F, 8.0F);
        this.log4.addBox(0.0F, 0.0F, 0.0F, 10, 3, 3, 0.0F);
        this.setRotateAngle(log4, 0.1020406195281061F, 0.1461337633559016F, 0.1879195346028469F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        this.render(f5);
    }

    public void render(float scale)
    {
        this.basefrontright.render(scale);
        this.basemiddle.render(scale);
        this.wallbackleft.render(scale);
        this.stick4.render(scale);
        this.stick5.render(scale);
        this.basefrontleft.render(scale);
        this.log3.render(scale);
        this.log5.render(scale);
        this.log2.render(scale);
        this.basebackleft.render(scale);
        this.wallright.render(scale);
        this.wallleft.render(scale);
        this.stick1.render(scale);
        this.baseleft.render(scale);
        this.wallfront.render(scale);
        this.baseback.render(scale);
        this.wallback.render(scale);
        this.wallfrontleft.render(scale);
        this.stick3.render(scale);
        this.flame1.render(scale);
        this.wallfrontright.render(scale);
        this.log1.render(scale);
        this.basebackright.render(scale);
        this.log6.render(scale);
        this.flame2.render(scale);
        this.stick6.render(scale);
        this.basefront.render(scale);
        this.baseright.render(scale);
        this.stick2.render(scale);
        this.wallbackright.render(scale);
        this.log4.render(scale);
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
