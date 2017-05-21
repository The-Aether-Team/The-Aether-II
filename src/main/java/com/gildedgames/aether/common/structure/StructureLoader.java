package com.gildedgames.aether.common.structure;

import com.gildedgames.aether.api.structure.IBakedStructure;
import com.gildedgames.aether.api.structure.IStructureLoader;
import com.gildedgames.aether.common.AetherCore;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

public class StructureLoader implements IStructureLoader
{
	private static final String FILE_EXTENSION = ".aestruct";

	private static final String DIRECTORY = "aether_structures";

	private final HashMap<ResourceLocation, IBakedStructure> loadedStructures = new HashMap<>();

	@Override
	public IBakedStructure getStructure(ResourceLocation path, boolean allowCaching)
	{
		if (allowCaching)
		{
			return this.loadedStructures.computeIfAbsent(path, this::readStructure);
		}

		return this.readStructure(path);
	}

	@Override
	public void saveStructure(World world, IBakedStructure structure)
	{
		if (structure.getName().length() <= 0)
		{
			AetherCore.LOGGER.error("Tried to save structure with empty name");

			return;
		}

		Path root = Paths.get(world.getSaveHandler().getWorldDirectory().getAbsolutePath(), DIRECTORY);

		if (!Files.exists(root))
		{
			try
			{
				Files.createDirectory(root);
			}
			catch (IOException e)
			{
				AetherCore.LOGGER.error("Couldn't create structures directory", e);

				return;
			}
		}

		Path path = Paths.get(root.toString(), structure.getName() + FILE_EXTENSION);

		this.saveStructure(structure, path);
	}

	private void saveStructure(IBakedStructure structure, Path path)
	{
		NBTTagCompound compound = new NBTTagCompound();
		structure.write(compound);

		try (FileOutputStream stream = new FileOutputStream(path.toFile()))
		{
			CompressedStreamTools.writeCompressed(compound, stream);
		}
		catch (IOException e)
		{
			AetherCore.LOGGER.error("Failed to save structure to " + path, e);
		}
	}

	private IBakedStructure readStructure(ResourceLocation resource)
	{
		String domain = resource.getResourceDomain();
		String path = resource.getResourcePath();

		try (InputStream stream = MinecraftServer.class.getResourceAsStream("/assets/" + domain + "/" + DIRECTORY + "/" + path + FILE_EXTENSION))
		{
			NBTTagCompound compound = CompressedStreamTools.readCompressed(stream);

			return new BakedStructure(compound);
		}
		catch (IOException e)
		{
			AetherCore.LOGGER.error("Failed to read structure from " + resource, e);
		}

		return null;
	}
}
