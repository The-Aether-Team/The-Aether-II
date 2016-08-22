package com.gildedgames.aether.client.renderer.entities.living;

import com.gildedgames.aether.client.models.entities.living.ModelTempest;
import com.gildedgames.aether.client.renderer.entities.living.layers.LayerGlowing;
import com.gildedgames.aether.common.AetherCore;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

public class RenderTempest extends RenderLiving<EntityLiving>
{

	private static final ResourceLocation TEXTURE = AetherCore.getResource("textures/entities/tempest/tempest.png");

	private static final ResourceLocation TEXTURE_MARKINGS = AetherCore.getResource("textures/entities/tempest/markings.png");

	public RenderTempest(RenderManager manager)
	{
		super(manager, new ModelTempest(), 1.0F);

		this.addLayer(new LayerGlowing<>(this, TEXTURE_MARKINGS));
	}

	@Override
	protected void preRenderCallback(EntityLiving entity, float f)
	{
		GlStateManager.translate(0.0D, 0.5D, 0.0D);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityLiving entity)
	{
		return TEXTURE;
	}

}
