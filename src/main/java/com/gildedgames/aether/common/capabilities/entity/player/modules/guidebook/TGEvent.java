package com.gildedgames.aether.common.capabilities.entity.player.modules.guidebook;

import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

public class TGEvent implements Comparable<TGEvent>
{
	private String titleKey;
	private String descriptionKey;
	private ResourceLocation image;

	public TGEvent(String titleKey, String descriptionKey, ResourceLocation image)
	{
		this.titleKey = titleKey;
		this.descriptionKey = descriptionKey;
		this.image = image;
	}

	@Override
	public int compareTo(TGEvent o)
	{
		return this.titleKey.compareTo(o.titleKey);
	}

	public String getTitleKey()
	{
		return this.titleKey;
	}

	public String getDescriptionKey()
	{
		return this.descriptionKey;
	}

	public String getImagePath()
	{
		return this.image.getPath();
	}

	public String getFormattedTitle()
	{
		return I18n.format(this.titleKey);
	}

	public String getFormattedDescription()
	{
		return I18n.format(this.descriptionKey);
	}

	public ResourceLocation getImageResourceLocation()
	{
		return this.image;
	}

}
