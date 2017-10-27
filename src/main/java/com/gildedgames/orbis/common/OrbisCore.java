package com.gildedgames.orbis.common;

import com.gildedgames.aether.api.orbis.management.IProjectManager;
import com.gildedgames.orbis.common.data.management.OrbisProjectManager;
import com.gildedgames.orbis.common.data.management.ProjectIdentifier;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.event.FMLServerStartedEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;

import java.io.File;

public class OrbisCore
{

	private static final GameRegistrar GAME_REGISTRAR = new GameRegistrar();

	private static IProjectManager projectManager;

	public static IProjectManager getProjectManager()
	{
		return projectManager;
	}

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

	public static void onServerStopping(final FMLServerStoppingEvent event)
	{
		projectManager.flushProjects();
	}

	public static void onServerStarted(final FMLServerStartedEvent event)
	{
		projectManager = new OrbisProjectManager(new File(DimensionManager.getCurrentSaveRootDirectory(), "/orbis/projects/"));

		projectManager.createProject("test_project", new ProjectIdentifier("test", "kingbdogz"));
	}
}
