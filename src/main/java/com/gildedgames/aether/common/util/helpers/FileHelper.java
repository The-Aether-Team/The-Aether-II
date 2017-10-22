package com.gildedgames.aether.common.util.helpers;

import java.io.File;

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

}
