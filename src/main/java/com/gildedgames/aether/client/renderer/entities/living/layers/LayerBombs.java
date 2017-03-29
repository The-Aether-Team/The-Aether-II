package com.gildedgames.aether.client.renderer.entities.living.layers;

import com.gildedgames.aether.client.models.entities.attachments.*;
import com.gildedgames.aether.client.renderer.entities.living.RenderBattleGolem;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.entities.living.dungeon.labyrinth.EntityBattleGolem;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;

public class LayerBombs implements LayerRenderer<EntityBattleGolem>
{
	private static final ResourceLocation texture = AetherCore.getResource("textures/entities/battle_golem/battle_golem.png");

	private final ModelBomb1 bomb1 = new ModelBomb1();

	private final ModelBomb2 bomb2 = new ModelBomb2();

	private final ModelBomb3 bomb3 = new ModelBomb3();

	private final ModelBomb4 bomb4 = new ModelBomb4();

	private final ModelBombLeftHand bombLeftHand = new ModelBombLeftHand();

	private final ModelBombRightHand bombRightHand = new ModelBombRightHand();

	private final RenderBattleGolem render;

	public LayerBombs(RenderBattleGolem render)
	{
		this.render = render;
	}

	@Override
	public void doRenderLayer(EntityBattleGolem golem, float p_177141_2_, float p_177141_3_, float p_177141_4_, float p_177141_5_,
			float p_177141_6_, float p_177141_7_, float p_177141_8_)
	{
		if (!golem.isInvisible())
		{
			GlStateManager.scale(1.01F, 1.01F, 1.01F);

			this.render.bindTexture(texture);

			if (golem.getBombCount() >= 1)
			{
				this.bomb1.setModelAttributes(this.render.getMainModel());
				this.bomb1.setLivingAnimations(golem, p_177141_2_, p_177141_3_, p_177141_4_);
				this.bomb1.render(golem, p_177141_2_, p_177141_3_, p_177141_5_, p_177141_6_, p_177141_7_, p_177141_8_);
			}

			if (golem.getBombCount() >= 2)
			{
				this.bomb2.setModelAttributes(this.render.getMainModel());
				this.bomb2.setLivingAnimations(golem, p_177141_2_, p_177141_3_, p_177141_4_);
				this.bomb2.render(golem, p_177141_2_, p_177141_3_, p_177141_5_, p_177141_6_, p_177141_7_, p_177141_8_);
			}

			if (golem.getBombCount() >= 3)
			{
				this.bomb3.setModelAttributes(this.render.getMainModel());
				this.bomb3.setLivingAnimations(golem, p_177141_2_, p_177141_3_, p_177141_4_);
				this.bomb3.render(golem, p_177141_2_, p_177141_3_, p_177141_5_, p_177141_6_, p_177141_7_, p_177141_8_);
			}

			if (golem.getBombCount() >= 4)
			{
				this.bomb4.setModelAttributes(this.render.getMainModel());
				this.bomb4.setLivingAnimations(golem, p_177141_2_, p_177141_3_, p_177141_4_);
				this.bomb4.render(golem, p_177141_2_, p_177141_3_, p_177141_5_, p_177141_6_, p_177141_7_, p_177141_8_);
			}

			this.bombLeftHand.setModelAttributes(this.render.getMainModel());
			this.bombLeftHand.setLivingAnimations(golem, p_177141_2_, p_177141_3_, p_177141_4_);
			this.bombLeftHand.render(golem, p_177141_2_, p_177141_3_, p_177141_5_, p_177141_6_, p_177141_7_, p_177141_8_);

			this.bombRightHand.setModelAttributes(this.render.getMainModel());
			this.bombRightHand.setLivingAnimations(golem, p_177141_2_, p_177141_3_, p_177141_4_);
			this.bombRightHand.render(golem, p_177141_2_, p_177141_3_, p_177141_5_, p_177141_6_, p_177141_7_, p_177141_8_);
		}
	}

	@Override
	public boolean shouldCombineTextures()
	{
		return true;
	}
}
