package com.gildedgames.orbis.client.renderers.tiles;

import com.gildedgames.aether.api.orbis_core.block.BlockDataWithConditions;
import com.gildedgames.aether.api.orbis_core.block.BlockFilterLayer;
import com.gildedgames.aether.common.ReflectionAether;
import com.gildedgames.orbis.common.items.ItemForgedBlock;
import com.gildedgames.orbis.common.tiles.TileEntityForgedBlock;
import com.gildedgames.orbis.common.util.OpenGLHelper;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;

public class TileEntityForgedBlockRenderer extends TileEntitySpecialRenderer<TileEntityForgedBlock>
{

	private static final Field ALL_INVENTORIES_FIELD = ReflectionAether.getField(InventoryPlayer.class, "allInventories", "field_184440_g");

	private static final Minecraft mc = Minecraft.getMinecraft();

	public final BakedModel baked = new BakedModel();

	private BlockRendererDispatcher blockRenderer;

	private ItemStack stack;

	public TileEntityForgedBlockRenderer()
	{

	}

	@Override
	public void renderTileEntityAt(final TileEntityForgedBlock te, final double x, final double y, final double z, final float partialTicks,
			final int destroyStage)
	{
		if (this.stack == null)
		{
			return;
		}

		if (this.blockRenderer == null)
		{
			this.blockRenderer = mc.getBlockRendererDispatcher();
		}

		final BlockFilterLayer layer = ItemForgedBlock.getFilterLayer(this.stack);

		if (layer != null)
		{
			GlStateManager.pushMatrix();

			final List<BlockDataWithConditions> blocks = layer.getReplacementBlocks();

			final IBlockState state = blocks.get((int) ((System.currentTimeMillis() / 1000) % blocks.size())).getBlockState();

			this.renderBlock(state);

			GlStateManager.popMatrix();
		}
	}

	private void renderBlock(final IBlockState state)
	{
		final boolean inGuiContext = OpenGLHelper.isInGuiContext();

		final BlockRendererDispatcher blockRendererDispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();

		final IBakedModel model = blockRendererDispatcher.getModelForState(state);

		final Tessellator tessellator = Tessellator.getInstance();
		final VertexBuffer buffer = tessellator.getBuffer();
		this.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);

		if (!inGuiContext)
		{
			RenderHelper.disableStandardItemLighting();
		}

		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GlStateManager.enableBlend();
		GlStateManager.disableCull();

		buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.BLOCK);

		if (!inGuiContext)
		{
			GlStateManager.translate(0.5F, 0.5F, 0.5F);

			GlStateManager.scale(0.40F, 0.40F, 0.40F);

			GlStateManager.rotate(45.0F, 0.0F, 1.0F, 0.0F);

			GlStateManager.translate(-0.5F, -0.5F, -0.5F);
		}
		else
		{
			GlStateManager.disableLighting();
			this.transformForGui();
			ItemCameraTransforms
					.applyTransformSide(model.getItemCameraTransforms().getTransform(ItemCameraTransforms.TransformType.GUI), true);
		}

		blockRendererDispatcher.getBlockModelRenderer()
				.renderModel(mc.world, model, state, BlockPos.ORIGIN, buffer, false);

		buffer.setTranslation(0, 0, 0);

		tessellator.draw();

		if (!inGuiContext)
		{
			RenderHelper.enableStandardItemLighting();
		}
		else
		{
			GlStateManager.enableLighting();
		}

		GlStateManager.resetColor();
	}

	public void transformForWorld()
	{

	}

	public void transformForGui()
	{
		GlStateManager.translate(0.5F, 0.0F, 0.0F);
	}

	public static class DummyTile extends TileEntityForgedBlock
	{
	}

	@MethodsReturnNonnullByDefault
	@ParametersAreNonnullByDefault
	public class BakedModel implements IBakedModel
	{
		@Override
		public List<BakedQuad> getQuads(@Nullable final IBlockState state, @Nullable final EnumFacing side, final long rand)
		{
			return Collections.emptyList();
		}

		@Override
		public boolean isAmbientOcclusion()
		{
			return true;
		}

		@Override
		public boolean isGui3d()
		{
			return true;
		}

		@Override
		public boolean isBuiltInRenderer()
		{
			return true;
		}

		@Override
		public TextureAtlasSprite getParticleTexture()
		{
			return Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite("minecraft:blocks/soul_sand");
		}

		@Override
		public ItemCameraTransforms getItemCameraTransforms()
		{
			return ItemCameraTransforms.DEFAULT;
		}

		@Override
		public ItemOverrideList getOverrides()
		{
			return new Overrides();
		}

		private class Overrides extends ItemOverrideList
		{
			public Overrides()
			{
				super(Collections.emptyList());
			}

			@Override
			public IBakedModel handleItemState(final IBakedModel originalModel, final ItemStack stack, final World world, final EntityLivingBase entity)
			{
				TileEntityForgedBlockRenderer.this.stack = stack;
				return BakedModel.this;
			}
		}
	}

}
