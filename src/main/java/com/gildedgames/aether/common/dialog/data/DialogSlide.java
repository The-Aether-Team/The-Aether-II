package com.gildedgames.aether.common.dialog.data;

import com.gildedgames.aether.api.dialog.IDialogSlide;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class DialogSlide implements IDialogSlide
{
	@SerializedName("render")
	private String render;

	@SerializedName("data")
	private Map<String, String> slideData;

	@Override
	public Optional<String> getRenderer()
	{
		return this.render != null ? Optional.of(this.render) : Optional.empty();
	}

	@Override
	public Optional<Map<String, String>> getSlideData()
	{
		return this.slideData != null ? Optional.of(this.slideData) : Optional.empty();
	}
}
