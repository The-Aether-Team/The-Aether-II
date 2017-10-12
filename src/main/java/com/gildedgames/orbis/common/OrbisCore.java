package com.gildedgames.orbis.common;

import net.minecraftforge.fml.common.FMLCommonHandler;

public class OrbisCore
{

	private static final GameRegistrar GAME_REGISTRAR = new GameRegistrar();

	public static boolean isClient()
	{
		return FMLCommonHandler.instance().getSide().isClient();
	}

	public static boolean isServer()
	{
		return FMLCommonHandler.instance().getSide().isServer();
	}

	public static GameRegistrar getRegistrar()
	{
		return OrbisCore.GAME_REGISTRAR;
	}

}
