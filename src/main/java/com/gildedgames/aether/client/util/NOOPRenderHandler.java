package com.gildedgames.aether.client.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraftforge.client.IRenderHandler;

public class NOOPRenderHandler extends IRenderHandler
{
	@Override
	public void render(float partialTicks, WorldClient world, Minecraft mc)
	{
		// NO-OP
	}
}
