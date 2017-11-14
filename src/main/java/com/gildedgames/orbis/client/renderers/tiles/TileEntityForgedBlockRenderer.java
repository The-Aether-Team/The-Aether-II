package com.gildedgames.orbis.client.renderers.tiles;

import com.gildedgames.aether.api.orbis_core.OrbisCore;
import com.gildedgames.aether.api.orbis_core.block.BlockDataContainer;
import com.gildedgames.aether.api.orbis_core.block.BlockDataWithConditions;
import com.gildedgames.aether.api.orbis_core.block.BlockFilterLayer;
import com.gildedgames.aether.api.orbis_core.data.BlueprintData;
import com.gildedgames.orbis.client.renderers.AirSelectionRenderer;
import com.gildedgames.orbis.client.renderers.RenderBlueprint;
import com.gildedgames.orbis.common.items.ItemBlockPalette;
import com.gildedgames.orbis.common.tiles.TileEntityForgedBlock;
import com.gildedgames.orbis.common.util.OpenGLHelper;
import com.gildedgames.orbis.common.world_objects.Blueprint;
import com.google.common.base.Optional;
import com.google.common.cache.*;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class TileEntityForgedBlockRenderer extends TileEntitySpecialRenderer<TileEntityForgedBlock>
		implements RemovalListener<BlockFilterLayer, Optional<RenderBlueprint>>
{

	private static final Minecraft mc = Minecraft.getMinecraft();

	public final BakedModel baked = new BakedModel();

	private final LoadingCache<BlockFilterLayer, Optional<RenderBlueprint>> blueprintCache = CacheBuilder.newBuilder()
			.maximumSize(200)
			.expireAfterWrite(10, TimeUnit.MINUTES)
			.removalListener(this)
			.build(
					new CacheLoader<BlockFilterLayer, Optional<RenderBlueprint>>()
					{
						@Override
						public Optional<RenderBlueprint> load(final BlockFilterLayer layer)
						{
							int sizeX = Math.max(2, layer.getReplacementBlocks().size() / 2);

							while (layer.getReplacementBlocks().size() <= (sizeX - 1) * (sizeX - 1))
							{
								sizeX--;
							}

							final int sizeDoubled = (sizeX * 2);

							final int remainder =
									layer.getReplacementBlocks().size() > sizeDoubled && (layer.getReplacementBlocks().size() % sizeDoubled) > 0 ? 1 : 0;

							final int sizeY = sizeX + remainder;

							final BlockDataContainer container = new BlockDataContainer(sizeX, sizeY, 1);

							final int minSize = Math.min(sizeX, sizeY);

							int i = 0;

							for (final BlockDataWithConditions block : layer.getReplacementBlocks())
							{
								final int x = i % minSize;
								final int y = i / minSize;

								container.set(block, x, y, 0);

								i++;
							}

							final RenderBlueprint blueprint = new RenderBlueprint(new Blueprint(mc.world, BlockPos.ORIGIN, new BlueprintData(container)),
									mc.world);
							blueprint.useCamera = false;

							return Optional.of(blueprint);
						}
					});

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

		try
		{
			final BlockFilterLayer layer = ItemBlockPalette.getFilterLayer(this.stack);

			if (layer == null)
			{
				return;
			}

			final RenderBlueprint blueprint = this.blueprintCache.get(layer).orNull();

			if (blueprint == null)
			{
				return;
			}

			GlStateManager.pushMatrix();

			final boolean inGuiContext = OpenGLHelper.isInGuiContext();

			if (!inGuiContext)
			{
				blueprint.transformForWorld();
				this.setLightmapDisabled(true);
			}
			else
			{
				blueprint.transformForGui();
			}

			blueprint.doGlobalRendering(mc.world, AirSelectionRenderer.PARTIAL_TICKS);

			if (!inGuiContext)
			{
				this.setLightmapDisabled(false);
			}

			GlStateManager.resetColor();

			GlStateManager.popMatrix();
		}
		catch (final ExecutionException e)
		{
			OrbisCore.LOGGER.error(e);
		}
	}

	@Override
	public void onRemoval(final RemovalNotification<BlockFilterLayer, Optional<RenderBlueprint>> notification)
	{
		final Optional<RenderBlueprint> opt = notification.getValue();

		if (opt == null)
		{
			return;
		}

		final RenderBlueprint blueprint = opt.orNull();

		if (blueprint != null)
		{
			blueprint.onRemoved();
		}
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
