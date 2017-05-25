package com.gildedgames.aether.common.dialog.data;

import com.gildedgames.aether.api.dialog.IDialogSlide;
import com.gildedgames.aether.api.dialog.IDialogSlideRenderer;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Optional;

public class DialogSlide implements IDialogSlide
{
	@SerializedName("address")
	private String address;

	@SerializedName("renderer")
	private IDialogSlideRenderer renderer;

	@SerializedName("data")
	private List<String> slideData;

	@Override
	public String getAddress()
	{
		return this.address;
	}

	@Override
	public Optional<IDialogSlideRenderer> getRenderer()
	{
		return this.renderer != null ? Optional.of(this.renderer) : Optional.empty();
	}

	@Override
	public Optional<List<String>> getSlideData()
	{
		return this.slideData != null ? Optional.of(this.slideData) : Optional.empty();
	}
}
