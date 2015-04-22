package com.gildedgames.aether.client;

import com.gildedgames.aether.client.models.ModelsAether;
import com.gildedgames.aether.server.ServerProxy;
import net.minecraft.client.Minecraft;

public class ClientProxy extends ServerProxy
{
	private ModelsAether models;

	@Override
	public void preInit()
	{
		this.models = new ModelsAether(Minecraft.getMinecraft());

		this.models.prepareModels();
	}

	@Override
	public void init()
	{
		this.models.registerModels();
	}

	@Override
	public ModelsAether getModels()
	{
		return this.models;
	}
}
