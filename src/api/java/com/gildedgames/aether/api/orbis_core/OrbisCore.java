package com.gildedgames.aether.api.orbis_core;

import com.gildedgames.aether.api.orbis_core.api.GameRegistrar;
import net.minecraftforge.fml.common.FMLCommonHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OrbisCore
{
	public static final Logger LOGGER = LogManager.getLogger("Orbis_CORE");

	private static final IOHelper io = new IOHelper();

	private static final GameRegistrar GAME_REGISTRAR = new GameRegistrar();

	public static boolean isClient()
	{
		return FMLCommonHandler.instance().getSide().isClient();
	}

	public static GameRegistrar getRegistrar()
	{
		return OrbisCore.GAME_REGISTRAR;
	}

	public static IOHelper io()
	{
		return OrbisCore.io;
	}
}
