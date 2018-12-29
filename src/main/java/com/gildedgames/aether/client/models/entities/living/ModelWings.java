package com.gildedgames.aether.client.models.entities.living;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelWings extends ModelBase
{
	public final ModelRenderer leftWingInner;

	public final ModelRenderer leftWingOuter;

	public final ModelRenderer rightWingInner;

	public final ModelRenderer rightWingOuter;

	public ModelWings()
	{
		this.leftWingInner = new ModelRenderer(this, 0, 0);
		this.leftWingInner.addBox(-1F, -8F, -4F, 2, 16, 8, 0.0F);

		this.leftWingOuter = new ModelRenderer(this, 20, 0);
		this.leftWingOuter.addBox(-1F, -8F, -4F, 2, 16, 8, 0.0F);

		this.rightWingInner = new ModelRenderer(this, 0, 0);
		this.rightWingInner.addBox(-1F, -8F, -4F, 2, 16, 8, 0.0F);

		this.rightWingOuter = new ModelRenderer(this, 40, 0);
		this.rightWingOuter.addBox(-1F, -8F, -4F, 2, 16, 8, 0.0F);

		this.rightWingOuter.rotateAngleY = 3.14159265F;
	}
}
