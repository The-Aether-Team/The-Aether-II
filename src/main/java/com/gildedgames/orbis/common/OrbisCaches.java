package com.gildedgames.orbis.common;

import com.gildedgames.aether.api.io.NBTFunnel;
import com.gildedgames.aether.api.orbis_core.OrbisCore;
import com.gildedgames.aether.api.orbis_core.api.exceptions.OrbisMissingDataException;
import com.gildedgames.aether.api.orbis_core.data.BlueprintData;
import com.gildedgames.aether.api.orbis_core.data.management.IDataIdentifier;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.orbis.client.renderers.RenderBlueprint;
import com.gildedgames.orbis.common.data.BlueprintPalette;
import com.gildedgames.orbis.common.world_objects.Blueprint;
import com.google.common.base.Optional;
import com.google.common.cache.*;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.concurrent.TimeUnit;

public class OrbisCaches
{

	private static final LoadingCache<NBTTagCompound, Optional<BlueprintPalette>> blueprintPaletteCache = CacheBuilder.newBuilder()
			.maximumSize(1000)
			.expireAfterWrite(10, TimeUnit.MINUTES)
			.build(
					new CacheLoader<NBTTagCompound, Optional<BlueprintPalette>>()
					{
						@Override
						public Optional<BlueprintPalette> load(final NBTTagCompound tag)
						{
							final NBTFunnel funnel = OrbisCore.io().createFunnel(tag);

							final BlueprintPalette palette = funnel.get("palette");

							return Optional.of(palette);
						}
					});

	@SideOnly(Side.CLIENT)
	private static final LoadingCache<IDataIdentifier, Optional<RenderBlueprint>> blueprintRenderCache = CacheBuilder.newBuilder()
			.maximumSize(1000)
			.expireAfterWrite(10, TimeUnit.MINUTES)
			.removalListener(new BlueprintRenderCacheRL())
			.build(
					new CacheLoader<IDataIdentifier, Optional<RenderBlueprint>>()
					{
						@Override
						public Optional<RenderBlueprint> load(final IDataIdentifier id)
						{
							try
							{
								final BlueprintData data = Orbis.getProjectManager().findData(id);

								final RenderBlueprint blueprint = new RenderBlueprint(new Blueprint(Minecraft.getMinecraft().world, BlockPos.ORIGIN, data),
										Minecraft.getMinecraft().world);
								blueprint.useCamera = false;

								return Optional.of(blueprint);
							}
							catch (final OrbisMissingDataException e)
							{
								AetherCore.LOGGER.error("Missing in blueprint render cache: " + e);
							}

							return Optional.absent();
						}
					});

	public static LoadingCache<NBTTagCompound, Optional<BlueprintPalette>> getBlueprintPalettes()
	{
		return blueprintPaletteCache;
	}

	@SideOnly(Side.CLIENT)
	public static LoadingCache<IDataIdentifier, Optional<RenderBlueprint>> getBlueprintRenders()
	{
		return blueprintRenderCache;
	}

	private static class BlueprintRenderCacheRL implements RemovalListener<IDataIdentifier, Optional<RenderBlueprint>>
	{

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
	}

}
