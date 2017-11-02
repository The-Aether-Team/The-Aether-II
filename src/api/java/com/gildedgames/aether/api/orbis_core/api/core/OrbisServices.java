package com.gildedgames.aether.api.orbis_core.api.core;

import com.gildedgames.aether.api.io.NBTFunnel;
import com.gildedgames.aether.api.orbis_core.OrbisCore;
import com.gildedgames.aether.api.orbis_core.api.registry.IOrbisDefinitionRegistry;
import com.gildedgames.aether.api.orbis_core.data.management.IProject;
import com.google.common.collect.Maps;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.IOUtils;

import javax.annotation.Nullable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Map;

public class OrbisServices implements IOrbisServices
{
	private final Map<String, IOrbisDefinitionRegistry> idToRegistry = Maps.newHashMap();

	private final String baseFolder = "orbis";

	private final Map<String, IProject> loadedProjects = Maps.newHashMap();

	@Nullable
	@Override
	public IOrbisDefinitionRegistry findDefinitionRegistry(final String registryId)
	{
		final IOrbisDefinitionRegistry registry = this.idToRegistry.get(registryId);

		return registry;
	}

	@Override
	public void register(final IOrbisDefinitionRegistry registry)
	{
		if (registry == null)
		{
			throw new RuntimeException("Registry provided is null! Aborting.");
		}

		this.idToRegistry.put(registry.getRegistryId(), registry);
	}

	@Override
	@Nullable
	public IProject loadProject(final MinecraftServer server, final ResourceLocation location)
	{
		return this.get(server, location);
	}

	@Nullable
	private IProject get(@Nullable final MinecraftServer server, final ResourceLocation templatePath)
	{
		final String s = templatePath.getResourcePath();

		if (this.loadedProjects.containsKey(s))
		{
			return this.loadedProjects.get(s);
		}
		else
		{
			if (server == null)
			{
				this.readProjectFromJar(templatePath);
			}
			else
			{
				this.readProject(templatePath);
			}

			return this.loadedProjects.getOrDefault(s, null);
		}
	}

	/**
	 * This reads a structure template from the given location and stores it.
	 * This first attempts get the template from an external folder.
	 * If it isn't there then it attempts to take it from the minecraft jar.
	 */
	private boolean readProject(final ResourceLocation server)
	{
		final String s = server.getResourcePath();
		final File file1 = new File(this.baseFolder, s + "/.project");

		if (!file1.exists())
		{
			return this.readProjectFromJar(server);
		}
		else
		{
			InputStream inputstream = null;
			boolean flag;

			try
			{
				inputstream = new FileInputStream(file1);
				this.readProjectFromStream(s, inputstream, new File(this.baseFolder, s + "/").toURI());
				return true;
			}
			catch (final Throwable var10)
			{
				flag = false;
			}
			finally
			{
				IOUtils.closeQuietly(inputstream);
			}

			return flag;
		}
	}

	/**
	 * reads a template from the minecraft jar
	 */
	private boolean readProjectFromJar(final ResourceLocation id)
	{
		final String s = id.getResourceDomain();
		final String s1 = id.getResourcePath();
		InputStream inputstream = null;
		boolean flag;

		try
		{
			inputstream = MinecraftServer.class.getResourceAsStream("/assets/" + s + "/orbis/" + s1 + "/.project");
			this.readProjectFromStream(s1, inputstream, URI.create("/assets/" + s + "/orbis/" + s1 + "/"));
			return true;
		}
		catch (final IOException var10)
		{
			flag = false;
			OrbisCore.LOGGER.error(var10);
		}
		finally
		{
			IOUtils.closeQuietly(inputstream);
		}

		return flag;
	}

	/**
	 * reads a template from an inputstream
	 */
	private void readProjectFromStream(final String id, final InputStream stream, final URI location) throws IOException
	{
		final NBTTagCompound tag = CompressedStreamTools.readCompressed(stream);

		final NBTFunnel funnel = OrbisCore.io().createFunnel(tag);

		final IProject project = funnel.get("project");

		project.setJarLocation(location);
		project.loadAndCacheData();

		this.loadedProjects.put(id, project);
	}
}
