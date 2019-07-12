package com.gildedgames.aether.client.renderer.entities;

import com.gildedgames.aether.common.entities.blocks.EntityFloatingBlock;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class RenderFloatingBlock extends Render<EntityFloatingBlock>
{
	public RenderFloatingBlock(final EntityRendererManager renderManager)
	{
		super(renderManager);

		this.shadowSize = 0.5f;
	}

	@Override
	public void doRender(final EntityFloatingBlock floatingBlock, final double x, final double y, final double z, final float entityYaw,
			final float partialTicks)
	{
		if (floatingBlock.getBlockState() != null)
		{
			this.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);

			final BlockState state = floatingBlock.getBlockState();

			final BlockPos pos = new BlockPos(floatingBlock);
			final World world = floatingBlock.getEntityWorld();

			if (state != world.getBlockState(pos))
			{
				if (state.getRenderType() == BlockRenderType.MODEL)
				{
					RenderHelper.disableStandardItemLighting();

					GlStateManager.pushMatrix();

					GlStateManager.translatef(x, y, z);
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

					blockRendererDispatcher.getBlockModelRenderer()
							.renderModel(world, model, state, pos, worldRenderer, false);

					worldRenderer.setTranslation(0.0D, 0.0D, 0.0D);

					tessellator.draw();

					RenderHelper.enableStandardItemLighting();

					GlStateManager.enableLighting();
					GlStateManager.popMatrix();

					super.doRender(floatingBlock, x, y, z, entityYaw, partialTicks);
				}
			}
		}
	}

	@Override
	protected ResourceLocation getEntityTexture(final EntityFloatingBlock entity)
	{
		return TextureMap.LOCATION_BLOCKS_TEXTURE;
	}
}
