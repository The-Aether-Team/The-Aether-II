package us.ichun.mods.ichunutil.common.module.tabula.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.entity.Entity;

/**
 * ModelRotationControls - iChun
 * Created using Tabula 4.1.0
 */
public class ModelRotationControls extends ModelBase {
    public ModelRenderer shape1;
    public ModelRenderer shape1_1;
    public ModelRenderer shape1_2;
    public ModelRenderer shape1_3;
    public ModelRenderer shape1_4;
    public ModelRenderer shape1_5;
    public ModelRenderer shape1_6;
    public ModelRenderer shape1_7;

    public ModelRotationControls() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.shape1_2 = new ModelRenderer(this, 0, 0);
        this.shape1_2.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.shape1_2.addBox(-2.5F, -0.5F, 5.04F, 5, 1, 1, 0.0F);
        this.setRotateAngle(shape1_2, 0.0F, 3.141592653589793F, 0.0F);
        this.shape1_1 = new ModelRenderer(this, 0, 0);
        this.shape1_1.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.shape1_1.addBox(-2.5F, -0.5F, 5.04F, 5, 1, 1, 0.0F);
        this.setRotateAngle(shape1_1, 0.0F, 2.356194490192345F, 0.0F);
        this.shape1_7 = new ModelRenderer(this, 0, 0);
        this.shape1_7.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.shape1_7.addBox(-2.5F, -0.5F, 5.04F, 5, 1, 1, 0.0F);
        this.setRotateAngle(shape1_7, 0.0F, 0.7853981633974483F, 0.0F);
        this.shape1_6 = new ModelRenderer(this, 0, 0);
        this.shape1_6.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.shape1_6.addBox(-2.5F, -0.5F, 5.04F, 5, 1, 1, 0.0F);
        this.setRotateAngle(shape1_6, 0.0F, 1.5707963267948966F, 0.0F);
        this.shape1_5 = new ModelRenderer(this, 0, 0);
        this.shape1_5.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.shape1_5.addBox(-2.5F, -0.5F, 5.04F, 5, 1, 1, 0.0F);
        this.setRotateAngle(shape1_5, 0.0F, 5.497787143782138F, 0.0F);
        this.shape1_4 = new ModelRenderer(this, 0, 0);
        this.shape1_4.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.shape1_4.addBox(-2.5F, -0.5F, 5.04F, 5, 1, 1, 0.0F);
        this.setRotateAngle(shape1_4, 0.0F, 4.71238898038469F, 0.0F);
        this.shape1_3 = new ModelRenderer(this, 0, 0);
        this.shape1_3.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.shape1_3.addBox(-2.5F, -0.5F, 5.04F, 5, 1, 1, 0.0F);
        this.setRotateAngle(shape1_3, 0.0F, 3.9269908169872414F, 0.0F);
        this.shape1 = new ModelRenderer(this, 0, 0);
        this.shape1.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.shape1.addBox(-2.5F, -0.5F, 5.04F, 5, 1, 1, 0.0F);
    }

    public void render(float f5) {
        this.shape1_2.render(f5);
        this.shape1_1.render(f5);
        this.shape1_7.render(f5);
        this.shape1_6.render(f5);
        this.shape1_5.render(f5);
        this.shape1_4.render(f5);
        this.shape1_3.render(f5);
        this.shape1.render(f5);
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }

    public void destroy()
    {
        if(shape1.compiled)
        {
            GLAllocation.deleteDisplayLists(shape1.displayList);
        }
        if(shape1_1.compiled)
        {
            GLAllocation.deleteDisplayLists(shape1_1.displayList);
        }
        if(shape1_2.compiled)
        {
            GLAllocation.deleteDisplayLists(shape1_2.displayList);
        }
        if(shape1_3.compiled)
        {
            GLAllocation.deleteDisplayLists(shape1_3.displayList);
        }
        if(shape1_4.compiled)
        {
            GLAllocation.deleteDisplayLists(shape1_4.displayList);
        }
        if(shape1_5.compiled)
        {
            GLAllocation.deleteDisplayLists(shape1_5.displayList);
        }
        if(shape1_6.compiled)
        {
            GLAllocation.deleteDisplayLists(shape1_6.displayList);
        }
        if(shape1_7.compiled)
        {
            GLAllocation.deleteDisplayLists(shape1_7.displayList);
        }
    }
}
