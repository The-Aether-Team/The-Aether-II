package com.gildedgames.aether.client.models.entities.tile;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * icestone_cooler - Undefined
 * Created using Tabula 5.1.0
 */
public class ModelIcestoneCooler extends ModelBase {
    public ModelRenderer Main;
    public ModelRenderer Base1;
    public ModelRenderer Base2;
    public ModelRenderer Lid;
    public ModelRenderer LidTop;
    public ModelRenderer LidHandle;

    public ModelIcestoneCooler() {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.LidHandle = new ModelRenderer(this, 34, 13);
        this.LidHandle.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.LidHandle.addBox(-3.5F, -1.75F, -11.0F, 7, 1, 7, 0.0F);
        this.Base2 = new ModelRenderer(this, 0, 13);
        this.Base2.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Base2.addBox(-2.0F, 18.1F, -9.0F, 4, 6, 18, 0.0F);
        this.setRotateAngle(Base2, 0.0F, 0.7853981633974483F, 0.0F);
        this.LidTop = new ModelRenderer(this, 36, 6);
        this.LidTop.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.LidTop.addBox(0.2F, -2.0F, -6.2F, 6, 1, 6, 0.0F);
        this.setRotateAngle(LidTop, 0.0F, 0.7853981633974483F, 0.0F);
        this.Base1 = new ModelRenderer(this, 0, 13);
        this.Base1.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Base1.addBox(-2.0F, 18.1F, -9.0F, 4, 6, 18, 0.0F);
        this.setRotateAngle(Base1, 0.0F, -0.7853981633974483F, 0.0F);
        this.Main = new ModelRenderer(this, 0, 38);
        this.Main.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Main.addBox(-6.0F, 10.0F, -6.0F, 12, 14, 12, 0.0F);
        this.Lid = new ModelRenderer(this, 0, 3);
        this.Lid.setRotationPoint(0.0F, 10.0F, 4.5F);
        this.Lid.addBox(-4.5F, -1.0F, -9.0F, 9, 1, 9, 0.0F);
        this.Lid.addChild(this.LidHandle);
        this.Lid.addChild(this.LidTop);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        super.render(entity, f, f1, f2, f3, f4, f5);
        this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        this.render(f5);
    }

    public void render(float f5) {
        this.Base2.render(f5);
        this.Base1.render(f5);
        this.Main.render(f5);
        this.Lid.render(f5);
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
