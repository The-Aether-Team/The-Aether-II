package com.gildedgames.aether.client.renderer.entities;

import com.gildedgames.aether.common.entities.blocks.EntityFloatingBlock;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class RenderFloatingBlock extends Render<EntityFloatingBlock>
{
	public RenderFloatingBlock(RenderManager renderManager)
	{
		super(renderManager);

		this.shadowSize = 0.5f;
	}

	@Override
	public void doRender(EntityFloatingBlock floatingBlock, double x, double y, double z, float entityYaw, float partialTicks)
	{
		if (floatingBlock.getBlockState() != null)
		{
			this.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);

			IBlockState state = floatingBlock.getBlockState();
			Block block = state.getBlock();

			BlockPos pos = new BlockPos(floatingBlock);
			World world = floatingBlock.getEntityWorld();

			if (state != world.getBlockState(pos))
			{
				if (block.getRenderType(state) == EnumBlockRenderType.MODEL)
				{
					RenderHelper.disableStandardItemLighting();

					GlStateManager.pushMatrix();
					GlStateManager.translate(x, y, z);
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
	protected ResourceLocation getEntityTexture(EntityFloatingBlock entity)
	{
		return TextureMap.LOCATION_BLOCKS_TEXTURE;
	}
}
