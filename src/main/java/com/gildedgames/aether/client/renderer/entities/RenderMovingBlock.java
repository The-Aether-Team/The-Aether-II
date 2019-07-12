package com.gildedgames.aether.client.renderer.entities;

import com.gildedgames.aether.common.entities.blocks.EntityMovingBlock;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class RenderMovingBlock extends Render<EntityMovingBlock>
{
	public RenderMovingBlock(final EntityRendererManager renderManager)
	{
		super(renderManager);

		this.shadowSize = 0.5f;
	}

	@Override
	public void doRender(final EntityMovingBlock entity, final double x, final double y, final double z, final float entityYaw, final float partialTicks)
	{
		if (entity.getBlockState() != null)
		{
			this.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);

			final BlockState state = entity.getBlockState();

			final BlockPos pos = new BlockPos(0, 256, 0);

			final World world = entity.getEntityWorld();

			if (state != world.getBlockState(pos))
			{
				if (state.getRenderType() == BlockRenderType.MODEL)
				{
					RenderHelper.disableStandardItemLighting();

					GlStateManager.pushMatrix();

					GlStateManager.translatef(x, y, z);

					final float scale = 0.9f;

					GlStateManager.scalef(scale, scale, scale);

					final float f1 = entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks;
					final float f2 = entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks;

					GlStateManager.rotatef(f1, 0.0f, 0.0f, 1.0f);
					GlStateManager.rotatef(f2, 1.0f, 0.0f, 0.0f);

					GlStateManager.disableLighting();

					if (Minecraft.isAmbientOcclusionEnabled())
					{
						GlStateManager.shadeModel(7425);
					}
					else
					{
						GlStateManager.shadeModel(7424);
					}

					final Tessellator tessellator = Tessellator.getInstance();

					final BufferBuilder worldRenderer = tessellator.getBuffer();
					worldRenderer.begin(7, DefaultVertexFormats.BLOCK);
					final int i = pos.getX();
					final int j = pos.getY();
					final int k = pos.getZ();

					worldRenderer.setTranslation(((float) -i) - 0.5F, -j, ((float) -k) - 0.5F);

					final BlockRendererDispatcher blockRendererDispatcher = Minecraft.getInstance().getBlockRendererDispatcher();

					final IBakedModel model = blockRendererDispatcher.getModelForState(state);

					blockRendererDispatcher.getBlockModelRenderer().renderModel(world, model, state, pos, worldRenderer, false);

					worldRenderer.setTranslation(0.0D, 0.0D, 0.0D);

					tessellator.draw();

					RenderHelper.enableStandardItemLighting();

					GlStateManager.enableLighting();
					GlStateManager.popMatrix();

					super.doRender(entity, x, y, z, entityYaw, partialTicks);
				}
			}
		}
	}

	@Override
	protected ResourceLocation getEntityTexture(final EntityMovingBlock entity)
	{
		return TextureMap.LOCATION_BLOCKS_TEXTURE;
	}
}
