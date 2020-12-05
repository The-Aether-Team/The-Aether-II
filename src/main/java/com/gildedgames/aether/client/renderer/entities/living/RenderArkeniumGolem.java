package com.gildedgames.aether.client.renderer.entities.living;


import com.gildedgames.aether.client.models.entities.living.ModelArkeniumGolem;
import com.gildedgames.aether.client.renderer.entities.living.layers.LayerGlowing;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.entities.monsters.EntityArkeniumGolem;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderArkeniumGolem extends RenderLiving<EntityArkeniumGolem>
{

	private static final ResourceLocation TEXTURE = AetherCore.getResource("textures/entities/arkenium_golem/arkenium_golem.png");

	private static final ResourceLocation TEXTURE_MARKINGS = AetherCore.getResource("textures/entities/arkenium_golem/arkenium_golem_eyes.png");

	public RenderArkeniumGolem(RenderManager manager)
	{
		super(manager, new ModelArkeniumGolem(), 1.0F);

		this.addLayer(new LayerGlowing<>(this, TEXTURE_MARKINGS));
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityArkeniumGolem entity)
	{
		return TEXTURE;
	}

}
