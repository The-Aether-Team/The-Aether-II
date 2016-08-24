package com.gildedgames.aether.client.renderer.entities.living;

import com.gildedgames.aether.client.models.entities.living.ModelCockatrice;
import com.gildedgames.aether.client.models.entities.living.ModelLabyrinthChestMimic;
import com.gildedgames.aether.client.models.entities.living.ModelLabyrinthChestMimicSpawning;
import com.gildedgames.aether.client.renderer.entities.living.layers.LayerGlowing;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.entities.dungeon.labyrinth.EntityChestMimic;
import com.gildedgames.aether.common.entities.living.enemies.EntityCockatrice;
import com.gildedgames.aether.common.entities.moa.EntityMoa;
import com.google.common.base.Supplier;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

public class RenderLabyrinthChestMimic extends RenderLiving<EntityChestMimic>
{

	private static final ResourceLocation TEXTURE = AetherCore.getResource("textures/entities/mimic/active.png");

	private static final ResourceLocation TEXTURE_GLOW = AetherCore.getResource("textures/entities/mimic/active_glow.png");

	private static final ResourceLocation TEXTURE_ANGRY = AetherCore.getResource("textures/entities/mimic/angry.png");

	private static final ResourceLocation TEXTURE_ANGRY_GLOW = AetherCore.getResource("textures/entities/mimic/angry_glow.png");

	private ModelBase spawnModel, activeModel;

	private GlowingLayer glowingLayer;

	public RenderLabyrinthChestMimic(RenderManager manager)
	{
		super(manager, new ModelLabyrinthChestMimic(), 1.0F);

		this.activeModel = this.mainModel;
		this.spawnModel = new ModelLabyrinthChestMimicSpawning();

		this.glowingLayer = new GlowingLayer();

		this.glowingLayer.setResourceLocation(TEXTURE_GLOW);

		this.addLayer(new LayerGlowing<>(this, this.glowingLayer));
	}

	@Override
	protected void preRenderCallback(EntityChestMimic entity, float f)
	{
		if (entity.ticksExisted < 10)
		{
			GlStateManager.translate(0.0D, -0.5D + (1.0D / (double)entity.ticksExisted), 0.0D);

			this.mainModel = this.spawnModel;
		}
		else
		{
			this.mainModel = this.activeModel;

			GlStateManager.translate(0.0D, Math.min(0.8D, 0.8D * ((entity.ticksExisted - 10) / 3.5D)), 0.0D);
		}
	}

	@Override
	public void doRender(EntityChestMimic entity, double x, double y, double z, float entityYaw, float partialTicks)
	{
		super.doRender(entity, x, y, z, entityYaw, partialTicks);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityChestMimic entity)
	{
		if (entity.isOverheating())
		{
			this.glowingLayer.setResourceLocation(TEXTURE_GLOW);
		}
		else
		{
			this.glowingLayer.setResourceLocation(TEXTURE_ANGRY_GLOW);
		}

		return entity.isOverheating() ? TEXTURE : TEXTURE_ANGRY;
	}

	private static class GlowingLayer implements Supplier<ResourceLocation>
	{

		private ResourceLocation resource;

		private void setResourceLocation(ResourceLocation resource)
		{
			this.resource = resource;
		}

		@Override
		public ResourceLocation get()
		{
			return this.resource;
		}
	}

}
