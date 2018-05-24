package com.gildedgames.aether.client.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraftforge.client.IRenderHandler;

import java.util.List;

public class RenderHandlerMulti extends IRenderHandler
{
	private final List<IRenderHandler> list;

	public RenderHandlerMulti(List<IRenderHandler> list)
	{
		this.list = list;
	}

	@Override
	public void render(float partialTicks, WorldClient world, Minecraft mc)
	{
		for (IRenderHandler handler : this.list)
		{
			handler.render(partialTicks, world, mc);
		}
	}
}
