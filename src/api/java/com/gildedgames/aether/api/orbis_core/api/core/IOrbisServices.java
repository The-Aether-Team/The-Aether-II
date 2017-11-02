package com.gildedgames.aether.api.orbis_core.api.core;

import com.gildedgames.aether.api.orbis_core.api.registry.IOrbisDefinitionRegistry;
import com.gildedgames.aether.api.orbis_core.data.management.IProject;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public interface IOrbisServices
{

	/**
	 * Searches for the definition registry linked with the
	 * provided registry id. If it cannot find it, it will
	 * return null.
	 * @param registryId The unique registry id associated
	 *                   with the definition registry you're
	 *                   attempting to find.
	 */
	@Nullable
	IOrbisDefinitionRegistry findDefinitionRegistry(String registryId);

	/**
	 *
	 * @param registry The registry you're registering.
	 */
	void register(IOrbisDefinitionRegistry registry);

	/**
	 * Loads a project with the provided resource location.
	 * @param location The location of the project.
	 * @return The loaded project.
	 */
	IProject loadProject(MinecraftServer server, ResourceLocation location);

}
