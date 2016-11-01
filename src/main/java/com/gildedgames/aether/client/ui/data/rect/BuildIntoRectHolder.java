package com.gildedgames.aether.client.ui.data.rect;

public class BuildIntoRectHolder extends RectBuilder
{

	protected ModDim2D modDim;

	public BuildIntoRectHolder(ModDim2D modDim)
	{
		this.modDim = modDim;
	}

	@Override
	public Rect flush()
	{
		Rect commit = super.flush();

		this.modDim.set(commit);

		return this.modDim;
	}

}
