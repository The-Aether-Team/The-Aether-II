package com.gildedgames.aether.client.renderer.entities.living;

import com.gildedgames.aether.client.models.entities.living.ModelCockatrice;
import com.gildedgames.aether.client.models.entities.living.ModelSlider;
import com.gildedgames.aether.client.renderer.entities.living.layers.GlowingLayer;
import com.gildedgames.aether.client.renderer.entities.living.layers.LayerGlowing;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.entities.dungeon.labyrinth.boss.EntitySlider;
import com.google.common.base.Supplier;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderSlider extends RenderLiving<EntitySlider>
{

	private static final ResourceLocation AWAKE = AetherCore.getResource("textures/entities/slider/awake.png");

	private static final ResourceLocation AWAKE_CRITICAL = AetherCore.getResource("textures/entities/slider/awake_critical.png");

	private static final ResourceLocation ASLEEP = AetherCore.getResource("textures/entities/slider/asleep.png");

	private static final ResourceLocation GLOW = AetherCore.getResource("textures/entities/slider/glow.png");

	private static final ResourceLocation GLOW_CRITICAL = AetherCore.getResource("textures/entities/slider/glow_critical.png");

	private GlowingLayer glowingLayer;

	public RenderSlider(RenderManager manager)
	{
		super(manager, new ModelSlider(), 1.0F);

		this.glowingLayer = new GlowingLayer();

		this.glowingLayer.setResourceLocation(GLOW);

		this.addLayer(new LayerGlowing<>(this, this.glowingLayer));
	}

	@Override
	protected ResourceLocation getEntityTexture(EntitySlider entity)
	{
		if (entity.isAwake())
		{
			if (entity.isCritical())
			{
				this.glowingLayer.setResourceLocation(GLOW_CRITICAL);

				return AWAKE_CRITICAL;
			}
			else
			{
				this.glowingLayer.setResourceLocation(GLOW);

				return AWAKE;
			}
		}
		else
		{
			this.glowingLayer.setResourceLocation(null);
		}

		return ASLEEP;
	}

}
