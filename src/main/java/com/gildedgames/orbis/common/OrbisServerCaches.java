package com.gildedgames.orbis.common;

import com.gildedgames.aether.api.io.NBTFunnel;
import com.gildedgames.aether.api.orbis_core.OrbisCore;
import com.gildedgames.orbis.common.data.BlueprintPalette;
import com.google.common.base.Optional;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import net.minecraft.nbt.NBTTagCompound;

import java.util.concurrent.TimeUnit;

public class OrbisServerCaches
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

	public static LoadingCache<NBTTagCompound, Optional<BlueprintPalette>> getBlueprintPalettes()
	{
		return blueprintPaletteCache;
	}

}
