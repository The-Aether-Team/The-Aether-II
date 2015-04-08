package com.gildedgames.aether.client;

import com.gildedgames.aether.client.models.ModelsAether;
import com.gildedgames.aether.server.ServerProxy;

public class ClientProxy extends ServerProxy
{
	private ModelsAether models;

	@Override
	public void init()
	{
		this.models = new ModelsAether();
	}

	@Override
	public ModelsAether getModels()
	{
		return this.models;
	}
}
