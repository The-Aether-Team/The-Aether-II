package com.gildedgames.aether.client.renderer.entities.attachments;

import com.gildedgames.aether.client.models.entities.attachments.ModelParachute;
import com.gildedgames.aether.common.entities.blocks.EntityParachute;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderParachute extends Render<EntityParachute>
{

	private final ModelParachute model = new ModelParachute();

	public RenderParachute(EntityRendererManager renderManager)
	{
		super(renderManager);

		this.shadowSize = 1.0F;
	}

	@Override
	public void doRender(EntityParachute entity, double x, double y, double z, float entityYaw, float partialTicks)
	{
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glDepthMask(true);
		GL11.glRotatef(-180F, 0.005F, 0F, 10F);

		float f = 0.0625F;

		if (entity.getParachutingPlayer() != null)
		{
			PlayerEntity player = entity.getParachutingPlayer();
			float f2 = player.prevRenderYawOffset + ((player.renderYawOffset - player.prevRenderYawOffset));
			GL11.glRotatef(f2, 0F, Math.abs(player.rotationYaw), 0F);
		}

		GL11.glTranslatef(0.028f, -2f, -0.40f);
		this.renderManager.textureManager.bindTexture(entity.getType().texture);

		this.model.Cloud1.render(f);
		this.model.Cloud2.render(f);
		this.model.Cloud3.render(f);
		this.model.Cloud4.render(f);
		this.model.Cloud5.render(f);
		this.model.Shape1.render(f);
		this.model.Shape2.render(f);
		this.model.Shape3.render(f);
		this.model.Shape4.render(f);

		GL11.glDepthMask(false);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityParachute entity)
	{
		return null;
	}
}
