package com.gildedgames.orbis.client.util.rect;

public class BuildIntoRectHolder extends RectBuilder
{

	protected ModDim2D modDim;

	public BuildIntoRectHolder(final ModDim2D modDim)
	{
		this.modDim = modDim;
	}

	@Override
	public Rect flush()
	{
		final Rect commit = super.flush();

		this.modDim.set(commit);

		return this.modDim;
	}

}
