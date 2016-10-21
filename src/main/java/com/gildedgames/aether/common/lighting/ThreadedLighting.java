package com.gildedgames.aether.common.lighting;

import com.google.common.eventbus.EventBus;
import net.minecraftforge.fml.common.DummyModContainer;
import net.minecraftforge.fml.common.LoadController;
import net.minecraftforge.fml.common.ModMetadata;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;

public class ThreadedLighting extends DummyModContainer
{

	private static final Logger logger = LogManager.getLogger("THREADED_LIGHTING");

	public static final String VERSION = "1.8-1.0";

	public static final String MOD_ID = "threaded-lighting";

	public ThreadedLighting()
	{
		super(new ModMetadata());
		ModMetadata meta = this.getMetadata();

		meta.modId = "mod_ThreadedLighting";
		meta.name = "Threaded Lighting";
		meta.version = "1.7.10-1.0";
		meta.credits = "cafaxo";
		meta.authorList = Arrays.asList("Gilded Games");
		meta.description = "A core mod which implements a threaded environment for world lighting updates. This fixes a lot of core issues associated with lighting updates.";
		meta.url = "www.gilded-games.com/threaded-lighting/";
		meta.updateUrl = "";
		meta.screenshots = new String[0];
		meta.logoFile = "";
	}

	public static void print(String line)
	{
		/*if (UtilCore.DEBUG_MODE)
		{
			logger.info(line);
		}*/
	}

	@Override
	public boolean registerBus(EventBus bus, LoadController controller)
	{
		return true;
	}

}
