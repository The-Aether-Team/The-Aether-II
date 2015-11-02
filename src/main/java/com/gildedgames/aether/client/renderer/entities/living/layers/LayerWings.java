package com.gildedgames.aether.client.renderer.entities.living.layers;

import com.gildedgames.aether.client.models.entities.living.ModelWings;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.entities.living.mounts.EntityFlyingAnimal;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

public class LayerWings implements LayerRenderer
{
	private static final ResourceLocation wingsTexture = AetherCore.getResource("textures/entities/flying_animal/wings.png");

	private final ModelWings modelWings = new ModelWings();

	private final RenderLiving renderLiving;

	private final float translateY;

	public LayerWings(RenderLiving renderLiving, float translateY)
	{
		this.renderLiving = renderLiving;
		this.translateY = translateY;
	}

	@Override
	public void doRenderLayer(EntityLivingBase entity, float p_177141_2_, float p_177141_3_, float p_177141_4_, float p_177141_5_, float p_177141_6_, float p_177141_7_, float partialTicks)
	{
		EntityFlyingAnimal animal = (EntityFlyingAnimal) entity;

		if (animal.isChild())
		{
			GlStateManager.scale(1.0F / 2.0F, 1.0F / 2.0F, 1.0F / 2.0F);
			GlStateManager.translate(0.0F, 24F * partialTicks, 0.0F);
		}

		GlStateManager.translate(0.0f, this.translateY * partialTicks, 0.0f);

		float wingBend = -(float) Math.acos(animal.wingFold);
		float x = 32F * animal.wingFold / 4F;
		float y = -32F * (float) Math.sqrt(1F - animal.wingFold * animal.wingFold) / 4F;

		float z = 0F;
		float x2 = x * (float) Math.cos(animal.wingAngle) - y * (float) Math.sin(animal.wingAngle);
		float y2 = x * (float) Math.sin(animal.wingAngle) + y * (float) Math.cos(animal.wingAngle);

		this.modelWings.leftWingInner.setRotationPoint(4F + x2, y2 + 12F, z);
		this.modelWings.rightWingInner.setRotationPoint(-4F - x2, y2 + 12F, z);

		x *= 3;
		x2 = x * (float) Math.cos(animal.wingAngle) - y * (float) Math.sin(animal.wingAngle);
		y2 = x * (float) Math.sin(animal.wingAngle) + y * (float) Math.cos(animal.wingAngle);

		this.modelWings.leftWingOuter.setRotationPoint(4F + x2, y2 + 12F, z);

		this.modelWings.rightWingOuter.setRotationPoint(-4F - x2, y2 + 12F, z);

		this.modelWings.leftWingInner.rotateAngleZ = animal.wingAngle + wingBend + 1.57079633F;
		this.modelWings.leftWingOuter.rotateAngleZ = animal.wingAngle - wingBend + 1.57079633F;

		this.modelWings.rightWingInner.rotateAngleZ = -(animal.wingAngle + wingBend - 1.57079633F);
		this.modelWings.rightWingOuter.rotateAngleZ = -(animal.wingAngle - wingBend + 1.57079633F);

		this.renderLiving.bindTexture(wingsTexture);

		this.modelWings.leftWingInner.render(partialTicks);
		this.modelWings.leftWingOuter.render(partialTicks);

		this.modelWings.rightWingOuter.render(partialTicks);
		this.modelWings.rightWingInner.render(partialTicks);
	}

	@Override
	public boolean shouldCombineTextures()
	{
		return false;
	}
}
