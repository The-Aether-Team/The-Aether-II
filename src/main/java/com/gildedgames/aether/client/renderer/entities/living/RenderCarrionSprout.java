package com.gildedgames.aether.client.renderer.entities.living;

import com.gildedgames.aether.client.models.entities.living.ModelCarrionSprout;
import com.gildedgames.aether.common.entities.living.EntityCarrionSprout;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderCarrionSprout extends RenderLiving<EntityCarrionSprout>
{
	private static final ResourceLocation TEXTURE = new ResourceLocation("aether", "textures/entities/carrion_sprout/carrion_sprout.png");

	private final ModelCarrionSprout plantModel;

	public RenderCarrionSprout(RenderManager manager, ModelCarrionSprout model, float shadowSize)
	{
		super(manager, model, shadowSize);
		this.plantModel = model;
	}

	@Override
	protected void preRenderCallback(EntityCarrionSprout sprout, float partialTicks)
	{
		//GL11.glColor3f(sprout.getSproutSize(), 1.0f, sprout.getSproutSize());
		GL11.glScalef(sprout.getSproutSize(), sprout.getSproutSize(), sprout.getSproutSize());

		float sinage = (float) Math.sin(sprout.sinage - 1), sinage2;

		if (sprout.hurtTime > 0)
		{
			sinage *= 0.45F;
			sinage -= 0.125F;

			sinage2 = 1.75F + ((float) Math.sin(sprout.sinage + 2.0F) * 1.5F);
		}
		else
		{
			sinage *= 0.25F;
			sinage2 = 1.75F + ((float) Math.sin(sprout.sinage + 2.0F) * 1.5F);
		}

		this.plantModel.sinage = sinage;
		this.plantModel.sinage2 = sinage2;

		this.shadowSize = sprout.getSproutSize() / 1.5F;
	}

	/*protected int shouldRenderPass(EntityLiving entityliving, int i, float f) {
		return a((EntityAechorPlant)entityliving, i, f);
	}*/

	@Override
	protected ResourceLocation getEntityTexture(EntityCarrionSprout entity)
	{
		return TEXTURE;
	}
}
