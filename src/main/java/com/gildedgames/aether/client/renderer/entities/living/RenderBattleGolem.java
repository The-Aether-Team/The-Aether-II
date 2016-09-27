package com.gildedgames.aether.client.renderer.entities.living;

import com.gildedgames.aether.client.models.entities.living.ModelBattleGolem;
import com.gildedgames.aether.client.renderer.entities.living.layers.LayerBombs;
import com.gildedgames.aether.common.AetherCore;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderBattleGolem extends RenderLiving<EntityLiving>
{

	private static final ResourceLocation TEXTURE = new ResourceLocation(AetherCore.MOD_ID, "textures/entities/battle_golem/battle_golem.png");

	public RenderBattleGolem(RenderManager renderManager)
	{
		super(renderManager, new ModelBattleGolem(), 1f);

		this.addLayer(new LayerBombs(this));
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityLiving entity)
	{
		return TEXTURE;
	}

	@Override
	protected void preRenderCallback(EntityLiving entityliving, float f)
	{
		GL11.glScalef(0.75F, 0.75F, 0.75F);
	}

}
