package com.gildedgames.aether.client.renderer.entities.living;

import com.gildedgames.aether.client.models.entities.living.ModelTempestInsect;
import com.gildedgames.aether.client.models.entities.living.ModelTempestShell;
import com.gildedgames.aether.client.renderer.entities.living.layers.LayerGlowing;
import com.gildedgames.aether.client.renderer.entities.living.layers.LayerTempestShell;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.entities.monsters.EntityTempest;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

public class RenderTempest extends RenderLiving<EntityTempest>
{

	private static final ResourceLocation INSECT = AetherCore.getResource("textures/entities/tempest/tempest_insect.png");

	private static final ResourceLocation INSECT_MARKINGS = AetherCore.getResource("textures/entities/tempest/tempest_insect_glow.png");

	private static final ResourceLocation SHELL = AetherCore.getResource("textures/entities/tempest/tempest_shell.png");

	private static final ResourceLocation SHELL_MARKINGS = AetherCore.getResource("textures/entities/tempest/tempest_shell_glow.png");

	public RenderTempest(RenderManager manager)
	{
		super(manager, new ModelTempestInsect(), 1.0F);

		this.addLayer(new LayerGlowing<>(this, INSECT_MARKINGS));

		ModelTempestShell shell = new ModelTempestShell();

		this.addLayer(new LayerTempestShell(this, SHELL, shell));
		this.addLayer(new LayerGlowing<>(this, SHELL_MARKINGS, shell));
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityTempest entity)
	{
		return INSECT;
	}

}
