package com.gildedgames.aether.client.models;

import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.IModel;

import java.util.Map;

public class SimpleModelLoader implements ICustomModelLoader
{

	private Map<Block, IModel> registeredModels = Maps.newHashMap();

	private String modID;

	public SimpleModelLoader(String modID)
	{
		this.modID = modID;
	}

	public void registerModel(Block block, IModel model)
	{
		this.registeredModels.put(block, model);
	}

	private boolean match(Block block, ResourceLocation modelLocation)
	{
		return modelLocation.getResourceDomain().equals(this.modID) && modelLocation.getResourcePath().equals(block.getRegistryName().getResourcePath());
	}

	@Override
	public void onResourceManagerReload(IResourceManager resourceManager) {}

	@Override
	public boolean accepts(ResourceLocation modelLocation)
	{
		if (!modelLocation.getResourceDomain().equals(this.modID))
		{
			return false;
		}

		for (Block block : this.registeredModels.keySet())
		{
			if (modelLocation.getResourcePath().equals(block.getRegistryName().getResourcePath()))
			{
				return true;
			}
		}

		return false;
	}

	@Override
	public IModel loadModel(ResourceLocation modelLocation) throws Exception
	{
		for (Map.Entry<Block, IModel> entry : this.registeredModels.entrySet())
		{
			Block block = entry.getKey();
			IModel model = entry.getValue();

			if (this.match(block, modelLocation))
			{
				return model;
			}
		}

		return null;
	}

}
