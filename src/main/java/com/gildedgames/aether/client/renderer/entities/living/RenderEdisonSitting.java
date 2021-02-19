package com.gildedgames.aether.client.renderer.entities.living;

import com.gildedgames.aether.api.entity.EntityCharacter;
import com.gildedgames.aether.client.models.entities.living.ModelEdisonSitting;
import com.gildedgames.aether.client.renderer.EyeUtil;
import com.gildedgames.aether.common.AetherCore;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderEdisonSitting extends RenderLiving<EntityCharacter>
{

	private static final ResourceLocation TEXTURE = AetherCore.getResource("textures/entities/npcs/edison/eddy.png");

	private static final ResourceLocation EYES_CLOSED = AetherCore.getResource("textures/entities/npcs/edison/edison_eyes_closed.png");

	private static final ResourceLocation PUPIL_LEFT = AetherCore.getResource("textures/entities/npcs/edison/edison_pupil_left.png");

	private static final ResourceLocation PUPIL_RIGHT = AetherCore.getResource("textures/entities/npcs/edison/edison_pupil_right.png");

	public RenderEdisonSitting(final RenderManager renderManager)
	{
		super(renderManager, new ModelEdisonSitting(), 0.5f);
	}

	@Override
	protected ResourceLocation getEntityTexture(final EntityCharacter entity)
	{
		return TEXTURE;
	}


}
