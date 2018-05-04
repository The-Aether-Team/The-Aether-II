package com.gildedgames.aether.api.patron;

public enum PatronTier
{
	HUMAN(100, false), ANGEL(500, false), VALKYRIE(1000, false), ARKENZUS(1500, false), GILDED(5000, true);

	private int cents;

	private boolean grantsPermanentAccess;

	PatronTier(int cents, boolean grantsPermanentAccess)
	{
		this.cents = cents;
		this.grantsPermanentAccess = grantsPermanentAccess;
	}

	public int getCents()
	{
		return this.cents;
	}

	public boolean grantsPermanentAccess()
	{
		return this.grantsPermanentAccess;
	}
}