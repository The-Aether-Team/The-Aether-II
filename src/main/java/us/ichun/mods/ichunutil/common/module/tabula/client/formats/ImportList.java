package us.ichun.mods.ichunutil.common.module.tabula.client.formats;

import com.gildedgames.aether.common.AetherCore;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.FilenameUtils;
import us.ichun.mods.ichunutil.common.module.tabula.client.formats.types.Importer;
import us.ichun.mods.ichunutil.common.module.tabula.common.project.ProjectInfo;
import us.ichun.mods.ichunutil.common.module.tabula.client.formats.types.ImportTabula;

import java.io.File;
import java.net.URISyntaxException;
import java.util.HashMap;

public class ImportList
{
    public static final ImportTabula tabulaImporterInstance = new ImportTabula();
    public static final HashMap<String, Importer> compatibleFormats = new HashMap<String, Importer>() {{
        put("tcn", tabulaImporterInstance);
        put("tc2", tabulaImporterInstance);
        put("tbl", tabulaImporterInstance);
    }};

    public static boolean isFileSupported(File file)
    {
        return !file.isDirectory() && compatibleFormats.containsKey(FilenameUtils.getExtension(file.getName()));
    }

	public static File getFile(ResourceLocation id)
	{
		String s = id.getResourceDomain();
		String s1 = id.getResourcePath();
		File file = null;

		try
		{
			file = new File(MinecraftServer.class.getResource("/assets/" + s + "/" + s1).toURI());
		}
		catch (URISyntaxException e)
		{
			e.printStackTrace();
		}

		return file;
	}

    public static ProjectInfo createProjectFromFile(File file)
    {
        if(compatibleFormats.containsKey(FilenameUtils.getExtension(file.getName())))
        {
            Importer importer = compatibleFormats.get(FilenameUtils.getExtension(file.getName()));
            try
            {
                ProjectInfo projectInfo = importer.createProjectInfo(file);
                projectInfo.projVersion = importer.getProjectVersion();
                projectInfo.repair();
                return projectInfo;
            }
            catch(Exception e)
            {
                StringBuilder sb = new StringBuilder();
                sb.append("Error creating Project for format ").append(FilenameUtils.getExtension(file.getName())).append(" for file ").append(file.getAbsolutePath()).append(" by importer ").append(importer);
                AetherCore.LOGGER.warn(sb.toString());
                return null;
            }
        }
        return null;
    }

    /**
     * Use this method if you want to register your importer for Tabula reading/use.
     * @param format
     * @param importer
     * @return false if importer format has been registered
     */
    public static boolean registerImporter(String format, Importer importer)
    {
        if(compatibleFormats.containsKey(format))
        {
            return false;
        }
        compatibleFormats.put(format, importer);
        return true;
    }
}
