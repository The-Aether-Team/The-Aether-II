package us.ichun.mods.ichunutil.common.module.tabula.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import us.ichun.mods.ichunutil.common.module.tabula.client.wrappers.ModelRendererAccessor;

public class ModelRotationPoint extends ModelBase
{
	public ModelRenderer cube1;

	public ModelRenderer cube2;

	public ModelRenderer cube3;

	public ModelRotationPoint()
	{
		float size = 1;
		this.cube1 = new ModelRenderer(this, 0, 0);
		this.cube1.addBox(-(size / 2), -(size / 2), -(size / 2), (int) size, (int) size, (int) size);
		this.cube1.rotateAngleX = (float) Math.toRadians(45F);
		this.cube2 = new ModelRenderer(this, 0, 0);
		this.cube2.addBox(-(size / 2), -(size / 2), -(size / 2), (int) size, (int) size, (int) size);
		this.cube2.rotateAngleY = (float) Math.toRadians(45F);
		this.cube3 = new ModelRenderer(this, 0, 0);
		this.cube3.addBox(-(size / 2), -(size / 2), -(size / 2), (int) size, (int) size, (int) size);
		this.cube3.rotateAngleZ = (float) Math.toRadians(45F);
	}

	public void render(float f5)
	{
		this.cube1.render(f5);
		this.cube2.render(f5);
		this.cube3.render(f5);
	}

	public void destroy()
	{
		if (ModelRendererAccessor.isCompiled(this.cube1))
		{
			ModelRendererAccessor.deleteDisplayLists(this.cube1);
		}
		if (ModelRendererAccessor.isCompiled(this.cube2))
		{
			ModelRendererAccessor.deleteDisplayLists(this.cube2);
		}
		if (ModelRendererAccessor.isCompiled(this.cube3))
		{
			ModelRendererAccessor.deleteDisplayLists(this.cube3);
		}
	}
}
