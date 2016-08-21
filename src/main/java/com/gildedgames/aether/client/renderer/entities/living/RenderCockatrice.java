package com.gildedgames.aether.client.renderer.entities.living;

import com.gildedgames.aether.client.models.entities.living.ModelCockatrice;
import com.gildedgames.aether.client.renderer.entities.living.layers.LayerGlowing;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.entities.living.EntityAerbunny;
import com.gildedgames.aether.common.entities.living.enemies.EntityCockatrice;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderCockatrice extends RenderLiving<EntityCockatrice>
{

	private static final ResourceLocation TEXTURE = AetherCore.getResource("textures/entities/cockatrice/cockatrice.png");

	private static final ResourceLocation TEXTURE_MARKINGS = AetherCore.getResource("textures/entities/cockatrice/markings.png");

	public RenderCockatrice(RenderManager manager)
	{
		super(manager, new ModelCockatrice(), 1.0F);

		this.addLayer(new LayerGlowing<EntityCockatrice>(this, TEXTURE_MARKINGS));
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityCockatrice entity)
	{
		return TEXTURE;
	}

}
