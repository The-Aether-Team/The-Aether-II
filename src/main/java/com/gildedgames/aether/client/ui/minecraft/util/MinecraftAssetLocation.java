package com.gildedgames.aether.client.ui.minecraft.util;

import com.gildedgames.aether.client.ui.data.AssetLocation;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.lang3.Validate;

public class MinecraftAssetLocation implements AssetLocation
{

	protected final String domain, path;

	public MinecraftAssetLocation(String path)
	{
		this("minecraft", path);
	}

	public MinecraftAssetLocation(String... paths)
	{
		this.domain = paths[0];
		this.path = paths[1];

		Validate.notNull(this.path);
	}

	public MinecraftAssetLocation(ResourceLocation location)
	{
		this.domain = location.getResourceDomain();
		this.path = location.getResourcePath();
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		else if (obj instanceof ResourceLocation)
		{
			ResourceLocation resourcelocation = (ResourceLocation) obj;

			return this.domain.equals(resourcelocation.getResourceDomain()) && this.path.equals(resourcelocation.getResourcePath());
		}
		else if (obj instanceof AssetLocation)
		{
			AssetLocation asset = (AssetLocation) obj;

			return this.domain.equals(asset.getDomain()) && this.path.equals(asset.getPath());
		}

		return false;
	}

	@Override
	public String getDomain()
	{
		return this.domain;
	}

	@Override
	public String getPath()
	{
		return this.path;
	}

	@Override
	public byte[] getInputBytes()
	{
		return null;
	}

}
