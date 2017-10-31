package com.gildedgames.orbis_core;

import com.gildedgames.orbis.common.GameRegistrar;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class OrbisCore
{

	private static final GameRegistrar GAME_REGISTRAR = new GameRegistrar();

	public static boolean isClient()
	{
		return FMLCommonHandler.instance().getSide().isClient();
	}

	public static GameRegistrar getRegistrar()
	{
		return OrbisCore.GAME_REGISTRAR;
	}

}
