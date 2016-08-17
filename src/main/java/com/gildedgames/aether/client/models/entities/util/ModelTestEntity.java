package com.gildedgames.aether.client.models.entities.util;

import com.gildedgames.aether.common.entities.living.EntityAerbunny;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;

public class ModelTestEntity extends ModelBase
{

	ModelRenderer cube;

	public ModelTestEntity()
	{
		this.cube = new ModelRenderer(this, 0, 0);
		this.cube.addBox(-8.0F, 8.0F, -8.0F, 16, 16, 16);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float scale)
	{
		this.setRotationAngles(f, f1, f2, f3, f4, scale, entity);

		GlStateManager.translate(0.0F, 0.001F, 0.0F);

		AxisAlignedBB bb = entity.getEntityBoundingBox();

		double width = bb.maxX - bb.minX;
		double length = bb.maxZ - bb.minZ;
		double height = bb.maxY - bb.minY;

		GlStateManager.scale(width, length, height);

		this.cube.render(scale);
	}

}
