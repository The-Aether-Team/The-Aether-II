package com.gildedgames.orbis.client.renderers.tiles;

import com.gildedgames.aether.api.orbis_core.OrbisCore;
import com.gildedgames.aether.api.orbis_core.block.BlockDataContainer;
import com.gildedgames.aether.api.orbis_core.data.BlueprintData;
import com.gildedgames.aether.common.ReflectionAether;
import com.gildedgames.orbis.client.renderers.AirSelectionRenderer;
import com.gildedgames.orbis.client.renderers.RenderBlueprint;
import com.gildedgames.orbis.common.items.ItemBlockDataContainer;
import com.gildedgames.orbis.common.tiles.TileEntityBlockDataContainer;
import com.gildedgames.orbis.common.util.OpenGLHelper;
import com.gildedgames.orbis.common.world_objects.Blueprint;
import com.google.common.base.Optional;
import com.google.common.cache.*;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class TileEntityBlockDataContainerRenderer extends TileEntitySpecialRenderer<TileEntityBlockDataContainer>
		implements RemovalListener<ItemStack, Optional<RenderBlueprint>>
{

	private static final Field ALL_INVENTORIES_FIELD = ReflectionAether.getField(InventoryPlayer.class, "allInventories", "field_184440_g");

	private static final Minecraft mc = Minecraft.getMinecraft();

	public final BakedModel baked = new BakedModel();

	private final LoadingCache<ItemStack, Optional<RenderBlueprint>> blueprintCache = CacheBuilder.newBuilder()
			.maximumSize(1000)
			.expireAfterWrite(10, TimeUnit.MINUTES)
			.removalListener(this)
			.build(
					new CacheLoader<ItemStack, Optional<RenderBlueprint>>()
					{
						@Override
						public Optional<RenderBlueprint> load(final ItemStack key)
						{
							final BlockDataContainer container = ItemBlockDataContainer.getDataContainer(key);

							if (container == null)
							{
								return Optional.absent();
							}

							final RenderBlueprint blueprint = new RenderBlueprint(new Blueprint(mc.world, BlockPos.ORIGIN, new BlueprintData(container)),
									mc.world);
							blueprint.useCamera = false;

							return Optional.of(blueprint);
						}
					});

	private RenderItem itemRenderer;

	private ItemStack stack;

	public TileEntityBlockDataContainerRenderer()
	{

	}

	public boolean hasItemStack(final ItemStack itemStackIn)
	{
		if (mc.player.inventory.getItemStack() == itemStackIn)
		{
			return true;
		}

		final List<NonNullList<ItemStack>> lists = (List<NonNullList<ItemStack>>) ReflectionAether.getValue(ALL_INVENTORIES_FIELD, mc.player.inventory);

		label19:

		for (final List<ItemStack> list : lists)
		{
			final Iterator iterator = list.iterator();

			while (true)
			{
				if (!iterator.hasNext())
				{
					continue label19;
				}

				final ItemStack itemstack = (ItemStack) iterator.next();

				if (!itemstack.isEmpty() && ItemStack.areItemStacksEqual(itemstack, itemStackIn))
				{
					break;
				}
			}

			return true;
		}

		return false;
	}

	@Override
	public void renderTileEntityAt(final TileEntityBlockDataContainer te, final double x, final double y, final double z, final float partialTicks,
			final int destroyStage)
	{
		try
		{
			if (this.stack == null)
			{
				return;
			}

			final RenderBlueprint blueprint = this.blueprintCache.get(this.stack).orNull();

			if (blueprint == null)
			{
				return;
			}

			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

			if (this.itemRenderer == null)
			{
				this.itemRenderer = Minecraft.getMinecraft().getRenderItem();
			}

			final boolean inGuiContext = OpenGLHelper.isInGuiContext();

			if (inGuiContext)
			{
				blueprint.transformForWorld();
			}
			else
			{
				blueprint.transformForGui();
			}

			this.setLightmapDisabled(true);

			blueprint.doGlobalRendering(mc.world, AirSelectionRenderer.PARTIAL_TICKS);

			if (inGuiContext)
			{
				this.setLightmapDisabled(false);
			}

			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		}
		catch (final ExecutionException e)
		{
			OrbisCore.LOGGER.error(e);
		}
	}

	@Override
	public void onRemoval(final RemovalNotification<ItemStack, Optional<RenderBlueprint>> notification)
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

	public static class DummyTile extends TileEntityBlockDataContainer
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
				TileEntityBlockDataContainerRenderer.this.stack = stack;
				return BakedModel.this;
			}
		}
	}

}
