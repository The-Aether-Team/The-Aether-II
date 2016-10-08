package us.ichun.mods.ichunutil.common.module.tabula.client.formats;

import com.gildedgames.aether.common.AetherCore;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.FilenameUtils;
import us.ichun.mods.ichunutil.common.module.tabula.client.formats.types.ImportTabula;
import us.ichun.mods.ichunutil.common.module.tabula.client.formats.types.Importer;
import us.ichun.mods.ichunutil.common.module.tabula.common.project.ProjectInfo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

public class ImportList
{
	public static final ImportTabula tabulaImporterInstance = new ImportTabula();

	public static final HashMap<String, Importer> compatibleFormats = new HashMap<String, Importer>()
	{{
		this.put("tcn", tabulaImporterInstance);
		this.put("tc2", tabulaImporterInstance);
		this.put("tbl", tabulaImporterInstance);
	}};

	public static boolean isFileSupported(File file)
	{
		return !file.isDirectory() && compatibleFormats.containsKey(FilenameUtils.getExtension(file.getName()));
	}

	public static ProjectInfo createProjectFromFile(File file)
	{
		try (FileInputStream stream = new FileInputStream(file))
		{
			load(stream, file.getName());
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		return null;
	}

	public static ProjectInfo createProjectFromResource(ResourceLocation id)
	{
		try (InputStream stream = createStream(id))
		{
			return load(stream, id.getResourcePath());
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		return null;
	}

	public static ProjectInfo load(InputStream stream, String file)
	{
		if (compatibleFormats.containsKey(FilenameUtils.getExtension(file)))
		{
			Importer importer = compatibleFormats.get(FilenameUtils.getExtension(file));

			try
			{
				ProjectInfo projectInfo = importer.createProjectInfo(file, stream);
				projectInfo.projVersion = importer.getProjectVersion();
				projectInfo.repair();

				return projectInfo;
			}
			catch (Exception e)
			{
				AetherCore.LOGGER.warn("Error creating Project for format " + FilenameUtils.getExtension(file) + " for file " + file + " by importer " + importer);

				return null;
			}
		}

		return null;
	}

	private static InputStream createStream(ResourceLocation id)
	{
		String domain = id.getResourceDomain();
		String path = id.getResourcePath();

		String file = "/assets/" + domain + "/" + path;

		return MinecraftServer.class.getResourceAsStream(file);
	}


	/**
	 * Use this method if you want to register your importer for Tabula reading/use.
	 * @param format
	 * @param importer
	 * @return false if importer format has been registered
	 */
	public static boolean registerImporter(String format, Importer importer)
	{
		if (compatibleFormats.containsKey(format))
		{
			return false;
		}
		compatibleFormats.put(format, importer);
		return true;
	}
}
