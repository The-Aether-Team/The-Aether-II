package com.gildedgames.aether.client.renderer.entities.projectiles;

import com.gildedgames.aether.client.models.entities.attachments.ModelBattleBomb;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.entities.projectiles.EntityBattleBomb;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderBattleBomb extends Render<EntityBattleBomb>
{

	private static final ResourceLocation texture = AetherCore.getResource("textures/entities/battle_bomb/battle_bomb.png");

	private final ModelBattleBomb model = new ModelBattleBomb();

	public RenderBattleBomb(RenderManager renderManager)
	{
		super(renderManager);
	}

	public void doRender(EntityBattleBomb entity, double posX, double posY, double posZ, float entityYaw, float partialTicks)
	{
		GL11.glPushMatrix();

		float f = 0.0625F;

		this.renderManager.renderEngine.bindTexture(texture);

		GL11.glTranslated(posX, posY, posZ);

		this.model.render(entity, 0, 0, 0, 0, 0, f);

		GL11.glPopMatrix();

		super.doRender(entity, posX, posY, posZ, entityYaw, partialTicks);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityBattleBomb entity)
	{
		return null;
	}
}
