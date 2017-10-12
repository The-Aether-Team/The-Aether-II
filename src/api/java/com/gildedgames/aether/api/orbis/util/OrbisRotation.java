package com.gildedgames.aether.api.orbis.util;

import net.minecraft.nbt.NBTTagCompound;

public enum OrbisRotation
{

	NORTH(), EAST(), SOUTH(), WEST();

	public static OrbisRotation getRotation(final int index)
	{
		return values()[index];
	}

	public static OrbisRotation neutral()
	{
		return NORTH;
	}

	public boolean getGoClockwise(final OrbisRotation rotation)
	{
		final int rotationAmount = this.indexDifference(rotation);
		final boolean clockwise = rotationAmount > 0;

		if (rotationAmount == 3)
		{
			return false;
		}
		else if (rotationAmount == -3)
		{
			return true;
		}
		return clockwise;
	}

	public boolean isOddDiffWithNeutral()
	{
		return Math.abs(this.getRotationAmount(neutral())) == 1;
	}

	public int getRotationAmount(final OrbisRotation rotation)
	{
		final int rotationAmount = this.indexDifference(rotation);

		if (rotationAmount == 3 || rotationAmount == -3)
		{
			return 1;
		}
		return rotationAmount;
	}

	private int indexDifference(final OrbisRotation rotation)
	{
		return this.ordinal() - rotation.ordinal();
	}

	public OrbisRotation addRotation(final OrbisRotation rotation)
	{
		int ordinal = this.ordinal() + rotation.ordinal();
		ordinal %= 4;
		return OrbisRotation.values()[ordinal];
	}

	public OrbisRotation getNextRotation(final boolean clockwise)
	{
		int newIndex = this.ordinal() + (clockwise ? 1 : -1);
		if (newIndex < 0)
		{
			newIndex = values().length - 1;
		}
		else if (newIndex == values().length)
		{
			newIndex = 0;
		}
		return values()[newIndex];
	}

	public OrbisRotation getNextRotation(final boolean clockwise, final int rotationAmount)
	{
		OrbisRotation rotation = null;

		for (int count = 0; count < rotationAmount; count++)
		{
			rotation = this.getNextRotation(clockwise);
		}

		return rotation;
	}

	public void set(final String key, final NBTTagCompound output)
	{
		output.setString(key, this.name());
	}

	public OrbisRotation get(final String key, final NBTTagCompound input)
	{
		return OrbisRotation.valueOf(input.getString(key));
	}

}
