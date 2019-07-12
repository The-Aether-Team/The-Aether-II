package com.gildedgames.aether.client.renderer.entities.living;

import com.gildedgames.aether.client.models.entities.living.ModelJosediya;
import com.gildedgames.aether.client.renderer.EyeUtil;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.entities.characters.EntityJosediya;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.util.ResourceLocation;

public class RenderJosediya extends LivingRenderer<EntityJosediya, ModelJosediya>
{

	private static final ResourceLocation TEXTURE = AetherCore.getResource("textures/entities/npcs/josediya/josediya.png");

	private static final ResourceLocation EYES_CLOSED = AetherCore.getResource("textures/entities/npcs/josediya/josediya_eyes_closed.png");

	private static final ResourceLocation PUPIL_LEFT = AetherCore.getResource("textures/entities/npcs/josediya/josediya_pupil_left.png");

	private static final ResourceLocation PUPIL_RIGHT = AetherCore.getResource("textures/entities/npcs/josediya/josediya_pupil_right.png");

	public RenderJosediya(final EntityRendererManager renderManager)
	{
		super(renderManager, new ModelJosediya(), 0.5f);
	}

	@Override
	protected ResourceLocation getEntityTexture(final EntityJosediya entity)
	{
		return TEXTURE;
	}

	@Override
	protected void renderModel(EntityJosediya entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale)
	{
		super.renderModel(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);

		ModelJosediya model = this.getEntityModel();

		boolean globalInvisible = !entity.isInvisible() || this.renderOutlines;
		boolean playerInvisible = !globalInvisible && !entity.isInvisibleToPlayer(Minecraft.getInstance().player);

		if (globalInvisible || playerInvisible)
		{
			EyeUtil.renderEyes(this.renderManager, model, model.head1, entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, PUPIL_LEFT,
					PUPIL_RIGHT, EYES_CLOSED, true);
		}
	}

}
