package com.gildedgames.aether.client.renderer.entities.attachments;

import com.gildedgames.aether.client.models.entities.attachments.ModelParachute;
import com.gildedgames.aether.common.entities.blocks.EntityParachute;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderParachute extends Render<EntityParachute>
{

	private final ModelParachute model = new ModelParachute();

	public RenderParachute(RenderManager renderManager)
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
			EntityPlayer player = entity.getParachutingPlayer();
			float f2 = player.prevRenderYawOffset + ((player.renderYawOffset - player.prevRenderYawOffset));
			GL11.glRotatef(f2, 0F, Math.abs(player.rotationYaw), 0F);

			if (player.isSneaking())
			{
				GL11.glTranslatef(0.0f, -1.05f, 0.0f);
			}
			else
			{
				GL11.glTranslatef(0.0f, -1.4f, 0.0f);
			}
		}

		this.renderManager.renderEngine.bindTexture(entity.getType().texture);

		this.model.Canopy_Main.render(f);
		this.model.Canopy_2nd_Right.render(f);
		this.model.Canopy_2nd_Left.render(f);
		this.model.Canopy_3rd_Right.render(f);
		this.model.Canopy_3rd_Left.render(f);
		this.model.Cord_Front_Left.render(f);
		this.model.Cord_Front_Right.render(f);
		this.model.Cord_Back_Left.render(f);
		this.model.Cord_Back_Right.render(f);
		this.model.Cord_Middle_Left.render(f);
		this.model.Cord_Middle_Right.render(f);
		this.model.Chest_Strap.render(f);
		this.model.Shoulder_Strap_Right.render(f);
		this.model.Shoulder_Strap_Left.render(f);

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
