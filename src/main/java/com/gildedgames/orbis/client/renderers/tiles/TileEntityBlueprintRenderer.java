package com.gildedgames.orbis.client.renderers.tiles;

import com.gildedgames.aether.api.orbis_core.OrbisCore;
import com.gildedgames.aether.api.orbis_core.api.exceptions.OrbisMissingDataException;
import com.gildedgames.aether.api.orbis_core.api.exceptions.OrbisMissingProjectException;
import com.gildedgames.aether.api.orbis_core.data.BlueprintData;
import com.gildedgames.aether.api.orbis_core.data.management.IDataIdentifier;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.orbis.client.renderers.AirSelectionRenderer;
import com.gildedgames.orbis.client.renderers.RenderBlueprint;
import com.gildedgames.orbis.common.Orbis;
import com.gildedgames.orbis.common.items.ItemBlueprint;
import com.gildedgames.orbis.common.tiles.TileEntityBlueprint;
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

public class TileEntityBlueprintRenderer extends TileEntitySpecialRenderer<TileEntityBlueprint>
		implements RemovalListener<IDataIdentifier, Optional<RenderBlueprint>>
{

	private static final Minecraft mc = Minecraft.getMinecraft();

	public final BakedModel baked = new BakedModel();

	private final LoadingCache<IDataIdentifier, Optional<RenderBlueprint>> blueprintCache = CacheBuilder.newBuilder()
			.maximumSize(1000)
			.expireAfterWrite(10, TimeUnit.MINUTES)
			.removalListener(this)
			.build(
					new CacheLoader<IDataIdentifier, Optional<RenderBlueprint>>()
					{
						@Override
						public Optional<RenderBlueprint> load(final IDataIdentifier id)
						{
							try
							{
								final BlueprintData blueprint = Orbis.getProjectManager().findData(id);

								final RenderBlueprint render = new RenderBlueprint(new Blueprint(mc.world, BlockPos.ORIGIN, blueprint), mc.world);
								render.useCamera = false;

								return Optional.of(render);
							}
							catch (final OrbisMissingDataException | OrbisMissingProjectException e)
							{
								AetherCore.LOGGER.error(e);
							}

							return Optional.absent();
						}
					});

	private ItemStack stack;

	public TileEntityBlueprintRenderer()
	{

	}

	@Override
	public void renderTileEntityAt(final TileEntityBlueprint te, final double x, final double y, final double z, final float partialTicks,
			final int destroyStage)
	{
		try
		{
			if (this.stack == null)
			{
				return;
			}

			final IDataIdentifier id = ItemBlueprint.getBlueprintId(this.stack);

			if (id == null)
			{
				return;
			}

			final RenderBlueprint blueprint = this.blueprintCache.get(id).orNull();

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
	public void onRemoval(final RemovalNotification<IDataIdentifier, Optional<RenderBlueprint>> notification)
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

	public static class DummyTile extends TileEntityBlueprint
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
				TileEntityBlueprintRenderer.this.stack = stack;
				return BakedModel.this;
			}
		}
	}

}
