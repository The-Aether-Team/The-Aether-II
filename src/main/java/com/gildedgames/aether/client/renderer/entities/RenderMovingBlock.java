package com.gildedgames.aether.client.renderer.entities;

import com.gildedgames.aether.common.entities.blocks.EntityMovingBlock;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class RenderMovingBlock extends Render<EntityMovingBlock>
{
	public RenderMovingBlock(RenderManager renderManager)
	{
		super(renderManager);

		this.shadowSize = 0.5f;
	}

	@Override
	public void doRender(EntityMovingBlock entity, double x, double y, double z, float entityYaw, float partialTicks)
	{
		if (entity.getBlockState() != null)
		{
			this.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);

			IBlockState state = entity.getBlockState();
			Block block = state.getBlock();

			BlockPos pos = new BlockPos(0, 256, 0);

			World world = entity.getEntityWorld();

			if (state != world.getBlockState(pos))
			{
				if (block.getRenderType(state) == EnumBlockRenderType.MODEL)
				{
					RenderHelper.disableStandardItemLighting();

					GlStateManager.pushMatrix();

					GlStateManager.translate(x, y, z);

					float scale = 0.9f;

					GlStateManager.scale(scale, scale, scale);

					float f1 = entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks;
					float f2 = entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks;

					GlStateManager.rotate(f1, 0.0f, 0.0f, 1.0f);
					GlStateManager.rotate(f2, 1.0f, 0.0f, 0.0f);

					GlStateManager.disableLighting();

					if (Minecraft.isAmbientOcclusionEnabled())
					{
						GlStateManager.shadeModel(7425);
					}
					else
					{
						GlStateManager.shadeModel(7424);
					}

					Tessellator tessellator = Tessellator.getInstance();

					VertexBuffer worldRenderer = tessellator.getBuffer();
					worldRenderer.begin(7, DefaultVertexFormats.BLOCK);
					int i = pos.getX();
					int j = pos.getY();
					int k = pos.getZ();

					worldRenderer.setTranslation(((float) -i) - 0.5F, -j, ((float) -k) - 0.5F);

					BlockRendererDispatcher blockRendererDispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();

					IBakedModel model = blockRendererDispatcher.getModelForState(state);

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
	protected ResourceLocation getEntityTexture(EntityMovingBlock entity)
	{
		return TextureMap.LOCATION_BLOCKS_TEXTURE;
	}
}
