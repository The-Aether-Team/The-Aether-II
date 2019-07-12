package com.gildedgames.aether.client.models.entities.living;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;

public class ModelWings extends EntityModel
{
	public final RendererModel leftWingInner;

	public final RendererModel leftWingOuter;

	public final RendererModel rightWingInner;

	public final RendererModel rightWingOuter;

	public ModelWings()
	{
		this.leftWingInner = new RendererModel(this, 0, 0);
		this.leftWingInner.addBox(-1F, -8F, -4F, 2, 16, 8, 0.0F);

		this.leftWingOuter = new RendererModel(this, 20, 0);
		this.leftWingOuter.addBox(-1F, -8F, -4F, 2, 16, 8, 0.0F);

		this.rightWingInner = new RendererModel(this, 0, 0);
		this.rightWingInner.addBox(-1F, -8F, -4F, 2, 16, 8, 0.0F);

		this.rightWingOuter = new RendererModel(this, 40, 0);
		this.rightWingOuter.addBox(-1F, -8F, -4F, 2, 16, 8, 0.0F);

		this.rightWingOuter.rotateAngleY = 3.14159265F;
	}
}
