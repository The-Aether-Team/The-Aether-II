package com.gildedgames.aether.client.models.entities.tile;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * masonry_bench.tcn - TechneToTabulaImporter
 * Created using Tabula 5.1.0
 */
public class ModelMasonryBench extends ModelBase {
    public ModelRenderer MainLeft;
    public ModelRenderer TopRight;
    public ModelRenderer TopBack;
    public ModelRenderer TopLeft;
    public ModelRenderer Workslab;
    public ModelRenderer SawRotor;
    public ModelRenderer SawRight;
    public ModelRenderer SawLeft;
    public ModelRenderer RimRight;
    public ModelRenderer RimBack;
    public ModelRenderer RimLeft;

    public ModelMasonryBench() {
        this.textureWidth = 128;
        this.textureHeight = 128;
        this.SawRotor = new ModelRenderer(this, 40, 29);
        this.SawRotor.setRotationPoint(2.0F, 11.0F, 0.0F);
        this.SawRotor.addBox(0.0F, -0.5F, -0.5F, 3, 1, 1, 0.0F);
        this.setRotateAngle(SawRotor, 0.7853981852531433F, -0.0F, 0.0F);
        this.RimBack = new ModelRenderer(this, 32, 14);
        this.RimBack.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.RimBack.addBox(-8.0F, 8.0F, 7.0F, 16, 2, 1, 0.0F);
        this.RimLeft = new ModelRenderer(this, 66, 0);
        this.RimLeft.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.RimLeft.addBox(7.0F, 8.0F, -8.0F, 1, 2, 15, 0.0F);
        this.RimRight = new ModelRenderer(this, 0, 0);
        this.RimRight.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.RimRight.addBox(-8.0F, 8.0F, -8.0F, 1, 2, 15, 0.0F);
        this.Workslab = new ModelRenderer(this, 0, 17);
        this.Workslab.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Workslab.addBox(-6.0F, 9.5F, -7.0F, 7, 1, 13, 0.0F);
        this.SawRight = new ModelRenderer(this, 48, 21);
        this.SawRight.setRotationPoint(2.0F, 11.0F, 0.0F);
        this.SawRight.addBox(0.8999999761581421F, -2.5F, -2.5F, 1, 5, 5, 0.0F);
        this.TopBack = new ModelRenderer(this, 52, 44);
        this.TopBack.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.TopBack.addBox(2.0F, 10.0F, 5.0F, 3, 2, 3, 0.0F);
        this.TopRight = new ModelRenderer(this, 0, 31);
        this.TopRight.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.TopRight.addBox(-8.0F, 10.0F, -8.0F, 10, 2, 16, 0.0F);
        this.TopLeft = new ModelRenderer(this, 64, 31);
        this.TopLeft.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.TopLeft.addBox(5.0F, 10.0F, -8.0F, 3, 2, 16, 0.0F);
        this.SawLeft = new ModelRenderer(this, 60, 21);
        this.SawLeft.setRotationPoint(2.0F, 11.0F, 0.0F);
        this.SawLeft.addBox(1.100000023841858F, -2.5F, -2.5F, 1, 5, 5, 0.0F);
        this.setRotateAngle(SawLeft, 0.7853981852531433F, -0.0F, 0.0F);
        this.MainLeft = new ModelRenderer(this, 0, 49);
        this.MainLeft.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.MainLeft.addBox(-8.0F, 12.0F, -8.0F, 16, 12, 16, 0.0F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        super.render(entity, f, f1, f2, f3, f4, f5);
        this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        this.render(f5);
    }

    public void render(float f5) {
        this.SawRotor.render(f5);
        this.RimBack.render(f5);
        this.RimLeft.render(f5);
        this.RimRight.render(f5);
        this.Workslab.render(f5);
        this.SawRight.render(f5);
        this.TopBack.render(f5);
        this.TopRight.render(f5);
        this.TopLeft.render(f5);
        this.SawLeft.render(f5);
        this.MainLeft.render(f5);
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
    {
        super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    }
}
