package us.ichun.mods.ichunutil.common.module.tabula.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GLAllocation;

public class ModelRotationPoint extends ModelBase
{
    public ModelRenderer cube1;
    public ModelRenderer cube2;
    public ModelRenderer cube3;

    public ModelRotationPoint()
    {
        float size = 1;
        cube1 = new ModelRenderer(this, 0, 0);
        cube1.addBox(-(size / 2), -(size / 2), -(size / 2), (int)size, (int)size, (int)size);
        cube1.rotateAngleX = (float)Math.toRadians(45F);
        cube2 = new ModelRenderer(this, 0, 0);
        cube2.addBox(-(size / 2), -(size / 2), -(size / 2), (int)size, (int)size, (int)size);
        cube2.rotateAngleY = (float)Math.toRadians(45F);
        cube3 = new ModelRenderer(this, 0, 0);
        cube3.addBox(-(size / 2), -(size / 2), -(size / 2), (int)size, (int)size, (int)size);
        cube3.rotateAngleZ = (float)Math.toRadians(45F);
    }

    public void render(float f5)
    {
        cube1.render(f5);
        cube2.render(f5);
        cube3.render(f5);
    }

    public void destroy()
    {
        if(cube1.compiled)
        {
            GLAllocation.deleteDisplayLists(cube1.displayList);
        }
        if(cube2.compiled)
        {
            GLAllocation.deleteDisplayLists(cube2.displayList);
        }
        if(cube3.compiled)
        {
            GLAllocation.deleteDisplayLists(cube3.displayList);
        }
    }
}
