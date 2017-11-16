package com.gildedgames.orbis.client;

import com.gildedgames.aether.api.orbis_core.api.exceptions.OrbisMissingDataException;
import com.gildedgames.aether.api.orbis_core.data.BlueprintData;
import com.gildedgames.aether.api.orbis_core.data.management.IDataIdentifier;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.orbis.client.renderers.RenderBlueprint;
import com.gildedgames.orbis.common.Orbis;
import com.gildedgames.orbis.common.world_objects.Blueprint;
import com.google.common.base.Optional;
import com.google.common.cache.*;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.concurrent.TimeUnit;

public class OrbisClientCaches
{

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
