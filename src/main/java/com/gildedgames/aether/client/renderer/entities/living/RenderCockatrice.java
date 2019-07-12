package com.gildedgames.aether.client.renderer.entities.living;

import com.gildedgames.aether.client.models.entities.living.ModelCockatrice;
import com.gildedgames.aether.client.renderer.entities.living.layers.LayerGlowing;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.entities.monsters.EntityCockatrice;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.util.ResourceLocation;

public class RenderCockatrice extends LivingRenderer<EntityCockatrice, ModelCockatrice>
{

	private static final ResourceLocation TEXTURE = AetherCore.getResource("textures/entities/cockatrice/cockatrice.png");

	private static final ResourceLocation TEXTURE_MARKINGS = AetherCore.getResource("textures/entities/cockatrice/markings.png");

	public RenderCockatrice(EntityRendererManager manager)
	{
		super(manager, new ModelCockatrice(), 0.75F);

		this.addLayer(new LayerGlowing<>(this, TEXTURE_MARKINGS));
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityCockatrice entity)
	{
		return TEXTURE;
	}

}
