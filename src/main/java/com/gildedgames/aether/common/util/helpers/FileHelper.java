package com.gildedgames.aether.common.util.helpers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileHelper
{

	public static boolean deleteDirectory(final File path)
	{
		if (path.exists())
		{
			final File[] files = path.listFiles();

			for (int i = 0; i < files.length; i++)
			{
				if (files[i].isDirectory())
				{
					deleteDirectory(files[i]);
				}
				else
				{
					files[i].delete();
				}
			}
		}

		return (path.delete());
	}

	public static void unhide(final File file) throws IOException
	{
		Files.setAttribute(Paths.get(file.getPath()), "dos:hidden", false);
	}

	public static void hide(final File file) throws IOException
	{
		Files.setAttribute(Paths.get(file.getPath()), "dos:hidden", true);
	}

}
