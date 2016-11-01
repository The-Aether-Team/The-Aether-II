package com.gildedgames.aether.client.ui.data.rect;

import org.apache.commons.lang3.builder.HashCodeBuilder;

public class RectModifier
{

	private RectHolder modifyWith;

	private ModifierType type;

	public RectModifier(RectHolder modifyWith, ModifierType types)
	{
		this.modifyWith = modifyWith;
		this.type = types;
	}

	public RectHolder modifyingWith()
	{
		return this.modifyWith;
	}

	public ModifierType getType()
	{
		return this.type;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (super.equals(obj))
		{
			return true;
		}

		if (!(obj instanceof RectModifier))
		{
			return false;
		}

		RectModifier modifier = (RectModifier) obj;
		return modifier.modifyWith.equals(this.modifyWith) && modifier.type.equals(this.type);
	}

	@Override
	public int hashCode()
	{
		return new HashCodeBuilder(97, 37).append(this.modifyWith).append(this.type).toHashCode();
	}

	public enum ModifierType
	{
		X, Y, POS, WIDTH, HEIGHT, AREA, SCALE, ROTATION, CENTERING, ALL
	}

}
