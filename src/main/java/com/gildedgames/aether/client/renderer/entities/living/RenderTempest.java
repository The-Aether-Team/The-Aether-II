package com.gildedgames.aether.client.renderer.entities.living;

import com.gildedgames.aether.client.models.entities.living.ModelTempest;
import com.gildedgames.aether.client.renderer.entities.living.layers.LayerGlowing;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.entities.monsters.EntityTempest;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.MobEntity;
import net.minecraft.util.ResourceLocation;

public class RenderTempest extends LivingRenderer<EntityTempest, ModelTempest>
{

	private static final ResourceLocation TEXTURE = AetherCore.getResource("textures/entities/tempest/tempest.png");

	private static final ResourceLocation TEXTURE_MARKINGS = AetherCore.getResource("textures/entities/tempest/tempest_markings.png");

	public RenderTempest(EntityRendererManager manager)
	{
		super(manager, new ModelTempest(), 1.0F);

		this.addLayer(new LayerGlowing<>(this, TEXTURE_MARKINGS));
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityTempest entity)
	{
		return TEXTURE;
	}

}
