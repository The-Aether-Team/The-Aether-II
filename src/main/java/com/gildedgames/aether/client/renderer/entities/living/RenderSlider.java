package com.gildedgames.aether.client.renderer.entities.living;

import com.gildedgames.aether.client.models.entities.living.ModelSlider;
import com.gildedgames.aether.client.renderer.entities.living.layers.GlowingLayer;
import com.gildedgames.aether.client.renderer.entities.living.layers.LayerGlowing;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.entities.living.boss.slider.EntitySlider;
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

	private static final ResourceLocation SIGNAL_LEFT = AetherCore.getResource("textures/entities/slider/signal_left.png");

	private static final ResourceLocation SIGNAL_RIGHT = AetherCore.getResource("textures/entities/slider/signal_right.png");

	private static final ResourceLocation SIGNAL_FORWARD = AetherCore.getResource("textures/entities/slider/signal_forward.png");

	private static final ResourceLocation SIGNAL_BACKWARD = AetherCore.getResource("textures/entities/slider/signal_backward.png");

	private GlowingLayer glowingLayer, signalLayer;

	public RenderSlider(RenderManager manager)
	{
		super(manager, new ModelSlider(), 1.0F);

		this.glowingLayer = new GlowingLayer();
		this.signalLayer = new GlowingLayer();

		this.glowingLayer.setResourceLocation(GLOW);
		this.signalLayer.setResourceLocation(null);

		this.addLayer(new LayerGlowing<>(this, this.glowingLayer));
		this.addLayer(new LayerGlowing<>(this, this.signalLayer));
	}

	@Override
	protected ResourceLocation getEntityTexture(EntitySlider entity)
	{
		if (entity.isAwake())
		{
			boolean signalling = (entity.getSignalTimer().getTicksPassed() == 0 || entity.getSignalTimer().getTicksPassed() == 5);

			if (entity.isCritical())
			{
				this.signalLayer.setResourceLocation(signalling ? GLOW : null);
			}
			else
			{
				switch (entity.getDirection())
				{
					case NONE:
					{
						this.signalLayer.setResourceLocation(null);
						break;
					}
					case RIGHT:
					{
						this.signalLayer.setResourceLocation(signalling ? SIGNAL_RIGHT : null);
						break;
					}
					case LEFT:
					{
						this.signalLayer.setResourceLocation(signalling ? SIGNAL_LEFT : null);
						break;
					}
					case FORWARD:
					{
						this.signalLayer.setResourceLocation(signalling ? SIGNAL_FORWARD : null);
						break;
					}
					case BACKWARD:
					{
						this.signalLayer.setResourceLocation(signalling ? SIGNAL_BACKWARD : null);
						break;
					}
				}
			}

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
			this.signalLayer.setResourceLocation(null);
		}

		return ASLEEP;
	}

}
