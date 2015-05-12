package com.gildedgames.aether.client;

import com.gildedgames.aether.common.CommonProxy;
import com.gildedgames.aether.client.models.ModelsAether;
import net.minecraft.client.Minecraft;

public class ClientProxy extends CommonProxy
{
	private ModelsAether models;

	@Override
	public void preInit()
	{
		this.models = new ModelsAether(Minecraft.getMinecraft());

		this.models.prepareModels();

		super.preInit();
	}

	@Override
	public void init()
	{
		this.models.registerModels();

		super.init();
	}

	@Override
	public ModelsAether getModels()
	{
		return this.models;
	}
}
