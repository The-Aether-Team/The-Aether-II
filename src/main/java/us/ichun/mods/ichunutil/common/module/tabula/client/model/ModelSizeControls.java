package us.ichun.mods.ichunutil.common.module.tabula.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.entity.Entity;

/**
 * ModelSizeControls - iChun
 * Created using Tabula 4.1.0
 */
public class ModelSizeControls extends ModelBase {
    public ModelRenderer shape1;
    public ModelRenderer shape2;
    public ModelRenderer shape2_1;
    public ModelRenderer shape2_2;
    public ModelRenderer shape2_3;

    public ModelSizeControls() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.shape2_1 = new ModelRenderer(this, 0, 0);
        this.shape2_1.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.shape2_1.addBox(-0.5F, 1.93F, -4.08F, 1, 1, 3, 0.0F);
        this.setRotateAngle(shape2_1, 1.0471975511965976F, 1.5707963267948966F, 0.0F);
        this.shape1 = new ModelRenderer(this, 0, 0);
        this.shape1.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.shape1.addBox(-0.5F, -1.0F, -0.5F, 1, 6, 1, 0.0F);
        this.shape2 = new ModelRenderer(this, 0, 0);
        this.shape2.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.shape2.addBox(-0.5F, 1.93F, -4.08F, 1, 1, 3, 0.0F);
        this.setRotateAngle(shape2, 1.0471975511965976F, 0.0F, 0.0F);
        this.shape2_3 = new ModelRenderer(this, 0, 0);
        this.shape2_3.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.shape2_3.addBox(-0.5F, 1.93F, -4.08F, 1, 1, 3, 0.0F);
        this.setRotateAngle(shape2_3, 1.0471975511965976F, 4.71238898038469F, 0.0F);
        this.shape2_2 = new ModelRenderer(this, 0, 0);
        this.shape2_2.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.shape2_2.addBox(-0.5F, 1.93F, -4.08F, 1, 1, 3, 0.0F);
        this.setRotateAngle(shape2_2, 1.0471975511965976F, 3.141592653589793F, 0.0F);
        this.shape1.addChild(this.shape2_1);
        this.shape1.addChild(this.shape2);
        this.shape1.addChild(this.shape2_3);
        this.shape1.addChild(this.shape2_2);
    }

    public void render(float f5) {
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
        if(shape2.compiled)
        {
            GLAllocation.deleteDisplayLists(shape2.displayList);
        }
        if(shape2_1.compiled)
        {
            GLAllocation.deleteDisplayLists(shape2_1.displayList);
        }
        if(shape2_2.compiled)
        {
            GLAllocation.deleteDisplayLists(shape2_2.displayList);
        }
        if(shape2_3.compiled)
        {
            GLAllocation.deleteDisplayLists(shape2_3.displayList);
        }
    }
}
