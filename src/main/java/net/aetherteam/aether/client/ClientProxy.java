package net.aetherteam.aether.client;

import net.aetherteam.aether.CommonProxy;
import net.aetherteam.aether.client.models.ModelsAether;

public class ClientProxy extends CommonProxy
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
