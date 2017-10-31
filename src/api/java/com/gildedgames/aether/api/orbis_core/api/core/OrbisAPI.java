package com.gildedgames.aether.api.orbis_core.api.core;

/**
 * This OrbisAPI allows mod developers to integrate
 * the projects they've developed with the Orbis tool
 * into their mod designs. It's recommended you create an
 * IOrbisDefinitionRegistry and register it in this API's services.
 */
public class OrbisAPI
{
	private static IOrbisServices services;

	private OrbisAPI()
	{

	}

	public static IOrbisServices services()
	{
		if (OrbisAPI.services == null)
		{
			OrbisAPI.services = new OrbisServices();
		}

		return OrbisAPI.services;
	}
}
