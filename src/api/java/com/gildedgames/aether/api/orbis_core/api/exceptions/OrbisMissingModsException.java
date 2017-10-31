package com.gildedgames.aether.api.orbis_core.api.exceptions;

import java.util.Set;

public class OrbisMissingModsException extends RuntimeException
{
	private static final long serialVersionUID = -7047767026314598960L;

	public final Set<String> missingMods;

	public final String whoMissesIt;

	public OrbisMissingModsException(final Set<String> missingMods, final String whoMissesIt)
	{
		super(whoMissesIt + " misses mods " + missingMods.toString() + " while trying to load an Orbis file.");
		this.missingMods = missingMods;
		this.whoMissesIt = whoMissesIt;
	}

	public OrbisMissingModsException(final String message, final Set<String> missingMods, final String whoMissesIt)
	{
		super(whoMissesIt + " misses mods " + missingMods.toString() + " while trying to load an Orbis file." + message);
		this.missingMods = missingMods;
		this.whoMissesIt = whoMissesIt;
	}
}
